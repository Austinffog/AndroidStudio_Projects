package com.example.signinsingupsettings;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class MainActivity extends AppCompatActivity {
private Button register, signIn;
private EditText txtUserName, editTextTextPassword;
private FirebaseAuth mAuth;
FirebaseDatabase firebaseDatabase;
DatabaseReference databaseReference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        register = (Button) findViewById(R.id.btnRegister);
        signIn = (Button) findViewById(R.id.btnLogIn);
        txtUserName = (EditText) findViewById(R.id.txtUserName);
        editTextTextPassword = (EditText) findViewById(R.id.editTextTextPassword);


        signIn.setOnClickListener((View.OnClickListener) new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                openSettingsActivity();
            }
        });


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRegisterActivity();
            }
        });




    }

    public void openRegisterActivity(){
        Intent intent = new Intent(this, regiSter.class);
        startActivity(intent);
    }


    public void openSettingsActivity(){
        String username = txtUserName.getText().toString().trim();
        String password = editTextTextPassword.getText().toString().trim();

        if(username.isEmpty()){
            txtUserName.setError("Username is required");
            txtUserName.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextTextPassword.setError("Password is required");
            editTextTextPassword.requestFocus();
            return;

        }






        mAuth.signInWithEmailAndPassword(username, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, settings.class));

                }else{
                    Toast.makeText(MainActivity.this, "Access granted!!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, settings.class));
                    txtUserName.clearFocus();
                    editTextTextPassword.clearFocus();
                }
            }
        });
    }
}
