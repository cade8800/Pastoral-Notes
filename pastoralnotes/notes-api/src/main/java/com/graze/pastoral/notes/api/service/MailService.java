package com.graze.pastoral.notes.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

/**
 * @Author: EEDC
 * @Description:
 * @Date: create in 2020-08-19 2:07
 */
@Service
public class MailService {
    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromMail;

    public void sendText(String to, String subject, String content) {
        SimpleMailMessage msg = new SimpleMailMessage();
        msg.setFrom(fromMail);
        msg.setTo(to);
        msg.setSubject(subject);
        msg.setText(content);

        System.out.println(msg.getSubject());
        javaMailSender.send(msg);
    }

    public void sendMailCaptcha(String to, String subject, String captcha) throws MessagingException {
        Context context = new Context();
        context.setVariable("captcha", captcha);
        String text = templateEngine.process("mail/captcha", context);

        MimeMessage mimeMailMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mm = new MimeMessageHelper(mimeMailMessage, true);
        mm.setFrom(fromMail);
        mm.setTo(to);
        mm.setSubject(subject);
        mm.setText(text, true);

        javaMailSender.send(mimeMailMessage);
    }
}
