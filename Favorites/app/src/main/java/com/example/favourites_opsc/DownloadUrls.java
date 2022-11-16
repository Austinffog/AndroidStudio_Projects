package com.example.favourites_opsc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadUrls {
    public String ReadTheUrl(String placeUrl) throws IOException{
        String Data = "";
        InputStream inputStream = null;
        HttpURLConnection httpURLConnection = null;

        try {
            URL url = new URL(placeUrl); //create url
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.connect(); //connect url

            //read the data of the url
            inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream)); //parse the inputstream
            StringBuffer stringBuffer = new StringBuffer();

            String line = "";

            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuffer.append(line);
            }

            Data = stringBuffer.toString();
            bufferedReader.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally{
            inputStream.close();
            httpURLConnection.disconnect();
        }
        return Data; //return data in json
    }
}

/*Reference
Android Nearby Places Tutorial 06 – Making 3 classes– Google Maps Nearby Places Tutorial.
2018. YouTube Video, added by Coding Cafe.
[Online]. Available at: https://www.youtube.com/watch?v=0QzKquJ4j8Y [Accessed 7 June 2021].
 */
