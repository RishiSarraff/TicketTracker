package com.example.ticketchecker.model.sheets;
import org.apache.commons.text.similarity.LevenshteinDistance;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SheetDetailsValidator {

    public static boolean sheetNameValidator(String nameOfSheet, String nameOfApp){
        String processedSheet = nameOfSheet.toLowerCase().replaceAll("\\s+", "");
        String processedSheetApp = nameOfApp.toLowerCase().replaceAll("\\s+", "");

        LevenshteinDistance distance = new LevenshteinDistance();

        int diffBetweenStrings = distance.apply(processedSheet, processedSheetApp);

        int maxTitleLength =  Math.max(processedSheet.length(), processedSheetApp.length());

        double similarityProportion = 1.0 - (double) diffBetweenStrings/maxTitleLength;

        if(similarityProportion > 0.65){
            return true;
        }
        else{
           // DialogUtils.dialogPopUp("Sheet name and file name do not match, check your inputs again", "Retype sheet information");
            return false;
        }
    }

    public static boolean cellRangeValidator(String rangeOfCells){
        String regPattern = "^[A-P]([2-9]|([1-9]\\d{0,2}))" +
                            ":[A-P]([1-9]\\d{0,2})$";

        String range = rangeOfCells.toUpperCase();

        Pattern pattern = Pattern.compile(regPattern);

        Matcher matcher = pattern.matcher(range);

        if(matcher.matches()){
            return true;
        }
        else{
           // DialogUtils.dialogPopUp("The cell range format is off, it should follow exactly some thing like A2:J100", "Cell Range Input Error");
            return false;
        }
    }

}
