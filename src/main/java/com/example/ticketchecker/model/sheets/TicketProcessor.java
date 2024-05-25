package com.example.ticketchecker.model.sheets;
import com.example.ticketchecker.database.DatabaseDriver;
import com.example.ticketchecker.model.TicketSubmission;
import javafx.scene.layout.Pane;

import java.sql.SQLException;
import java.util.List;

public class TicketProcessor {

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");

    public static TicketSubmission rowProcessor(List<Object> row) {
        TicketSubmission newTicket = new TicketSubmission();

        String email = (String) row.get(0);
        String firstName = (String) row.get(1);
        String lastName = (String) row.get(2);
        int year = yearDecider((String) row.get(3));
        String ticketOptions = (String) row.get(4);
        String payment = (String) row.get(5);
        String phoneNumber = (String) row.get(6);

        newTicket.setEmailAddress(processEmail(email));
        newTicket.setFirstName(firstName);
        newTicket.setLastName(lastName);
        newTicket.setYear(year);
        newTicket.setMemberStatus(getMemberStatus(ticketOptions));
        newTicket.setPaymentOption(getPaymentOption(payment));
        newTicket.setPhoneNumber(processPhoneNumber(phoneNumber));

        return newTicket;
    }

    private static int yearDecider(String s) {
        s = s.toLowerCase();
        if(s.startsWith("first")){
            return 1;
        }
        else if(s.startsWith("second")){
            return 2;
        }
        else if(s.startsWith("third")){
            return 3;
        }
        else if(s.startsWith("fourth")){
            return 4;
        }
        return -1;
    }

    private static String processEmail(String email) {
        if(email == null || email.equals("")){
            return "default@example.com";
        }
        email = email.trim().replaceAll("\\s", "");

        return email;
    }

    private static String getMemberStatus(String memberStatus) {
        if(memberStatus == null || memberStatus.equals("")){
            return "";
        }
        memberStatus = memberStatus.trim().replaceAll("\\s", "").toLowerCase();
        if(memberStatus.startsWith("regular")){
            return "Member";
        }
        else if(memberStatus.startsWith("non")){
            return "Non-Member";
        }
        else if(memberStatus.startsWith("fourthyear") || memberStatus.startsWith("4")){
            return "Fourth-Year";
        }
        else{
            return "";
        }
    }
    private static String getPaymentOption(String paymentType) {
        if(paymentType == null || paymentType.equals("")){
            return "";
        }
        paymentType = paymentType.trim().replaceAll("\\s", "").toLowerCase();
        if(paymentType.startsWith("venmo")){
            return "Venmo";
        }
        else if(paymentType.startsWith("zelle")){
            return "Zelle";
        }
        else if(paymentType.startsWith("cash")){
            return "Cash";
        }
        else{
            return "Fourth Year Free";
        }
    }
    private static long processPhoneNumber(String phoneNumber) {
        StringBuilder str = new StringBuilder();
        if(phoneNumber == null || phoneNumber.equals("")){
            return 0;
        }
        for(int i = 0; i<phoneNumber.length(); i++){
            Character currChar = phoneNumber.charAt(i);
            if(Character.isDigit(currChar)){
                str.append(currChar);
            }
        }
        if(str.toString().equals("")){
            return 0;
        }

        return Long.parseLong(str.toString());
    }
}
