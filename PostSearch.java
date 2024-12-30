
package com.example;
import com.example.XMLParser;

import java.util.*;

import static com.example.XMLParser.parseXML;

public class PostSearch {

    // Search for posts by a specific topic
    public List<XMLParser.Post> searchPostsByTopic(XMLParser.Graph graph, String topic) {
        List<XMLParser.Post> matchingPosts = new ArrayList<>();

        // Iterate through all users in the graph
        for (XMLParser.User user : graph.getUsers()) {
            // Iterate through all posts of each user
            for (XMLParser.Post post : user.getPosts()) {
                // Check if the post contains the topic
                if (post.getTopics().contains(topic)) {
                    matchingPosts.add(post);
                }
            }
        }

        return matchingPosts;
    }

    // Search for posts by a specific word in the body
    public List<XMLParser.Post> searchPostsByWord(XMLParser.Graph graph, String word) {
        List<XMLParser.Post> matchingPosts = new ArrayList<>();

        // Iterate through all users in the graph
        for (XMLParser.User user : graph.getUsers()) {
            // Iterate through all posts of each user
            for (XMLParser.Post post : user.getPosts()) {
                // Check if the post body contains the word
                if (post.getBody().toLowerCase().contains(word.toLowerCase())) {
                    matchingPosts.add(post);
                }
            }
        }

        return matchingPosts;
    }

    // Display posts with the user ID
    public void displayPosts(List<XMLParser.Post> posts, XMLParser.Graph graph) {
        if (posts.isEmpty()) {
            System.out.println("No posts found with the specified criteria.");
        } else {
            for (XMLParser.Post post : posts) {
                // Find the user who posted the post
                XMLParser.User user = findUserByPost(graph, post);

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
    private XMLParser.User findUserByPost(XMLParser.Graph graph, XMLParser.Post post) {
        for (XMLParser.User user : graph.getUsers()) {
            if (user.getPosts().contains(post)) {
                return user;
            }
        }
        return null;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        // Create an instance of the graph
        XMLParser.Graph graph = XMLParser.getGraph();

        // Parse the XML file and populate the graph
        parseXML("D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\main\\java\\com\\example\\sample.xml", graph);
        // Create an instance of PostSearch
        PostSearch postSearch = new PostSearch();

        // Search for posts containing a specific topic
        System.out.println("Enter the topic you want to Search about : ");
        String topic = scanner.nextLine();
        List<XMLParser.Post> foundPostsByTopic = postSearch.searchPostsByTopic(graph, topic);
        postSearch.displayPosts(foundPostsByTopic, graph);  // Pass the graph to displayPosts

        // Search for posts containing a specific word in the body
        System.out.println("Enter the Word you want to Search about : ");
        String word = scanner.nextLine();
        List<XMLParser.Post> foundPostsByWord = postSearch.searchPostsByWord(graph, word);
        postSearch.displayPosts(foundPostsByWord, graph);  // Pass the graph to displayPosts

    }
}
