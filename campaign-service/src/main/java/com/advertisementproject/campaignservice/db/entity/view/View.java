package com.advertisementproject.campaignservice.db.entity.view;

/**
 * @JsonView class for defining views, in other words to limit the information being shown in the JSON response. Only
 * the annotated fields will be shown when a view is set for a controller.
 */
public class View {

    public interface publicInfo {}

}
