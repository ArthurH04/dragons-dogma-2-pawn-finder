package com.dd2pawn.pawnapi.service;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

       @Override
    public void sendPasswordResetEmail(String to, String token) {
        String resetLink = "http://localhost:5173/reset-password?token=" + token;

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true); 

            helper.setTo(to);
            helper.setSubject("Password Reset Request");

            String content = "<!DOCTYPE html>"
        + "<html>"
        + "<head>"
        + "  <meta charset=\"UTF-8\">"
        + "  <style>"
        + "    body { font-family: Arial, sans-serif; color: #333; }"
        + "    .container { max-width: 600px; margin: auto; padding: 20px; border: 1px solid #ddd; border-radius: 8px; }"
        + "    h2 { color: #2c3e50; }"
        + "    p { line-height: 1.5; }"
        + "    a.button { display: inline-block; padding: 10px 20px; margin-top: 15px; "
        + "               background-color: #007BFF; color: #fff; text-decoration: none; "
        + "               border-radius: 5px; font-weight: bold; }"
        + "    a.button:hover { background-color: #0056b3; }"
        + "    .footer { margin-top: 20px; font-size: 12px; color: #777; }"
        + "  </style>"
        + "</head>"
        + "<body>"
        + "  <div class=\"container\">"
        + "    <h2>Password Reset Request</h2>"
        + "    <p>We received a request to reset the password for your account.</p>"
        + "    <p>To proceed, please click the button below. This link will expire in <strong>" + PasswordReset.EXPIRATION_MINUTES + " minutes</strong>.</p>"
        + "    <a href=\"" + resetLink + "\" class=\"button\">Reset Password</a>"
        + "    <p class=\"footer\">If you did not request this change, you can safely ignore this email. "
        + "    Your current password will remain valid.</p>"
        + "  </div>"
        + "</body>"
        + "</html>";
            helper.setText(content, true);

            mailSender.send(message);
            System.out.println("Password reset email sent to: " + to);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send email");
        }
    }
    
}
