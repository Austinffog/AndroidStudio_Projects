package com.example.favourites_opsc;

import android.os.AsyncTask;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlaces extends AsyncTask<Object, String, String> {

    private String googlePlaceData, url;
    private GoogleMap mMap;

    @Override
    protected String doInBackground(Object... objects) {
        mMap = (GoogleMap) objects[0];
        url = (String) objects[1];

        DownloadUrls downloadUrls = new DownloadUrls();
        try {
            googlePlaceData = downloadUrls.ReadTheUrl(url); //parse the url to the ReadTheUrl in DownloadUrls
        } catch (IOException e) {
            e.printStackTrace();
        }

        return googlePlaceData;
    }

    @Override
    protected void onPostExecute(String s) {
        List<HashMap<String, String>> nearbyPlaceList = null;
        DataParser dataParser = new DataParser();
        nearbyPlaceList = dataParser.parse(s);

        DisplayNearbyPlaces(nearbyPlaceList);
    }

    private void DisplayNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList){
        for(int i=0; i<nearbyPlaceList.size(); i++){
            MarkerOptions markerOptions = new MarkerOptions();

            HashMap<String, String> googleNearbyPlaces = nearbyPlaceList.get(i);
            String nameOfPlaces = googleNearbyPlaces.get("place_name");
            String vicinity = googleNearbyPlaces.get("vicinity");
            double lat = Double.parseDouble(googleNearbyPlaces.get("lat"));
            double lng = Double.parseDouble(googleNearbyPlaces.get("lng"));

            LatLng latLng = new LatLng(lat, lng);
            markerOptions.position(latLng);
            markerOptions.title(nameOfPlaces + " : " + vicinity);
            markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
            mMap.addMarker(markerOptions);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }
    }
}
/*Reference
Android Nearby Places Tutorial 06 – Making 3 classes– Google Maps Nearby Places Tutorial.
2018. YouTube Video, added by Coding Cafe.
[Online]. Available at: https://www.youtube.com/watch?v=0QzKquJ4j8Y [Accessed 7 June 2021].
 */
