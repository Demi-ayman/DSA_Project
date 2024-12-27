package org.example;
import java.io.*;


public class XMLMinifier {
    private String inputFile;
    private String outputFile;

    // Constructor
    public XMLMinifier(String input, String output) {
        this.inputFile = input;
        this.outputFile = output;
    }

    // Function to perform the minification
    public void minify() {
        try (BufferedReader inFile = new BufferedReader(new FileReader(inputFile));
             BufferedWriter outFile = new BufferedWriter(new FileWriter(outputFile))) {

            String line;
            boolean insideTag = false;

            while ((line = inFile.readLine()) != null) {
                for (char c : line.toCharArray()) {
                    // Skip all whitespace characters when outside a tag
                    if (Character.isWhitespace(c) && !insideTag) {
                        continue;
                    }

                    // When entering a tag
                    if (c == '<') {
                        insideTag = true;
                    }

                    // Write the character
                    outFile.write(c);

                    // When exiting a tag
                    if (c == '>') {
                        insideTag = false;
                    }
                }
            }

            System.out.println("Minified XML saved to " + outputFile);

        } catch (FileNotFoundException e) {
            System.err.println("Error: Could not open input file.");
        } catch (IOException e) {
            System.err.println("Error: Could not process the files.");
        }
}
}
// this is the main of the xml minifier
public class Main {
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        try {
            System.out.print("Enter the input XML file path: ");
            String inputFile = reader.readLine();

            System.out.print("Enter the output XML file path: ");
            String outputFile = reader.readLine();

            // Create an instance of XMLMinifier
            XMLMinifier minifier = new XMLMinifier(inputFile, outputFile);

            // Perform the minification
            minifier.minify();
        } catch (IOException e) {
            System.err.println("Error: Failed to read input.");
        }
    }
}
