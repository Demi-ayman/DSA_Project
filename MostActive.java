package com.example;

import java.util.*;

public class MostActive {

    private XMLParser.Graph graph;

    // Constructor accepts a Graph object
    public MostActive(XMLParser.Graph graph) {
        this.graph = graph;
    }

    // Finds the most active user based on the number of followers they follow
    public int findMostActiveUser() {
        Map<Integer, Integer> activityMap = new HashMap<>();

        // Traverse through the users in the graph
        for (XMLParser.User user : graph.getUsers()) {
            int userId = user.getId();
            int outgoingConnections = user.getFollowers().size(); // Followers they are following
            activityMap.put(userId, outgoingConnections);
        }

        // Determine the user with the highest number of outgoing connections
        int mostActiveUser = -1;
        int maxConnections = 0;

        for (Map.Entry<Integer, Integer> entry : activityMap.entrySet()) {
            int userId = entry.getKey();
            int connections = entry.getValue();

            if (connections > maxConnections) {
                maxConnections = connections;
                mostActiveUser = userId;
            }
        }

        return mostActiveUser;
    }

    public static void main(String[] args) {
        if (args.length < 3 || !args[0].equals("most_active") || !args[1].equals("-i")) {
            System.out.println("Usage: xml_editor most_influencer -i input_file.xml");
            return;
        }

        String inputFile = args[2];

        try {
            // Initialize the XMLParser's graph
            XMLParser.Graph graph = XMLParser.getGraph();

            // Parse the XML file and populate the graph
            XMLParser.parseXML(inputFile, graph);

            // Create the MostActive instance
            MostActive mostActive = new MostActive(graph);

            // Find and display the most active user
            int mostActiveUserId = mostActive.findMostActiveUser();
            System.out.println("The most active user ID is: " + mostActiveUserId);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
