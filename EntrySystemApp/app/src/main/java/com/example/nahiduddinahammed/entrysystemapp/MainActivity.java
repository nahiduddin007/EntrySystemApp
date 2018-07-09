package com.example.nahiduddinahammed.entrysystemapp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private Button buttonlogin;
    private EditText editTextemail;
    private EditText editTextpassword;
    private ProgressDialog dialog;


    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);


        dialog = new ProgressDialog(this);


        firebaseAuth = FirebaseAuth.getInstance();


        if (firebaseAuth.getCurrentUser() != null) {
            Intent intent = new Intent(MainActivity.this, EntryPageActivity.class);
            startActivity(intent);
            Toast.makeText(this, firebaseAuth.getCurrentUser().toString(), Toast.LENGTH_SHORT).show();
        }


        buttonlogin = (Button) findViewById(R.id.LoginButton);
        editTextemail = (EditText) findViewById(R.id.TVEmail);
        editTextpassword = (EditText) findViewById(R.id.TVPassword);

        buttonlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });


    }

    private void login() {

        String email = editTextemail.getText().toString().trim();
        String password = editTextpassword.getText().toString().trim();

        if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password)) {
            if (TextUtils.isEmpty(email)) {
                editTextemail.setError("username is empty");
            }
            if (TextUtils.isEmpty(password)) {
                editTextpassword.setError("password is empty");
                Toast.makeText(this, "Enter the username and password", Toast.LENGTH_SHORT).show();
            }

        } else {

            dialog.show();
            if (editTextpassword.length() < 6) {
                dialog.setMessage("Loggin in");
                Toast.makeText(this, "Password is too short,password at least 6 character", Toast.LENGTH_LONG).show();
            }


            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, task.getResult().getUser().getEmail(), Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this, EntryPageActivity.class);

                        intent.putExtra("userName", task.getResult().getUser().getEmail());

                        startActivity(intent);
                    } else {


                        Toast.makeText(MainActivity.this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
                        Toast.makeText(MainActivity.this, "Cannot Login", Toast.LENGTH_LONG).show();
                    }
                    dialog.cancel();

                }
            });

        }





    }
    private void backpressed()
    {
        Intent intent=new Intent(MainActivity.this,MainActivity.class);
        startActivity(intent);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backpressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (firebaseAuth.getCurrentUser()!= null) {
            Intent intent=new Intent(MainActivity.this,EntryPageActivity.class);
            startActivity(intent);
        }
    }





}





