package org.testing.framework.utils.date;

import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

    static org.slf4j.Logger logger = LoggerFactory.getLogger(DateUtility.class.getName());

    public static final String GENERAL_DATE_FORMAT = "EEE,MMMM_dd_yyyy,HH:mm:ss:zzz";
    public static final String CASHBACK_FORMAT = "dd_MM_yyyy_00_00_00";
    public static final String DEFAULT_DATE_FORMAT = "EEE MMMM dd HH:mm:ss zzz yyyy";
    public static final String DATABASE_DATE_FORMAT1 = "yyyy-MM-dd hh:mm:ss";
    public static final String DATABASE_DATE_FORMAT2 = "yyyy-MM-dd hh:mm:ss.S";
    public static final String DATABASE_DATE_FORMAT3 = "yyyy-MM-dd HH:mm:ss.S";
    public static final String TIMEZONE_FORMAT = "EEE MMM dd HH:mm:ss zzz yyyy";
    public static final String BULK_UPLOAD_FORMAT = "dd_MM_yyyy_HH_mm_ss";
    public static final String SIMPLE_FORMAT = "ddMMHHmmss";
    public static final String BRAND_SUBCATEGORY_URSEFUL_LINKS_FORMAT = "EEE,dd MMMMM yyyy HH:mm";
    public static final String TIMESTAMP = "HHmmssSSS";

    public static final DateFormat DEFAULT_FORMATTER = new SimpleDateFormat(
            DEFAULT_DATE_FORMAT);

    public static void main(String[] ss) {
        DateUtility dateutils = new DateUtility();
        System.out.println(dateutils.convertDateFormat(new Date(), CASHBACK_FORMAT));
    }

    /**
     * convert from original date format to required date format
     *
     * @param original_date_pattern
     * @param original_date
     * @param required_date_pattern
     * @return date in required format
     */
    public static String convertDateFormat(String original_date_pattern,
                                    Date original_date, String required_date_pattern) {
        DateFormat original_date_format = new SimpleDateFormat(
                original_date_pattern);
        DateFormat required_date_format = new SimpleDateFormat(
                required_date_pattern);
        Date temp = null;
        try {
            temp = (Date) original_date_format.parse(original_date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String required_date = required_date_format.format(temp);

        logger.info("original date : " + original_date + " required date : "
                + required_date);

        return required_date.toString();

    }

    public static String convertDateFormat(String original_date_pattern,
                                    String original_date, String required_date_pattern) {
        DateFormat original_date_format = new SimpleDateFormat(
                original_date_pattern);
        DateFormat required_date_format = new SimpleDateFormat(
                required_date_pattern);
        Date temp = null;
        try {
            temp = (Date) original_date_format.parse(original_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String required_date = required_date_format.format(temp);

        logger.info("original date : " + original_date + " required date : "
                + required_date);

        return required_date.toString();

    }

    /**
     * Formats the given Date object with the required format
     *
     * @param original_date
     * @param required_date_pattern
     * @return
     */
    public static String convertDateFormat(Date original_date,
                                    String required_date_pattern) {

        DateFormat required_date_format = new SimpleDateFormat(
                required_date_pattern);
        Date temp = null;
        try {
            temp = (Date) DEFAULT_FORMATTER.parse(original_date.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        String required_date = required_date_format.format(temp);

        logger.info("original date : " + original_date + " required date : "
                + required_date);

        return required_date.toString();

    }

    public static Date getDateObjectFromString(String pattern, String date) {
        DateFormat date_format = new SimpleDateFormat(pattern);
        Date temp = null;
        try {
            temp = date_format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    public static String convertDateToFormat(Date original_date,
                                      String required_date_pattern) {

        DateFormat required_date_format = new SimpleDateFormat(
                required_date_pattern);

        String required_date = required_date_format.format(original_date);

        logger.info("original date : " + original_date + " required date : "
                + required_date);

        return required_date.toString();

    }

    public static String getCurrentTime(boolean sleep) {
//        if (sleep)
//            GenericFunctionHandler.sleep(1l);
        return convertDateFormat(new Date(), SIMPLE_FORMAT);
    }

    /**
     * This will return the current timestamp as a String
     *
     * @return
     */
    public static String getTimeStamp() {
        //wait for a milli second to avoid duplicate timestamp
//        GenericFunctionHandler.sleepMilli(1l);
        return String.valueOf(new Date().getTime());
    }

    public static Integer getAgeFromDOB(Date birthDate) {

        int years = 0;
        int months = 0;
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(birthDate.getTime());
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;
        months = currMonth - birthMonth;
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            now.add(Calendar.MONTH, -1);
        } else if (now.get(Calendar.DATE) == birthDay.get(Calendar.DATE) && months == 12) {
            years++;
            months = 0;
        }
        logger.info("Date : " + birthDate + " Age : " + years);
        return years;
    }

    public static Date addDays(Date date, int days)
    {
        date.setTime(date.getTime() + days * 1000 * 60 * 60 * 24);
        return date;
    }
}
