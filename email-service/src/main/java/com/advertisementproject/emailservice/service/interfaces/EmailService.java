package com.advertisementproject.emailservice.service.interfaces;

public interface EmailService {

    void sendConfirmationLinkEmail(String toEmail, String name, String token);
}
