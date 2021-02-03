package com.advertisementproject.userservice.db.entity.types;

/**
 * Enum for which role a user has. Customer users have the role CUSTOMER. Company users have the role COMPANY and may
 * create campaigns in the Campaign Service application for instance. ADMIN users are manually created in the database
 * and have full access.
 */
public enum Role {
    CUSTOMER,
    COMPANY,
    ADMIN
}
