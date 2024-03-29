package com.example.fauth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class CreateAccount extends AppCompatActivity {
    private ProgressBar progressBar;
    private TextInputEditText email;
    private TextInputEditText password;
    private TextInputEditText username;
    private Button createAccount;
    private TextView haveAccount;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private FirebaseUser currentUser;

    //Firestore connection
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private final CollectionReference collectionReference = db.collection("Users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        firebaseAuth = FirebaseAuth.getInstance();




        progressBar = findViewById(R.id.progressbar);
        email = findViewById(R.id.emailCreateAccount);
        username = findViewById(R.id.username);
        password = findViewById(R.id.passwordCreateAccount);
        createAccount = findViewById(R.id.buttonCreateAccount);
        haveAccount = findViewById(R.id.haveAccount);

        haveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, MainActivity.class));
                finish();
            }
        });

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                currentUser = firebaseAuth.getCurrentUser();

                if (currentUser != null){
                    //we have a user
                }
                else {
                    //no user yet
                }

            }
        };

        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!TextUtils.isEmpty(email.getText().toString())
                        && !TextUtils.isEmpty(password.getText().toString())
                        && !TextUtils.isEmpty(username.getText().toString())) {

                    String Email = email.getText().toString().trim();
                    String Password = password.getText().toString().trim();
                    String Username = username.getText().toString().trim();

                    createUserEmailAccount(Email, Password, Username);

                }else {
                    Toast.makeText(CreateAccount.this,
                            "Empty Fields Not Allowed",
                            Toast.LENGTH_LONG)
                            .show();
                }

            }
        });


    }

    private void createUserEmailAccount (String email, String password, final String username) {
        if (!TextUtils.isEmpty(email)
                && !TextUtils.isEmpty(password)
                && !TextUtils.isEmpty(username)) {

            progressBar.setVisibility(View.VISIBLE);

            firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                //we take user to PostAccountActivity
                                currentUser = firebaseAuth.getCurrentUser();
                                assert currentUser != null;
                                final String currentUserId = currentUser.getUid();

                                //Create a user Map so we can create a user in the User collection
                                Map<String, String> userObj = new HashMap<>();
                                userObj.put("userId", currentUserId);
                                userObj.put("username", username);

                                //save to firestore database
                                collectionReference.add(userObj)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                documentReference.get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if (Objects.requireNonNull(task.getResult()).exists()) {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                    String name = task.getResult()
                                                                            .getString("username");


                                                                    Intent intent = new Intent(CreateAccount.this,
                                                                            MainActivity.class);
                                                                    intent.putExtra("username", name);
                                                                    intent.putExtra("userId", currentUserId);
                                                                    startActivity(intent);
                                                                    overridePendingTransition(R.anim.slide_in, R.anim.slide_out, R.anim.slide_back_in, R.anim.slide_back_out);

                                                                } else {
                                                                    progressBar.setVisibility(View.INVISIBLE);
                                                                }
                                                            }
                                                        });

                                            }
                                        })

                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {

                                            }
                                        });

                            } else {
                                //something went wrong

                            }
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                        }
                    });


            }

        }

    private void overridePendingTransition(int slide_in, int slide_out, int slide_back_in, int slide_back_out) {
    }

    //check if user is currently signed in
    @Override
    protected void onStart() {
        super.onStart();

        currentUser = firebaseAuth.getCurrentUser();
        firebaseAuth.addAuthStateListener(authStateListener);
    }
}