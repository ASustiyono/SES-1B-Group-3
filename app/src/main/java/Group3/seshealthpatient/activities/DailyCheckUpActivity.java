package group3.seshealthpatient.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.LinkedList;

import group3.seshealthpatient.R;

public class DailyCheckUpActivity extends AppCompatActivity {

    private String patientId;
    private String dataPacketId;
    private String dataPacketTitle;
    private String feedbackType;
    private String mCommentsPath;
    private boolean hasLocation;
    private LinkedList<Comment> mComments;

    DatabaseReference mUsersDb;
    DatabaseReference mCommentsDb;
    DatabaseReference mDataPacketDb;

    private RelativeLayout mAddMessageWrapper;
    private TextView mMessageTV;
    private ListView mListView;
    private EditText addMessageTxt;
    private RelativeLayout mLocationRecommendation;

    private ValueEventListener mCommentsEventListener;
    private ValueEventListener mUsersEventListener;

    DataSnapshot mUserDataSnapshot;
    DataSnapshot mDataPacketDataSnapshot;

    public DailyCheckUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_daily_check_up );

        setTitle( "YOUR DAILY CHECKUP" );

        patientId = (String)getIntent().getSerializableExtra("PATIENT_ID");
        dataPacketId = (String)getIntent().getSerializableExtra("DATA_PACKET_ID");
        dataPacketTitle = (String)getIntent().getSerializableExtra("DATA_PACKET_TITLE");
        feedbackType = (String)getIntent().getSerializableExtra("FEEDBACK_TYPE");
        hasLocation = (boolean)getIntent().getSerializableExtra("HAS_LOCATION");

        //Adding Toolbar
        Toolbar toolbar = findViewById(R.id.tool);
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
}
