package com.advertisementproject.userservice.db.entity.types;

/**
 * Enum for which role a user has. Customer users have the role CUSTOMER. Company users have the role COMPANY and may
 * create campaigns in the Campaign Service application for instance. ADMIN users are manually created in the database
 * and have full access.
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
