package com.example.fauth;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private ProgressBar progressBar;
    private EditText email;
    private EditText password;
    private Button login;
    private TextView or;
    private TextView createAccount;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressBar = findViewById(R.id.progressBar);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.sign_in_button);
        createAccount = findViewById(R.id.create_account);
    }
}