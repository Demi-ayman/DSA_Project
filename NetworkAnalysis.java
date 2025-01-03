package com.example;

import java.util.*;

public class NetworkAnalysis {

    public static void analyzeNetwork(Map<Integer, List<Integer>> adjacencyList) {
        // To store incoming connections for each user
        Map<Integer, Integer> incomingConnections = new HashMap<>();

        // Initialize incomingConnections with 0 for each user
        for (Integer user : adjacencyList.keySet()) {
            incomingConnections.put(user, 0);
        }

        // Count incoming connections for each user (users who follow)
        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            List<Integer> followers = entry.getValue();
            for (Integer follower : followers) {
                incomingConnections.put(follower, incomingConnections.getOrDefault(follower, 0) + 1);
            }
        }

//        // Debug: Print incoming connections
//        System.out.println("Incoming connections: " + incomingConnections);

        // Determine most active users (highest outgoing connections)
        List<Integer> mostActiveUsers = new ArrayList<>();
        int maxOutgoingConnections = 0; // Start with 0 since outgoing connections can't be negative

        for (Map.Entry<Integer, List<Integer>> entry : adjacencyList.entrySet()) {
            int user = entry.getKey();
            int outgoingConnections = entry.getValue().size();

            if (outgoingConnections > maxOutgoingConnections) {
                maxOutgoingConnections = outgoingConnections;
                mostActiveUsers.clear();
                mostActiveUsers.add(user);
            } else if (outgoingConnections == maxOutgoingConnections && outgoingConnections > 0) {
                mostActiveUsers.add(user);
            }
        }

        // Debug: Print most active users
        System.out.println("Most Active User IDs: " + (mostActiveUsers.isEmpty() ? "None" : mostActiveUsers));

        // Determine most influential users (highest incoming connections)
        List<Integer> mostInfluentialUsers = new ArrayList<>();
        int maxIncomingConnections = 0; // Start with 0 since incoming connections can't be negative

        for (Map.Entry<Integer, Integer> entry : incomingConnections.entrySet()) {
            int user = entry.getKey();
            int incomingConnectionsCount = entry.getValue();

            if (incomingConnectionsCount > maxIncomingConnections) {
                maxIncomingConnections = incomingConnectionsCount;
                mostInfluentialUsers.clear();
                mostInfluentialUsers.add(user);
            } else if (incomingConnectionsCount == maxIncomingConnections && incomingConnectionsCount > 0) {
                mostInfluentialUsers.add(user);
            }
        }

        // Debug: Print most influential users
        System.out.println("Most Influential User IDs: " + (mostInfluentialUsers.isEmpty() ? "None" : mostInfluentialUsers));
    }

    public static void main(String[] args) {
        // Example adjacency list (Test Case)
        Map<Integer, List<Integer>> adjacencyList = new HashMap<>();
        adjacencyList.put(1, Arrays.asList(2, 3));
        adjacencyList.put(2, Arrays.asList(3, 4));
        adjacencyList.put(3, Arrays.asList(4, 5));
        adjacencyList.put(4, Arrays.asList(1, 2));
        adjacencyList.put(5, Arrays.asList(1, 2, 3));

        // Call the analysis function
        analyzeNetwork(adjacencyList);
    }
}