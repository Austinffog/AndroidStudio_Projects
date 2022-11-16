package com.example.opsc7311_task2_19004941;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity {

    private EditText Weight, Height, Water, Calories, Sleep;
    private Button Goals, Save;
    private TextView tvWeight, tvHeight, tvWater, tvCalories, tvSleep;
    private TextView tvMeasurement, tvMeasurement2, tvMeasurement5, tvMWG, tvMSW, tvMEW;
    private CheckBox Measurement;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Goals = (Button) findViewById(R.id.btnGoals);
        Weight = (EditText) findViewById(R.id.etWeight);
        Height = (EditText) findViewById(R.id.etHeight);
        Water = (EditText) findViewById(R.id.etWater);
        Calories = (EditText) findViewById(R.id.etCalories);
        Sleep = (EditText) findViewById(R.id.etSleep);
        tvWeight = (TextView) findViewById(R.id.tvWeight);
        tvHeight = (TextView) findViewById(R.id.tvHeight);
        tvWater = (TextView) findViewById(R.id.tvWater);
        tvCalories = (TextView) findViewById(R.id.tvCalories);
        tvSleep = (TextView) findViewById(R.id.tvSleep);
        tvMeasurement = (TextView) findViewById(R.id.tvMeasurement);
        tvMeasurement2 = (TextView) findViewById(R.id.tvMeasurement2);
        tvMeasurement5 = (TextView) findViewById(R.id.tvMeasurement5);
        Measurement = (CheckBox) findViewById(R.id.cbMeasurement);
        tvMWG = (TextView) findViewById(R.id.tvMWG);
        tvMSW = (TextView) findViewById(R.id.tvMSW);
        tvMEW = (TextView) findViewById(R.id.tvMEW);

        Goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openThirdActivity(); //call method
            }
        });

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weight = Weight.getText().toString(); //get the value
                tvWeight.setText(weight); //set the value
                String height = Height.getText().toString();
                tvHeight.setText(height);
                String water = Water.getText().toString();
                tvWater.setText(water);
                String calories = Calories.getText().toString();
                tvCalories.setText(calories);
                String sleep = Sleep.getText().toString();
                tvSleep.setText(sleep);
            }
        });

        Measurement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Measurement.isChecked()){
                    String measurementOne = ("lb");
                    tvMeasurement.setText(measurementOne);
                    String measurementTwo = ("\"");
                    tvMeasurement2.setText(measurementTwo);
                    String measurementFive = ("gal");
                    tvMeasurement5.setText(measurementFive);
                    String measurementWG = ("lb");
                    tvMWG.setText(measurementWG);
                    String measurementSW = ("lb");
                    tvMSW.setText(measurementSW);
                    String measurementEW = ("lb");
                    tvMEW.setText(measurementEW);
                }
                else {
                    String measurementOne = ("kg");
                    tvMeasurement.setText(measurementOne);
                    String measurementTwo = ("m");
                    tvMeasurement2.setText(measurementTwo);
                    String measurementFive = ("l");
                    tvMeasurement5.setText(measurementFive);
                    String measurementWG = ("kg");
                    tvMWG.setText(measurementWG);
                    String measurementSW = ("kg");
                    tvMSW.setText(measurementSW);
                    String measurementEW = ("kg");
                    tvMEW.setText(measurementEW);

                }
            }
        });
    }

    public void openThirdActivity(){
        Intent intent = new Intent(this, ThirdActivity.class); //loads the net activity/page
        startActivity(intent);
    }

    private void startActivity(Intent intent) {
    }

}
