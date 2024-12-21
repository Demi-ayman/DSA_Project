import java.io.*;

public class XMLFormatter {

    // Function to format XML and save to file
    public static void formatXML(String xml, String outputFilePath) {
        if (xml == null || xml.isBlank()) {
            System.err.println("Error: XML content is empty or null.");
            return;
        }

        try (BufferedWriter outFile = new BufferedWriter(new FileWriter(outputFilePath))) {
            int indentLevel = 0;
            boolean inTag = false;
            boolean preserveInline = false;

            for (int i = 0; i < xml.length(); i++) {
                char ch = xml.charAt(i);

                // Determine tag type
                if (ch == '<') {
                    boolean isClosingTag = (i + 1 < xml.length() && xml.charAt(i + 1) == '/');
                    boolean isSelfClosingTag = (i + 1 < xml.length() && xml.charAt(xml.indexOf('>', i) - 1) == '/');

                    // Adjust indentation for closing tags
                    if (isClosingTag) {
                        indentLevel--;
                    }

                    // Indent for new tags unless preserving inline
                    if (!inTag && !preserveInline) {
                        outFile.write(" ".repeat(Math.max(0, indentLevel * 4)));
                    }

                    // Write the tag
                    inTag = true;
                    preserveInline = false;
                }

                // Write the character
                outFile.write(ch);

                // End of a tag
                if (ch == '>') {
                    inTag = false;

                    // Determine inline or multiline formatting
                    if (xml.charAt(i - 1) == '/') { // Self-closing tag
                        preserveInline = false;
                    } else if (i + 1 < xml.length() && xml.charAt(i + 1) != '<') { // Inline text
                        preserveInline = true;
                    } else { // Start new indentation for nested tags
                        if (!preserveInline) {
                            indentLevel++;
                        }
                        preserveInline = false;
                    }

                    // Add newline after closing tag unless preserving inline
                    if (!preserveInline) {
                        outFile.write("\n");
                    }
                }
            }

            System.out.println("Formatted XML saved to " + outputFilePath);

        } catch (IOException e) {
            System.err.println("Error: Could not write to file " + outputFilePath);
            e.printStackTrace();
        }
    }
}