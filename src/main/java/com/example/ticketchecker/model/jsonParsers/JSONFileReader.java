package com.example.ticketchecker.model.jsonParsers;

import com.google.api.client.json.Json;
import org.json.JSONObject;

import java.io.*;
import java.net.URL;
import java.util.stream.Collectors;

public class JSONFileReader {

    private URL url;
    private String fileName;

    public JSONFileReader(URL url){
        this.url = url;
    }

    public JSONFileReader(String fileName){
        this.fileName = fileName;
    }

    public JSONObject getJSONObject() {
        try {
            File currJsonFile = new File("src/main/resources/com/example/ticketchecker/" + this.fileName);
            InputStream currStream = new FileInputStream(currJsonFile);
            InputStreamReader currStreamReader = new InputStreamReader(currStream);
            BufferedReader bufferedReader = new BufferedReader(currStreamReader);
            var fileContents = bufferedReader.lines().collect(Collectors.joining("\n"));
            return new JSONObject(fileContents);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
