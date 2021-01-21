package com.advertisementproject.campaignservice.db.model.types;

/**
 * In order to allow expansion into other markets we have added Currency as an enum,
 * but since the original market is only Sweden the currency will be set to SEK as
 * default.
 */
public enum Currency {
    SEK,
    USD,
    EUR
}
