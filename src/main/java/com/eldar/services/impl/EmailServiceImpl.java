package com.eldar.services.impl;

import com.eldar.api.exceptions.customs.EmailSendingdException;
import com.eldar.services.contracts.EmailService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * @author caito Vilas
 * date: 08/2024
 * Email service implementation
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender javaMailSender;
    @Value("${application.email}")
    private String from;
    private final String ERROR_MESSAGE = "no se pudo enviar el e-mail";
    private final String ERROR_TEMPLATE = "no se pudo cargar el template";

    /**
     * Send email
     * @param to
     * @param subject
     * @param body
     */
    @Override
    public void sendEmail(String[] to, String subject, String body){
        log.info("Sending email to: {}", to);
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(to);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
        }catch (Exception e){
            log.error(ERROR_MESSAGE);
            throw new EmailSendingdException(ERROR_MESSAGE);
        }
    }

    /**
     * Send email with attachment
     * @param to
     * @param subject
     * @param body
     * @param file
     */
    @Override
    public void sendEmailWithAttachment(String[] to, String subject, String body, File file) {
        log.info("Sending email to: {}", to);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(body);
            helper.addAttachment(file.getName(), file);
            javaMailSender.send(message);
        }catch (Exception e){
            log.error(ERROR_MESSAGE);
            throw new EmailSendingdException(ERROR_MESSAGE);
        }
    }

    /**
     * Send email with template
     * @param to
     * @param subject
     * @param templateName
     * @param data
     */
    @Override
    public void sendEmailWithTemplate(String[] to, String subject, String templateName, Map<String, String> data) {
        log.info("Sending email to: {}", to);
        MimeMessage message = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, StandardCharsets.UTF_8.name());
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            String template = getTemplate(templateName);
            for (Map.Entry<String, String> entry : data.entrySet()) {
                template = template.replace("${" + entry.getKey() + "}", entry.getValue());
            }
            helper.setText(template, true);
            javaMailSender.send(message);
        }catch (Exception e){
            log.error(ERROR_TEMPLATE);
            throw new EmailSendingdException(ERROR_TEMPLATE);
        }
    }

    /**
     * Get template
     * @param templateName
     * @return String
     */
    private String getTemplate(String templateName){
        //templateName = "templates/".concat(templateName);
        ClassPathResource resource = new ClassPathResource(templateName);
        try {
            return new String(resource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new EmailSendingdException(ERROR_TEMPLATE);
        }
    }
}
