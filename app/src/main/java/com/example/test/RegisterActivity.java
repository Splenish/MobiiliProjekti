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
import com.google.firebase.database.FirebaseDatabase;

import static com.example.test.ExtendedApplication.MY_PREFS_NAME;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        Button to_login_button = findViewById(R.id.to_login_button_from_reg);
        to_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                startActivity(intent);
            }
        });

        Button register_button = findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText emailEdit = findViewById(R.id.email_field_register);
                String email_string = emailEdit.getText().toString();

                EditText passEdit = findViewById(R.id.password_field_register);
                String password_string = passEdit.getText().toString();

                EditText passEditConfirm = findViewById(R.id.password_field_register_confirm);
                String confirm_password_string = passEditConfirm.getText().toString();

                if(password_string.equals(confirm_password_string)) {
                    createAccount(email_string, password_string);
                } else {
                    Toast.makeText(RegisterActivity.this, "Passwords don't match", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void createAccount(final String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    String profilepicHardCoded = "https://vignette.wikia.nocookie.net/lpmc/images/9/9d/Sam_hyde.PNG/revision/latest?cb=20171105142606";
                    EditText name_field = findViewById(R.id.name_field);
                    String name = name_field.getText().toString();
                    User newUser =  new User(name, email, profilepicHardCoded);

                    /*
                    SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                    editor.putString("name", name);
                    editor.putString("profile_pic", profilepicHardCoded);
                    editor.putString("email", email);
                    editor.apply();*/

                    SharedPrefsHelper helper = new SharedPrefsHelper();
                    helper.userToPrefs(getBaseContext(), newUser.getName(), newUser.getProfile_pic(), newUser.getEmail());

                    FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance()
                            .getCurrentUser().getUid()).setValue(newUser);

                    Log.d("LOGIN", "user registration successful");
                    Intent intent = new Intent(getBaseContext(), LoginActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(RegisterActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
