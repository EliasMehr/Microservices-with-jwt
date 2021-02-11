package com.advertisementproject.userservice.db.entity.types;

/**
 * Enum for what type a company is
 */
public enum CompanyType {
    /**
     * The company is in the retail industry
     */
    RETAIL,
    /**
     * The company is in the telecom industry
     */
    TELECOM,
    /**
     * The company is in the health industry
     */
    HEALTH,
    /**
     * The company is in the restaurant industry
     */
    RESTAURANT,
    /**
     * The company is in the transportation industry
     */
    TRANSPORTATION,
    /**
     * The company is in the software industry
     */
    SOFTWARE,
    /**
     * The company is in another type of industry not available in these company type options
     */
    OTHER,
    /**
     * The company is unwilling to disclose their industry
     */
    NOT_SPECIFIED
}