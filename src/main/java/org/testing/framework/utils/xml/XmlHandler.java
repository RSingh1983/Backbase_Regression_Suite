package org.testing.framework.utils.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Map;

public class XmlHandler {

    private Document doc;
    private DocumentBuilder documentBuilder = null;
    private XPath xpath = null;
    private final static String XPATH_EXPRESSION = "//";

    /**
     * Default constructor
     *
     * @param doc The document to assign to the xml handler
     * @throws Exception
     */
    public XmlHandler(Document doc) throws Exception {
        this.doc = doc;
    }

    /**
     * This constructor will create an xml document from a given xml string
     *
     * @param xmlString The string to convert to an xml document
     * @throws Exception
     */
    public XmlHandler(String xmlString) throws Exception {
        doc = getXmlDocument(xmlString);
    }

    /**
     * This constructor will create an xml document from a given file
     *
     * @param xmlFile The file to convert to an xml document
     * @throws Exception
     */
    public XmlHandler(File xmlFile) throws Exception {
        doc = getXmlDocument(xmlFile);
    }

    /**
     * This will get the document associated with this instance
     * @return Document
     */
    public Document getDocument() {
        return doc;
    }

    /**
     * This will return the document as a string
     *
     * @return The string value of the document
     * @throws Exception
     */
    public String getDocumentAsString() throws Exception {
        DOMSource domSource = new DOMSource(this.doc);
        StringWriter writer = new StringWriter();
        StreamResult result = new StreamResult(writer);
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer = tf.newTransformer();
        transformer.transform(domSource, result);
        return writer.toString();
    }

    /**
     * This will create an xml document from a given xml string
     *
     * @param xmlString The string to convert to an xml document
     * @return	An xml Document
     * @throws Exception
     */
    private Document getXmlDocument(String xmlString) throws Exception {

        // Return the parsed xml file from the string
        return getDocumentBuilder().parse(new InputSource(new StringReader(xmlString)));
    }

    /**
     * This will create an xml document from a given xml file
     *
     * @param xmlFile The file to convert to an xml document
     * @return	An xml Document
     * @throws Exception
     */
    private Document getXmlDocument(File xmlFile) throws Exception {

        // Return the parsed xml file from the string
        return getDocumentBuilder().parse(new InputSource(new FileReader(xmlFile)));
    }

    /**
     * Gets a new document builder
     * @return A Document builder
     * @throws Exception
     */
    private DocumentBuilder getDocumentBuilder() throws Exception {

        if(documentBuilder == null) {
            // Create a new document factory
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            // Set Coalescing to true so that CDATA is accepted as text
            documentBuilderFactory.setCoalescing(true);
            documentBuilderFactory.setNamespaceAware(true);
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        }
        return documentBuilder;
    }

    /**
     * Gets a new instance of xpath
     *
     * @return xpath instance
     */
    private XPath getXPath() {
        if(xpath == null) {
            xpath = XPathFactory.newInstance().newXPath();
        }
        return this.xpath;
    }

    /**
     * This will get a list of nodes based on the xpath
     *
     * @param xpath The xpath expression used within the xml document
     * @return NodeList
     * @throws Exception
     */
    private NodeList getNodeList(String xpath) throws Exception {
        // Use xpath to find the field
        XPathExpression xPathExpression = getXPath().compile(xpath);
        return (NodeList) xPathExpression.evaluate(this.doc, XPathConstants.NODESET);
    }

    /**
     * This will get a list of nodes based on the xpath within the passed nodelist
     *
     * @param nodeList The NodeList to look inside
     * @param xpath The xpath expression used within the nodelist
     * @return NodeList
     * @throws Exception
     */
    private NodeList getNodeList(NodeList nodeList, String xpath) throws Exception {
        // Use xpath to find the field
        XPathExpression xPathExpression = getXPath().compile(xpath);
        return (NodeList) xPathExpression.evaluate(nodeList, XPathConstants.NODESET);
    }

    /**
     * This will get a list of nodes based on the tag name
     *
     * @param tag The tag name in the xml doc to find
     * @return NodeList
     * @throws Exception
     */
    private NodeList getNodeListByTag(String tag) throws Exception {
        // Use xpath to find the field
        return this.doc.getElementsByTagName(tag);
    }

    /**
     * This will get a string value from a unique field within the xml document
     *
     * @param field	The name of the field within the xml document to find
     * @return	The value of the xml field as a String
     * @throws Exception
     */
    public String getFieldValue(String field) throws Exception {

        // Use xpath to find the field
        NodeList nodes = getNodeList(generateXpathExpression(field));

        // If only one item is found
        if(nodes != null && nodes.getLength() == 1) {
            // Return the value found at the node
            return nodes.item(0).getChildNodes().item(0).getNodeValue();
        }
        // Else there was an error, so throw the appropriate message
        else {
            String exception;
            if (nodes == null || nodes.getLength() == 0) {
                exception = "Specified field \"" + field + "\" is not present in the document";
            } else {
                exception = "Specified field \"" + field + "\" is found more than one time";
            }
            throw new Exception(exception);
        }
    }

