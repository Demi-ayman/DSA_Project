import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main extends Application {

    private BorderPane root;
    private TextArea xmlInputArea;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Create the main layout with a background image
        ImageView backgroundImage = new ImageView(new Image("file:src/resources/background.jpg"));
        backgroundImage.setPreserveRatio(false);

        StackPane rootPane = new StackPane();
        rootPane.getChildren().add(backgroundImage);

        root = new BorderPane();
        rootPane.getChildren().add(root);

        // Create input, button, and output sections
        VBox inputSection = createInputSection();
        VBox buttonSection = createButtonSection(); // Buttons aligned to the right
        VBox outputSection = createOutputSection();

        // Add sections to the layout
        root.setTop(inputSection);
        root.setRight(buttonSection); // Buttons are now on the right
        root.setBottom(outputSection);

        // Set the scene and bind the background image size to the scene size
        Scene scene = new Scene(rootPane, 800, 600);
        backgroundImage.fitWidthProperty().bind(scene.widthProperty());
        backgroundImage.fitHeightProperty().bind(scene.heightProperty());

        primaryStage.setScene(scene);
        primaryStage.setTitle("XML Editor");
        primaryStage.show();
    }

    private VBox createInputSection() {
        VBox inputSection = new VBox(10);
        inputSection.setPadding(new Insets(10));
        inputSection.setAlignment(Pos.TOP_CENTER);

        Label inputLabel = new Label("Input XML:");
        inputLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");

        xmlInputArea = new TextArea();
        xmlInputArea.setPrefWidth(50); // Small width for one word
        xmlInputArea.setPrefHeight(40); // Small height for minimal text
        xmlInputArea.setStyle("-fx-background-color: #333; -fx-text-fill: #FFF; -fx-font-size: 12px; -fx-border-color: #1E88E5;");
        VBox.setVgrow(xmlInputArea, Priority.ALWAYS);

        Button browseButton = new Button("Browse XML File");
        styleButton(browseButton);
        browseButton.setOnAction(event -> openFileChooser(xmlInputArea));

        inputSection.getChildren().addAll(inputLabel, xmlInputArea, browseButton);
        return inputSection;
    }

    private VBox createButtonSection() {
        VBox buttonSection = new VBox(20); // Vertical layout with spacing
        buttonSection.setPadding(new Insets(20)); // Spacing from the page borders
        buttonSection.setAlignment(Pos.TOP_RIGHT); // Align buttons to the top right

        Button prettifyButton = new Button("Prettify XML");
        Button convertToJSONButton = new Button("Convert to JSON");
        Button saveButton = new Button("Save Output");

        styleButton(prettifyButton);
        styleButton(convertToJSONButton);
        styleButton(saveButton);

        prettifyButton.setOnAction(event -> prettifyXML());
        convertToJSONButton.setOnAction(event -> convertToJSON());
        saveButton.setOnAction(event -> saveOutput(outputArea.getText()));

        buttonSection.getChildren().addAll(prettifyButton, convertToJSONButton, saveButton);
        return buttonSection;
    }

    private void styleButton(Button button) {
        button.setStyle("-fx-font-size: 16px; -fx-background-color: linear-gradient(#1E88E5, #1565C0); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 15 25 15 25; -fx-background-radius: 10px; -fx-border-radius: 10px;");
        button.setOnMouseEntered(e -> button.setStyle("-fx-font-size: 16px; -fx-background-color: linear-gradient(#42A5F5, #1E88E5); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 15 25 15 25; -fx-background-radius: 10px; -fx-border-radius: 10px;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-font-size: 16px; -fx-background-color: linear-gradient(#1E88E5, #1565C0); -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 15 25 15 25; -fx-background-radius: 10px; -fx-border-radius: 10px;"));
    }

    private VBox createOutputSection() {
        VBox outputSection = new VBox(10);
        outputSection.setPadding(new Insets(10));
        outputSection.setAlignment(Pos.TOP_CENTER);

        Label outputLabel = new Label("Output:");
        outputLabel.setStyle("-fx-font-size: 14px; -fx-text-fill: #FFFFFF; -fx-font-weight: bold;");

        outputArea = new TextArea();
        outputArea.setPrefWidth(50); // Small width for one word
        outputArea.setPrefHeight(40); // Small height for minimal text
        outputArea.setEditable(false);
        outputArea.setStyle("-fx-background-color: #333; -fx-text-fill: #FFF; -fx-font-size: 12px; -fx-border-color: #1E88E5;");
        VBox.setVgrow(outputArea, Priority.ALWAYS);

        outputSection.getChildren().addAll(outputLabel, outputArea);
        return outputSection;
    }

    private void prettifyXML() {
        // Implementation omitted for brevity
    }

    private void openFileChooser(TextArea xmlInputArea) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML Files", "*.xml"));
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
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
        // Implementation omitted for brevity
    }

    public static void main(String[] args) {
        launch(args);
    }
}
