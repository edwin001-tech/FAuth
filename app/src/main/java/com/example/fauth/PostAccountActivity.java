package com.example.fauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fauth.userAPI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class PostAccountActivity extends AppCompatActivity {
    private TextView currentUserTextView;
    private String userId;
    private String currentUserName;
    private FirebaseUser user;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_account);


        firebaseAuth = FirebaseAuth.getInstance();
        user = firebaseAuth.getCurrentUser();

        currentUserTextView = findViewById(R.id.currentUsername);

        if (userAPI.getInstance() != null) {
            userId = userAPI.getInstance().getUserId();
            currentUserName = userAPI.getInstance().getUsername();

            currentUserTextView.setText(currentUserName);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.location:
                //show user location


        break;
            case R.id.signout:
                //signout the current user
                if (user != null && firebaseAuth != null) {
                    firebaseAuth.signOut();
                    startActivity(new Intent(PostAccountActivity.this,
                            MainActivity.class));
                    //finish();
                }

                break;



        }
        return super.onOptionsItemSelected(item);
    }
}