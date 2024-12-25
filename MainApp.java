package com.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.*;
import java.nio.file.Files;
import java.util.Scanner;
import java.io.File;
public class MainApp extends Application {

    private BorderPane root;
    private TextArea xmlInputArea;
    private TextArea outputArea;
    @Override
    public void start(Stage primaryStage) {
        // Create the main layout
        root = new BorderPane();

        // Create input, button, and output sections
        VBox inputSection = createInputSection();
        VBox buttonSection = createButtonSection();
        VBox outputSection = createOutputSection();

        // Add sections to the layout
        root.setTop(inputSection);
        root.setCenter(buttonSection);
        root.setBottom(outputSection);

        // Set the scene and stage
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("XML Editor");
        primaryStage.show();
    }
    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        Label inputLabel = new Label("Input XML/JSON:");
        xmlInputArea = new TextArea();
        Button browseButton = new Button("Browse XML File");
        browseButton.setOnAction(event -> openFileChooser(xmlInputArea));
        inputSection.getChildren().addAll(inputLabel, xmlInputArea, browseButton);
        return inputSection;
    }
    private VBox createButtonSection() {
        VBox buttonSection = new VBox(10);
        Button prettifyButton = new Button("Prettify XML");
        Button convertToJSONButton = new Button("Convert to JSON");
        Button compressButton = new Button("Compress File");
        Button decompressButton = new Button("Decompress File");
        Button minifyButton = new Button("Minify XML");

        prettifyButton.setOnAction(event -> prettifyXML());
        convertToJSONButton.setOnAction(event -> convertToJSON());
        compressButton.setOnAction(event -> compressFile());
        decompressButton.setOnAction(event -> decompressFile());
        minifyButton.setOnAction(event -> minifyXML());

        buttonSection.getChildren().addAll(prettifyButton, convertToJSONButton, compressButton, decompressButton, minifyButton);
        return buttonSection;
    }

    private VBox createOutputSection() {
        VBox outputSection = new VBox(10);
        Label outputLabel = new Label("Output:");
        outputArea = new TextArea();
        outputArea.setEditable(false);
        Button saveButton = new Button("Save Output");
        saveButton.setOnAction(event -> saveOutput(outputArea.getText()));
        outputSection.getChildren().addAll(outputLabel, outputArea, saveButton);
        return outputSection;
    }
    private void prettifyXML() {
        try {
            // Get the input and output areas
            VBox inputSection = (VBox) root.getTop();
            TextArea xmlInputArea = (TextArea) inputSection.getChildren().get(1);
            VBox outputSection = (VBox) root.getBottom();
            TextArea outputArea = (TextArea) outputSection.getChildren().get(1);

            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Prompt user to select a location to save the prettified file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            fileChooser.setInitialFileName("prettified.xml");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                // Call the XMLFormatter's formatXML method to prettify and save the file
                XMLFormatter.formatXML(inputXML, saveFile.getAbsolutePath());

                // Read the prettified file and display its content in the output area
                try (Scanner scanner = new Scanner(saveFile)) {
                    StringBuilder prettifiedXML = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        prettifiedXML.append(scanner.nextLine()).append("\n");
                    }
                    outputArea.setText(prettifiedXML.toString());
                } catch (IOException e) {
                    showErrorDialog("Error reading prettified XML: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            showErrorDialog("Error prettifying XML: " + e.getMessage());
        }
    }
    private void convertToJSON() {
        try {
            // Get the input and output areas
            VBox inputSection = (VBox) root.getTop();
            TextArea xmlInputArea = (TextArea) inputSection.getChildren().get(1);
            VBox outputSection = (VBox) root.getBottom();
            TextArea outputArea = (TextArea) outputSection.getChildren().get(1);

            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Prompt user to select a location to save the JSON file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON Files", "*.json"));
            fileChooser.setInitialFileName("output.json");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                // Convert the XML content to a tree structure
                Node root = XMLToTreeConverter.parseXMLFromString(inputXML);

                // Convert the tree structure to JSON format
                String json = TreeToJSONConverter.convertToJSON(root);

                // Write the JSON data to the specified file
                TreeToJSONConverter.writeToFile(json, saveFile.getAbsolutePath());

                // Read the JSON file and display its content in the output area
                try (Scanner scanner = new Scanner(saveFile)) {
                    StringBuilder jsonOutput = new StringBuilder();
                    while (scanner.hasNextLine()) {
                        jsonOutput.append(scanner.nextLine()).append("\n");
                    }
                    outputArea.setText(jsonOutput.toString());
                } catch (IOException e) {
                    showErrorDialog("Error reading JSON: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            showErrorDialog("Error converting to JSON: " + e.getMessage());
        }
    }
    private void compressFile() {
        try {
            // Get the input and output areas
            VBox inputSection = (VBox) root.getTop();
            TextArea xmlInputArea = (TextArea) inputSection.getChildren().get(1);
            VBox outputSection = (VBox) root.getBottom();
            TextArea outputArea = (TextArea) outputSection.getChildren().get(1);

            String inputText = xmlInputArea.getText();
            if (inputText.isEmpty()) {
                showErrorDialog("Input XML/JSON is empty. Please load or type content.");
                return;
            }

            // Prompt user to choose the output file path for the compressed .comp file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("COMP Files", "*.comp"));
            fileChooser.setInitialFileName("compressed.comp");
            File compFile = fileChooser.showSaveDialog(null);

            if (compFile != null) {
                // Call the Compressor's compress method
                Compressor.formatAndSaveCompressedFile(inputText, compFile.getAbsolutePath());

                // Now, read the compressed file and display it in the output area
                try (DataInputStream compFileStream = new DataInputStream(new FileInputStream(compFile))) {
                    StringBuilder compressedContent = new StringBuilder();
                    while (compFileStream.available() > 0) {
                        int code = compFileStream.readInt();  // Read each compressed code (LZW codes)
                        compressedContent.append(code).append("\n");  // Append code for display
                    }

                    // Display the raw compressed codes in the output area
                    outputArea.setText(compressedContent.toString());

                } catch (IOException e) {
                    showErrorDialog("Error reading compressed file: " + e.getMessage());
                }
            }
        } catch (Exception e) {
            showErrorDialog("Error during compression: " + e.getMessage());
        }
    }

    private void decompressFile() {
        try {
            // Get the output section TextArea
            VBox outputSection = (VBox) root.getBottom();
            TextArea outputArea = (TextArea) outputSection.getChildren().get(1);

            // Prompt the user to choose the compressed file
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("COMP Files", "*.comp"));
            fileChooser.setTitle("Select Compressed File");
            File compressedFile = fileChooser.showOpenDialog(null);

            if (compressedFile != null) {
                // Prompt the user to choose where to save the decompressed file
                FileChooser saveChooser = new FileChooser();
                saveChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
                saveChooser.setInitialFileName("decompressed.xml");
                File decompressedFile = saveChooser.showSaveDialog(null);

                if (decompressedFile != null) {
                    // Call the Decompressor to decompress the file
                    Decompressor.decompress(compressedFile.getAbsolutePath(), decompressedFile.getAbsolutePath());

                    // Read the decompressed file and display its content
                    String decompressedContent = new String(Files.readAllBytes(decompressedFile.toPath()));
                    outputArea.setText(decompressedContent);

                    System.out.println("Decompressed content displayed in the output area.");
                }
            }
        } catch (IOException e) {
            showErrorDialog("Error during decompression: " + e.getMessage());
        } catch (Exception e) {
            showErrorDialog("Unexpected error: " + e.getMessage());
        }
    }
    private void minifyXML() {
        try {
            // Get the input and output areas
            VBox inputSection = (VBox) root.getTop();
            TextArea xmlInputArea = (TextArea) inputSection.getChildren().get(1);
            VBox outputSection = (VBox) root.getBottom();
            TextArea outputArea = (TextArea) outputSection.getChildren().get(1);

            String inputXML = xmlInputArea.getText();
            if (inputXML.isEmpty()) {
                showErrorDialog("Input XML is empty. Please load or type XML content.");
                return;
            }

            // Prompt user to select where to save the minified XML
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
            fileChooser.setInitialFileName("minified.xml");
            File saveFile = fileChooser.showSaveDialog(null);

            if (saveFile != null) {
                // Write the input XML to a temporary file
                File tempInputFile = File.createTempFile("tempInput", ".xml");
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(tempInputFile))) {
                    writer.write(inputXML);
                }

                // Minify the file using XMLMinifier
                XMLMinifier minifier = new XMLMinifier(tempInputFile.getAbsolutePath(), saveFile.getAbsolutePath());
                minifier.minify();

                // Read the minified content and display it in the output area
                String minifiedContent = new String(Files.readAllBytes(saveFile.toPath()));
                outputArea.setText(minifiedContent);

                // Cleanup temporary file
                tempInputFile.delete();

                System.out.println("Minified XML saved and displayed in the output area.");
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
            // Load file content into xmlInputArea
            try (Scanner scanner = new Scanner(file)) {
                StringBuilder content = new StringBuilder();
                while (scanner.hasNextLine()) {
                    content.append(scanner.nextLine()).append("\n");
                }
                xmlInputArea.setText(content.toString());
            } catch (Exception e) {
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
            } catch (Exception e) {
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

        //String inputFilePath = "D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\TestFx\\src\\main\\java\\com\\example\\sample.xml";  // Change this to your actual input file path
        //String outputFilePath = "file.comp";  // Output file as file.comp



    }

    // Method to read a file into a string
    public static String readFile(String filePath) throws IOException {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    // Method to write a string to a file
    public static void writeFile(String filePath, String data) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(data);
        }
    }
}
