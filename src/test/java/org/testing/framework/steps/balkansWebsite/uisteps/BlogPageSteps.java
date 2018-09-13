package org.testing.framework.steps.balkansWebsite.uisteps;

import net.thucydides.core.pages.Pages;
import org.testing.framework.model.uiModels.BeanFilePath;
import org.testing.framework.steps.uiSteps.UISteps;

import javax.annotation.Resource;

public class BlogPageSteps extends UISteps {

    @Resource
    private BeanFilePath balkansWebsiteBlogPageBeanFilePath;

    public BlogPageSteps(Pages pages) {
        super(pages);
    }

    public String getBeanFilePath() {
        return balkansWebsiteBlogPageBeanFilePath.getBeanFilePath();
    }
}

