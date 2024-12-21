import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.*;
import java.io.*;
import java.util.Scanner;
import java.io.File;
public class Main extends Application {

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
        Label inputLabel = new Label("Input XML:");
        xmlInputArea = new TextArea();
        Button browseButton = new Button("Browse XML File");
        browseButton.setOnAction(event -> openFileChooser(xmlInputArea));
        inputSection.getChildren().addAll(inputLabel, xmlInputArea, browseButton);
        return inputSection;
    }

    private VBox createButtonSection() {
        VBox buttonSection = new VBox(10);
        Button prettifyButton = new Button("Prettify XML");
        // Placeholder buttons for future features
        Button convertToJSONButton = new Button("Convert to JSON");
        convertToJSONButton.setOnAction(event -> convertToJSON());
        prettifyButton.setOnAction(event -> prettifyXML());
        buttonSection.getChildren().addAll(prettifyButton, convertToJSONButton);
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

    private void openFileChooser(TextArea xmlInputArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
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






    public static void main(String[] args) {
        //launch(args);

    }
}

