package group3.seshealthpatient.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


import group3.seshealthpatient.R;


public class SendHeartPacket extends AppCompatActivity {

    private TextView textTime, textHR;
    private Button sendButton;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String ID;

    private DatabaseReference mUserRef;
    private String mCurrentUserId;

    String heartRate = "N/A";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_heart_packet);

        textHR = findViewById(R.id.displayHeartRate);
        textTime = findViewById(R.id.displayTimeStamp);
        sendButton = findViewById(R.id.confirmBtn);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

        mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child( "Patients" ).child( mCurrentUserId );

        if(mAuth.getCurrentUser() != null){
            ID = mAuth.getCurrentUser().getUid();
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM yyyy");
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedTime = currentTime.format(c.getTime());

        textTime.setText(formattedDate + " at " + formattedTime);
        heartRate = getIntent().getStringExtra("HR");

        setTextView();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(SendHeartPacket.this, "Heart Data has been confirmed", Toast.LENGTH_LONG).show();

                /*)
                //Andre's Part for Daily CheckUp
                String hr = heartRate;
                Intent i = new Intent(SendHeartPacket.this, DailyCheckUpActivity.class);
                Bundle b = new Bundle();
                b.putString("HR", hr);
                i.putExtras(b);
                */

                final String mHeartRate = textHR.getText().toString();
                final String mTime = textTime.getText().toString();

                final HashMap userMap = new HashMap();

                userMap.put( "tempHeartRate", mHeartRate );
                userMap.put( "tempHeartRateTime", mTime );

                mUserRef.updateChildren( userMap ).addOnCompleteListener( new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( getApplicationContext(), "Saved Heart Rate@", Toast.LENGTH_SHORT ).show();

                            userMap.put( "tempHeartRate", heartRate );

                            finish();
                        } else {
                            String message = task.getException().getMessage();
                            Toast.makeText( getApplicationContext(), "Something went wrong. Please try again.\n" + message, Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );

                //Finish activity
                finish();

            }
        });
        //Adding Toolbar
        Toolbar toolbar = findViewById(R.id.tool);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //startActivity(new Intent(ActivityOne.this, ActivityTwo.class));
        finish();
    }

    private void setTextView() {
        textHR.setText(heartRate);
    }
}




