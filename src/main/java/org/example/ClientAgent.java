package org.example;

import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class ClientAgent extends Agent {
    protected void setup() {
        addBehaviour(new ReserveRestaurantBehaviour());
    }

    private class ReserveRestaurantBehaviour extends OneShotBehaviour {
        public void action() {
            // Recherche des agents restaurants
            DFAgentDescription template = new DFAgentDescription();
            ServiceDescription sd = new ServiceDescription();
            sd.setType("restaurant");
            template.addServices(sd);
            try {
                DFAgentDescription[] result = DFService.search(myAgent, template);
                if (result.length > 0) {
                    AID restaurant = result[0].getName();
                    // Envoyer une demande de réservation au premier restaurant trouvé
                    ACLMessage request = new ACLMessage(ACLMessage.REQUEST);
                    request.addReceiver(restaurant);
                    request.setContent("Reservation request");
                    myAgent.send(request);
                    // Attendre la réponse du restaurant
                    ACLMessage reply = myAgent.blockingReceive();
                    if (reply != null && reply.getPerformative() == ACLMessage.INFORM) {
                        System.out.println("Reservation confirmed: " + reply.getContent());
                    } else {
                        System.out.println("Reservation failed: " + reply.getContent());
                    }
                } else {
                    System.out.println("No restaurants found.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
