package org.example;
import java.util.*;

public class PostSearch {

    // Search for posts by a specific topic
    public List<DSprojectJAVA.Post> searchPostsByTopic(DSprojectJAVA.Graph graph, String topic) {
        List<DSprojectJAVA.Post> matchingPosts = new ArrayList<>();

        // Iterate through all users in the graph
        for (DSprojectJAVA.User user : graph.getUsers()) {
            // Iterate through all posts of each user
            for (DSprojectJAVA.Post post : user.getPosts()) {
                // Check if the post contains the topic
                if (post.getTopics().contains(topic)) {
                    matchingPosts.add(post);
                }
            }
        }

        return matchingPosts;
    }

    // Search for posts by a specific word in the body
    public List<DSprojectJAVA.Post> searchPostsByWord(DSprojectJAVA.Graph graph, String word) {
        List<DSprojectJAVA.Post> matchingPosts = new ArrayList<>();

        // Iterate through all users in the graph
        for (DSprojectJAVA.User user : graph.getUsers()) {
            // Iterate through all posts of each user
            for (DSprojectJAVA.Post post : user.getPosts()) {
                // Check if the post body contains the word
                if (post.getBody().toLowerCase().contains(word.toLowerCase())) {
                    matchingPosts.add(post);
                }
            }
        }

        return matchingPosts;
    }

    // Display posts with the user ID
    public void displayPosts(List<DSprojectJAVA.Post> posts, DSprojectJAVA.Graph graph) {
        if (posts.isEmpty()) {
            System.out.println("No posts found with the specified criteria.");
        } else {
            for (DSprojectJAVA.Post post : posts) {
                // Find the user who posted the post
                DSprojectJAVA.User user = findUserByPost(graph, post);

                // Print the user's ID, name, post body, and topics
                if (user != null) {
                    System.out.println("User ID: " + user.getId());
                    System.out.println("User Name: " + user.getName());
                }
                System.out.println("Post Body: " + post.getBody());
                System.out.println("Topics: " + String.join(", ", post.getTopics()));
                System.out.println("-----");
            }
        }
    }

    // Helper method to find the user by their post
    private DSprojectJAVA.User findUserByPost(DSprojectJAVA.Graph graph, DSprojectJAVA.Post post) {
        for (DSprojectJAVA.User user : graph.getUsers()) {
            if (user.getPosts().contains(post)) {
                return user;
            }
        }
        return null;
    }
}