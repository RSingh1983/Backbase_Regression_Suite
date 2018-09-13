package org.testing.framework.utils.xml;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

public class XmlValidator {

    static Logger logger = LoggerFactory.getLogger(XmlValidator.class.getName());

    /**
     * A private constructor so that the use of this class is static only
     */
    private XmlValidator(){}

    /**
     * This will assert whether the field is not found in the xml file
     *
     * @param xml The xml as a string
     * @param field The field or node to find in the xml
     * @throws Exception
     */
    public static void isFieldNotPresentInXml(String xml, String field) throws Exception {

        String testValue = null;
        try{
            // Get the xml document
            XmlHandler xmlHandler = new XmlHandler(xml);

            // Find the value from the document
            testValue = xmlHandler.getFieldValue(field);
            assertTrue(testValue != null);

        } catch (Exception e) {
            logger.info("Exception Raised :- " + e.getMessage());
            assertTrue(e.getMessage().contains("Specified field \"" + field + "\" is not present in the document"));
        }

    }

    /**
     * This will assert whether the value is found in the xml file
     *
     * @param xml The xml as a string
     * @param field The field or node to find in the xml
     * @param value The value to compare against
     * @throws Exception
     */
    public static void isValuePresentInXmlField(String xml, String field, String value) throws Exception {
        // Get the xml document
        XmlHandler xmlHandler = new XmlHandler(xml);
        // Find the value from the document
        String testValue = xmlHandler.getFieldValue(field);

        // Assert whether its been found
        assertTrue(value.trim().equals(testValue.trim()));
    }

    /**
     * This will assert whether the value is found in the xml file
     *
     * @param doc The xml document as a Document
     * @param field The field or node to find in the xml
     * @param value The value to compare against
     * @throws Exception
     */
    public static void isValuePresentInXmlField(Document doc, String field, String value) throws Exception {
        // Get the xml document
        XmlHandler xmlHandler = new XmlHandler(doc);
        // Find the value from the document
        String testValue = xmlHandler.getFieldValue(field);

        // Assert whether its been found
        assertTrue(value.trim().equals(testValue.trim()));
    }

    /**
     * This will assert whether multiple same values are found in the xml file
     *
     * @param xml The xml as a string
     * @param field The field or node to find in the xml
     * @param value The value to compare against
     * @throws Exception
     */
    public static void isValuesPresentInXmlField(String xml, String field, String value) throws Exception {

        // Get the xml document
        XmlHandler xmlHandler = new XmlHandler(xml);
        // Find the values from the document
        ArrayList<String> testValues = xmlHandler.getFieldValues(field);

        boolean found = true;

        for(String listValue : testValues) {
            if(!listValue.equalsIgnoreCase(value)) {
                found = false;
                break;
            }
        }

        // Assert whether its been found
        assertTrue(found);
    }

    /**
     * This will assert whether multiple different values are found in the xml file
     *
     * @param xml The xml as a string
     * @param field The field or node to find in the xml
     * @param values The values to compare against, the list must be ordered as expected
     * @throws Exception
     */
    public static void isValuesPresentInXmlField(String xml, String field, ArrayList<String> values) throws Exception {
        // Get the xml document
        XmlHandler xmlHandler = new XmlHandler(xml);
        // Find the values from the document
        ArrayList<String> testValues = xmlHandler.getFieldValues(field);

        if(testValues.size() != values.size()) {
            logger.info("Size of Existing Nodes " + testValues.size());
            logger.info("Size of Expected Nodes " + values.size());
            throw new Exception("Result xml and compare values are not the same size");
        }

        boolean found = true;

        for(int i=0;i<values.size();i++)
            if(!testValues.contains(values.get(i))) {
                logger.info(values.get(i) + " value not found for tag " + field);
                found = false;
                break;
            }

        // Assert whether its been found
        assertTrue(found);
    }
}
