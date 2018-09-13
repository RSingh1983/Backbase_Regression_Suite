package org.testing.framework.model.uiModels;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class WebElementsModel {

    private enum URLType {
        hostname
    }
    private HashMap<String, String> urlMap;
    private HashMap<String, String> loadTimeout;
    private String ClickSelector;
    private String inputSelector;
    private String comboSelector;
    private String textSelector;

    public HashMap<String, String> getUrlMap() {
        return urlMap;
    }

    public void setUrlMap(Map<String, String> urlMap) {
        this.urlMap = (HashMap<String, String>)urlMap;
    }

    public HashMap<String, String> getLoadTimeout() {
        return loadTimeout;
    }

    public void setLoadTimeout(Map<String, String> loadTimeout) {
        this.loadTimeout = (HashMap<String, String>)loadTimeout;
    }

    public String getHostname() {
        return urlMap.get(URLType.hostname.name());
    }

    public String getClickSelector() {
        return ClickSelector;
    }

    public void setClickSelector(String ClickSelector) {
        this.ClickSelector = ClickSelector;
    }

    public String getInputSelector() {
        return inputSelector;
    }

    public void setInputSelector(String inputSelector) {
        this.inputSelector = inputSelector;
    }

    public String getComboSelector() {
        return comboSelector;
    }

    public void setComboSelector(String comboSelector) {
        this.comboSelector = comboSelector;
    }

    public String getTextSelector() {
        return textSelector;
    }

    public void setTextSelector(String textSelector) {
        this.textSelector = textSelector;
    }
}

