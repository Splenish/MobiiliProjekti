package com.example.test;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Random;

import static com.example.test.ExtendedApplication.MY_PREFS_NAME;

public class NewPostActivity extends AppCompatActivity {


    private StorageReference mStorageRef;
    private static final int PICK_IMAGE = 100;
    Uri imageUri;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_post);

        mStorageRef = FirebaseStorage.getInstance().getReference();

        ImageView upload_image_btn = findViewById(R.id.upload_photo_button);
        upload_image_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button publish_button = findViewById(R.id.publish_button);
        publish_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publishToDb();
            }
        });


    }

    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            ImageView upload_image_btn = findViewById(R.id.upload_photo_button);
            imageUri = data.getData();
            Log.d("UPLOAD_PIC", "uri: " + imageUri);
            upload_image_btn.setImageURI(imageUri);
            TextView uploadText = findViewById(R.id.upload_photo_text);
            uploadText.setText("");
            Log.d("UPLOAD_PIC", upload_image_btn.getDrawable() + "");

        }
    }
    public void publishToDb() {

        EditText post_title_field = findViewById(R.id.edit_text_title);
        String post_title = post_title_field.getText().toString();

        if(post_title == null) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }

        EditText post_message_field = findViewById(R.id.edit_text_description);
        final String post_message = post_message_field.getText().toString();

        if(post_message == null) {
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }



        RadioGroup group = findViewById(R.id.radio_group_type);
        RadioButton checked_button = findViewById(group.getCheckedRadioButtonId());

        if(checked_button == null) {
            Log.d("NEW_POST", "null");
            Toast.makeText(this, "Please fill in all fields!", Toast.LENGTH_SHORT).show();
            return;
        }



        if(checked_button.getId() == R.id.radio_found) {
            Log.d("NEW_POST", "found");
            type = "found";
        } else if(checked_button.getId() == R.id.radio_missing) {
            Log.d("NEW_POST", "missing");
            type = "missing";
        } else if(checked_button.getId() == R.id.radio_deceased) {
            Log.d("NEW_POST", "deceased");
            type = "deceased";
        } else if(checked_button.getId() == R.id.radio_sighting) {
            Log.d("NEW_POST", "sighting");
            type = "sighting";
        }

        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        final String user_name = prefs.getString("name", null);
        final String profile_pic = prefs.getString("profile_pic", null);


        if(imageUri != null) {
            Random r = new Random();
            int randomNumber = r.nextInt(Integer.MAX_VALUE);
            final StorageReference profilePicRef = mStorageRef.child("postpics/" + FirebaseAuth.getInstance().getUid() + "/" +  randomNumber);
            final TextView progressText = findViewById(R.id.progress_text);
            profilePicRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    profilePicRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Log.d("NEW_POST", uri.toString());
                            progressText.setText("Upload done!");

                            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("shoutboard").child("posts").push();
                            String key = myRef.getKey();

                            Post post = new Post(0, post_message, uri.toString(), type, System.currentTimeMillis(),
                                    FirebaseAuth.getInstance().getUid(), 0, user_name, profile_pic, key);

                            myRef.setValue(post).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    Log.d("NEW_POST", "tungettii paskaa postiin");
                                    Intent intent = new Intent(getBaseContext(), ShoutboardActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                    });
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
                    Log.d("NEW_POST", "Upload is " + progress + "% done");
                    progressText.setText("Upload is " + Math.ceil(progress) + "% done");
                }
            });
        } else {

        }


    }
}
