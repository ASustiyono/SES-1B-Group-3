package Group3.seshealthpatient.Activities;

import android.os.Bundle;
import android.widget.TextView;

import Group3.seshealthpatient.R;

public class RecievingActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recieving);


        Bundle bundle = getIntent().getExtras();

        // pass data to string variables

        String data_1 = bundle.getString("firstdata");
        String data_2 = bundle.getString("seconddata");

        // assign xml objects for Textviews into variables

        TextView textView = (TextView) findViewById(R.id.textView);
        TextView textView2 = (TextView) findViewById(R.id.textView2);


        textView.setText(data_1);
        textView2.setText(data_2);















    }
}
