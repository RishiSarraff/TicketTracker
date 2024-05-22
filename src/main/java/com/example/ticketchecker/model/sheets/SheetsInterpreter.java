package com.example.ticketchecker.model.sheets;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;


import com.google.api.client.auth.oauth2.Credential;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.ValueRange;
import com.google.gson.Gson;


public class SheetsInterpreter {
    private static Sheets sheetsService;

    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKENS_DIRECTORY_PATH = "tokens";

    private static final List<String> SCOPES =
            Collections.singletonList(SheetsScopes.SPREADSHEETS);
    private static String APPLICATION_NAME = ""; // get the google sheets name the same way.
    private static String SPREADSHEET_ID = ""; // get the sheet ID from the url the user copy pastses

    private static String sheetName = "";

    private static String cellRange = "";

    public SheetsInterpreter(){ // default this to the demo doc with default values.
        setApplicationName("");
        setSheetID("");
        setSheetName("");
        setCellRange("");
    }

    public SheetsInterpreter(String applicationName, String sheetID, String sheetName, String cellRange){
        setApplicationName(applicationName);
        setSheetID(sheetID);
        setSheetName(sheetName);
        setCellRange(cellRange);
    }

    public static String getApplicationName() {
        return APPLICATION_NAME;
    }

    public static void setApplicationName(String applicationName) {
        APPLICATION_NAME = applicationName;
    }

    public static String getSheetID() {
        return SPREADSHEET_ID;
    }

    public static void setSheetID(String spreadsheetId) {
        SPREADSHEET_ID = spreadsheetId;
    }

    public static String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        if(SheetDetailsValidator.sheetNameValidator(sheetName, APPLICATION_NAME)){
            this.sheetName = sheetName;
        }
    }

    public static String getCellRange() {
        return cellRange;
    }

    public void setCellRange(String cellRange) {
        if(SheetDetailsValidator.cellRangeValidator(cellRange)){
            this.cellRange = cellRange;
        }
    }


    private static Credential authorize() throws IOException, GeneralSecurityException{
        InputStream in = SheetsInterpreter.class.getResourceAsStream("/com/example/ticketchecker/credentials.json");
        if(in == null){
                throw new FileNotFoundException("Resource not found: " + "/credentials.json");
        }
        GoogleClientSecrets client = GoogleClientSecrets.load(
                JSON_FACTORY, new InputStreamReader(in)
        );

        GoogleAuthorizationCodeFlow gacf = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY,
                client, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();

        return new AuthorizationCodeInstalledApp(
                gacf, new LocalServerReceiver())
                .authorize("user");

    }

    static Sheets getService() throws IOException, GeneralSecurityException{
        Credential creds = authorize();
        return new Sheets.Builder(GoogleNetHttpTransport.newTrustedTransport(),
                GsonFactory.getDefaultInstance(), creds)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        SheetsInterpreter sheetsReader = new SheetsInterpreter("ISA First Party Tickets", "1nDahXjR5vxjGbdUfkFdvQ05eyWI4EkLNVxz5UT9On4A", "ISA First Party Tickets", "A2:J334");
//        Sheets service = sheetsReader.getService();
//
//        String fetchVals = sheetsReader.getSheetName() + "!" + sheetsReader.getCellRange();
//
//        ValueRange response = service.spreadsheets().values().get("1nDahXjR5vxjGbdUfkFdvQ05eyWI4EkLNVxz5UT9On4A", fetchVals).execute();
//
//        List<List<Object>> values = response.getValues();
//
//        if(values == null || values.isEmpty()){
//            System.out.println("No data found.");
//        }
//        else{
//            for(List row : values){
//                // we will currently System.out to the console, but we should send each row to the javafx application along with a checkbox or something.
//                System.out.println(row);
//            }
//        }
//    }

}

