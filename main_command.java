class main_command {
    public static void main(String[] args) {
        try {
            if (args.length < 4 || !args[0].equals("xml_editor") || !args[1].equals("json")) {
                System.out.println("Usage: xml_editor json -i input_file.xml -o output_file.json");
                return;
            }
            String inputFile = args[3];
            String outputFile = args[5];
            Node root = XMLToTreeConverter.parseXML(inputFile);
            String json = TreeToJSONConverter.convertToJSON(root);
            TreeToJSONConverter.writeToFile(json, outputFile);
            System.out.println("Conversion completed. JSON saved to " + outputFile);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}