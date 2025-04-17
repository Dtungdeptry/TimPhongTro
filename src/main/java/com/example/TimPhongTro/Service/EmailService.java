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

    public void sendOtpEmail(String toEmail, String otp) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        // Đặt địa chỉ email người gửi
        helper.setFrom("dtung9868@gmail.com");
        helper.setTo(toEmail);

        helper.setSubject("Xác thực đăng ký - TimPhongTro");
        String htmlContent = """
        <div style="font-family:Arial,sans-serif;color:#333;font-size:14px;">
            <h2>Xác thực tài khoản TimPhongTro</h2>
            <p>Chào bạn,</p>
            <p>Mã OTP xác thực của bạn là: <strong style="color:#d33;">%s</strong></p>
            <p>Mã có hiệu lực trong 5 phút.</p>
            <br/>
            <p>Trân trọng,<br/>Đội ngũ TimPhongTro</p>
        </div>
        """.formatted(otp);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

}
