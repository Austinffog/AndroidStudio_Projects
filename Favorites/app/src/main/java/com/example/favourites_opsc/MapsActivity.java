package com.example.favourites_opsc;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements
        OnMapReadyCallback,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        LocationListener //used when there is a change is location
{

    //Initialize variables
    private GoogleMap mMap;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest; //get location of user
    private Location lastLocation;
    private Marker currentUserLocationMarker;
    private static final int Request_User_Location_Code = 99;
    private double latitude, longitude;
    private int proximity = 10000;

    Button btBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkUserLocationPermission();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btBack = findViewById(R.id.bt_back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

    public void onClick(View view) {

        String historical = "historical", natural = "natural", modern = "modern", popular = "popular";
        Object transferData[] = new Object[2];
        GetNearbyPlaces getNearbyPlaces = new GetNearbyPlaces();

        switch (view.getId()) {
            case R.id.search_address:
                EditText addressField = (EditText) findViewById(R.id.location_search);
                String addressText = addressField.getText().toString(); //get the text of the location name

                List<Address> addressList = null;
                MarkerOptions userMarkerOptions = new MarkerOptions();

                if (!TextUtils.isEmpty(addressText)) {
                    Geocoder geocoder = new Geocoder(this);
                    try {
                        addressList = geocoder.getFromLocationName(addressText, 6);

                        if (addressList != null) {
                            for (int i = 0; i < addressList.size(); i++) {
                                Address userAddress = addressList.get(i); //get addresses
                                LatLng latLng = new LatLng(userAddress.getLatitude(), userAddress.getLongitude()); //get the user lat and long
                                userMarkerOptions.position(latLng);
                                userMarkerOptions.title(addressText);
                                userMarkerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED));
                                mMap.addMarker(userMarkerOptions); //add marker to where the user is looking
                                mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                                mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
                            }
                        } else {
                            Toast.makeText(this, "Location not found.", Toast.LENGTH_SHORT).show();
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(this, "Write location name.", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.historical_nearby: //get historical sites
                mMap.clear();
                String url = getUrl(latitude, longitude, historical);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby historical sites...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby historical sites...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.natural_nearby: //get natural sites ie.forests
                mMap.clear();
                url = getUrl(latitude, longitude, natural);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby natural sites...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby natural sites...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.modern_nearby: // get modern sites
                mMap.clear();
                url = getUrl(latitude, longitude, modern);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby modern sites...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby modern sites...", Toast.LENGTH_SHORT).show();
                break;

            case R.id.popular_nearby: //get popular places
                mMap.clear();
                url = getUrl(latitude, longitude, popular);
                transferData[0] = mMap;
                transferData[1] = url;

                getNearbyPlaces.execute(transferData);
                Toast.makeText(this, "Searching for nearby popular sites...", Toast.LENGTH_SHORT).show();
                Toast.makeText(this, "Showing nearby popular sites...", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private String getUrl(double latitude, double longitude, String nearbyPlace){
        StringBuilder googleURL = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googleURL.append("location=" + latitude + "." + longitude);
        googleURL.append("&radius=" + proximity);
        googleURL.append("&type=" + nearbyPlace);
        googleURL.append("&sensor=true");
        googleURL.append("&key=" + "AIzaSyA90pTeTaFlpSROrSNloqh19JDR41nr7Co");

        Log.d("MapsActivity", "url = " + googleURL.toString());

        return googleURL.toString();
    }


    @Override
    public void onMapReady(GoogleMap googleMap) { //gets the users current location
        mMap = googleMap;

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            buildGoogleApiClient();

            mMap.setMyLocationEnabled(true);
        }

    }

    public boolean checkUserLocationPermission() { //check if permission is granted or not
        //if permission is not granted
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) { //if it returns true
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, Request_User_Location_Code);
            }
            return false;
        } else {
            return true;
        }
    }

    //handles permission granted response
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case Request_User_Location_Code:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) { //check if permission is granted or not
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                        if (googleApiClient == null) {
                            buildGoogleApiClient(); //creates new client if it is null
                        }
                        mMap.setMyLocationEnabled(true);
                    }
                } else { //if permission is denied
                    Toast.makeText(this, "Permission Denied...", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }

    protected synchronized void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        googleApiClient.connect(); //connect googleApiClient
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) { //called whenever there is a connection
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1100);
        locationRequest.setFastestInterval(1100);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        //get permission to access location
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this); //gets location of user
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) { //called when the connection fails

    }

    @Override
    public void onLocationChanged(Location location) {
        latitude = location.getLatitude();
        longitude = location.getLongitude();

        lastLocation = location;

        //check if there is already a marker, if there is remove it
        if (currentUserLocationMarker != null) {
            currentUserLocationMarker.remove();
        }

        //get new user position
        LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

        //create marker for users current location
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("User Current Location");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        currentUserLocationMarker = mMap.addMarker(markerOptions);

        //move camera to user location marker
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomBy(14)); //zoom into location area

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }
    }

}

/*Reference
Button In Android – How Start an Activity on Button Click.
2020. YouTube video, added by Codes Easy.
[Online]. Available at: https://www.youtube.com/watch?v=UGTyhxiEKH0 [Accessed 7 June 2021].

Google Maps Current Location in Android Studio using Google Map – Get Current Location of user.
2018. YouTube Video, added by Coding Cafe.
[Online]. Available at: https://www.youtube.com/watch?v=4kk-dYWVNsc [Accessed 7 June 2021].

Google Places API Android Studio Tutorial 07 Show Nearby Places Google Maps Nearby Places API key.
2018. YouTube Video, added by Coding Cafe.
[Online]. Available at: https://www.youtube.com/watch?v=lz4y0ofVTk4 [Accessed 7 June 2021].

Search Location in Google Maps Android Studio Tutorial 05 Google Maps Search Location / Address.
2018. YouTube Video, added by Coding Cafe.
[Online]. Available at: https://www.youtube.com/watch?v=NxQY0-QRM1c [Accessed 7 June 2021].

 */