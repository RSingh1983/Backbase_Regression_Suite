package org.testing.framework.utils.csv;

import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

import org.testing.framework.utils.commonFunctions.GenericFunctionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CsvParser {

    static Logger logger = LoggerFactory.getLogger(CsvParser.class.getName());

    // Variables
    private static final String DEFAULT_SEPARATOR = ";";
    private String SEPARATOR = "";

    /**
     * Constructor
     *
     * Sets the separator to the default
     */
    public CsvParser() {
        // Set separator to default
        this.SEPARATOR = DEFAULT_SEPARATOR;
    }

    /**
     * Constructor that takes the separator as an argument
     *
     * @param split	A value as a string that is to be used to split the csv file fields
     */
    public CsvParser(String split) {
        this.SEPARATOR = split;
    }

    /**
     * Getter for the separator
     *
     * @return Separator as a string
     */
    public String getSeparator() {
        return SEPARATOR;
    }

    /**
     * Setter for the separator
     *
     * @param sep	Sets the separtor to the given string
     */
    public void setSeparator(String sep) {
        SEPARATOR = sep;
    }

    /**
     * This will take a csv file location and create a date table model from it
     *
     * @param csvLocation A csv file location as a string
     *
     * @return An ArrayList of the csv file
     */
    public ArrayList<ArrayList<String>> createArrayListFromCSVFile(String csvLocation) {
        return this.createArrayListFromCSVFile(new File(csvLocation));
    }

    /**
     * This will take a csv file and create a date table model from it
     *
     * @param file A csv file location as a File
     * @return An ArrayList of the csv file
     */
    public ArrayList<ArrayList<String>> createArrayListFromCSVFile(File file) {
        // Set up the array list to be used
        ArrayList<ArrayList<String>> data = null;
        // Set up the buffered reader to be used
        BufferedReader bufRead = null;

        // Try and read from the file
        try {
            // Open up a file input stream to the file
            FileInputStream inStream = new FileInputStream(file);

            // Get a buffered reader to this stream via an input stream reader (the input stream reader will take the byte stream from fileInputStream
            // and make it into a character stream)
            bufRead = new BufferedReader(new InputStreamReader(inStream, "iso-8859-15"));

            // Create a array list of strings that will hold the date
            data = new ArrayList<ArrayList<String>>();

            // While there are lines to be read
            while (bufRead.ready()) {
                // Split the line using a delimiter and place it into an object array, set limit to -1 to allow empty trailing columns to be read
                String[] row = bufRead.readLine().split(SEPARATOR, -1);
                // Convert this to an arrayList
                ArrayList<String> rowList = new ArrayList<String>(Arrays.asList(row));
                // Add it to the date array list
                data.add(rowList);
            }
        }
        // Catch the exception if any and display to the screen
        catch (Exception ex) {
            logger.warn(ex.getMessage());
        }
        // Finally do this
        finally {
            // Close the connections
            try {
                bufRead.close();
            }
            // Catch errors here
            catch (Exception e) {
                // Ignore
            }
        }

        // Return this created table model
        return data;
    }

    /**
     * This will take a csv file as a string and create a date list model from it
     *
     * @param csvString A csv file as a string
     *
     * @return An ArrayList of the csv file
     */
    public ArrayList<ArrayList<String>> createArrayListFromCSVString(String csvString) {

        // Get a list of lines from the string
        ArrayList<String> lineList = GenericFunctionHandler.getLinesFromStringAsList(csvString);

        // Create a array list of strings that will hold the date
        ArrayList<ArrayList<String>> data = new ArrayList<ArrayList<String>>();

        // While there are lines to be read
        for(String line : lineList) {
            // Split the line using a delimiter and place it into an object array, set limit to -1 to allow empty trailing columns to be read
            String[] row = line.split(SEPARATOR, -1);
            // Convert this to an arrayList
            ArrayList<String> rowList = new ArrayList<String>(Arrays.asList(row));
            // Add it to the date array list
            data.add(rowList);
        }

        return data;
    }

    /**
     * This will write a file from a given array list string
     *
     * @param fileName The file name as a String
     * @param list The text to be written to file
     */
    public void writeArrayListToCsvFile(String fileName, ArrayList<ArrayList<String>> list) {

        FileWriter output = null;
        BufferedWriter writer = null;

        try {
            output = new FileWriter(fileName);
            writer = new BufferedWriter(output);

            // For each row in the array list, join each element using the separator then write it
            for(ArrayList<String> row : list) {
                writer.write(StringUtils.join(row, this.SEPARATOR));
                writer.write("\n");
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            if (output != null) {
                try {
                    writer.close();
                    output.close();
                } catch (IOException e) {
                    // Ignore issues during closing
                }
            }
        }
    }

    /**
     * This will get the column number for the given header name of an array list, it assumes the header is on the first row
     *
     * @param list The array list of an array of strings
     * @param columnName The header name to be found as a String
     * @return The location as an int or -1 if not found
     */
    public static int getColumnNumberFromHeader(ArrayList<ArrayList<String>> list, String columnName) {
        return getColumnNumberFromHeader(list, columnName, 0);
    }

    /**
     * This will get the column number for the given header name of an array list
     *
     * @param list The array list of an array of strings
     * @param columnName The header name to be found as a String
     * @param headerRowNumber The row that the header is located on
     * @return The location as an int or -1 if not found
     */
    public static int getColumnNumberFromHeader(ArrayList<ArrayList<String>> list, String columnName, int headerRowNumber) {
        return list.get(headerRowNumber).lastIndexOf(columnName);
    }

    /**
     * This will delete a column from a csv list
     *
     * @param csvList A csv file as an array list of strings
     * @param columnName The column name to delete
     * @throws Exception Produced when column name is not valid
     */
    public static void deleteColumn(ArrayList<ArrayList<String>> csvList, String columnName) throws Exception {
        // Get the column number using the column name then delete
        deleteColumn(csvList, getColumnNumberFromHeader(csvList, columnName));
    }

    /**
     * This will delete a column from a csv list
     *
     * @param csvList A csv file as an array list of strings
     * @param columnNumber The column number to delete
     * @throws Exception Produced when column number is not valid or an empty array
     */
    public static void deleteColumn(ArrayList<ArrayList<String>> csvList, int columnNumber) throws Exception  {

        if(csvList.size() > 0) {
            // Get header size
            int headerSize = csvList.get(0).size();

            // Check the column number is valid
            if((columnNumber < -1) || (columnNumber > csvList.get(0).size())) {
                throw new Exception("Column number: " + columnNumber + " not found. List size is: " + csvList.get(0).size());
            }

            // Delete the column from every row
            for(ArrayList<String> row : csvList) {
                // If a row is not the same column count as the header then ignore this row
                if(row.size() == headerSize) {
                    row.remove(columnNumber);
                } else {
                    logger.warn("Row found that does not contain the same column count as the header, row ignored on delete");
                }
            }
        } else {
            throw new Exception("ArrayList is empty");
        }
    }
}

