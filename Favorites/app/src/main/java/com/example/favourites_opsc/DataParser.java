package com.example.favourites_opsc;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser { //will hold data
    private HashMap<String, String> getSingleNearbyPlace(JSONObject googlePlaceJSON){ //will store one place
        HashMap<String, String> googlePlaceMap = new HashMap<>();
        String NameOfPlace = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";

        try{
            if (!googlePlaceJSON.isNull("name")){
                NameOfPlace = googlePlaceJSON.getString("name");
            }
            if (!googlePlaceJSON.isNull("vicinity")){
                NameOfPlace = googlePlaceJSON.getString("vicinity");
            }
            latitude = googlePlaceJSON.getJSONObject("getVicinity").getJSONObject("location").getString("lat");
            longitude = googlePlaceJSON.getJSONObject("getVicinity").getJSONObject("location").getString("lng");
            reference = googlePlaceJSON.getString("reference");

            googlePlaceMap.put("place_name", NameOfPlace);
            googlePlaceMap.put("vicinity", vicinity);
            googlePlaceMap.put("lat", latitude);
            googlePlaceMap.put("lng", longitude);
            googlePlaceMap.put("reference", reference);
        }
        catch (JSONException e){
            e.printStackTrace();
        }

        return googlePlaceMap; // return hashmap
    }

    private List<HashMap<String, String>> getAllNearbyPlace(JSONArray jsonArray){ //will store all places
        int counter = jsonArray.length();

        List<HashMap<String, String>> nearbyPlaceList = new ArrayList(); //store each place
        HashMap<String, String> nearbyPlaceMap = null;

        for (int i=0; i<counter; i++){
            try{
                nearbyPlaceMap = getSingleNearbyPlace((JSONObject) jsonArray.get(i));
                nearbyPlaceList.add(nearbyPlaceMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return nearbyPlaceList;
    }

    public List<HashMap<String, String>> parse(String JSONData){
        JSONArray jsonArray = null;
        JSONObject jsonObject;


        try {
            jsonObject = new JSONObject(JSONData);
            jsonArray = jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getAllNearbyPlace(jsonArray);
    }

}

/*Reference
Android Nearby Places Tutorial 06 – Making 3 classes– Google Maps Nearby Places Tutorial.
2018. YouTube Video, added by Coding Cafe.
[Online]. Available at: https://www.youtube.com/watch?v=0QzKquJ4j8Y [Accessed 7 June 2021].
 */
