package Group3.seshealthpatient.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import Group3.seshealthpatient.R;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import static Group3.seshealthpatient.Activities.DoctorRegistrationActivity.EMAIL_ADDRESS_DOCTOR;

/**
 * Class: LoginActivity
 * Extends: {@link AppCompatActivity}
 * Author: Carlos Tirado < Carlos.TiradoCorts@uts.edu.au> and YOU!
 * Description:
 * <p>
 * Welcome to the first class in the project. I will be leaving some comments like this through all
 * the classes I write in order to help you get a hold on the project. Here I took the liberty of
 * creating an empty Log In activity for you to fill in the details of how your log in is
 * gonna work. Please, Modify Accordingly!
 * <p>
 */
public class LoginActivity extends AppCompatActivity {


    /**
     * Use the @BindView annotation so Butter Knife can search for that view, and cast it for you
     * (in this case it will get casted to Edit Text)
     */
    @BindView(R.id.usernameET)
    EditText usernameEditText;

    /**
     * If you want to know more about Butter Knife, please, see the link I left at the build.gradle
     * file.
     */
    @BindView(R.id.passwordET)
    EditText passwordEditText;

    /**
     * It is helpful to create a tag for every activity/fragment. It will be easier to understand
     * log messages by having different tags on different places.
     */
    private static String TAG = "LoginActivity";

    FirebaseAuth Auth;

    private boolean firstTime;

    private String tempEmail;
    private String tempPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // You need this line on your activity so Butter Knife knows what Activity-View we are referencing
        ButterKnife.bind(this);

        // A reference to the toolbar, that way we can modify it as we please
        //Toolbar toolbar = findViewById(R.id.login_toolbar);
        //setSupportActionBar(toolbar);

        // Please try to use more String resources (values -> strings.xml) vs hardcoded Strings.
        setTitle(R.string.login_activity_title);

        Auth = FirebaseAuth.getInstance();

        firstTime = getSharedPreferences("PREFERENCE", MODE_PRIVATE)
                .getBoolean("firstTime", true);
    }


    /**
     * See how Butter Knife also lets us add an on click event by adding this annotation before the
     * declaration of the function, making our life way easier.
     */
    @OnClick(R.id.login_btn)
    public void LogIn() {
        final String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if(username.isEmpty()){
            usernameEditText.setError("Email is required");
            usernameEditText.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){   // THIS METHOD CHECKS IF ITS A REAL EMAIL
            usernameEditText.setError("Please enter a valid email");
            usernameEditText.requestFocus();
            return;
        }
        if(password.isEmpty()){
            passwordEditText.setError("Password is required");
            passwordEditText.requestFocus();
            return;
        }

        Auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(!firstTime) {
                        getSharedPreferences("PREFERENCE", MODE_PRIVATE).edit()
                                .putBoolean("isFirstRun", false).commit();

                        Intent tutorialIntent = new Intent(LoginActivity.this, TutorialActivity.class);
                        tutorialIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(tutorialIntent);
                        finish();
                    } else if(EMAIL_ADDRESS_DOCTOR.matcher(username).matches()) {
                        Intent homePageIntent = new Intent(LoginActivity.this, DoctorMainActivity.class);
                        startActivity(homePageIntent);
                        finish();
                    } else {
                        Intent homePageIntent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(homePageIntent);
                        finish();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        // TODO: For now, the login button will simply print on the console the username/password and let you in
        // TODO: It is up to you guys to implement a proper login system

        // Having a tag, and the name of the function on the console message helps allot in
        // knowing where the message should appear.
        Log.d(TAG, "LogIn: username: " + username + " password: " + password);


        /**THIS IS REMOVED TO ADD FIREBASE REGISTRATION
        // Start a new activity
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        */
    }

    @OnClick(R.id.register_btn)
    public void onClick(View v) {
        Intent RegisterIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(RegisterIntent);
    }
}
