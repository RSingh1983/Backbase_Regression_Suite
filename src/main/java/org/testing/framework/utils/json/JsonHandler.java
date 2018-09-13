package org.testing.framework.utils.json;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JsonHandler {

    static Logger logger = LoggerFactory.getLogger(JsonHandler.class.getName());

    private JsonHandler(){}

    /**
     * This will take a json object as a string and a POJO class and try and read the values
     * from the json into an object of the class
     * <p>
     * The return object must be cast to the class to be of use
     *
     * @param json	Json object as a string
     * @param classToConvertTo	A POJO class to use to convert the json into to
     * @return	An object of the POJO class
     */
    public static Object jsonToObject(String json, Class<?> classToConvertTo){

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a default empty object
        Object object = new Object();

        try {
            // Try and read the values of the json to the POJO class
            object = mapper.readValue(json, classToConvertTo);
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return the object
        return object;
    }

    /**
     * This will take a POJO object and try and convert it into a valid json string
     *
     * @param object	A POJO object to convert from
     * @return	A valid json string
     */
    public static String objectToString(Object object){

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create an empty string
        String jsonString = "";

        try {
            // Convert a POJO to a json string
            jsonString = mapper.writeValueAsString(object);
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return the json string
        return jsonString;
    }

    /**
     * This will take a json object as a String, find a field and then return the edited json back as a String
     * <p>
     * This works for a nested value, for cases where the json has multiple fields called the same name.
     *
     * @param json The json object as a String
     * @param field The field in the json to find. Separate fields using a '.' character. Must be a complete path or contain a unique field name.
     * @return
     */
    public static String getFieldValueFromJson(String json, String field) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return string
        String returnJsonFieldValue = "";

        try {
            // Read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Set up a found node
            JsonNode foundNode = rootNode;

            // Get an array of fields to search using the character delimiter
            String[] fieldArray = field.split("\\.");

            // For each field then find the node
            for(String split : fieldArray) {
                // Find the field in the json
                foundNode = foundNode.findPath(split);
            }

            // If the node is found
            if(!foundNode.isMissingNode()) {
                // Get the field value as text
                returnJsonFieldValue = foundNode.getTextValue();
            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return the found value
        return returnJsonFieldValue;
    }

    /**
     * This will search for a value in the json string and return if found or not
     *
     * @param json The json object as a String
     * @param field The field in the json to get the value from
     * @param value The value that is to be searched for
     * @return
     */
    public static boolean isFieldValuePresentInJson(String json, String field, String value) {

        // Create a return boolean
        boolean found = false;

        // Get the value from the json field and see if it's the same
        if(getFieldValueFromJson(json, field).equals(value)) {
            found = true;
        }

        // Return whether the value was found or not
        return found;
    }

    /**
     * This will search for a value in the json string and return if found or not
     * It searches the whole json array for the value, this can find multiple cases
     *
     * @param json The json object as a String
     * @param field The field in the json to get the value from
     * @param value The value that is to be searched for
     * @return
     */
    public static boolean isFieldValuePresentInJsonArray(String json, String field, String value) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return boolean
        boolean found = true;

        try {
            // read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Find the field values in the json
            List<String> foundValues = rootNode.findValuesAsText(field);

            // If the value is found in the list for all field instances
            for(String listvalue : foundValues) {
                logger.info("Field value " + listvalue);
                if(!listvalue.contains(value)) {
                    found = false;
                }
            }

        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return whether the value was found or not
        return found;
    }

    /**
     * This will take a json object as a String, find and edit a field and then return the edited json back as a String
     *
     * @param json The json object as a String
     * @param field The field in the json to edit
     * @param value The value that the field should be changed to
     * @return
     */
    public static String editFieldValueInJson(String json, String field, String value) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return string
        String returnJson = "";

        try {
            // read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Find the field in the json
            JsonNode foundNode = rootNode.findPath(field);

            // If the node is found
            if (!foundNode.isMissingNode()) {
                ObjectNode parent = ((ObjectNode)rootNode.findParent(field));
                parent.remove(field);
                // Change it's value
                parent.put(field, value);
                // Write the root node back to a string
                returnJson = mapper.writeValueAsString(rootNode);
            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return the edited json or an empty string
        return returnJson;
    }


    public static String removeFieldInJson(String json, String field) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return string
        String returnJson = "";

        try {
            // read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Find the field in the json
            JsonNode foundNode = rootNode.findPath(field);

            // If the node is found
            if (!foundNode.isMissingNode()) {
                ObjectNode parent = ((ObjectNode)rootNode.findParent(field));
                parent.remove(field);
                // Write the root node back to a string
                returnJson = mapper.writeValueAsString(rootNode);
            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return the edited json or an empty string
        return returnJson;
    }

    /**
     * This will take a json object as a String, find and edit a field and then return the edited json back as a String
     * <p>
     * This works for a nested value, for cases where the json has multiple fields called the same name.
     *
     * @param json The json object as a String
     * @param field The field in the json to edit. Separate fields using a '.' character. Must be a complete path or contain a unique field name.
     * @param value The value that the field should be changed to
     * @return
     */
    public static String editNestedFieldValueInJson(String json, String field, String value) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return string
        String returnJson = "";

        try {
            // Read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Set up a found node and parent node to keep track of the nodes
            JsonNode foundNode = rootNode;
            JsonNode parentNode = rootNode;

            // Get an array of fields to search using the character delimeter
            String[] fieldArray = field.split("\\.");

            // For each field then find the node
            for(String split : fieldArray) {
                // Find the field in the json, keeping a link to the parent node
                parentNode = foundNode;
                foundNode = foundNode.findPath(split);
            }

            // If the node is found
            if(!foundNode.isMissingNode()) {

                // Change the value of the found node using the parent node
                ((ObjectNode) parentNode).put(fieldArray[fieldArray.length-1], value);

                // Write the root node back to a string
                returnJson = mapper.writeValueAsString(rootNode);
            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        }

        // Return the edited json or an empty string
        return returnJson;
    }

    /**
     * This will take a json object as a String, find parent and add an array field and then return the edited json back as a String
     * <p>
     * This works for multiple values, for cases where the json has multiple fields called the same name.
     *
     * @param json The json object as a String
     * @param fields The field in the json to edit. Separate fields using a '.' character. Must be a complete path or contain a unique field name.
     * @param values The value that the field should be changed to
     * @return
     */
    public static String addArrayElementsInJson(String json, String parent, String fields, String values) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        String[] fieldArray = fields.split("\\,");
        String[] valArray = values.split("\\,");

        // Create a return string
        String returnJson = "";

        try {
            // read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Find the field in the json
            JsonNode foundNode = rootNode.findPath(parent);

            // If the node is found
            if(!foundNode.isMissingNode()) {

                // Change it's value
                logger.info("Parent Node " + ((ArrayNode) rootNode.findParent(parent).get(parent)));
                ArrayNode arrNode = ((ArrayNode) rootNode.findParent(parent).get(parent));
                arrNode.removeAll();

                //If only one field name is provided, create nodes equal to value array
                if(fieldArray.length == 1){
                    for (int i = 0; i < valArray.length; i++) {
                        arrNode.addObject().put(fieldArray[0], valArray[i]);
                    }
                    // Write the root node back to a string
                    returnJson = mapper.writeValueAsString(rootNode);
                } else if(fieldArray.length == valArray.length) {
                    for (int i = 0; i < valArray.length; i++) {
                        arrNode.addObject().put(fieldArray[i], valArray[i]);
                    }
                    // Write the root node back to a string
                    returnJson = mapper.writeValueAsString(rootNode);
                } else {
                    throw new Exception ("Array length of field and values are not same");
                }
            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        // Return the edited json or an empty string
        return returnJson;
    }


    public static String addListElementsInJson(String json, String field, List<String> values) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return string
        String returnJson = "";

        try {
            // read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Find the field in the json
            JsonNode foundNode = rootNode.findPath(field);

            // If the node is found
            if (!foundNode.isMissingNode()) {

                // Change it's value
                logger.info("Parent Node " + ((ArrayNode) rootNode.findParent(field).get(field)));
                ArrayNode arrNode = ((ArrayNode) rootNode.findParent(field).get(field));
                arrNode.removeAll();

                for (String value:values) {
                    arrNode.add(value);
                }
                // Write the root node back to a string
                returnJson = mapper.writeValueAsString(rootNode);
            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        // Return the edited json or an empty string
        return returnJson;
    }

    /**
     * This will take a json object as a String, and return subobject with specified field-value
     * <p>
     * This works for multiple values, for cases where the json has multiple fields called the same name.
     *
     * @param json The json object as a String
     * @param field The field in the json to edit. Separate fields using a '.' character. Must be a complete path or contain a unique field name.
     */
    public static ArrayNode getJsonSubObject(String json,String field) {

        // Create an objectmapper
        ObjectMapper mapper = new ObjectMapper();

        // Create a return string
        ArrayNode arrNode = null;

        try {
            // read the json file to a root node
            JsonNode rootNode = mapper.readTree(json);

            // Find the field in the json
            JsonNode foundNode = rootNode.findPath(field);

            // If the node is found
            if(!foundNode.isMissingNode()) {

                // Change it's value
                arrNode =  ((ArrayNode) rootNode.findParent(field).get(field));

            }
        } catch (JsonGenerationException e) {
            logger.warn(e.getMessage());
        } catch (JsonMappingException e) {
            logger.warn(e.getMessage());
        } catch (IOException e) {
            logger.warn(e.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }

        // Return the edited json or an empty string
        return arrNode;
    }
}
