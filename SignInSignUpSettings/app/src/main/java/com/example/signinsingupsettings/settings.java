package com.example.signinsingupsettings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class settings extends AppCompatActivity{

    Button btnSave;
    UserSettings userSettings;
    RadioButton rabBtnMetric, radBtnImperial, rbtnNatural, rbtnHistorical, rbtnModern, rbtnPopular;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_screen);

       btnSave = (Button)findViewById(R.id.btnSave);
       userSettings=new UserSettings();
       rabBtnMetric = (RadioButton)findViewById(R.id.rabBtnMetric);
        rbtnNatural = (RadioButton)findViewById(R.id.rbtnNatural);
        radBtnImperial = (RadioButton)findViewById(R.id.radBtnImperial);
        rbtnHistorical = (RadioButton)findViewById(R.id.rbtnHistorical);
        rbtnModern = (RadioButton)findViewById(R.id.rbtnModern);
        rbtnPopular = (RadioButton)findViewById(R.id.rbtnPopular);

        databaseReference = firebaseDatabase.getInstance().getReference().child("Settings");
        databaseReference.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        if(snapshot.exists()){
            i =(int)snapshot.getChildrenCount();

        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Meassure1= rabBtnMetric.getText().toString();
                String Meassure2= radBtnImperial.getText().toString();

                String LandMarks1 = rbtnHistorical.getText().toString();
                String LandMarks2 = rbtnModern.getText().toString();
                String LandMarks3 = rbtnNatural.getText().toString();
                String LandMarks4 = rbtnPopular.getText().toString();

                if (radBtnImperial.isChecked()){
                    userSettings.setMeasureSystem(Meassure2);
                    databaseReference.child(String.valueOf(i+1)).setValue(userSettings);
                }else{
                    userSettings.setMeasureSystem(Meassure1);
                    databaseReference.child(String.valueOf(i+1)).setValue(userSettings);

                }

                if (rbtnPopular.isChecked()){
                    userSettings.setPrefLandMarks(LandMarks4);
                    databaseReference.child(String.valueOf(i+1)).setValue(userSettings);
                }if (rbtnNatural.isChecked()){
                    userSettings.setPrefLandMarks(LandMarks3);
                    databaseReference.child(String.valueOf(i+1)).setValue(userSettings);
                }if (rbtnModern.isChecked()){
                    userSettings.setPrefLandMarks(LandMarks2);
                    databaseReference.child(String.valueOf(i+1)).setValue(userSettings);
            }
                else{
                    userSettings.setPrefLandMarks(LandMarks1);
                    databaseReference.child(String.valueOf(i+1)).setValue(userSettings);
                }
                Toast.makeText(settings.this, "Settings saved!!", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
