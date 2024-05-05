package org.example;

import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.AgentController;

public class RestaurantContainer {
    public static void main(String[] args) {
        try {
            Runtime runtime = Runtime.instance();
            ProfileImpl profileImpl = new ProfileImpl(false);
            AgentContainer restaurantContainer = runtime.createAgentContainer(profileImpl);

            // Création et démarrage de 4 agents restaurants
            for (int i = 0; i < 4; i++) {
                AgentController agentController = restaurantContainer.createNewAgent("RestaurantAgent" + i, "org.example.RestaurantAgent", null);
                agentController.start();
            }
            restaurantContainer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
