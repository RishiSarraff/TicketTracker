package com.example.ticketchecker.model;

import com.example.ticketchecker.database.DatabaseDriver;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;

import java.io.*;
import java.nio.file.Paths;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

import com.google.api.client.googleapis.json.GoogleJsonError;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.gmail.model.Message;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Set;

import jakarta.mail.MessagingException;
import jakarta.mail.Session;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

import static com.google.api.services.gmail.GmailScopes.GMAIL_SEND;


public class ForgotPassword {

    private static final String senderEmailAddress = "sarraff2004@gmail.com"; // change this depending on who wants to send the email

    private static final JsonFactory JSON_FACTORY =  GsonFactory.getDefaultInstance();
    private static final List<String> SCOPES = Collections.singletonList(GmailScopes.GMAIL_SEND);



    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");


    public static void passwordGenerator(String userName, String firstName, String lastName, String email){
        try{
            if(!db.isConnected){
                db.connect();
            }

            String chairPosition = db.getChairPosition(firstName, lastName);

            String newPassword = chairPosition+"2425";

            sendEmail(firstName, userName, newPassword, email);

        } catch (SQLException | GeneralSecurityException | IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }


    private static Credential authorize() throws IOException, GeneralSecurityException {
        InputStream in = ForgotPassword.class.getResourceAsStream("/com/example/ticketchecker/clientSecretsGmail.json");
        if(in == null){
            throw new FileNotFoundException("Resource not found");
        }
        GoogleClientSecrets client = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(in)
        );

        GoogleAuthorizationCodeFlow gacf = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                client, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File("tokens")))
                .setAccessType("offline")
                .build();

        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();

        return new AuthorizationCodeInstalledApp(
                gacf, receiver).authorize("user");

    }

    private static void sendEmail(String firstName, String userName, String newPassword, String toEmailAddress) throws GeneralSecurityException, IOException, MessagingException {
        NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize())
                .setApplicationName("Testing Sending Mail")
                .build();

        String messageSubject = "New Password";
        String bodyText = String.format("""
                                 Hello '%s',
                                 
                                 Please keep your login credentials secure and do not share them with anyone.
                                 This username and password will remain the same for the entirety of your account, it is not temporary.
                                 If you have any questions or need further assistance, please contact Rishi.
                                 
                                 Your new username is: '%s'
                                 Your new password is: '%s'
                      
                                 Thank you,
                                 TicketChecker Team
                                 
                                """, firstName, userName, newPassword);

        // Encode as MIME message
        Properties props = new Properties();
        Session session = Session.getDefaultInstance(props, null);
        MimeMessage email = new MimeMessage(session);
        email.setFrom(new InternetAddress(senderEmailAddress));
        email.addRecipient(jakarta.mail.Message.RecipientType.TO,
                new InternetAddress(toEmailAddress));
        email.setSubject(messageSubject);
        email.setText(bodyText);

        // Encode and wrap the MIME message into a gmail message
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        email.writeTo(buffer);
        byte[] rawMessageBytes = buffer.toByteArray();
        String encodedEmail = Base64.encodeBase64URLSafeString(rawMessageBytes);
        Message message = new Message();
        message.setRaw(encodedEmail);

        try {
            // Create send message
            message = service.users().messages().send("me", message).execute();
            System.out.println("Message id: " + message.getId());
            System.out.println(message.toPrettyString());
        } catch (GoogleJsonResponseException e) {
            // TODO(developer) - handle error appropriately
            GoogleJsonError error = e.getDetails();
            if (error.getCode() == 403) {
                System.err.println("Unable to send message: " + e.getDetails());
            } else {
                throw e;
            }
        }

    }
    public static void main(String[] args){
        ForgotPassword newy = new ForgotPassword();
        newy.passwordGenerator("sarraff2004", "Rishi", "Sarraff", "vedhavias123@gmail.com");
    }

}
