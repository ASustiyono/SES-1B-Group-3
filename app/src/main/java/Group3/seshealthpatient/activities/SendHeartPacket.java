package group3.seshealthpatient.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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



import group3.seshealthpatient.R;


public class SendHeartPacket extends AppCompatActivity {

    private TextView textTime, textHR;
    private Button sendButton;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String ID;



    String heartRate = "N/A";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_heart_packet);


        textHR = findViewById(R.id.displayHeartRate);
        textTime = findViewById(R.id.displayTimeStamp);
        sendButton =findViewById(R.id.confirmBtn);

        myRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();

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
                Intent intent = new Intent(SendHeartPacket.this,MainActivity.class);
                startActivity(intent);

            }
        });





    }

    private void setTextView() {
        textHR.setText(heartRate);
    }


}




