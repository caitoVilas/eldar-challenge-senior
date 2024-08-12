package com.eldar.services.contracts;

import java.io.File;
import java.util.Map;

/**
 * @author caito Vilas
 * date: 08/2024
 * EmailService interface
 */
public interface EmailService {
    void sendEmail(String[] to, String subject, String body);
    void sendEmailWithAttachment(String[] to, String subject, String body, File file);
    void sendEmailWithTemplate(String[] to, String subject, String templateName, Map<String, String> data);
}
