package com.carona.helpers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class PostAndNotificationHelper {
    
    public String requestGetLatAndLon(String parameter) throws MalformedURLException, IOException{
        String encodedSearch = URLEncoder.encode(parameter, "utf-8");
        URL url = new URL("https://api.geoapify.com/v1/geocode/search?text="+ encodedSearch + "&lang=pt&limit=1&type=city&filter=countrycode:br&apiKey=72153e202e824dc6afa201e03851dac8");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/json");

        InputStream is = http.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();

        http.disconnect();
        
        Integer indexGeometry = response.toString().indexOf("coordinates");
        Integer indexBbox = response.toString().indexOf("bbox");
        
        return response.toString().substring(indexGeometry + 14, indexBbox - 4);
    }

    public String requestGetCity(String parameterLat, String parameterLon) throws MalformedURLException, IOException{
        URL url = new URL("https://api.geoapify.com/v1/geocode/reverse?lat="+ parameterLat + "&lon="+ parameterLon + "&apiKey=72153e202e824dc6afa201e03851dac8");
        HttpURLConnection http = (HttpURLConnection)url.openConnection();
        http.setRequestProperty("Accept", "application/json");

        InputStream is = http.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
        String line;
        while ((line = rd.readLine()) != null) {
            response.append(line);
            response.append('\r');
        }
        rd.close();

        http.disconnect();
        
        Integer indexGeometry = response.toString().indexOf("city");
        Integer indexBbox = response.toString().indexOf(",", indexGeometry);
        
        return response.toString().substring(indexGeometry + 7, indexBbox - 1);
    }

   

}
