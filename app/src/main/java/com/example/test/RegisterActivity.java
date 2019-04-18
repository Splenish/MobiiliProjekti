package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.io.File;

import static com.example.test.ExtendedApplication.MY_PREFS_NAME;

public class RegisterActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private StorageReference mStorageRef;
    private static final int PICK_IMAGE = 111;
    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        ImageView register_pic = findViewById(R.id.register_logo);
        register_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

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
                    helper.userToPrefs(getBaseContext(), newUser.getName(), newUser.getProfile_pic(), newUser.getEmail(),
                            FirebaseAuth.getInstance().getCurrentUser().getUid());

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

    public void openGallery(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            ImageView upload_prof_pic = findViewById(R.id.register_logo);
            imageUri = data.getData();
            Log.d("UPLOAD_PIC", "uri: " + imageUri);

            //File fileLocation = new File(imageUri.toString()); //file path, which can be String, or Uri



            Picasso.get().load(imageUri).into(upload_prof_pic);

            upload_prof_pic.setImageURI(imageUri);
            upload_prof_pic.requestLayout();
            TextView uploadText = findViewById(R.id.upload_prompt_register);
            uploadText.setText("");
            Log.d("UPLOAD_PIC", upload_prof_pic.getDrawable() + "");

        }
    }

}
