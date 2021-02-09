package com.advertisementproject.emailservice.service.interfaces;

/**
 * Service that sends an email with a confirmation token link. When the user clicks this confirmation link it will go
 * to an endpoint in the Confirmation Token Service that confirms the token included in the link.
 * <p>
 * Relevant settings for the email server are found in application.yml in the resources folder.
 */
public interface EmailService {

    /**
     * Sends a confirmation link email with the supplied email details
     *
     * @param toEmail the email address to send an email to
     * @param name    the full name or company name of the user
     * @param token   the token that should be included in the confirmation link
     */
    void sendConfirmationLinkEmail(String toEmail, String name, String token);
}
