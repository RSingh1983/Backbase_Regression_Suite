package org.testing.framework.utils.commonFunctions;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericFunctionHandler {

    static Logger logger = LoggerFactory.getLogger(GenericFunctionHandler.class.getName());

    private final static String NEW_LINE = "\n";

    private GenericFunctionHandler(){}


    /**
     * This will return the current timestamp as a String
     * @return
     */
    public static String getTimeStamp() {
        return String.valueOf(new Date().getTime());
    }

    /**
     * This will generate a random number between a given range
     *
     * @param min The minimum value in the range as an int
     * @param max The maximum value in the range as an int
     * @return
     */
    public static int generateRandomNumberInRange(int min, int max) {

        // Create a random variable
        Random rand = new Random();

        // Generate a random number using the rand variable
        // nextInt is normally exclusive of the top value so add 1 to make it inclusive
        int randomNum = rand.nextInt((max - min) + 1) + min;

        return randomNum;
    }

    public static String generateRandomMail() {

        //Generate a random alphabetic string within the given range

        String email = "test." + RandomStringUtils.randomAlphabetic(5) + "@at.com";
        logger.info("generated email: " + email);
        return email;
    }

    /**
     * This will return an array of lines from a string
     *
     * @param string The string to split into lines
     * @return
     */
    public static String[] getLinesFromString(String string) {
        if (string.isEmpty()) {
            return new String[0];
        } else {
            return string.split(NEW_LINE, -1);
        }
    }

    /**
     * This will return an ArrayList of lines from a string
     *
     * @param string The string to split into lines
     * @return
     */
    public static ArrayList<String> getLinesFromStringAsList(String string) {
        return getStringAsList(string, NEW_LINE);
    }

    /**
     * This will return an ArrayList from a string using the split char
     *
     * @param string    The string to split into lines
     * @param splitChar The charater to use in the split process
     * @return
     */
    public static ArrayList<String> getStringAsList(String string, String splitChar) {
        if (string.isEmpty()) {
            return new ArrayList<String>();
        } else {
            return new ArrayList<String>(Arrays.asList(string.split(splitChar, -1)));
        }
    }

//    @Step
    public static void sleep(long seconds) {

        logger.debug("\nSleeping for " + seconds + " second(s)\n");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(int minutes) {

        logger.debug("\nSleeping for " + minutes + " minute(s)\n");
        try {
            Thread.sleep(minutes * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepMilli(long milliseconds) {
        logger.debug("\nSleeping for " + milliseconds + " millisecond(s)\n");
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String appendQuotes(String valueToAppend) {
        StringBuilder builder=new StringBuilder("\"");
        builder.append(valueToAppend);
        builder.append("\"");
        return builder.toString();
    }
}
