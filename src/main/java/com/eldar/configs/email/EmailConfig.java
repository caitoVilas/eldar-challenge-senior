package com.eldar.configs.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;
/**
 * @author caito Vilas
 * date: 08/2024
 * This class is used to configure the email sender.
 */
@Configuration
public class EmailConfig {
    @Value("${application.email}")
    private String email;
    @Value("${application.email-pass}")
    private String emailPassword;

    /**
     * This method is used to create a JavaMailSender object and set the email and password of the email sender.
     * @return JavaMailSender object
     */
    @Bean
    public JavaMailSender javaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(email);
        mailSender.setPassword(emailPassword);
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }
}
