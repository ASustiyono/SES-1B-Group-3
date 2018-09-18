package group3.seshealthpatient.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

import java.util.regex.Pattern;

import group3.seshealthpatient.R;

public class DoctorRegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    EditText emailEditText, passwordEditText, matchPasswordEditText;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_doctor_registration );

        emailEditText = (EditText) findViewById( R.id.doctor_register_email_editText );
        passwordEditText = (EditText) findViewById( R.id.doctor_register_password_editText );
        matchPasswordEditText = (EditText) findViewById( R.id.doctor_register_matchPassword_editText );

        mAuth = FirebaseAuth.getInstance();

        findViewById( R.id.doctor_register_btn ).setOnClickListener( this );
        findViewById( R.id.doctor_register_close_btn ).setOnClickListener( this );
        findViewById( R.id.doctor_user_btn ).setOnClickListener( this );
    }

    private void registerUser() {
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String matchPassword = matchPasswordEditText.getText().toString().trim();
        //String name = editText6.getText().toString().trim();

        if (email.isEmpty()) {
            emailEditText.setError( "Email is required" );
            emailEditText.requestFocus();
            return;
        }
        if (!EMAIL_ADDRESS_DOCTOR.matcher( email ).matches()) {   // THIS METHOD CHECKS IF ITS A REAL EMAIL
            emailEditText.setError( "Please enter a valid email" );
            emailEditText.requestFocus();
            return;
        }
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

        mAuth.createUserWithEmailAndPassword( email, password )
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText( getApplicationContext(), "You've Successfully Registered", Toast.LENGTH_SHORT ).show();
                            startActivity( new Intent( DoctorRegistrationActivity.this, DoctorAddInfoActivity.class ) );
                            finish();
                        } else if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                            Toast.makeText( getApplicationContext(), "You've already registered mate", Toast.LENGTH_SHORT ).show();
                        }
                    }
                } );
    }

    public static final Pattern EMAIL_ADDRESS_DOCTOR
            = Pattern.compile(
            "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                    "\\@" +
                    "doctor" +
                    "(" +
                    "\\." +
                    "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                    ")+"
    );

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.doctor_register_btn:
                registerUser();
                break;
            case R.id.doctor_register_close_btn:
                finish();
                break;
            case R.id.doctor_user_btn:
                startActivity( new Intent( DoctorRegistrationActivity.this, RegistrationActivity.class ) );
                finish();
                break;
        }
    }
}
