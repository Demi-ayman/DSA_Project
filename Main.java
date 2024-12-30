import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        
        
        
        List<Integer> friends = SuggestionFriends.suggestFriends("G:\\DSA_Project\\SuggestionFriends\\src\\sample.xml",2);

        // Print the suggested friends
        System.out.print("Suggested friends " +  ": ");
        if (friends.isEmpty()) {
            System.out.println("No suggestions.");
        } else {
            for (int friendNode : friends) {
                System.out.print(friendNode + " ");
            }
            System.out.println();
        }
    }
}
