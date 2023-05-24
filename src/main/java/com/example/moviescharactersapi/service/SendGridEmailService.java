package com.example.moviescharactersapi.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class SendGridEmailService {

    @Autowired
    private SendGrid sendGrid;

    public void sendEmail( String to, String subject ) throws IOException {
        Email from = new Email( "example@gmail.com" );
        Email toEmail = new Email( to );
        Content emailContent = new Content( "text/plain", "Successful registration. Welcome to the world of Disney!" );
        Mail mail = new Mail( from, subject, toEmail, emailContent );

        Request request = new Request();
        request.setMethod( Method.POST );
        request.setEndpoint( "mail/send" );
        request.setBody( mail.build() );

        Response response = sendGrid.api( request );
        System.out.println( response.getStatusCode() );
        System.out.println( response.getBody() );
        System.out.println( response.getHeaders() );
    }
}
