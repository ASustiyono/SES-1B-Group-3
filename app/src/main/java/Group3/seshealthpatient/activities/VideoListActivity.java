package group3.seshealthpatient.activities;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

import group3.seshealthpatient.R;
import group3.seshealthpatient.adapters.VideoListAdapter;

public class VideoListActivity extends AppCompatActivity {
    private DatabaseReference mDatabaseRef;
    private List<VideoUpload> vidList;
    private ListView lv;
    private VideoListAdapter adapter;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_list);
        vidList = new ArrayList<>();
        lv = (ListView) findViewById(R.id.listViewVideo);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait loading list video...");
        progressDialog.show();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference(UploadVideoActivity.FB_DATABASE_PART);

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                progressDialog.dismiss();

                //Fetch video data from firebase database
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    //VideoUpload class require default constructor
                    VideoUpload vid = snapshot.getValue(VideoUpload.class);
                    vidList.add(vid);
                }

                //Init adapter
                adapter = new VideoListAdapter(VideoListActivity.this,R.layout.activity_video_list,vidList);
                //Set adapter for listview
                lv.setAdapter(adapter);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                progressDialog.dismiss();
            }
        });
    }
}
