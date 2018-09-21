package group3.seshealthpatient.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import group3.seshealthpatient.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class HelloActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_hello );

        ButterKnife.bind( this );
    }

    @OnClick({R.id.hello_btn, R.id.hello_register_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.hello_btn:
                startActivity( new Intent( this, LoginActivity.class ) );
                finish();
                break;
            case R.id.hello_register_btn:
                startActivity( new Intent( this, RegistrationActivity.class ) );
                break;
        }
    }
}
