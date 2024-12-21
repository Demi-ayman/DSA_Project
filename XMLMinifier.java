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
                // Process the line character by character
                for (char c : line.toCharArray()) {
                    // Skip whitespace outside of tags
                    if (Character.isWhitespace(c) && !insideTag) {
                        continue;
                    }

                    // If entering a tag, set the flag
                    if (c == '<') {
                        insideTag = true;
                    }

                    // Write the character to the output file
                    outFile.write(c);

                    // If exiting a tag, reset the flag
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

    public static void main(String[] args) {
        // Specify the input and output file paths directly in the code
        String inputFile = "D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\sample.xml";  // Replace with your input file path
        String outputFile = "D:\\CSE-Senior 1\\Fall 25\\DSA\\Project\\output.xml";  // Replace with your desired output file path

        // Create the minifier and call the minify function
        XMLMinifier minifier = new XMLMinifier(inputFile, outputFile);
        minifier.minify();
    }
}



