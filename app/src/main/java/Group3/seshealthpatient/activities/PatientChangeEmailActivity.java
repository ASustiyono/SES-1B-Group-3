package group3.seshealthpatient.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;

import butterknife.ButterKnife;
import butterknife.OnClick;
import group3.seshealthpatient.R;

import static group3.seshealthpatient.activities.DoctorRegistrationActivity.EMAIL_ADDRESS_DOCTOR;

public class PatientChangeEmailActivity extends AppCompatActivity {

    EditText emailEditText, matchEmailEditText;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient_change_email );

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = mUser.getUid();

        ButterKnife.bind( this );

        emailEditText = findViewById( R.id.patient_email_editTxt );
        matchEmailEditText = findViewById( R.id.patient_confirmEmail_editTxt );


        mAuth = FirebaseAuth.getInstance();
        //Setting Toolbar Text
        //setTitle( "RESET PASSWORD" );\
        setTitle("SETTINGS");

        //Adding Toolbar
        Toolbar toolbar = findViewById(R.id.setting_email_tool);
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

    private void changeEmail() {
        String email = emailEditText.getText().toString().trim();
        String matchEmail = matchEmailEditText.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError( "Email is required" );
            emailEditText.requestFocus();
            return;
        }
        /*
        if (!EMAIL_ADDRESS_DOCTOR.matcher( email ).matches()) {   // THIS METHOD CHECKS IF ITS A REAL EMAIL
            emailEditText.setError( "Please enter a valid email" );
            emailEditText.requestFocus();
            return;
        }
        */
        if (!email.equals( matchEmail )) {
            matchEmailEditText.setError( "Email don't match" );
            matchEmailEditText.requestFocus();
            return;
        }
            }

    @OnClick({R.id.patient_confirmEmail_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.patient_confirmEmail_btn:
                changeEmail();
                break;
        }
    }
}
