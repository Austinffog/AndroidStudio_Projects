package com.example.signinsingupsettings;

import android.content.Intent;
import android.os.Bundle;
import android.os.PatternMatcher;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class regiSter extends AppCompatActivity implements View.OnClickListener {
    private FirebaseAuth mAuth;
    private EditText txtFirsName, txtLastName, txtUserName, txtEmail, txtPassword;
    private Button btnSubmit;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

// Initializing Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initializing my variables

        btnSubmit = (Button) findViewById(R.id.btnSubmit);
        btnSubmit.setOnClickListener(this);
        txtFirsName = (EditText) findViewById(R.id.txtFirsName);
        txtLastName = (EditText) findViewById(R.id.txtLastName);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtPassword = (EditText) findViewById(R.id.txtPassword);

    }
    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.btnSubmit:
                registerUser();
                break;

        }
    }

    private void registerUser() {
        //GETTING ENTRIES FROM ALL THE EDITTEXT CONERTING THEM INTO STRINGS AND SAVING THEM INTO VARIABLES
        String username = txtUserName.getText().toString().trim();
        String firstname = txtFirsName.getText().toString().trim();
        String lastname = txtLastName.getText().toString().trim();
        String email = txtEmail.getText().toString().trim();
        String password = txtPassword.getText().toString().trim();

        //CREATING VALIDATION FOR ALL THE FIELDS
        if (username.isEmpty()){
            txtUserName.setError("UserName is required");
            txtUserName.requestFocus();
            return;
        }
        if (firstname.isEmpty()){
            txtFirsName.setError("FirstName is required");
            txtFirsName.requestFocus();
            return;
        }
        if (lastname.isEmpty()){
            txtLastName.setError("LastName is required");
            txtLastName.requestFocus();
            return;
        }
        if (email.isEmpty()){
            txtEmail.setError("Email is required");
            txtEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            txtEmail.setError("Please provide a valid Email");
            txtEmail.requestFocus();
            return;
        }
        if (password.isEmpty()){
            txtPassword.setError("Password is required");
            txtPassword.requestFocus();
            return;
        }

        if (password.length() < 6){
            txtPassword.setError("The Min password length should be 6 characters!");
            txtPassword.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            UserInfo user = new UserInfo(firstname,lastname,username, email, password);
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            Task<Void> myRef = database.getReference("Users").
                                    child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>()
                                    {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()){
                                        Toast.makeText(regiSter.this, "Registration Completed Successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(regiSter.this, MainActivity.class));
                                    }else{
                                        Toast.makeText(regiSter.this, "Registration Incomplete", Toast.LENGTH_SHORT).show();
                                }
                                }
                            });
                        }else{
                            Toast.makeText(regiSter.this, "Registration Incompletedd", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

}
