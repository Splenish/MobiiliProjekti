package com.example.test;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class MyService extends Service {

    Trap trapPassedFromIntent;
    String CHANNEL_ID = "TRIGGER";
    boolean FIRST_ATTACH = true;
    String currentUser;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }



    @Override
    public void onCreate() {
        //Log.d("SERVIISI", "on service create");
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        //Log.d("SERVIISI", "On service destroy");
        super.onDestroy();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        setTrapListener(intent);
        //Log.d("SERVIISI", "on start command");
        //currentUser = intent.getStringExtra("ownerID");
        return START_REDELIVER_INTENT;
    }
    public void notificationHandle(DataSnapshot data) {
        Log.d("SERVICETEST", "nyt ollaan jännän äärellä.");
        //Intent returnIntent = new Intent(this, MainActivity.class);
        //Log.d("MAPPIA", "data get key: " + data.getKey().toString());
        data.getRef().getParent().addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Log.d("MAPPIA", "nyt ollaan jännän äärellä.");

                Trap aTrap = dataSnapshot.getValue(Trap.class);
                Log.d("SERVICETEST", "triggered trap ID " + aTrap.getTrapID());
                Intent returnIntent = new Intent(getBaseContext(), MapActivity.class);
                returnIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);


                returnIntent.putExtra("trapIntent", aTrap);

                Log.d("MAPPIA", "urlstring of triggered trap" + aTrap.getUrlString());

                //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                PendingIntent pendingIntent = PendingIntent.getActivity(getBaseContext(), 0, returnIntent, PendingIntent.FLAG_UPDATE_CURRENT);



                NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(getBaseContext(), CHANNEL_ID)
                        .setSmallIcon(R.drawable.notification_icon)
                        .setContentTitle("Loukku lauennut!")
                        .setContentText("loukkusi " + aTrap.getTrapID() + " on lauennut")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        // Set the intent that will fire when the user taps the notification
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);

                NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getBaseContext());

                // notificationId is a unique int for each notification that you must define
                notificationManager.notify(0, mBuilder.build());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void setTrapListener(Intent passedIntent) {
        trapPassedFromIntent = (Trap) passedIntent.getSerializableExtra("passedTrap");
        Log.d("SERVICETEST", "servicee tullee trap ID " + trapPassedFromIntent.getTrapID());
        //Log.d("SERVIISI", "gOT trap ID : " + trapPassedFromIntent.getTrapID());
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference trapRefs = database.getReference("traps").child(trapPassedFromIntent.getTrapID()).child("triggered");

        Log.d("SERVICETEST", "testi2");
        trapRefs.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Log.d("SERVIISI", "Specific trap triggered " + dataSnapshot.getValue());
                Log.d("SERVICETEST", "testi");
                if(dataSnapshot.getValue().toString().equals("true")) {
                    //Log.d("SERVIISI", "loukkusi " + dataSnapshot.getRef().getParent().getKey() + " on lauennut");
                    notificationHandle(dataSnapshot);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //myRef.setValue("Hello, World!");

    }
}
