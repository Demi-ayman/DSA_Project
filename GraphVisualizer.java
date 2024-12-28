package com.example;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

public class GraphVisualizer extends JPanel {
    private final XMLParser.Graph graph;

    public GraphVisualizer(XMLParser.Graph graph) {
        this.graph = graph;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();
        int radius = 30; // Radius of the node circles
        int centerX = width / 2;
        int centerY = height / 2;

        List<XMLParser.User> users = graph.getUsers();
        int userCount = users.size();
        double angleStep = 2 * Math.PI / userCount;

        // Store user positions for drawing edges later
        int[] xPositions = new int[userCount];
        int[] yPositions = new int[userCount];

        // Draw nodes
        for (int i = 0; i < userCount; i++) {
            XMLParser.User user = users.get(i);

            int x = (int) (centerX + Math.cos(i * angleStep) * (centerX - radius * 2));
            int y = (int) (centerY + Math.sin(i * angleStep) * (centerY - radius * 2));
            xPositions[i] = x;
            yPositions[i] = y;

            // Draw node circle
            g2d.setColor(Color.BLUE);
            g2d.fillOval(x - radius, y - radius, radius * 2, radius * 2);

            // Draw user name inside the node
            g2d.setColor(Color.WHITE);
            g2d.drawString(user.getName(), x - radius / 2, y + 5);
        }

        // Draw edges (follower relationships)
        for (int i = 0; i < userCount; i++) {
            XMLParser.User user = users.get(i);
            for (int followerId : user.getFollowers()) {
                XMLParser.User follower = graph.getUsers().stream()
                        .filter(u -> u.getId() == followerId)
                        .findFirst()
                        .orElse(null);

                if (follower != null) {
                    int followerIndex = users.indexOf(follower);
                    int startX = xPositions[i];
                    int startY = yPositions[i];
                    int endX = xPositions[followerIndex];
                    int endY = yPositions[followerIndex];

                    // Draw edge line
                    g2d.setColor(Color.BLACK);
                    g2d.drawLine(startX, startY, endX, endY);
                }
            }
        }
    }

    public static void saveGraphAsImage(XMLParser.Graph graph, String filePath) {
        int width = 800;
        int height = 800;

        // Create a new image
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Create and render the visualizer
        GraphVisualizer visualizer = new GraphVisualizer(graph);
        visualizer.setSize(width, height);
        visualizer.paintComponent(g2d);

        // Save the image to a file
        g2d.dispose();
        try {
            ImageIO.write(image, "jpg", new File(filePath));
            System.out.println("Graph saved as image at: " + filePath);
        } catch (IOException e) {
            System.err.println("Error saving graph as image: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        // Parse the XML file and populate the graph
        XMLParser.Graph graph = XMLParser.getGraph();
        XMLParser.parseXML("D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\main\\java\\com\\example\\sample.xml", graph);

        // Visualize the graph in a GUI
        JFrame frame = new JFrame("Graph Visualizer");
        GraphVisualizer visualizer = new GraphVisualizer(graph);
        frame.add(visualizer);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Save the graph as an image
        saveGraphAsImage(graph, "graph_output.jpg");
    }
}
