package com.example.ticketchecker.model;

import com.example.ticketchecker.database.DatabaseDriver;

import com.example.ticketchecker.model.smallFeatures.DialogUtils;
import com.google.api.services.gmail.model.Message;
import java.util.Properties;

import jakarta.mail.Authenticator;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.security.GeneralSecurityException;
import java.sql.SQLException;
import jakarta.mail.MessagingException;
import javafx.stage.Stage;


import java.io.IOException;

public class ForgotPassword {

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");


    private static final String EMAIL_FROM = "sarraff2004@gmail.com"; // change this depending on who wants to send the email
    private static final String APP_PASSWORD = "fhcs qwnh xomp rtgg";


    public static void passwordGenerator(String userName, String firstName, String lastName, String email){
        try{
            if(!db.isConnected){
                db.connect();
            }

            String chairPosition = db.getChairPosition(firstName, lastName);

            String newPassword = chairPosition+"2425";

            if(updatePassword(newPassword)){
                sendEmail(firstName, userName, newPassword, email);
            }

        } catch (SQLException | GeneralSecurityException | IOException | MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    private static boolean updatePassword(String newPassword) {
        // update password here, if the password matches the same as newPassword then do not update and tell them to check their own email
        // if the password doesnt match then update.
        try{
            if(!db.isConnected){
                db.connect();
            }

            //db.getPassword();
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }

    private static void sendEmail(String firstName, String userName, String newPassword, String toEmailAddress) throws GeneralSecurityException, IOException, MessagingException {
        String messageSubject = "New Password";
        String bodyText = String.format("""
                                 Hello %s,
                                 
                                 Please keep your login credentials secure and do not share them with anyone.
                                 This username and password will remain the same for the entirety of your account, it is not temporary.
                                 If you have any questions or need further assistance, please contact Rishi.
                                 
                                 Your new username is: %s
                                 Your new password is: %s
                      
                                 Thank you,
                                 TicketChecker Team
                                 
                                """, firstName, userName, newPassword);


        MimeMessage message = new MimeMessage(getEmailSession());
        message.setFrom(new InternetAddress(EMAIL_FROM));
        message.setRecipients(MimeMessage.RecipientType.TO, InternetAddress.parse(toEmailAddress));
        message.setSubject(messageSubject);
        message.setText(bodyText);
        Transport.send(message);

    }


    private static Session getEmailSession() {
        return Session.getInstance(getGmailProperties(), new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(EMAIL_FROM, APP_PASSWORD);
            }
        });
    }

    private static Properties getGmailProperties() {
        Properties prop = new Properties();
        prop.put("mail.smtp.auth", "true");
        prop.put("mail.smtp.starttls.enable", "true");
        prop.put("mail.smtp.host", "smtp.gmail.com");
        prop.put("mail.smtp.port", "587");
        prop.put("mail.smtp.ssl.trust", "smtp.gmail.com");
        return prop;
    }

    public static boolean manageForgotPasswordInteraction(String fName, String lName, Stage currStage) {
        try{
            if(!db.isConnected){
                db.connect();
            }

            if(db.checkUserID(fName, lName)){
                DialogUtils.dialogPopUp("You cannot access forgot password without creating an account first", "Please create an account", currStage);
                return false;
            }
            return true;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getUsername(String userFirstName, String userLastName) {
        try{
            if(!db.isConnected){
                db.connect();
            }

            if(!db.checkUserID(userFirstName, userLastName)){
                int userID = db.getUserID(userFirstName, userLastName);
                return db.getUsername(userID);
            }

            return null;


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    // we have to set this as theyre new password and keep username the same.
}
