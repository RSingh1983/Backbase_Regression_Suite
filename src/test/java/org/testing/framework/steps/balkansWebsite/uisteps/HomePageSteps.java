package org.testing.framework.steps.balkansWebsite.uisteps;

import net.thucydides.core.pages.Pages;
import org.testing.framework.model.uiModels.BeanFilePath;
import org.testing.framework.steps.uiSteps.UISteps;

import javax.annotation.Resource;

public class HomePageSteps extends UISteps {

    @Resource
    private BeanFilePath balkansWebsiteHomePageBeanFilePath;

    public HomePageSteps(Pages pages) {
        super(pages);
    }

    public String getBeanFilePath() {
        return balkansWebsiteHomePageBeanFilePath.getBeanFilePath();
    }
}