    /**
     * This will get multiple string values from a unique field within the xml document
     *
     * @param field	The name of the field within the xml document to find
     * @return	The values of the xml field as a String array
     * @throws Exception
     */
    public ArrayList<String> getFieldValues(String field) throws Exception {

        // Use xpath to find the field
        NodeList nodes = getNodeList(generateXpathExpression(field));

        // If not null
        if(nodes != null && nodes.getLength() != 0) {

            ArrayList<String> nodeStringList = new ArrayList<String>();

            // Get the size of the node list
            int size = nodes.getLength();

            // Add the value found at the node
            for(int i=0; i<size; i++) {
                nodeStringList.add(nodes.item(i).getChildNodes().item(0).getNodeValue());
            }

            return nodeStringList;
        }
        // Else there was an error, so throw the appropriate message
        else {
            throw new Exception("Specified field \"" + field + "\" is not present in the document");
        }
    }

    /**
     * This will set the passed node value with the passed value.
     * If there is more than one node found then it will throw an exception
     *
     * @param mapOfNodesAndValues The node and it's value to change contained in a Map
     * @throws Exception
     */
    private void setNodeValue(Map<String, String> mapOfNodesAndValues) throws Exception {

        // If there is values to change, then change them
        if(mapOfNodesAndValues != null) {
            for(Map.Entry<String, String> entry : mapOfNodesAndValues.entrySet()) {
                setFieldValue(entry.getKey(), entry.getValue());
            }
        }
    }

    /**
     * This will set a string value in a unique field within the xml document
     *
     * @param field	The name of the field within the xml document to find
     * @param value The value to add to the field as a String
     * @throws Exception
     */
    public void setFieldValue(String field, String value) throws Exception {
        this.setFieldValueUsingXpath(generateXpathExpression(field), value);
    }

    /**
     * This will set a string value in a field within an xml document using an xpath expression
     *
     * @param xpath	The xpath expression within the xml document to find
     * @param value The value to add to the field as a String
     * @throws Exception
     */
    public void setFieldValueUsingXpath(String xpath, String value) throws Exception {

        // Use xpath to find the field
        NodeList nodes = getNodeList(xpath);

        // Set the node value
        this.setNodeValue(nodes, value);
    }

    /**
     * This will set a string value in a field within a node list using an xpath expression
     *
     * @param nodeList The NodeList to look inside and change
     * @param xpath	The xpath expression used within the node list
     * @param value The value to add to the field as a String
     * @throws Exception
     */
    private void setFieldValueUsingXpath(NodeList nodeList, String xpath, String value) throws Exception {

        // Use xpath to find the node
        NodeList nodes = getNodeList(nodeList, xpath);

        // Set the node value
        this.setNodeValue(nodes, value);
    }

    /**
     * This will set the passed node value with the passed value.
     * If there is more than one node then it will throw an exception
     *
     * @param nodes The node to edit as a node list
     * @param value The value that the node should have
     * @throws Exception
     */
    private void setNodeValue(NodeList nodes, String value) throws Exception {
        // If only one item is found
        if(nodes != null && nodes.getLength() == 1) {
            // Set the value at the found node
            nodes.item(0).getChildNodes().item(0).setNodeValue(value);
        }
        // Else there was an error, so throw the appropriate message
        else {
            String exception;
            if (nodes == null) {
                exception = "Specified xpath \"" + xpath + "\" is not present in the document";
            } else {
                exception = "Specified xpath \"" + xpath + "\" is found more than one time";
            }
            throw new Exception(exception);
        }
    }

    /**
     * This will set multiple string values for a unique field within the xml document
     *
     * @param field	The name of the field within the xml document to find
     * @throws Exception
     */
    public void setFieldValues(String field,ArrayList<String> valuesList) throws Exception {

        // Use xpath to find the field
        NodeList nodes = getNodeList(generateXpathExpression(field));

        // If not null
        if(nodes != null && nodes.getLength() != 0) {

            //ArrayList<String> nodeStringList = new ArrayList<String>();

            // Get the size of the node list
            int sizeNodes = nodes.getLength();
            int sizeValues = valuesList.size();

            if(sizeValues >= sizeNodes) {

                // Add the value found at the node
                for(int i=0; i<sizeValues; i++) {
                    nodes.item(i).getChildNodes().item(0).setNodeValue(valuesList.get(i));
                }
            }
            else {
                throw new Exception("Values supplied for node "+ field +"are less than the number of Nodes Present");
            }

            //return nodeStringList;
        }
        // Else there was an error, so throw the appropriate message
        else {
            throw new Exception("Specified field \"" + field + "\" is not present in the document");
        }
    }


    /**
     * This will take a field value that can be separated by the '.' character and create
     * a valid xpath expression
     *
     * @param fields The field to create an expression for. Can be separated by a ',' char
     * @return A valid xpath expression
     */
    private String generateXpathExpression(String fields) {
        return XPATH_EXPRESSION + fields.replaceAll("\\.", XPATH_EXPRESSION);
    }

    /**
     * This will copy a node (field) and it's children and paste it as it's sibling. Can change the values of the copy using a node and values map
     *
     * @param node The node (or field) to copy
     * @param nodeAndValues The nodes within the node to copy node that requires a value change
     * @throws Exception
     */
    public void copyNodeToSibling(String node, Map<String, String> nodeAndValues) throws Exception {

        // Get the node list based on the field
        NodeList nodes = getNodeList(generateXpathExpression(node));

        // If there are found nodes
        if(nodes != null) {

            // Get the first node
            Node clonedNode = nodes.item(0).cloneNode(true);

            // If there is values to change, then change them
            if(nodeAndValues != null) {
                for(Map.Entry<String, String> entry : nodeAndValues.entrySet()) {
                    setFieldValueUsingXpath(clonedNode.getChildNodes(), entry.getKey(), entry.getValue());
                }
            }

            // Add the node as a sibling
            nodes.item(0).getParentNode().appendChild(clonedNode);
        }
    }
}

