package com.example.favourites_opsc;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.PlaceBuffer;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import android.view.View;

import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    //Initialize variables
    EditText etStreet;
    EditText etCity;
    EditText etCountry;
    Button btSave;
    ListView listView;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> adapter;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    Button btFavPlaces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Assign variables
        etStreet = findViewById(R.id.et_StreetAddress);
        etCity = findViewById(R.id.et_City);
        etCountry = findViewById(R.id.et_Country);
        btSave = findViewById(R.id.bt_Save);
        listView = findViewById(R.id.list_view);
        btFavPlaces = findViewById(R.id.bt_FavPlaces);

        //Initialize adapter
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, arrayList);

        //Initialize database
        firebaseDatabase = FirebaseDatabase.getInstance();
        //Initialize database reference
        databaseReference = firebaseDatabase.getReference().child("Data");

        //Create method
        getValue();

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get value from edit text
                String sValue = etStreet.getText().toString().trim() + ", " + etCity.getText().toString().trim()
                        + ", " + etCountry.getText().toString().trim() ;
                //initialize unique key
                String sKey = databaseReference.push().getKey();
                //check condition
                if (sKey != null){
                    //when key does not equal null
                    //add value with key name
                    databaseReference.child(sKey).child("value").setValue(sValue);
                    //clear edit text value
                    etStreet.setText("");
                    etCity.setText("");
                    etCountry.setText("");
                }
            }
        });

        btFavPlaces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapsActivity.class);
                startActivity(intent);
            }
        });

    }

    private void getValue(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //clear array list
                arrayList.clear();
                //use for loop
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    //get value
                    String sValue = dataSnapshot.child("value").getValue(String.class);
                    //add value in array list
                    arrayList.add(sValue);
                }
                //set adapter
                listView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                //Display toast
                Toast.makeText(getApplicationContext(), databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*Reference
    Button In Android – How Start an Activity on Button Click.
    2020. YouTube video, added by Codes Easy.
    [Online]. Available at: https://www.youtube.com/watch?v=UGTyhxiEKH0 [Accessed 7 June 2021].

    How to Implement Firebase Realtime Database in Android Studio | RealtimeDatabase | Android Coding. 2021.
    YouTube Video, added by Android Coding. [Online].
    Available at: https://www.youtube.com/watch?v=u4z8Job_sjk [Accessed 7 June 2021].
    */
}