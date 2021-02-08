package br.com.zup.mercadolivre.shared.email;

import org.springframework.stereotype.Service;

@Service
public class EmailService {

    public void send(String email, String subject, String body) {
        System.out.println("Sent email to " + email + "\n - Subject: " + subject + "\n - Body: " + body);
    }

}
