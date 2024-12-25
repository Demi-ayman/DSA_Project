package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.HashMap;
import java.util.Map;

public class MainApp extends Application {
    private DefaultDirectedGraph<String, DefaultEdge> graph;
    private Map<String, Circle> nodeMap = new HashMap<>();

    // Method to create and visualize the graph
    public void buildGraph() {
        graph = new DefaultDirectedGraph<>(DefaultEdge.class);

        // Example data: Add users and followers (edges)
        graph.addVertex("User1");
        graph.addVertex("User2");
        graph.addVertex("User3");

        graph.addEdge("User1", "User2");
        graph.addEdge("User1", "User3");

        // Now the graph contains User1 -> User2 and User1 -> User3
    }

    @Override
    public void start(Stage primaryStage) {
        // Build the graph
        buildGraph();

        // Create a pane to hold the graph visualization
        Pane root = new Pane();

        // Visualize the graph
        double[][] positions = {
                {300, 100}, // Position for User1
                {100, 400}, // Position for User2
                {500, 400}  // Position for User3
        };

        int index = 0;
        for (String vertex : graph.vertexSet()) {
            // Create a circle for each user
            Circle userNode = new Circle(30);
            userNode.setFill(Color.BLUE);
            userNode.setStroke(Color.BLACK);

            // Position the nodes
            userNode.setTranslateX(positions[index][0]);
            userNode.setTranslateY(positions[index][1]);

            // Store node in map
            nodeMap.put(vertex, userNode);

            root.getChildren().add(userNode);
            index++;
        }

        // Add edges between the users (followers)
        for (DefaultEdge edge : graph.edgeSet()) {
            String source = graph.getEdgeSource(edge);
            String target = graph.getEdgeTarget(edge);

            // Find positions for the nodes
            Circle sourceNode = nodeMap.get(source);
            Circle targetNode = nodeMap.get(target);

            Line edgeLine = new Line();
            edgeLine.setStartX(sourceNode.getTranslateX());
            edgeLine.setStartY(sourceNode.getTranslateY());
            edgeLine.setEndX(targetNode.getTranslateX());
            edgeLine.setEndY(targetNode.getTranslateY());
            edgeLine.setStroke(Color.BLACK);

            root.getChildren().add(edgeLine);
        }

        // Set up the scene
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Graph Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
