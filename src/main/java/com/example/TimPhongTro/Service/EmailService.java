package com.example.TimPhongTro.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class EmailService {
    @Autowired
    private JavaMailSender mailSender;

    public String generateOTP() {
        Random random = new Random();
        return String.format("%06d", random.nextInt(999999));
    }

    public void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Đặt địa chỉ email người gửi
        helper.setFrom("dtung6898@gmail.com");
        helper.setTo(to);
        helper.setSubject("Xác thực đăng ký - TimPhongTro");
        helper.setText("Mã OTP của bạn là: " + otp, true);

        mailSender.send(message);
    }

}
