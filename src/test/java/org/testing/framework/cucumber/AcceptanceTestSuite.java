package org.testing.framework.cucumber;

import cucumber.api.CucumberOptions;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import net.serenitybdd.junit.spring.integration.SpringIntegrationClassRule;
import net.thucydides.core.annotations.Step;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        plugin = {"pretty", "html:target/cucumber-html-report"},
        tags = {"@live"},
        features = "src/test/resources/features"
)
public class AcceptanceTestSuite {
    @Rule
    SpringIntegrationClassRule rule = new SpringIntegrationClassRule();
}