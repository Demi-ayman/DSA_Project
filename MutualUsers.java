package com.example;

import java.util.*;
import java.io.*;

public class MutualUsers {
    private XMLParser.Graph User;

    // Method to find mutual followers between two users
    public static List<XMLParser.User> findMutualFollowers(int userId1, int userId2, XMLParser.Graph graph) {
        XMLParser.User user1 = null;
        XMLParser.User user2 = null;

        // Find the users by their IDs
        for (XMLParser.User user : graph.getUsers()) {
            if (user.getId() == userId1) {
                user1 = user;
            } else if (user.getId() == userId2) {
                user2 = user;
            }
        }

        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("User not found.");
        }

        // Find the mutual followers by checking the intersection of their followers
        Set<Integer> followersSet1 = new HashSet<>(user1.getFollowers());
        Set<Integer> followersSet2 = new HashSet<>(user2.getFollowers());

        followersSet1.retainAll(followersSet2);  // Find the intersection of the two sets

        // Collect the mutual followers into a list
        List<XMLParser.User> mutualFollowers = new ArrayList<>();
        for (Integer followerId : followersSet1) {
            for (XMLParser.User user : graph.getUsers()) {
                if (user.getId() == followerId) {
                    mutualFollowers.add(user);
                    break;
                }
            }
        }

        return mutualFollowers;
    }

    // Method to handle command-line input and output
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: java MutualUsers -i input_file.xml -ids 1,2,3");
            return;
        }

        String inputFile = null;
        List<Integer> userIds = new ArrayList<>();

        // Parse command-line arguments
        for (int i = 0; i < args.length; i++) {
            if ("-i".equals(args[i])) {
                if (i + 1 < args.length) {
                    inputFile = args[i + 1];
                    i++; // Skip the next argument as it is the filename
                } else {
                    System.out.println("Error: Missing input file.");
                    return;
                }
            } else if ("-ids".equals(args[i])) {
                if (i + 1 < args.length) {
                    String[] ids = args[i + 1].split(",");
                    for (String id : ids) {
                        try {
                            userIds.add(Integer.parseInt(id));
                        } catch (NumberFormatException e) {
                            System.out.println("Error: Invalid user ID format.");
                            return;
                        }
                    }
                    i++; // Skip the next argument as it is the user IDs
                } else {
                    System.out.println("Error: Missing user IDs.");
                    return;
                }
            }
        }

        // Ensure we have the input file and user IDs
        if (inputFile == null || userIds.isEmpty()) {
            System.out.println("Error: Both input file and user IDs are required.");
            return;
        }

        try {
            // Parse the XML file into the graph
            XMLParser.Graph graph = new XMLParser.Graph();
            XMLParser.parseXML(inputFile, graph);

            // Find mutual followers for the given user IDs
            Set<XMLParser.User> mutualUsers = new HashSet<>();
            for (int i = 0; i < userIds.size() - 1; i++) {
                int userId1 = userIds.get(i);
                int userId2 = userIds.get(i + 1);
                List<XMLParser.User> mutualFollowers = findMutualFollowers(userId1, userId2, graph);
                mutualUsers.addAll(mutualFollowers);
            }

            // Print the mutual users
            System.out.println("Mutual users between the specified users:");
            for (XMLParser.User user : mutualUsers) {
                System.out.println("User ID: " + user.getId() + " | User Name: " + user.getName());
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
