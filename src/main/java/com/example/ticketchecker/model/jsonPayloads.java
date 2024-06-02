package com.example.ticketchecker.model;

import com.google.api.services.sheets.v4.model.Request;
import com.google.gson.Gson;

public class jsonPayloads {

    public static Request createPaidJson(int rowNum, int sheetID) {

        String createPaidJsonString = "{\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"updateConditionalFormatRule\": {\n" +
                "        \"sheetId\":" + sheetID + ",\n" +
                "        \"index\": 0,\n" +
                "        \"rule\": {\n" +
                "          \"ranges\": [\n" +
                "            {\n" +
                "              \"sheetId\":" + sheetID + ",\n" +
                "              \"startRowIndex\":" + rowNum + ",\n" +
                "              \"endRowIndex\":" + rowNum + "\n" +
                "            }\n" +
                "          ],\n" +
                "          \"coloredBackgroundRule\": {\n" +
                "            \"bgColor\": {\n" +
                "              \"green\": 1.0\n" +
                "           }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";



        Gson gson = new Gson();

        Request rq = gson.fromJson(createPaidJsonString, Request.class);

        return rq;
    }

    public static Request createEmailSentJson(int rowNum, int sheetID) {

        String createEmailSentJsonString = "{\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"updateConditionalFormatRule\": {\n" +
                "        \"sheetId\":" + sheetID + ",\n" +
                "        \"index\": 0,\n" +
                "        \"rule\": {\n" +
                "          \"ranges\": [\n" +
                "            {\n" +
                "              \"sheetId\":" + sheetID + ",\n" +
                "              \"startRowIndex\":" + rowNum + ",\n" +
                "              \"endRowIndex\":" + rowNum + "\n" +
                "            }\n" +
                "          ],\n" +
                "          \"coloredBackgroundRule\": {\n" +
                "            \"bgColor\": {\n" +
                "              \"red\": 1.0,\n" +
                "              \"green\": 0.75,\n" +
                "              \"blue\": 0.8\n" +
                "           }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Gson gson = new Gson();

        Request rq = gson.fromJson(createEmailSentJsonString, Request.class);

        return rq;
    }

    public static Request createResetJson(int rowNum, int sheetID) {

        String createResetJsonString = "{\n" +
                "  \"requests\": [\n" +
                "    {\n" +
                "      \"deleteConditionalFormatRule\": {\n" +
                "        \"sheetId\":"+sheetID+",\n" +
                "        \"index\": "+rowNum+"\n" +
                "      }\n" +
                "    }\n" +
                "  ]\n" +
                "}";

        Gson gson = new Gson();

        Request rq = gson.fromJson(createResetJsonString, Request.class);

        return rq;
    }
}
