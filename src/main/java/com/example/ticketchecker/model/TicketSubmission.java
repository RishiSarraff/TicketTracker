package com.example.ticketchecker.model;

public class TicketSubmission {

    private String emailAddress;

    private String firstName;

    private String lastName;

    private int year;

    private String memberStatus;

    private int rowNumber;

    private String paymentOption;

    private long phoneNumber;

    public TicketSubmission(){}

    public TicketSubmission(String emailAddress, String firstName, String lastName, int year, String memberStatus, String paymentOption, int phoneNumber) {
        this.emailAddress = emailAddress;
        this.firstName = firstName;
        this.lastName = lastName;
        this.year = year;
        this.memberStatus = memberStatus;
        this.paymentOption = paymentOption;
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus;
    }

    public String getPaymentOption() {
        return paymentOption;
    }

    public void setPaymentOption(String paymentOption) {
        this.paymentOption = paymentOption;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getRowNumber(){
        return rowNumber;
    }
    public void setRowNumber(int rowNum){
        this.rowNumber = rowNum;
    }

    @Override
    public String toString() {
        return "TicketSubmission{" +
                "emailAddress='" + emailAddress + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", year=" + year +
                ", memberStatus='" + memberStatus + '\'' +
                ", paymentOption='" + paymentOption + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}'+"\n";
    }
}
