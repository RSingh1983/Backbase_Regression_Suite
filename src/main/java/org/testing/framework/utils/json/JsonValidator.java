package org.testing.framework.utils.json;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertTrue;

public class JsonValidator {

    static Logger logger = LoggerFactory.getLogger(JsonValidator.class.getName());

    /**
     * A private constructor so that the use of this class is static only
     */
    private JsonValidator(){}

    /**
     * This will assert whether a json object is the same as a POJO object
     *
     * @param json	The json object as a string
     * @param object	The object to compare this json to
     * @param objectClass	The class of the object
     */
    public static void isJsonEqualToObject(String json, Object object, Class<?> objectClass) {
        assertTrue(object.equals(JsonHandler.jsonToObject(json, objectClass)));
    }

    /**
     * This will assert whether the value is found in the json file in the given field
     *
     * @param json The json as a string
     * @param field The field or node to find in the json
     * @param value The value to compare against
     * @throws Exception
     */
    public static void isValuePresentInJsonField(String json, String field, String value) throws Exception {
        // Assert whether its been found
        assertTrue(JsonHandler.isFieldValuePresentInJson(json, field, value));
    }

    /**
     * This will assert whether the value is found in the json array in the given field
     *
     * @param json The json as a string
     * @param field The field or node to find in the json
     * @param value The value to compare against
     * @throws Exception
     */
    public static void isValuePresentInJsonArray(String json, String field, String value) throws Exception {
        logger.info("Looking for value: " + value + ", in field: " + field);
        // Assert whether its been found
        assertTrue(JsonHandler.isFieldValuePresentInJsonArray(json, field, value));
    }
}
