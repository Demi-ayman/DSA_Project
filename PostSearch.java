package com.example;

import java.util.*;

public class PostSearch {
    private XMLParser.Graph graph;

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
    public XMLParser.User findUserByPost(XMLParser.Graph graph, XMLParser.Post post) {
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
        XMLParser.parseXML("D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\main\\java\\com\\example\\sample.xml", graph);

        // Create an instance of PostSearch
        PostSearch postSearch = new PostSearch();

        // Ask the user if they want to search by topic or word
        System.out.println("Do you want to search by (1) Topic or (2) Word?");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        List<XMLParser.Post> foundPosts = new ArrayList<>();
        if (choice == 1) {
            // If user wants to search by topic
            System.out.println("Enter the topic you want to search about: ");
            String topic = scanner.nextLine();
            foundPosts = postSearch.searchPostsByTopic(graph, topic);
        } else if (choice == 2) {
            // If user wants to search by word
            System.out.println("Enter the word you want to search about: ");
            String word = scanner.nextLine();
            foundPosts = postSearch.searchPostsByWord(graph, word);
        } else {
            System.out.println("Invalid choice. Please enter 1 for Topic or 2 for Word.");
            return;
        }

        // Display posts
        postSearch.displayPosts(foundPosts, graph);
    }
}
