import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SuggestionFriends {

     public static List<Integer> suggestFriends(String path, Integer userID) {
        // Step 1: Check if the userID is valid and the user exists
        parseXML(path, graph);
        User user = null;
        for (User usertemp : graph.getUsers()) {
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
            User dierctuser = null;
            for (User usertemp : graph.getUsers()) {
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

