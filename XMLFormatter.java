package com.example;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;
import java.io.*;

public class XMLFormatter {

    public static void main(String[] args) {
        if (args.length < 4 || !args[0].equals("format")) {
            System.out.println("Usage: xml_editor format -i input_file.xml -o output_file.xml");
            return;
        }

        String inputFile = null;
        String outputFile = null;

        // Parse command-line arguments
        for (int i = 1; i < args.length; i++) {
            switch (args[i]) {
                case "-i":
                    inputFile = args[++i];
                    break;
                case "-o":
                    outputFile = args[++i];
                    break;
                default:
                    System.out.println("Invalid argument: " + args[i]);
                    return;
            }
        }

        if (inputFile == null || outputFile == null) {
            System.out.println("Both input and output files are required.");
            return;
        }

        try {
            // Read XML content from file
            String xmlContent = readXMLFile(inputFile);
            if (xmlContent.isEmpty()) {
                System.out.println("Input file is empty or could not be read.");
                return;
            }

            // Format and save XML
            formatXML(xmlContent, outputFile);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Function to format and write XML to file
    public static void formatXML(String xml, String outputFilePath) throws Exception {
        // Remove unnecessary newlines and extra spaces between tags
        xml = xml.replaceAll(">\\s*<", "><");

        // Convert the string content into an InputStream to work with DOM
        InputStream inputStream = new ByteArrayInputStream(xml.getBytes());

        // Create a DocumentBuilder to parse the input XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(inputStream);

        // Create a Transformer to pretty-print the XML
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();

        // Set output properties for no extra line breaks
        transformer.setOutputProperty(OutputKeys.INDENT, "yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4"); // 4 spaces indentation
        transformer.setOutputProperty(OutputKeys.METHOD, "xml");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");

        // Convert DOM to string and write to output file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFilePath))) {
            // Write the XML to the output file with no extra newlines
            transformer.transform(new DOMSource(document), new StreamResult(writer));
            System.out.println("Formatted XML saved to " + outputFilePath);
        }
    }

    // Function to read the content of an XML file
    public static String readXMLFile(String filename) {
        StringBuilder xmlContent = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                xmlContent.append(line).append("\n");
            }
        } catch (IOException e) {
            System.err.println("Failed to open file: " + filename);
        }
        return xmlContent.toString();
    }
}
