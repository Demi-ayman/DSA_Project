package com.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import javax.swing.*;
import java.io.*;
import java.nio.file.Files;
import java.util.List;
import org.w3c.dom.*;
import org.xml.sax.InputSource;

import javax.xml.parsers.*;
import java.io.StringReader;
import java.util.*;

public class Main extends Application {

    private BorderPane root;
    private TextArea xmlInputArea;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Start Page Background Image
        Image startBackground = new Image("file:D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\image\\startBackground.jpeg");
        ImageView startBackgroundView = new ImageView(startBackground);
        startBackgroundView.setPreserveRatio(false);
        startBackgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        startBackgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        // StackPane for Start Page
        StackPane startPane = new StackPane();
        startPane.getChildren().add(startBackgroundView);

        // Start Button
        Button startButton = new Button("Start");
        startButton.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 16));
        startButton.setStyle("-fx-font-size: 20px; -fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
        startButton.setOnAction(event -> showMainProject(primaryStage));

        // Cancel Button
        Button cancelButton = new Button("Cancel");
        cancelButton.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 16));
        cancelButton.setStyle("-fx-font-size: 17px; -fx-background-color: #4682B4; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20 10 20;");
        cancelButton.setOnAction(event -> primaryStage.close()); // Close the application on cancel

        // VBox for Buttons
        VBox buttonBox = new VBox(10, startButton, cancelButton);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);

        // Add Button Box to Start Pane
        startPane.getChildren().add(buttonBox);
        StackPane.setAlignment(buttonBox, Pos.CENTER_RIGHT);
        StackPane.setMargin(buttonBox, new Insets(0, 175, 80, 0));

        // Scene for Start Page
        Scene startScene = new Scene(startPane, 1000, 600);
        primaryStage.setScene(startScene);
        primaryStage.setTitle("Welcome Page");
        primaryStage.show();
    }

    private void showMainProject(Stage primaryStage) {
        // Main Project Background Image
        Image mainBackground = new Image("file:D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\image\\mainBackground.jpeg");
        ImageView mainBackgroundView = new ImageView(mainBackground);
        mainBackgroundView.setPreserveRatio(false);
        mainBackgroundView.fitWidthProperty().bind(primaryStage.widthProperty());
        mainBackgroundView.fitHeightProperty().bind(primaryStage.heightProperty());

        // StackPane for Main Project
        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(mainBackgroundView);

        // Main Layout (BorderPane)
        root = new BorderPane();
        rootPane.getChildren().add(root);

        // Create Sections
        VBox inputSection = createInputSection();
        VBox buttonSection = createButtonSection();
        VBox outputSection = createOutputSection();

        // Set Sections to Positions
        root.setLeft(buttonSection);
        root.setCenter(inputSection);
        root.setRight(outputSection);

        // Scene for Main Project
        Scene mainScene = new Scene(rootPane, 1000, 600);
        primaryStage.setScene(mainScene);
        primaryStage.setTitle("XML Editor");
    }

    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        inputSection.setPadding(new Insets(20));
        inputSection.setAlignment(Pos.TOP_CENTER);

        Label inputLabel = new Label("Input XML/JSON:");
        inputLabel.setFont(Font.font("Serif", FontWeight.EXTRA_BOLD, 16));

        xmlInputArea = new TextArea();
        xmlInputArea.setPrefHeight(550);
        xmlInputArea.setMaxWidth(580);  // Make the TextArea flexible
        xmlInputArea.setFont(Font.font("Arial", 15));

        Button browseButton = new Button("Browse XML File");
        browseButton.setFont(Font.font("Serif", FontWeight.BOLD, 15));
        browseButton.setStyle("-fx-background-color: #87CEEB; -fx-text-fill: white;-fx-font-size: 16px");
        browseButton.setOnAction(event -> openFileChooser(xmlInputArea));

        inputSection.getChildren().addAll(inputLabel, xmlInputArea, browseButton);
        return inputSection;
    }

    private VBox createButtonSection() {
        VBox buttonSection = new VBox(3);
        buttonSection.setPadding(new Insets(43, 10, 10, 30));  // Slightly more padding to move buttons down
        buttonSection.setAlignment(Pos.TOP_LEFT);

        String buttonStyle = "-fx-background-color: #87CEEB; -fx-text-fill: white; -fx-font-size: 16px;";

        Button validateButton = new Button("Validate XML");
        validateButton.setStyle(buttonStyle);
        validateButton.setOnAction(event -> validateXML());

        Button prettifyButton = new Button("Prettify XML");
        prettifyButton.setStyle(buttonStyle);
        prettifyButton.setOnAction(event -> prettifyXML());

        Button convertToJSONButton = new Button("Convert to JSON");
        convertToJSONButton.setStyle(buttonStyle);
        convertToJSONButton.setOnAction(event -> convertToJSON());

        Button compressButton = new Button("Compress File");
        compressButton.setStyle(buttonStyle);
        compressButton.setOnAction(event -> compressFile());

        Button decompressButton = new Button("Decompress File");
        decompressButton.setStyle(buttonStyle);
        decompressButton.setOnAction(event -> decompressFile());

        Button minifyButton = new Button("Minify XML");
        minifyButton.setStyle(buttonStyle);
        minifyButton.setOnAction(event -> minifyXML());

        Button parseXMLButton = new Button("Parse and Save XML");
        parseXMLButton.setStyle(buttonStyle);
        parseXMLButton.setOnAction(event -> parseAndSaveXML());

        Button visualizeGraphButton = new Button("Visualize_Save Graph");
        visualizeGraphButton.setStyle(buttonStyle);
        visualizeGraphButton.setOnAction(event -> visualizeAndSaveGraph());

        Button mostInfluentialButton = new Button("Most Influential User");
        mostInfluentialButton.setStyle(buttonStyle);
        mostInfluentialButton.setOnAction(event -> findMostInfluentialUser());

        Button mostActiveButton = new Button("Most Active User");
        mostActiveButton.setStyle(buttonStyle);
        mostActiveButton.setOnAction(event -> findMostActiveUser());

        Button mutualUsersButton = new Button("Mutual User");
        mutualUsersButton.setStyle(buttonStyle);
        mutualUsersButton.setOnAction(event -> findMutualUsers());

        Button suggestedFollowersButton = new Button("Suggested Users");
        suggestedFollowersButton.setStyle(buttonStyle);
        suggestedFollowersButton.setOnAction(event -> suggestedFollowers());

        Button postSearchButton = new Button("Post Search");
        postSearchButton.setStyle(buttonStyle);
        postSearchButton.setOnAction(event -> postSearch());

        buttonSection.getChildren().addAll(
                validateButton, prettifyButton, convertToJSONButton, compressButton, decompressButton,
                minifyButton, parseXMLButton, visualizeGraphButton, mostInfluentialButton,mostActiveButton,postSearchButton,
                mutualUsersButton,suggestedFollowersButton
        );
        return buttonSection;
    }

    private VBox createOutputSection() {
        VBox outputSection = new VBox(10);
        outputSection.setPadding(new Insets(20));
        outputSection.setAlignment(Pos.TOP_CENTER);

        Label outputLabel = new Label("Output:");
        outputLabel.setFont(Font.font("Serif", FontWeight.BOLD, 16));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setFont(Font.font("Arial", 15));
        outputArea.setPrefHeight(550);
        outputArea.setMaxWidth(580);  // Make the TextArea flexible

        Button saveButton = new Button("Save Output");
        saveButton.setFont(Font.font("Serif", FontWeight.BOLD, 15));
        saveButton.setStyle("-fx-background-color: #87CEEB; -fx-text-fill: white;-fx-font-size: 16px");
        saveButton.setOnAction(event -> saveOutput(outputArea.getText()));

        outputSection.getChildren().addAll(outputLabel, outputArea, saveButton);
        return outputSection;
    }

    private void findMostActiveUser() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Create a new Graph instance
            XMLParser.Graph graph = new XMLParser.Graph();

            // Write the input XML to a temporary file for parsing
            File tempFile = File.createTempFile("input", ".xml");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(inputXML);
            }

            // Parse the XML to populate the Graph
            XMLParser.parseXML(tempFile.getAbsolutePath(), graph);

            // Remove the temporary file after parsing
            tempFile.delete();

            // Create an instance of MostActive and pass the Graph
            MostActive mostActive = new MostActive(graph);

            // Find the most active user
            int activeUserId = mostActive.findMostActiveUser();

            // Display the result
            outputArea.setText("Most Active User ID: " + activeUserId);
        } catch (Exception e) {
            showErrorDialog("Error finding most active user: " + e.getMessage());
        }
    }

    private void findMostInfluentialUser() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Create a new Graph instance
            XMLParser.Graph graph = new XMLParser.Graph();

            // Write the input XML to a temporary file for parsing
            File tempFile = File.createTempFile("input", ".xml");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(inputXML);
            }

            // Parse the XML to populate the Graph
            XMLParser.parseXML(tempFile.getAbsolutePath(), graph);

            // Remove the temporary file after parsing
            tempFile.delete();

            // Create an instance of MostActive and pass the Graph
            MostInfluential mostInfluential = new MostInfluential(graph);

            // Find the most active user
            int activeUserId = mostInfluential.findMostInfluentialUser();

            // Display the result
            outputArea.setText("Most Influential In User ID: " + activeUserId);
        } catch (Exception e) {
            showErrorDialog("Error finding most active user: " + e.getMessage());
        }
    }

    private void postSearch() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Create a new Graph instance
            XMLParser.Graph graph = new XMLParser.Graph();

            // Write the input XML to a temporary file for parsing
            File tempFile = File.createTempFile("input", ".xml");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(inputXML);
            }

            // Parse the XML to populate the Graph
            XMLParser.parseXML(tempFile.getAbsolutePath(), graph);

            // Delete the temporary file after parsing
            tempFile.delete();

            // Create an instance of PostSearch
            PostSearch postSearch = new PostSearch();

            // Prompt the user to select the search type
            String[] options = {"Search by Topic", "Search by Word"};
            int choice = JOptionPane.showOptionDialog(
                    null,  // Parent component; replace null with your frame or panel if needed
                    "Choose how you want to search:",  // Message
                    "Search Posts",  // Dialog title
                    JOptionPane.DEFAULT_OPTION,  // Option type
                    JOptionPane.QUESTION_MESSAGE,  // Message type
                    null,  // Icon
                    options,  // Available options
                    options[0]  // Default option
            );


            List<XMLParser.Post> matchingPosts = new ArrayList<>();
            if (choice == 0) { // Search by Topic
                String topic = JOptionPane.showInputDialog(this, "Enter the topic:");
                if (topic != null && !topic.trim().isEmpty()) {
                    matchingPosts = postSearch.searchPostsByTopic(graph, topic);
                } else {
                    showErrorDialog("Topic cannot be empty.");
                    return;
                }
            } else if (choice == 1) { // Search by Word
                String word = JOptionPane.showInputDialog(this, "Enter the word:");
                if (word != null && !word.trim().isEmpty()) {
                    matchingPosts = postSearch.searchPostsByWord(graph, word);
                } else {
                    showErrorDialog("Word cannot be empty.");
                    return;
                }
            } else {
                // User canceled the operation
                return;
            }

            // Display the results
            if (matchingPosts.isEmpty()) {
                outputArea.setText("No posts found matching the criteria.");
            } else {
                StringBuilder results = new StringBuilder();
                for (XMLParser.Post post : matchingPosts) {
                    XMLParser.User user = postSearch.findUserByPost(graph, post);
                    if (user != null) {
                        results.append("User ID: ").append(user.getId()).append("\n");
                        results.append("User Name: ").append(user.getName()).append("\n");
                    }
                    results.append("Post Body: ").append(post.getBody()).append("\n");
                    results.append("Topics: ").append(String.join(", ", post.getTopics())).append("\n");
                    results.append("-----\n");
                }
                outputArea.setText(results.toString());
            }
        } catch (Exception e) {
            showErrorDialog("Error during post search: " + e.getMessage());
        }
    }
    private void suggestedFollowers() {
        try {
            // Read XML content from the input text area
            String inputXML = xmlInputArea.getText().trim();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Prompt the user for the user ID
            String userIdStr = JOptionPane.showInputDialog(null, "Enter the User ID for friend suggestions:", "Friend Suggestions", JOptionPane.QUESTION_MESSAGE);
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                showErrorDialog("User ID cannot be empty.");
                return;
            }

            int userID;
            try {
                userID = Integer.parseInt(userIdStr.trim());
            } catch (NumberFormatException e) {
                showErrorDialog("Invalid User ID. Please enter a numeric value.");
                return;
            }

            // Write the XML to a temporary file for parsing
            File tempFile = File.createTempFile("input", ".xml");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(inputXML);
            }

            // Call the SuggestionFriends.suggestFriends method
            List<Integer> suggestedFriends = SuggestionFriends.suggestFriends(tempFile.getAbsolutePath(), userID);

            // Delete the temporary file
            tempFile.delete();

            // Display the results in the output area
            if (suggestedFriends.isEmpty()) {
                outputArea.setText("No friend suggestions found for User ID " + userID + ".\n");
            } else {
                StringBuilder result = new StringBuilder("Suggested friends for User ID " + userID + ":\n");
                for (Integer friendID : suggestedFriends) {
                    result.append("- User ID: ").append(friendID).append("\n");
                }
                outputArea.setText(result.toString());
            }
        } catch (IOException e) {
            showErrorDialog("Error writing XML to temporary file: " + e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Error finding suggested friends: " + e.getMessage());
        }
    }






    private void findMutualUsers() {
        try {
            // Read input XML from the input area
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Prompt the user to enter two user IDs
            String userId1Str = JOptionPane.showInputDialog(null, "Enter the first user ID:", "User Input", JOptionPane.QUESTION_MESSAGE);
            String userId2Str = JOptionPane.showInputDialog(null, "Enter the second user ID:", "User Input", JOptionPane.QUESTION_MESSAGE);

            // Validate input
            if (userId1Str == null || userId2Str == null || userId1Str.isEmpty() || userId2Str.isEmpty()) {
                showErrorDialog("User IDs cannot be empty. Please try again.");
                return;
            }

            int userId1 = Integer.parseInt(userId1Str.trim());
            int userId2 = Integer.parseInt(userId2Str.trim());

            // Create a new Graph instance
            XMLParser.Graph graph = new XMLParser.Graph();

            // Write the input XML to a temporary file for parsing
            File tempFile = File.createTempFile("input", ".xml");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                writer.write(inputXML);
            }

            // Parse the XML to populate the Graph
            XMLParser.parseXML(tempFile.getAbsolutePath(), graph);

            // Remove the temporary file after parsing
            tempFile.delete();

            // Find mutual followers using the MutualUsers logic
            List<XMLParser.User> mutualFollowers = MutualUsers.findMutualFollowers(userId1, userId2, graph);

            // Display the results in the output area
            if (mutualFollowers.isEmpty()) {
                outputArea.setText("No mutual followers found between User " + userId1 + " and User " + userId2 + ".\n");
            } else {
                StringBuilder result = new StringBuilder("Mutual Followers between User " + userId1 + " and User " + userId2 + ":\n");
                for (XMLParser.User user : mutualFollowers) {
                    result.append("- ").append(user.getName()).append(" (ID: ").append(user.getId()).append(")\n");
                }
                outputArea.setText(result.toString());
            }
        } catch (NumberFormatException e) {
            showErrorDialog("Invalid input. Please enter valid numeric user IDs.");
        } catch (Exception e) {
            showErrorDialog("Error finding mutual users: " + e.getMessage());
        }
    }

    private void parseAndSaveXML() {
        try {
            // Open file chooser to select XML file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            File selectedFile = fileChooser.showOpenDialog(null);

            if (selectedFile != null) {
                // Parse the XML file and populate the graph
                XMLParser.parseXML(selectedFile.getAbsolutePath(), XMLParser.getGraph());

                // Save the graph to a text file
                FileChooser saveFileChooser = new FileChooser();
                saveFileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
                saveFileChooser.setInitialFileName("output.txt");
                File outputFile = saveFileChooser.showSaveDialog(null);

                if (outputFile != null) {
                    XMLParser.getGraph().displayGraphToFile(outputFile.getAbsolutePath());

                    // Display the saved file content in the output area
                    outputArea.setText(new String(java.nio.file.Files.readAllBytes(outputFile.toPath())));
                }
            } else {
                outputArea.setText("No file selected.");
            }
        } catch (IOException e) {
            showErrorDialog("Error parsing and saving XML: " + e.getMessage());
        }
    }

    private void visualizeAndSaveGraph() {
        try {
            // Check if the graph is populated
            XMLParser.Graph graph = XMLParser.getGraph();
            if (graph == null || graph.getUsers().isEmpty()) {
                showErrorDialog("Graph is empty. Please load and parse an XML file first.");
                return;
            }

            // Visualize the graph in a new window
            JFrame frame = new JFrame("Graph Visualizer");
            GraphVisualizer visualizer = new GraphVisualizer(graph);
            frame.add(visualizer);
            frame.setSize(800, 800);
            frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            frame.setVisible(true);

            // Prompt the user to save the graph as an image
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JPG Files", "*.jpg"));
            fileChooser.setInitialFileName("graph_output.jpg");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                GraphVisualizer.saveGraphAsImage(graph, saveFile.getAbsolutePath());
                outputArea.setText("Graph visualized and saved as an image at: " + saveFile.getAbsolutePath());
            }
        } catch (Exception e) {
            showErrorDialog("Error visualizing and saving graph: " + e.getMessage());
        }
    }

    private void validateXML() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Validate and get the corrected XML (if any)
            String correctedXML = XMLValidate.validation(inputXML);

            // Check if the XML was modified or not
            if (correctedXML.equals(inputXML)) {
                outputArea.setText("Valid XML.");
            } else {
                outputArea.setText("Invalid XML. Please check the corrected file.");

                // Prompt user to save the corrected XML file
                FileChooser fileChooser = new FileChooser();
                fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
                fileChooser.setInitialFileName("corrected.xml");
                File saveFile = fileChooser.showSaveDialog(null);

                if (saveFile != null) {
                    // Save the corrected XML content to the chosen file
                    XMLFormatter.formatXML(correctedXML, saveFile.getAbsolutePath());

                    // Display the saved corrected XML in the output area
                    outputArea.setText(correctedXML);
                }
            }
        } catch (Exception e) {
            showErrorDialog("Error processing XML: " + e.getMessage());
        }
    }

    private void prettifyXML() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            fileChooser.setInitialFileName("prettified.xml");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                XMLFormatter.formatXML(inputXML, saveFile.getAbsolutePath());
                outputArea.setText(new String(java.nio.file.Files.readAllBytes(saveFile.toPath())));
            }
        } catch (Exception e) {
            showErrorDialog("Error prettifying XML: " + e.getMessage());
        }
    }

    private void convertToJSON() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            fileChooser.setInitialFileName("output.json");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                NodeJson root = XMLToTreeConverter.parseXMLFromString(inputXML);
                String json = TreeToJSONConverter.convertToJSON(root);
                TreeToJSONConverter.writeToFile(json, saveFile.getAbsolutePath());
                outputArea.setText(new String(java.nio.file.Files.readAllBytes(saveFile.toPath())));
            }
        } catch (Exception e) {
            showErrorDialog("Error converting to JSON: " + e.getMessage());
        }
    }

    private void compressFile() {
        try {
            String inputText = xmlInputArea.getText();
            if (inputText.isEmpty()) {
                showErrorDialog("Input XML/JSON is empty. Please load or type content.");
                return;
            }

            // Write the input text to a temporary file
            File tempInputFile = File.createTempFile("temp_input_", ".txt");
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempInputFile))) {
                writer.write(inputText);
            }

            // Compress the file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("COMP Files", "*.comp"));
            fileChooser.setInitialFileName("compressed.comp");
            File compFile = fileChooser.showSaveDialog(null);

            if (compFile != null) {
                System.out.println("Saving compressed file to: " + compFile.getAbsolutePath());  // Debugging line
                Compressor.formatAndSaveCompressedFile(tempInputFile.getAbsolutePath(), compFile.getAbsolutePath());
                outputArea.setText(new String(java.nio.file.Files.readAllBytes(compFile.toPath())));
            } else {
                showErrorDialog("File save cancelled.");
            }

            // Optionally delete the temporary file after compression
            tempInputFile.delete();

        } catch (Exception e) {
            showErrorDialog("Error during compression: " + e.getMessage());
        }
    }

    private void decompressFile() {
        try {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("COMP Files", "*.comp"));
            File compressedFile = fileChooser.showOpenDialog(null);

            if (compressedFile != null) {
                FileChooser saveChooser = new FileChooser();
                saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
                saveChooser.setInitialFileName("decompressed.xml");
                File decompressedFile = saveChooser.showSaveDialog(null);

                if (decompressedFile != null) {
                    Decompressor.decompress(compressedFile.getAbsolutePath(), decompressedFile.getAbsolutePath());
                    outputArea.setText(new String(java.nio.file.Files.readAllBytes(decompressedFile.toPath())));
                }
            }
        } catch (Exception e) {
            showErrorDialog("Error during decompression: " + e.getMessage());
        }
    }

    private void minifyXML() {
        try {
            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            fileChooser.setInitialFileName("minified.xml");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                File tempInputFile = File.createTempFile("tempInput", ".xml");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempInputFile))) {
                    writer.write(inputXML);
                }

                XMLMinifier minifier = new XMLMinifier(tempInputFile.getAbsolutePath(), saveFile.getAbsolutePath());
                minifier.minify();

                String minifiedContent = new String(Files.readAllBytes(saveFile.toPath()));
                outputArea.setText(minifiedContent);

                tempInputFile.delete();
            }
        } catch (IOException e) {
            showErrorDialog("Error during XML minification: " + e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Unexpected error: " + e.getMessage());
        }
    }

    private void openFileChooser(TextArea xmlInputArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML/JSON Files", "*.xml"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                xmlInputArea.setText(reader.lines().reduce("", (a, b) -> a + "\n" + b));
            } catch (IOException e) {
                showErrorDialog("Error loading file: " + e.getMessage());
            }
        }
    }

    private void saveOutput(String output) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
        File file = fileChooser.showSaveDialog(null);
        if (file != null) {
            try (PrintWriter writer = new PrintWriter(file)) {
                writer.write(output);
            } catch (IOException e) {
                showErrorDialog("Error saving file: " + e.getMessage());
            }
        }
    }

    private void showErrorDialog(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
