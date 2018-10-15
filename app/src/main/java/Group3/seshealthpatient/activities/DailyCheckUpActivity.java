package group3.seshealthpatient.activities;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.w3c.dom.Comment;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Locale;

import butterknife.OnClick;
import group3.seshealthpatient.R;
import group3.seshealthpatient.fragments.HeartRateFragment;
import group3.seshealthpatient.model.DataPacket;

public class DailyCheckUpActivity extends AppCompatActivity {

    //Components
    private EditText mTitle, mDescription;
    private TextView mHeartRate, mCurrentTime;

    private DatabaseReference mUserRef;
    private String mCurrentUserId;

    private Button send;
    private Button heartRateBtn, videoBtn, medicalBtn;

    //Data packet
    private DataPacket dataPacket;
    private DatabaseReference databaseReference;
    private FirebaseUser userId;
    private String uid;

    //Video
    private Uri mVideo_uri;
    private int VIDEO_REQUEST_CODE = 1001;
    private Uri filePath;
    private static final int PICK_VIDEO_REQUEST = 234;
    private Uri fileUri;
    StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private TextView mLatestTitle;

    public DailyCheckUpActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_daily_check_up );

        setTitle( "YOUR DAILY CHECKUP" );

        mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child( "Patients" ).child( mCurrentUserId );

        bindComponents();

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM yyyy");
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm:ss");
        String formattedDate = df.format(c.getTime());
        String formattedTime = currentTime.format(c.getTime());

        mCurrentTime.setText(formattedDate + " at " + formattedTime);

        uid =  FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                mHeartRate.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "tempHeartRate" ).getValue( String.class ) );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        } );

        //heartRate = getIntent().getStringExtra("HR");

        dataPacket = new DataPacket();

        send = findViewById( R.id.dcu_checkUp_button );
        send.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CheckUp();
            }
        } );

        heartRateBtn = findViewById( R.id.dcu_heart_button);
        heartRateBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DailyCheckUpActivity.this, HeartRateMonitor.class);
                //Start second activity
                startActivity(intent);
            }
        } );

        videoBtn = findViewById( R.id.dcu_video_button );
        videoBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //recordVideo(v);
                showFileChooser();
            }
        } );

        medicalBtn = findViewById( R.id.dcu_medical_button );
        medicalBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        } );

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

    private void bindComponents() {
        mTitle = findViewById(R.id.daily_title_editText);
        mDescription = findViewById(R.id.daily_description_editText);
        mHeartRate = findViewById(R.id.dcu_heart_textview);
        mCurrentTime = findViewById( R.id.dcu_time_textview );

        //mLatestTitle = findViewById( R.id.latestcu_title_textview );
    }

    private void CheckUp() {
        final String title = mTitle.getText().toString();
        final String description = mDescription.getText().toString();
        final String heartRate = mHeartRate.getText().toString();
        final String timeAndDate = mCurrentTime.getText().toString();

        final HashMap userMap = new HashMap();

        if (TextUtils.isEmpty( title )) {
            mTitle.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( description )) {
            mDescription.setError( "Required" );
            return;
        } else {
            userMap.put("latestCheckUpTitle", title);
            userMap.put("latestCheckUpDescription", description);
            userMap.put("latestCheckUpDate", timeAndDate);
            userMap.put("latestCheckUpHeartRate", heartRate);
        }

        mUserRef.updateChildren( userMap ).addOnCompleteListener( new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText( getApplicationContext(), "Successful CheckUp!", Toast.LENGTH_SHORT ).show();

                    String queryKey = mUserRef.child("CheckUp Packets").push().getKey();

                    dataPacket.setId(queryKey);
                    dataPacket.setDescription( description );
                    dataPacket.setTitle( title );
                    dataPacket.setHeartRate( heartRate );
                    dataPacket.setDate( timeAndDate );

                    mUserRef.child("CheckUp Packets").child(queryKey).setValue(dataPacket);

                    finish();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText( getApplicationContext(), "Something went wrong. Please try again.\n" + message, Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }

    /*
    public void recordVideo(View view) {
        Intent camera_intent = new Intent( MediaStore.ACTION_VIDEO_CAPTURE);
        File video_file = getFilepath();
        //Uri video_uri = Uri.fromFile(video_file);
        mVideo_uri = FileProvider.getUriForFile(DailyCheckUpActivity.this, "group3.seshealthpatient.provider", video_file);
        camera_intent.putExtra(MediaStore.EXTRA_OUTPUT, mVideo_uri);
        camera_intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
        startActivityForResult(camera_intent, VIDEO_REQUEST_CODE);
    }

    public File getFilepath() {
        //create a folder to store the video
        File folder = new File("sdcard/video_app");
        //check fold if exists
        if (folder.exists()) {
            folder.mkdir();
        }
        Date date = new Date();
        String dateString = String.format( Locale.ENGLISH, "VID_%1$tY%1$tm%1$td_%1$tk%1$tM%1$tS", date, ".mp4");
        File video_file = new File(folder, dateString);
        return video_file;
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_VIDEO_REQUEST) {
            if (resultCode == this.RESULT_OK && data.getData() != null) {
                filePath = data.getData();
                uploadFile();

            } else {
                Toast.makeText(this,
                        "Video uploaded canceled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    //this method will upload the file
    private void uploadFile() {
        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading Video");
            progressDialog.show();

            String userUid = FirebaseAuth.getInstance().getCurrentUser().getUid();


            Date date = new Date();
            String dateString = String.format(Locale.ENGLISH, "VID_%1$tY%1$tm%1$td_%1$tk%1$tM%1$tS", date, ".mp4");
            File video_file = new File(dateString);

            fileUri = Uri.fromFile(video_file);

            final StorageReference riversRef = storageReference.child(userUid+ "/"+ fileUri.getLastPathSegment());
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            //if the upload is successful
                            //hiding the progress dialog
                            progressDialog.dismiss();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            //if the upload is not successful
                            //hiding the progress dialog
                            progressDialog.dismiss();

                            //and displaying error message
                            Toast.makeText(DailyCheckUpActivity.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });

            UploadTask uploadTask = riversRef.putFile(filePath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    // Continue with the task to get the download URL
                    return riversRef.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        dataPacket.setVideo( downloadUri.toString() );
                        Toast.makeText(DailyCheckUpActivity.this, "Video uploaded successfully :)", Toast.LENGTH_LONG).show();
                    } else {
                        // Handle failures
                        // ...
                    }
                }
            });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
    */

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("video/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Video"), PICK_VIDEO_REQUEST);
    }
}
