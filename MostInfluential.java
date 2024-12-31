package com.example;

public class MostInfluential {

    private XMLParser.Graph graph; // Graph containing users and their followers

    // Constructor that accepts the graph
    public MostInfluential(XMLParser.Graph graph) {
        this.graph = graph;
    }

    // Method to find the most influential user based on the number of followers
    public int findMostInfluentialUser() {
        int mostInfluentialUserId = -1;
        int maxFollowersCount = -1;

        // Iterate over all users in the graph
        for (XMLParser.User user : graph.getUsers()) {
            int followerCount = user.getFollowers().size(); // Number of followers

            // Update if this user has more followers than the current maximum
            if (followerCount > maxFollowersCount) {
                maxFollowersCount = followerCount;
                mostInfluentialUserId = user.getId();
            }
        }

        return mostInfluentialUserId; // Return the ID of the most influential user
    }

    public static void main(String[] args) {
        // Ensure the correct command-line arguments are provided
        if (args.length < 3 || !args[0].equals("most_influencer") || !args[1].equals("-i")) {
            System.out.println("Usage: xml_editor most_influencer -i input_file.xml");
            return;
        }

        String inputFile = args[2]; // Input XML file path

        try {
            // Initialize the graph
            XMLParser.Graph graph = XMLParser.getGraph();

            // Parse the XML file to populate the graph
            XMLParser.parseXML(inputFile, graph);

            // Create an instance of MostInfluential
            MostInfluential mostInfluential = new MostInfluential(graph);

            // Find and display the most influential user
            int mostInfluentialUserId = mostInfluential.findMostInfluentialUser();
            System.out.println("The most influential user ID is: " + mostInfluentialUserId);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
