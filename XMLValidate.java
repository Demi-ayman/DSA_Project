package com.example;

import java.io.*;
import java.util.*;

public class XMLValidate {

    public static void main(String[] args) {
        if (args.length < 3 || !args[0].equals("verify")) {
            System.out.println("Usage: xml_editor verify -i input_file.xml -f -o output_file.xml");
            return;
        }

        String inputFile = null;
        String outputFile = null;
        boolean fix = false;

        // Parse command-line arguments
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    inputFile = args[++i];
                    break;
                case "-f":
                    fix = true;
                    break;
                case "-o":
                    outputFile = args[++i];
                    break;
                default:
                    System.out.println("Invalid argument: " + args[i]);
                    return;
            }
        }

        if (inputFile == null) {
            System.out.println("Input file is required. Use -i <input_file.xml>");
            return;
        }

        try {
            // Read XML content
            String xmlContent = readXMLFile(inputFile);
            if (xmlContent.isEmpty()) {
                System.out.println("Input file is empty or could not be read.");
                return;
            }

            // Validate or Fix XML
            String result = validation(xmlContent);

            if (fix && outputFile != null) {
                // Save the corrected XML
                File output = new File(outputFile);
                try (java.io.FileWriter writer = new java.io.FileWriter(output)) {
                    writer.write(result);
                }
                System.out.println("Corrected XML saved to: " + outputFile);
            }
            // No need to print the full XML if fix is not enabled
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Function to validate and fix XML string
    public static String validation(String xml) {
        Stack<String> st = new Stack<>();
        StringBuilder str = new StringBuilder();
        StringBuilder check = new StringBuilder();
        StringBuilder str_to_fix = new StringBuilder();
        boolean valid = true;
        int i;

        for (i = 0; i < xml.length(); i++) {
            // Check for opening tag
            if (xml.charAt(i) == '<' && xml.charAt(i + 1) != '/') {
                str.setLength(0); // Reset the string builder
                for (int j = i + 1; j < xml.length(); j++) {
                    if (xml.charAt(j) == '>' || xml.charAt(j) == ' ') {
                        break;
                    } else {
                        str.append(xml.charAt(j));
                    }
                }
                st.push(str.toString());
            }
            // Check for closing tag
            else if (xml.charAt(i) == '<' && xml.charAt(i + 1) == '/') {
                check.setLength(0); // Reset the string builder
                for (int k = i + 2; k < xml.length(); k++) {
                    if (xml.charAt(k) == '>') {
                        break;
                    } else {
                        check.append(xml.charAt(k));
                    }
                }

                if (!st.isEmpty() && check.toString().equals(st.peek())) {
                    st.pop();
                } else {
                    valid = false;

                    // If there's a mismatch, try to fix it
                    if (!st.isEmpty() && st.peek() != null) {
                        str_to_fix.setLength(0);
                        str_to_fix.append("</").append(st.peek()).append(">");
                        st.pop();
                    } else if (check != null) {
                        str_to_fix.setLength(0);
                        str_to_fix.append("<").append(check).append(">");
                        st.push(check.toString());
                    } else if (!st.isEmpty() && !check.toString().isEmpty() && st.peek() != null) {
                        str_to_fix.setLength(0);
                        str_to_fix.append("</").append(st.peek()).append(">").append("<").append(check).append(">");
                    }

                    // Fix the XML by inserting the corrected tag
                    xml = new StringBuilder(xml).insert(i, str_to_fix.toString()).toString();
                    // Call validation again after modification (avoid deep recursion)
                    return validation(xml); // Adjust recursion flow
                }
            }
        }

        // If stack is not empty after parsing, fix by adding missing closing tags
        if (i == xml.length() && !st.isEmpty()) {
            str_to_fix.setLength(0);
            str_to_fix.append("</").append(st.peek()).append(">");
            xml = new StringBuilder(xml).append(str_to_fix.toString()).toString();
        }

        // Print the validity message
        if (valid && st.isEmpty()) {
            System.out.println("XML is valid.");
        } else {
            System.out.println("XML is invalid.");
        }

        return xml; // Return the (possibly corrected) XML, but do not print it
    }

    // Function to read the content of an XML file
    public static String readXMLFile(String filename) {
        StringBuilder xmlContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                xmlContent.append(line);
            }
        } catch (IOException e) {
            System.err.println("Failed to open file: " + filename);
        }
        return xmlContent.toString();
    }
}
