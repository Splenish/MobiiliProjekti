package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        Button loginButton = findViewById(R.id.login_button);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userEmail = findViewById(R.id.email_field);
                String emailString = userEmail.getText().toString();

                EditText userPassword = findViewById(R.id.password_field);
                String passwordString = userPassword.getText().toString();

                signIn(emailString, passwordString);
            }
        });

        Button toRegisterButton = findViewById(R.id.to_registration_button);
        toRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Log.d("LOGIN", "already logged in");
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }

    public void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    Log.d("LOGIN", "sign in with email success");

                    FirebaseDatabase.getInstance().getReference("Users").child(mAuth.getCurrentUser()
                            .getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            User currentUser = dataSnapshot.getValue(User.class);
                            /*SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                            editor.putString("name", currentUser.getName());
                            editor.putString("profile_pic", currentUser.getName());
                            editor.putString("email", currentUser.getName());
                            editor.apply();*/
                            Log.d("SHOUTBOARD", "tungetaan shittii prefsii");
                            SharedPrefsHelper helper = new SharedPrefsHelper();
                            helper.userToPrefs(getBaseContext(), currentUser.getName(), currentUser.getProfile_pic(),
                                    currentUser.getEmail(), mAuth.getCurrentUser().getUid());
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });

                    FirebaseUser user = mAuth.getCurrentUser();
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    startActivity(intent);
                } else {
                    Log.w("LOGIN", "signInWithEmail:failure", task.getException());
                    Toast.makeText(LoginActivity.this, "Authentication failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
