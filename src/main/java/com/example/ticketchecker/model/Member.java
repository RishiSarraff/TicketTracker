package com.example.ticketchecker.model;

public class Member {

    private String permissionLevel;
    private String boardPosition;
    private String firstName;
    private String lastName;
    private String emailAddress;

    public Member(String permissionLevel, String boardPosition, String firstName, String lastName, String emailAddress){
        this.permissionLevel = permissionLevel;
        this.boardPosition = boardPosition;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = emailAddress;
    }

    public String getPermissionLevel() {
        return permissionLevel;
    }

    public void setPermissionLevel(String permissionLevel) {
        this.permissionLevel = permissionLevel;
    }

    public String getBoardPosition() {
        return boardPosition;
    }

    public void setBoardPosition(String boardPosition) {
        this.boardPosition = boardPosition;
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

    public String getEmailAddress(){
        return emailAddress;
    }

    public void setEmailAddress(String email){
        this.emailAddress = email;
    }

    @Override
    public String toString() {
        return  boardPosition +  ": "+
                firstName + " " +
                lastName +
                '\n';
    }
}
