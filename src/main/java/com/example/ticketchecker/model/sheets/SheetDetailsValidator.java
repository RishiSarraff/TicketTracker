package com.example.ticketchecker.model.sheets;
import com.example.ticketchecker.model.TicketSubmission;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.model.Sheet;
import com.google.api.services.sheets.v4.model.ValueRange;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.Timestamp;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SheetDetailsValidator {

    public static boolean sheetNameValidator(String nameOfSheet, String nameOfApp) {
        if (nameOfSheet == null || nameOfSheet.isEmpty()) {
            return false;
        }
        if (nameOfApp == null || nameOfApp.isEmpty()) {
            return false;
        }

        String processedSheet = nameOfSheet.toLowerCase().replaceAll("\\s+", "");
        String processedSheetApp = nameOfApp.toLowerCase().replaceAll("\\s+", "");

        LevenshteinDistance distance = new LevenshteinDistance();

        int diffBetweenStrings = distance.apply(processedSheet, processedSheetApp);

        int maxTitleLength = Math.max(processedSheet.length(), processedSheetApp.length());

        double similarityProportion = 1.0 - (double) diffBetweenStrings / maxTitleLength;

        if (similarityProportion > 0.65) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean cellRangeValidator(String rangeOfCells) {
        if (rangeOfCells == null || rangeOfCells.isEmpty()) {
            return false;
        }
        String regPattern = "^B([2-9]|([1-9]\\d{0,2}))" +
                ":[B-P]([1-9]\\d{0,2})$";

        String range = rangeOfCells.toUpperCase();

        Pattern pattern = Pattern.compile(regPattern);

        Matcher matcher = pattern.matcher(range);

        if (matcher.matches()) {
            return true;
        } else {
            return false;
        }
    }

    public static String spreadsheetIDGetter(String url) {
        if (url == null || url.isEmpty()) {
            return null;
        }
        String delimiter1 = "/d/";
        String delimiter2 = "/edit";
        int firstPivot = url.indexOf(delimiter1) + 3;
        int secondPivot = url.indexOf(delimiter2);

        if (firstPivot > url.length()) {
            return null;
        } else if (firstPivot == 2 || secondPivot == -1) {
            return null;
        }

        String result = url.substring(firstPivot, secondPivot);
        return result;
    }

    public static List<TicketSubmission> sendToSheetsInterpreter(String fileName, String spreadsheetID, String sheetName, String cellRange) throws GeneralSecurityException, IOException {
        try {
            List<TicketSubmission> finalList = new ArrayList<>();
            SheetsInterpreter sheetsReader = new SheetsInterpreter(fileName, spreadsheetID, sheetName, cellRange);
            Sheets service = sheetsReader.getService();

            String fetchVals = sheetsReader.getSheetName() + "!" + sheetsReader.getCellRange();

            ValueRange response = service.spreadsheets().values().get(spreadsheetID, fetchVals).execute();

            List<List<Object>> values = response.getValues();

            if (values == null || values.isEmpty()) {
                System.out.println("No data found.");
            } else {
                for (List<Object> row : values) {
                    TicketSubmission rowObject = TicketProcessor.rowProcessor(row);
                    finalList.add(rowObject);
                }
            }
            return finalList;

        } catch (IOException | GeneralSecurityException e) {
            throw e;

        }
    }

//    public static void main(String[] args) throws GeneralSecurityException, IOException {
//        SheetDetailsValidator.sendToSheetsInterpreter("testingData", "1gibwcsnJv55InYKDnXhGxftsIOUVjeSDX2X0oPqUb_M", "testingData", "B2:I334");
//    }
}