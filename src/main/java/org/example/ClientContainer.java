package org.example;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.util.ExtendedProperties;
import jade.util.leap.Properties;
import jade.wrapper.AgentController;

public class ClientContainer {
    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            Profile profile = new ProfileImpl(false);
            AgentContainer clientContainer = runtime.createAgentContainer(profile);
            for (int i = 0; i < 10; i++) {
                AgentController agentController = clientContainer.createNewAgent("ClientAgent" + i, "org.example.ClientAgent", null);
                agentController.start();
            }
            // Ajoutez ici la création et le démarrage des agents clients
            clientContainer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
