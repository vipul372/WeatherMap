package com.example.vipul.weathermap;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


final class QueryUtils {

    private QueryUtils() {
    }


    static String fetchWeatherData(String urls) {

        String jsonResult = "";
        URL url;

        try {
            url = new URL(urls);

            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            InputStream in = urlConnection.getInputStream();

            InputStreamReader reader = new InputStreamReader(in);

            int data = reader.read();

            while (data != -1) {

                char current = (char) data;

                jsonResult += current;

                data = reader.read();

            }

            return jsonResult;

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
