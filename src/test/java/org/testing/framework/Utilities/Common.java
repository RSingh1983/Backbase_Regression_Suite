package org.testing.framework.Utilities;

import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Common {

    private static boolean logSleep;
    static org.slf4j.Logger logger = LoggerFactory.getLogger(Common.class.getName());
    /**
     * Creates a java.util.logging.LOGGER
     *
     * @param classname class defining the LOGGER
     * @param filename  log file
     * @param append
     * @return new LOGGER instance
     */
    public final static Logger createLogger(Class classname, String path, String filename,
                                                              boolean append) {

        SimpleDateFormat date_format = new SimpleDateFormat("dd-MM-yyyy");
        String date = date_format.format(new Date());
        filename = filename + "." + date;
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        String completePath = path + "/" + filename
                + ".txt";
        Logger LOGGER = null;
        try {
            LOGGER = Logger.getLogger(classname.getName());

            LOGGER.setLevel(Level.ALL);
            FileHandler handler = new FileHandler(completePath, append);

            handler.setFormatter(new SimpleFormatter());
            LOGGER.addHandler(handler);
            LOGGER.setUseParentHandlers(false);
        } catch (SecurityException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return LOGGER;
    }

    public static void loadProperties(Properties properties, File file) {
        InputStream input = null;
        try {
            input = new FileInputStream(file.getAbsolutePath());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        try {
            if (input != null)
                properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String getMySqlInStatement(Collection<?> dataValues) {
        if ((dataValues == null) || (dataValues.size() == 0)) {
            throw new IllegalArgumentException();
        }
        StringBuilder sql = new StringBuilder("(");
        StringBuilder colValues = new StringBuilder();
        for (Object data : dataValues) {
            colValues.append("'").append(data).append("',");
        }
        sql.append(colValues.substring(0, (colValues.length() - 1)))
                .append(")");
        return sql.toString();
    }

    public static String getSanitizedContent(String content) {
        return removeAllWhiteSpaces(removeHTMLTags(content));
    }

    private static String removeHTMLTags(String str) {
        return str.replaceAll("\\<.*?\\>", "");
    }

    private static String removeAllWhiteSpaces(String str) {
        return str.replaceAll("\\s+", "");
    }


    public static void printStackTrace() {
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();
        for (StackTraceElement e : stackTraceElements)
            logger.info(e.getClassName() + "." + e.getMethodName());
    }

    public static void setLogSleep(boolean logSleep) {
        Common.logSleep = logSleep;
    }

    public static void sleep(int minutes) {

        if (logSleep) logger.info("\nSleeping for " + minutes + " minute(s)\n");
        try {
            Thread.sleep(minutes * 60 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(long seconds) {

        if (logSleep) logger.info("\nSleeping for " + seconds + " second(s)\n");
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleepMilli(long milliseconds) {
        if (logSleep) logger.info("\nSleeping for " + milliseconds + " millisecond(s)\n");
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void logStackTrace(Logger LOG, int index) {
        StackTraceElement[] stackTraceElements = Thread.currentThread()
                .getStackTrace();
        try {
            LOG.finest(stackTraceElements[index].getClassName() + "."
                    + stackTraceElements[index].getMethodName());
        } catch (ArrayIndexOutOfBoundsException e) {
            LOG.warning(e.toString());
        }
    }

    public static void showRunningThreads() {
        ThreadGroup currentGroup =
                Thread.currentThread().getThreadGroup();
        int noThreads = currentGroup.activeCount();
        Thread[] lstThreads = new Thread[noThreads];
        currentGroup.enumerate(lstThreads);
        for (int i = 0; i < noThreads; i++)
            logger.info("Thread No:" + i + " = "
                    + lstThreads[i].getName());
    }

    public static String decodeURL(String url) {
        String decoded = null;
        try {
            decoded = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } finally {
            return decoded;
        }
    }

    public static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return sb.toString();
        }
    }

    public static List<String> getStringListFromInputStream(InputStream is) {
        List<String> strings = new ArrayList<>();
        BufferedReader br = null;

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                strings.add(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return strings;
        }
    }

    public static Date getRemoteMachineDate(String remoteServerUrl) {
        Date remoteDate = null;
        URL url;
        URLConnection urlConn = null;
        try {
            url = new URL(remoteServerUrl);
            urlConn = url.openConnection();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HttpURLConnection conn = (HttpURLConnection) urlConn;
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
        conn.setInstanceFollowRedirects(true);
        conn.setRequestProperty("User-agent", "spider");
        try {
            conn.connect();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, List<String>> header = conn.getHeaderFields();
        for (String key : header.keySet()) {
            if (key != null && "Date".equals(key)) {
                List<String> data = header.get(key);
                String dateString = data.get(0);
                SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss");
                try {
                    remoteDate = sdf.parse(dateString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
        logger.info("\nremoteServerUrl: " + remoteServerUrl + "\nremoteDate: " + remoteDate);
        return remoteDate;
    }


    public static String replaceSystemProperties(String key) {
        String dollar = "$", startBrace = "{", endBrace = "}";
        String path = key;
        Integer index = key.indexOf(dollar);
        while (index != -1) {
            int braceEndIndex = path.indexOf(endBrace);
            String property = path.substring(index + 2, braceEndIndex);
            String propertyValue = System.getProperty(property);
            path = path.replace(new StringBuilder(dollar).append(startBrace).append(property).append(endBrace), propertyValue);
            index = path.indexOf("$");
        }
        return path;
    }
}

