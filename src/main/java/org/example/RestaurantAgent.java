package org.example;

import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import java.util.Random;

public class RestaurantAgent extends Agent {
    private int capacity;
    private double saturationProbability;

    protected void setup() {
        // Initialisation de la capacité et de la probabilité de saturation
        Random rand = new Random();
        capacity = rand.nextInt(9) + 1; // Capacité aléatoire entre 1 et 9
        saturationProbability = rand.nextDouble(); // Probabilité aléatoire entre 0 et 1

        // Enregistrement du service de restaurant
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("restaurant");
        sd.setName(getLocalName());
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        } catch (FIPAException fe) {
            fe.printStackTrace();
        }

        // Comportement pour gérer les demandes de réservation
        addBehaviour(new HandleReservationRequests());
    }

    private class HandleReservationRequests extends CyclicBehaviour {
        public void action() {
            ACLMessage msg = receive();
            if (msg != null) {
                // Traitement de la demande de réservation
                ACLMessage reply = msg.createReply();
                if (capacity > 0 && Math.random() > saturationProbability) {
                    reply.setPerformative(ACLMessage.INFORM);
                    reply.setContent("Reservation confirmed. Capacity: " + capacity);
                    capacity--; // Réduire la capacité après la réservation
                } else {
                    reply.setPerformative(ACLMessage.FAILURE);
                    reply.setContent("Reservation failed. Restaurant is full or saturated.");
                }
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}