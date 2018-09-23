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

public class PatientChangePasswordActivity extends AppCompatActivity {

    EditText passwordEditText, matchPasswordEditText;

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    String mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_patient_change_password );

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = mUser.getUid();

        ButterKnife.bind( this );

        passwordEditText = findViewById( R.id.patient_password_editTxt );
        matchPasswordEditText = findViewById( R.id.patient_confirmPassword_editTxt );


        mAuth = FirebaseAuth.getInstance();
        //Setting Toolbar Text
        //setTitle( "RESET PASSWORD" );\
        setTitle("SETTINGS");

        //Adding Toolbar
        Toolbar toolbar = findViewById(R.id.setting_password_tool);
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

    private void changePassword() {
        String password = passwordEditText.getText().toString().trim();
        String matchPassword = matchPasswordEditText.getText().toString().trim();

        if (password.isEmpty()) {
            passwordEditText.setError( "Password is required" );
            passwordEditText.requestFocus();
            return;
        }
        if (password.length() < 6) {
            passwordEditText.setError( "Minimum characters for a password is 6" );
            passwordEditText.requestFocus();
            return;
        }
        if (!password.equals( matchPassword )) {
            matchPasswordEditText.setError( "Passwords don't match" );
            matchPasswordEditText.requestFocus();
            return;
        }

        mUser.updatePassword(password).addOnCompleteListener( new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText( getApplicationContext(), "Successfully changed Password", Toast.LENGTH_SHORT ).show();
                    finish();
                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText( getApplicationContext(), "Please use another password", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        //This does not work sadly.
        /*
        mAuth.confirmPasswordReset(password,matchPassword ).addOnCompleteListener( new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText( getApplicationContext(), "Successfully changed Password", Toast.LENGTH_SHORT ).show();
                    finish();
                } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                    Toast.makeText( getApplicationContext(), "Please use another password", Toast.LENGTH_SHORT ).show();
                }
            }
        } );
        */


    }

    @OnClick({R.id.patient_confirmPassword_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.patient_confirmPassword_btn:
                changePassword();
                break;
        }
    }
}
