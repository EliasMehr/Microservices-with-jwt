package com.advertisementproject.campaignservice.db.entity.view;

/**
 * @JsonView class for defining views, in other words to limit the information being shown in the JSON response. Only
 * the annotated fields will be shown when a view is set for a controller.
 */
public class View {

    /**
     * @JsonView limits the information shown to the user. Used for defining what to show as public information,
     * which is limited compared to full the information for an object. Only annotated fields will be shown when
     * this view is set for a controller.
     */
    public interface publicInfo {
    }

}
