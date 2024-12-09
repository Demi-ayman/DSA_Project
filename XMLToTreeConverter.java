import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;

public class XMLToTreeConverter {

    public static Node parseXML(String filePath) {
        try {
            File file = new File(filePath);
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(file);
            document.getDocumentElement().normalize();

            return buildTree(document.getDocumentElement());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Node buildTree(Element element) {
        Node node = new Node(element.getTagName());

        // If the element has a text value, add it as the node's value
        if (element.getTextContent() != null && !element.getTextContent().trim().isEmpty()) {
            node.tagValue = element.getTextContent().trim();
        }

        // Add child nodes
        NodeList children = element.getChildNodes();
        for (int i = 0; i < children.getLength(); i++) {
            if (children.item(i).getNodeType() == org.w3c.dom.Node.ELEMENT_NODE) { // Fully qualify ELEMENT_NODE
                Element childElement = (Element) children.item(i);
                node.addChild(buildTree(childElement));
            }
        }

        return node;
    }
}
