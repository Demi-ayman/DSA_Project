import java.util.ArrayList;
import java.util.List;
public class SuggestionFriends {
    // Function to check if a friend is in the direct friends list
    public static boolean isInDirectFriends(List<Integer> directFriends, int friendNode) {
        for (int f : directFriends) {
            if (f == friendNode) {
                return true;
            }
        }
        return false;
    }

    // Function to check if a friend is in the already suggested list
    public static boolean isInAlreadySuggested(List<Integer> alreadySuggested, int friendNode) {
        for (int f : alreadySuggested) {
            if (f == friendNode) {
                return true;
            }
        }
        return false;
    }

    // Function to suggest friends (friends of friends but not direct friends)
    public static List<Integer> suggestFriends(List<List<Integer>> graph, int node) {
        // Check if the node is valid (within bounds)
        if (node < 0 || node >= graph.size()) {
            System.out.println("Invalid node!");
            return new ArrayList<>();  // Return an empty list for invalid nodes
        }

        // List to store suggested friends and a list to track already suggested friends
        List<Integer> suggestedFriends = new ArrayList<>();
        List<Integer> alreadySuggested = new ArrayList<>();
        List<Integer> directFriends = graph.get(node);  // Direct friends (neighbors) of the node

        // Step 1: Iterate through direct friends (outgoing neighbors)
        for (int friendNode : directFriends) {
            // Step 2: Check the friends of each direct friend (friends of friends)
            for (int friendOfFriend : graph.get(friendNode)) {
                // Check three conditions:
                // 1. It is not the same node we are studying
                // 2. It is not already a direct friend of the node
                // 3. It has not been already suggested
                if (friendOfFriend != node &&
                        !isInDirectFriends(directFriends, friendOfFriend) &&
                        !isInAlreadySuggested(alreadySuggested, friendOfFriend)) {

                    // If all conditions are met, add it to the suggestedFriends list
                    suggestedFriends.add(friendOfFriend);
                    alreadySuggested.add(friendOfFriend); // Track it in the alreadySuggested list
                }
            }
        }

        return suggestedFriends;
    }}
