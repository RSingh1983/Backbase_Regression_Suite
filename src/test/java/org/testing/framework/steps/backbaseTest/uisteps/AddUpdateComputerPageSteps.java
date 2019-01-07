package org.testing.framework.steps.backbaseTest.uisteps;

import net.thucydides.core.pages.Pages;
import org.testing.framework.model.uiModels.BeanFilePath;
import org.testing.framework.steps.uiSteps.UISteps;

import javax.annotation.Resource;

public class AddUpdateComputerPageSteps extends UISteps {

    @Resource
    private BeanFilePath addUpdateComputerPageBeanFilePath;

    public AddUpdateComputerPageSteps(Pages pages) {
        super(pages);
    }

    public String getBeanFilePath() {
        return addUpdateComputerPageBeanFilePath.getBeanFilePath();
    }
}

