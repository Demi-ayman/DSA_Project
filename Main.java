import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Example: Creating an adjacency list for a directed graph
        // Graph structure:
        // 0 --> 1, 2
        // 1 --> 3
        // 2 --> 3
        // 3 --> 1
        List<List<Integer>> graph = new ArrayList<>();
        graph.add(List.of(1, 2));    // Node 0's neighbors (outgoing edges)
        graph.add(List.of(3));       // Node 1's neighbors (outgoing edges)
        graph.add(List.of(3));       // Node 2's neighbors (outgoing edges)
        graph.add(List.of(1));       // Node 3's neighbors (outgoing edges)

        java.util.Scanner sc = new java.util.Scanner(System.in);
        System.out.print("Enter the node number to suggest friends: ");
        int node = sc.nextInt();

        // Get suggested friends (friends of friends) for the given node
        List<Integer> friends = SuggestionFriends.suggestFriends(graph, node);

        // Print the suggested friends
        System.out.print("Suggested friends for node " + node + ": ");
        if (friends.isEmpty()) {
            System.out.println("No suggestions.");
        } else {
            for (int friendNode : friends) {
                System.out.print(friendNode + " ");
            }
            System.out.println();
        }

        sc.close();
    }
}