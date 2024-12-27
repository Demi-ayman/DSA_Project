package org.example;

import java.util.List;

import static org.example.DSprojectJAVA.parseXML;

public class Main {
    public static void main(String[] args) {
        // Create an instance of the graph
        DSprojectJAVA.Graph graph = DSprojectJAVA.getGraph();

        // Parse the XML file and populate the graph
        parseXML("F:\\Senior\\Sem1\\DSA\\level2file.xml", graph);

        // Create an instance of PostSearch
        PostSearch postSearch = new PostSearch();

        // Search for posts containing a specific topic
        List<DSprojectJAVA.Post> foundPostsByTopic = postSearch.searchPostsByTopic(graph, "Technology");
        System.out.println("Posts containing the topic 'Technology':");
        postSearch.displayPosts(foundPostsByTopic, graph);  // Pass the graph to displayPosts

        // Search for posts containing a specific word in the body
        List<DSprojectJAVA.Post> foundPostsByWord = postSearch.searchPostsByWord(graph, "AI");
        System.out.println("Posts containing the word 'AI':");
        postSearch.displayPosts(foundPostsByWord, graph);  // Pass the graph to displayPosts
    }
}
