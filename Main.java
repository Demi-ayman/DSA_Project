package org.example;
import java.io.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
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