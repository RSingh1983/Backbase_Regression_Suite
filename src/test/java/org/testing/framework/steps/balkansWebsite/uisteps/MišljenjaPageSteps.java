package org.testing.framework.steps.balkansWebsite.uisteps;

import net.thucydides.core.pages.Pages;
import org.testing.framework.model.uiModels.BeanFilePath;
import org.testing.framework.steps.uiSteps.UISteps;

import javax.annotation.Resource;

public class MišljenjaPageSteps extends UISteps {

    @Resource
    private BeanFilePath balkansWebsiteMišljenjaPageBeanFilePath;

    public MišljenjaPageSteps(Pages pages) {
        super(pages);
    }

    public String getBeanFilePath() {
        return balkansWebsiteMišljenjaPageBeanFilePath.getBeanFilePath();
    }
}

