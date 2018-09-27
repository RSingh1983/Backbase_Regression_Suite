package org.testing.framework.properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

public class LoadProjectProperties {

    private static final Resource resource = new ClassPathResource("/project.properties");
    private static Properties props;

    public static final String WEBDRIVER_PAGELOAD_MAXTIMEOUT_SEC = "webdriver.pageload.maxtimeout.sec";
    public static final String WEBDRIVER_ELEMENTLOAD_MAXTIMEOUT_SEC = "webdriver.elementload.maxtimeout.sec";
    public static final String ASSERT_TEXT_DELIMITER = "assert.text.delimiter";
    public static final String REPLACE_TEXT_DELIMITER = "replace.text.delimiter";
    public static final String REPLACE_CHARACTER = "replace.character";
    public static final String REPLACE_CHARACTER_2 = "replace.character.2";
    public static final String REPLACE_CHARACTER_3 = "replace.character.3";
    public static final String REPLACE_CHARACTER_4 = "replace.character.4";
    public static final String AJAX_BUSY_IDENTIFIER = "ajax.busy.identifier";

    //API related properties
    public static final String API_PARAM_DELIMITER = "api.param.delimiter";
    public static final String API_PARAM_VALUE_DELIMITER = "api.param.value.delimiter";
    public static final String API_PARAM_LIST_SEPARATOR = "api.param.list.separator";
    public static final String API_PARAM_LIST_STARTCHAR = "api.param.list.startchar";
    public static final String API_PARAM_LIST_ENDCHAR = "api.param.list.endchar";
    public static final String API_PARAM_VALUE_QUERYCHAR = "api.param.value.querychar";
    public static final String API_PARAM_REMOVE = "api.param.remove";
    public static final String API_PARAM_CALLBACK_CHAR = "api.param.callback.char";
    public static final String API_PARAM_CALLBACK_PARAM_DELIMITER = "api.param.callback.param.delimiter";

    public static final String QUERY_KEY_DELIMITER = "query.key.delimiter";
    public static final String MAP_SEPARATOR_CHAR = "map.separator.char";
    public static final String MAP_KEY_VALUE_DELIMITER = "map.key.value.delimiter";
    public static final String MAP_KEY_DELIMITER = "map.key.delimiter";

    public static final String MAX_SSH_SESSIONS = "MaxSSHSession";
    public static final String MAX_PORT_FORWARDING = "MaxPortForwarding";

    public static final String MAX_WAIT_TIME = "max.wait.time";
    public static final String MAX_DB_RETRIES = "max.db.retries";

    public static String getStringProperty(String identifier) throws IOException {
        if (props == null) {
            props = PropertiesLoaderUtils.loadProperties(resource);
        }
        return props.getProperty(identifier);
    }

    public static long getNumberProperty(String identifier) throws IOException {
        if (props == null) {
            props = PropertiesLoaderUtils.loadProperties(resource);
        }
        return Long.parseLong(props.getProperty(identifier));
    }
}
