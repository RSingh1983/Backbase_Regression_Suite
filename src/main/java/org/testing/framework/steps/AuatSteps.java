package org.testing.framework.steps;

import net.thucydides.core.annotations.Step;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.thucydides.core.steps.ScenarioSteps;
import net.thucydides.core.pages.Pages;

@ContextConfiguration(locations = "/applicationContext.xml")
@Component
public abstract class AuatSteps extends ScenarioSteps {


    private static final long serialVersionUID = -6181218211075957591L;
    private static final String ENV_PROP_LOCATION = "/properties/";

    static Logger logger = LoggerFactory.getLogger(AuatSteps.class.getName());

    Properties prop = new Properties();
    Properties commonProp = new Properties();
    private static final String COMMON_PROP_LOCATION = "/properties/common.properties";
    public static final String LOG_DB_RESULTS = "log.db.results";
    public static boolean logDBResult = false;

    public AuatSteps(Pages pages) {
        super(pages);

        // load properties
        try {
            if (System.getProperty("env") != null)
                prop.load(AuatSteps.class.getResourceAsStream(ENV_PROP_LOCATION + System.getProperty("env") + ".properties"));
            else
                prop.load(AuatSteps.class.getResourceAsStream(ENV_PROP_LOCATION + "at" + ".properties"));
            commonProp.load(AuatSteps.class.getResourceAsStream(COMMON_PROP_LOCATION));
            setProperties();
        } catch (IOException e) {
            logger.error("properties can't be loaded", e);
        }
    }


    public String getProperty(String key) {
        return prop.getProperty(key);
    }

    public String getCommonProperty(String key) {
        return commonProp.getProperty(key);
    }

    public void setProperties() {
        logDBResult = getPropertyValue(LOG_DB_RESULTS);
    }

    private boolean getPropertyValue(String property) {
        String logDbResultStr = System.getProperty(property);
        if (null == logDbResultStr)
            logDbResultStr = getCommonProperty(property);
        if (null != logDbResultStr)
            return Boolean.valueOf(logDbResultStr);
        return false;
    }

    @Step
    public void logMessage(String message) {
        logger.info("log message:" + message);
    }
}

