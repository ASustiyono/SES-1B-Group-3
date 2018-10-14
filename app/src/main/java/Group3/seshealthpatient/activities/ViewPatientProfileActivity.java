package group3.seshealthpatient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import group3.seshealthpatient.R;

public class ViewPatientProfileActivity extends AppCompatActivity {

    private static final String TAG = "PatientProfileActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_view_patient_profile );
        Log.d(TAG, "onCreate:started");

        getIncomingIntent();
    }

    private void getIncomingIntent(){
        Log.d(TAG, "getIncomingIntent: checking for incoming intents.");

        if(getIntent().hasExtra("patient_name")){
            Log.d(TAG, "getIncomingIntent: found intent extras.");

            String imageName = getIntent().getStringExtra("patient_name");

            setImage(imageName);
        }
    }

    private void setImage(String imageName){
        Log.d(TAG, "setImage: setting name to widgets.");

        TextView name = findViewById(R.id.patient_profile_description);
        name.setText(imageName);
    }
}
