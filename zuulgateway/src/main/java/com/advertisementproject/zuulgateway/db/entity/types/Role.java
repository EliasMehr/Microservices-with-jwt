package com.advertisementproject.zuulgateway.db.entity.types;

/**
 * Enum for the different roles a user may have. Used to identify which endpoints a user should be able to access.
 */
public enum Role {
    /**
     * Customer users have access to a limited amount of features, such as changing their own data, viewing published
     * campaigns and retrieving discount codes for those campaigns.
     */
    CUSTOMER,
    /**
     * Company users have access to all that a customer user has and may in addition create and manage campaigns. A
     * company user may not access or manipulate other users' data, other than viewing published campaigns.
     */
    COMPANY,
    /**
     * Admin users have full access to the system and may modify other users' data. If necessary, an admin user may
     * revoke other users' permission to use the system.
     */
    ADMIN
}
