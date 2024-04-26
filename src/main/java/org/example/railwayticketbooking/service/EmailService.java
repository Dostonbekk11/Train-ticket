package org.example.railwayticketbooking.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.example.railwayticketbooking.dto.EmailDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class EmailService {

    private final JavaMailSender javaMailSender;
    private final Map<String, String> codeMap = new HashMap<>();

    @Autowired
    public EmailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(EmailDto emailDto) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(emailDto.getTo());
        helper.setSubject(emailDto.getSubject());
        helper.setText(String.valueOf(emailDto.getCode()), true);
        javaMailSender.send(message);
    }

    public String generateVerificationCode() {
        Random random = new Random();
        int min = 100000;
        int max = 999999;
        int verificationCode = random.nextInt(max - min) + min;
        return String.valueOf(verificationCode);
    }

    public void sendVerificationCode(String to) throws MessagingException {
        String verificationCode = generateVerificationCode();
        codeMap.put(to, verificationCode);

        EmailDto emailDto = new EmailDto();
        emailDto.setTo(to);
        emailDto.setSubject("Verification Code");
        emailDto.setCode(Boolean.valueOf("Your verification code is: " + verificationCode));

        sendEmail(emailDto);
    }

    public boolean isValidVerificationCode(String to, Boolean code) {
        String storedCode = codeMap.get(to);
        return storedCode != null && storedCode.equals(code);
    }



}
