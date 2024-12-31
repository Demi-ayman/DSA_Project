package com.example;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class SuggestionFriends {
    private XMLParser.Graph graph;

    public static void main(String[] args) {
        // Create a scanner to take user input
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter a user ID
        System.out.print("Enter the user ID to get friend suggestions: ");
        int userID = scanner.nextInt();
        scanner.close(); // Close the scanner after use

        // Path to the XML file
        String path = "D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\main\\java\\com\\example\\sample.xml";

        // Call the method to get suggested friends
        List<Integer> friends = suggestFriends(path, userID);

        // Print the suggested friends
        System.out.print("Suggested friends for user ID " + userID + ": ");
        if (friends.isEmpty()) {
            System.out.println("No suggestions.");
        } else {
            for (int friendNode : friends) {
                System.out.print(friendNode + " ");
            }
            System.out.println();
        }
    }

    public static List<Integer> suggestFriends(String path, Integer userID) {
        // Step 1: Check if the userID is valid and the user exists
        XMLParser.Graph graph = new XMLParser.Graph();
        XMLParser.parseXML(path, graph);
        XMLParser.User user = null;
        for (XMLParser.User usertemp : graph.getUsers()) {
            if (usertemp.getId() == userID) {
                user = usertemp;
                break;
            }
        }

        // If the user was not found, return an empty list
        if (user == null) {
            System.out.println("User with ID " + userID + " not found.");
            return new ArrayList<>();
        }

        // List to store suggested friends and a list to track already suggested friends
        List<Integer> suggestedFriends = new ArrayList<>();
        List<Integer> alreadySuggested = new ArrayList<>();

        // Get the direct followers (friends) of the user
        List<Integer> directFollowersIDs = user.getFollowers();

        // If the user's followers list is null, return an empty list
        if (directFollowersIDs == null) {
            System.out.println("User " + userID + " has no followers.");
            return suggestedFriends;
        }

        // Step 2: Iterate through direct friends (outgoing neighbors)
        for (Integer dierctfollowerID : directFollowersIDs) {

            // Step 3: Find the user corresponding to the direct follower ID
            XMLParser.User dierctuser = null;
            for (XMLParser.User usertemp : graph.getUsers()) {
                if (usertemp.getId() == dierctfollowerID) {
                    dierctuser = usertemp;
                    break;
                }
            }

            // If the direct follower does not exist, skip to the next one
            if (dierctuser == null) {
                System.out.println("Direct follower with ID " + dierctfollowerID + " not found.");
                continue; // Skip this iteration
            }

            // Step 4: Check the friends of each direct friend (friends of friends)
            List<Integer> friendsOfFriendIDs = dierctuser.getFollowers();
            if (friendsOfFriendIDs == null) {
                continue; // Skip if this user's followers list is null
            }

            for (Integer friendOfFriendID : friendsOfFriendIDs) {
                // Check three conditions:
                // 1. It is not the same node we are studying
                // 2. It is not already a direct friend of the node
                // 3. It has not been already suggested
                if (friendOfFriendID != userID &&
                        !isInDirectFriends(directFollowersIDs, friendOfFriendID) &&
                        !isInAlreadySuggested(alreadySuggested, friendOfFriendID)) {

                    // If all conditions are met, add it to the suggestedFriends list
                    suggestedFriends.add(friendOfFriendID);
                    alreadySuggested.add(friendOfFriendID); // Track it in the alreadySuggested list
                }
            }
        }

        return suggestedFriends;
    }

    // Helper method to check if a friend is a direct friend of the user
    private static boolean isInDirectFriends(List<Integer> directFollowersIDs, Integer friendID) {
        return directFollowersIDs.contains(friendID);
    }

    // Helper method to check if a friend is already suggested
    private static boolean isInAlreadySuggested(List<Integer> alreadySuggested, Integer friendID) {
        return alreadySuggested.contains(friendID);
    }
}