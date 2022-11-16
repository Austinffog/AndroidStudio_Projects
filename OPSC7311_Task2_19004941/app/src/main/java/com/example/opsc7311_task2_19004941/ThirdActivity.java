package com.example.opsc7311_task2_19004941;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class ThirdActivity extends AppCompatActivity {

    private Button Back, Goals, Log;
    private EditText WeightGoal, CalorieGoal, Start, End;
    private TextView tvWeightGoal, tvCalorieGoal, tvStart, tvEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Back = (Button) findViewById(R.id.btnBack);
        WeightGoal = (EditText) findViewById(R.id.etWeightGoal);
        CalorieGoal = (EditText) findViewById(R.id.etCalorieGoal);
        Start = (EditText) findViewById(R.id.etStartWeight);
        End = (EditText) findViewById(R.id.etEndWeight);
        Goals = (Button) findViewById(R.id.btnGoals);
        Log = (Button) findViewById(R.id.btnLog);
        tvWeightGoal = (TextView) findViewById(R.id.tvWeightGoal);
        tvCalorieGoal = (TextView) findViewById(R.id.tvCalorieGoal);
        tvStart = (TextView) findViewById(R.id.tvStart);
        tvEnd = (TextView) findViewById(R.id.tvEnd);

        Back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSecondActivity();
            }
        });

        Goals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String weightGoal = WeightGoal.getText().toString();
                tvWeightGoal.setText(weightGoal);
                String calorieGoal = CalorieGoal.getText().toString();
                tvCalorieGoal.setText(calorieGoal);
            }
        });

        Log.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String start = Start.getText().toString();
                tvStart.setText(start);
                String end = End.getText().toString();
                tvEnd.setText(end);
            }
        });
    }

    public void openSecondActivity(){
        Intent intent = new Intent(this, SecondActivity.class);
        startActivity(intent);
    }

    private void startActivity(Intent intent) {
    }

}

