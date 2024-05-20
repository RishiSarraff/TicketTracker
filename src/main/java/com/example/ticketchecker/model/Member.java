package com.example.ticketchecker.model;

public class Member {

    private String permissionLevel;
    private String boardPosition;
    private String firstName;
    private String lastName;

    public Member(String permissionLevel, String boardPosition, String firstName, String lastName){
        this.permissionLevel = permissionLevel;
        this.boardPosition = boardPosition;
        this.firstName = firstName;
        this.lastName = lastName;
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

    @Override
    public String toString() {
        return  boardPosition +  ": "+
                firstName + " " +
                lastName +
                '\n';
    }
}
