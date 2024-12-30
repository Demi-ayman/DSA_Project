import java.util.ArrayList;
import java.util.List;
public class SuggestionFriends {

    public static List<Integer> suggestFriends(Main.Graph graph, Integer userID) {
        // Step 1: Check if the userID is valid and the user exists
        Main.User user = null;
        for (Main.User usertemp : graph.getUsers()) {
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
            Main.User dierctuser = null;
            for (Main.User usertemp : graph.getUsers()) {
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
    }}
// Function to check if a friend is in the direct friends list
    /*public static boolean isInDirectFriends(List<Integer> directFriends, Integer friendNode) {
        for (int f : directFriends) {
            if (f == friendNode) {
                return true;
            }
        }
        return false;
    }

    // Function to check if a friend is in the already suggested list
    public static boolean isInAlreadySuggested(List<Integer> alreadySuggested, Integer friendNode) {
        for (int f : alreadySuggested) {
            if (f == friendNode) {
                return true;
            }
        }
        return false;
    }

    // Function to suggest friends (friends of friends but not direct friends)
    /*public static List<Integer> suggestFriends(Main.Graph graph, Integer userID) {
        // Check if the userID is valid (within bounds)
        Main.User user =null;
        for(Main.User usertemp : graph.getUsers()){
            if (usertemp.getId()==userID) {
                user = usertemp;
                break;}
        }

        // List to store suggested friends and a list to track already suggested friends
        List<Integer> suggestedFriends = new ArrayList<>();
        List<Integer> alreadySuggested = new ArrayList<>();
        List<Integer> directFollowersIDs = user.getFollowers();  // Direct friends (neighbors) of the node

        // Step 1: Iterate through direct friends (outgoing neighbors)
        for (Integer dierctfollowerID : directFollowersIDs) {

            Main.User dierctuser=null;
            for (Main.User usertemp : graph.getUsers()) {
                if (usertemp.getId() == dierctfollowerID) {
                    dierctuser = usertemp;
                    break;
                }
                // Step 2: Check the friends of each direct friend (friends of friends)
                for (Integer friendOfFriendID : dierctuser.getFollowers()) {
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

        }return suggestedFriends;
    }}*/
