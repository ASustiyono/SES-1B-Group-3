package Group3.seshealthpatient.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import Group3.seshealthpatient.R;

public class SendingActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending);
    }

    public void passData(View view){

        String data1 = "this is data 1";
        String data2 = "this is data 2";

        Intent passdata_intent = new Intent(this, RecievingActivity.class);

        passdata_intent.putExtra("firstdata", data1);
        passdata_intent.putExtra("seconddata", data2);


        startActivity(passdata_intent);







    }
}
