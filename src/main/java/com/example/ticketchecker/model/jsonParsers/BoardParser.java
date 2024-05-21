package com.example.ticketchecker.model.jsonParsers;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.ticketchecker.database.DatabaseDriver;
import com.example.ticketchecker.model.Member;
import org.json.*;

public class BoardParser {

    static DatabaseDriver db = DatabaseDriver.getInstance("TickCheckDB");

    // note: do not run this multiple times, only run when updating board positions and/or names.
    // note: use

    public static void main(String[] args) throws SQLException {
        JSONFileReader boardReader = new JSONFileReader("currentBoard.json");
        JSONObject boardDetails = boardReader.getJSONObject();
        List<Member> boardList = boardParser(boardDetails);
        System.out.print(boardList);
        try{
            if(!db.isConnected){
                db.connect();
            }
            db.createUserTable();

            db.createBoardTable();
            db.insertIntoBoardTable(boardList);

//            db.clearTable("Board");
//            db.clearTable("Users");


            db.commit();
        }
        catch(SQLException e){
            db.rollback();
            throw e;
        }


    }

    public static List<Member> boardParser(JSONObject boardDetails){
        List<Member> memberList = new ArrayList<>();
        JSONArray arrayOfAll = boardDetails.getJSONArray("persons");
        for(Object o : arrayOfAll){
            if(o instanceof JSONObject eachMember){
                String memberAccess = eachMember.getString("chairType");
                String memberPosition = eachMember.getString("chairPosition");
                String memberFirstName = eachMember.getString("firstName");
                String memberLastName = eachMember.getString("lastName");
                Member currMember = new Member(memberAccess, memberPosition, memberFirstName, memberLastName);

                memberList.add(currMember);
            }
        }

        return memberList;
    }



}
