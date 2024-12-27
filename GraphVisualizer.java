package com.example;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GraphVisualizer extends Application {

    private static XMLParser.Graph graph;

    @Override
    public void start(Stage primaryStage) {
        // Load the graph data from XML
        System.out.println("Loading graph data...");
        graph = new XMLParser.Graph();
        XMLParser.parseXML("D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\main\\java\\com\\example\\sample.xml", graph);

        if (graph.getUsers().isEmpty()) {
            System.out.println("No users to display in the graph.");
            return;  // Exit early if there are no users
        }

        // Setup JavaFX Pane
        Pane pane = new Pane();
        pane.setPrefSize(800, 800);
        pane.setStyle("-fx-background-color: white;");

        // Initialize node positions
        Map<Integer, double[]> positions = new HashMap<>();
        for (XMLParser.User user : graph.getUsers()) {
            positions.put(user.getId(), new double[]{Math.random() * 700 + 50, Math.random() * 700 + 50});
        }

        // Draw nodes (users)
        Map<Integer, Circle> nodeCircles = new HashMap<>();
        Map<Integer, Text> nodeLabels = new HashMap<>();
        for (XMLParser.User user : graph.getUsers()) {
            double[] pos = positions.get(user.getId());
            Circle circle = new Circle(pos[0], pos[1], 20, Color.LIGHTBLUE);
            circle.setStroke(Color.BLACK);
            Text label = new Text(pos[0] - 15, pos[1] + 5, user.getName());
            nodeCircles.put(user.getId(), circle);
            nodeLabels.put(user.getId(), label);
            pane.getChildren().addAll(circle, label);
        }

        // Draw edges (followers)
        for (XMLParser.User user : graph.getUsers()) {
            for (int followerId : user.getFollowers()) {
                double[] sourcePos = positions.get(user.getId());
                double[] targetPos = positions.get(followerId);
                if (targetPos != null) {
                    Line line = new Line(sourcePos[0], sourcePos[1], targetPos[0], targetPos[1]);
                    line.setStroke(Color.BLACK);
                    pane.getChildren().add(line);
                }
            }
        }

        // Attempt to write a dummy file to check permissions
        try {
            File dummyFile = new File("D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\dummy.txt");
            if (dummyFile.createNewFile()) {
                FileWriter writer = new FileWriter(dummyFile);
                writer.write("Dummy file created to check write permissions.");
                writer.close();
                System.out.println("Dummy file created successfully at: " + dummyFile.getAbsolutePath());
            } else {
                System.out.println("Failed to create dummy file.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Take a snapshot and save as JPEG file
        File outputFile = new File("D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\graph_output.jpg");
        System.out.println("Attempting to save file at: " + outputFile.getAbsolutePath());
        pane.snapshot(snapshotResult -> {
            try {
                boolean result = ImageIO.write(SwingFXUtils.fromFXImage(snapshotResult.getImage(), null), "jpg", outputFile);
                if (result) {
                    System.out.println("Graph saved successfully as graph_output.jpg");
                    System.out.println("File location: " + outputFile.getAbsolutePath());
                } else {
                    System.out.println("Failed to save the graph image.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }, new SnapshotParameters(), null);

        // Create Scene and show
        Scene scene = new Scene(pane, 800, 800);
        primaryStage.setTitle("Graph Visualization");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
