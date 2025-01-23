package com.tntteam.tntdropbox.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.mailersend.sdk.emails.Email;
import com.mailersend.sdk.MailerSend;
import com.mailersend.sdk.MailerSendResponse;
import com.mailersend.sdk.exceptions.MailerSendException;

@Service
public class EmailService {

    @Value("${mailersend.api.token}")
    private String apiToken;
    @Value("${mailersend.email}")
    private String appEmail;

    public void sendEmail(String toEmail, String subject, String body) {

        Email email = new Email();
        email.setFrom("TnT Dropbox", appEmail);
        email.addRecipient("", toEmail);
        email.setSubject(subject);
        email.setPlain(body);

        MailerSend ms = new MailerSend();
        ms.setToken(apiToken);

        try {
            MailerSendResponse response = ms.emails().send(email);
            System.out.println(response.messageId);
        } catch (MailerSendException e) {
            e.printStackTrace();
        }
    }

}
