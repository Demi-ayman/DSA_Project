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
import java.io.*;
import java.nio.file.Files;

public class Main extends Application {

    private BorderPane root;
    private TextArea xmlInputArea;
    private TextArea outputArea;

    @Override
    public void start(Stage primaryStage) {
        // Start Page Background Image
        Image startBackground = new Image("file:src/photos/Mina.jpg");
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
        Image mainBackground = new Image("file:src/photos/Yous.jpg");
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
        VBox buttonSection = new VBox(15);
        buttonSection.setPadding(new Insets(55, 22, 22, 22));  // Slightly more padding to move buttons down
        buttonSection.setAlignment(Pos.TOP_LEFT);


        String buttonStyle = "-fx-background-color: #87CEEB; -fx-text-fill: white; -fx-font-size: 16px;";

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

        buttonSection.getChildren().addAll(prettifyButton, convertToJSONButton, compressButton, decompressButton, minifyButton);
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
                Node root = XMLToTreeConverter.parseXMLFromString(inputXML);
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

            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("COMP Files", "*.comp"));
            fileChooser.setInitialFileName("compressed.comp");
            File compFile = fileChooser.showSaveDialog(null);

            if (compFile != null) {
                Compressor.formatAndSaveCompressedFile(inputText, compFile.getAbsolutePath());
                outputArea.setText("File compressed successfully.");
            }
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
