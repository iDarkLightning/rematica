package io.github.idarklightning.rematica.util;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class HttpUtils {

    public static String getRequest(String urlToQuery) {
        StringBuilder result = new StringBuilder();

        try {
            URL url = new URL(urlToQuery);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()))) {
                for (String line; (line = reader.readLine()) != null; ) {
                    result.append(line);
                }
            }

            return result.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
