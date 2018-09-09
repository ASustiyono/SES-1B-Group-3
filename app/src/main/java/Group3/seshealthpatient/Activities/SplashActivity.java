package Group3.seshealthpatient.Activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import Group3.seshealthpatient.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_splash );

        new Handler().postDelayed( new Runnable() {
            @Override
            public void run() {
                Intent mainIntent = new Intent(SplashActivity.this, HelloActivity.class );
                startActivity( mainIntent );
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                // close this activity
            }
        }, 1000 );
    }
}

