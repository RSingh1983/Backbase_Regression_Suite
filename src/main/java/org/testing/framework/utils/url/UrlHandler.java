package org.testing.framework.utils.url;

import java.net.URL;

public class UrlHandler {

    public static String PATH_IDENTIFIER = "/";

    /**
     * A private constructor so that the use of this class is static only
     */
    private UrlHandler(){}

    /**
     * This will return the parameter value from the url string using the parameter name
     *
     * @param url Url as a string
     * @param parameterName Name of the parameter as a String
     * @return The parameter value or null if not found
     * @throws Exception
     */
    public static String getUrlParameterValue(String url, String parameterName) throws Exception {
        // Create a mew url
        URL newUrl = new URL(url);
        // Return the parameter value
        return getUrlParameterValue(newUrl, parameterName);
    }

    /**
     * This will return the parameter value from the URL using the parameter name
     *
     * @param url Url as a URL object
     * @param parameterName Name of the parameter as a String
     * @return The parameter value or null if not found
     */
    public static String getUrlParameterValue(URL url, String parameterName) {
        // Split the url query string up using the & character
        for(String param : url.getQuery().split("&")) {
            // For each parameter split it using the = character
            String[] tempParam = param.split("=");
            // If it has date
            if(tempParam.length > 1) {
                // And the parameter name is found
                if(tempParam[0].equals(parameterName)) {
                    // Return the value
                    return tempParam[1];
                }
            }
        }
        // Else its not found so return null
        return null;
    }

    /**
     * Append a path to an existing Url
     *
     * @param url The url to have a path appended as a string
     * @param path The path as a string to be appended to the url
     * @return A completed url as a String
     */
    public static String appendPathToUrl(String url, String path) throws Exception {
        // Return the appended url as a string
        return appendPathToUrl(new URL(url), path);
    }

    /**
     * Append a path to an existing Url
     *
     * @param url The url to have a path appended
     * @param path The path as a string to be appended to the url
     * @return A completed url as a String
     */
    public static String appendPathToUrl(URL url, String path) throws Exception {

        // If there is a path already
        if(!url.getPath().isEmpty()) {
            // Add the new path to the end after the path identifier
            path = url.getPath() + PATH_IDENTIFIER + path;
        }

        // Return the appended url as a string
        return new URL(url, path).toString();
    }
}

