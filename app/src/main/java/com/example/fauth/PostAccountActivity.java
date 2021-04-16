package com.example.fauth;

import androidx.appcompat.app.AppCompatActivity;
import com.example.fauth.userAPI;


import android.os.Bundle;
import android.widget.TextView;

public class PostAccountActivity extends AppCompatActivity {
    private TextView currentUserTextView;
    private String userId;
    private String currentUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_account);

        currentUserTextView = findViewById(R.id.currentUsername);

        if (userAPI.getInstance() != null) {
            userId = userAPI.getInstance().getUserId();
            currentUserName = userAPI.getInstance().getUsername();

            currentUserTextView.setText(currentUserName);
        }
    }
}