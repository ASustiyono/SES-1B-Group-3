package group3.seshealthpatient.activities;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import group3.seshealthpatient.R;

public class UploadVideoActivity extends AppCompatActivity {
    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;
    private DatabaseReference patientsInfor;
    private VideoView videoView;
    FirebaseUser userId;
    private EditText txtVideoName;
    private Uri vidUrl;



    public static final String FB_DATABASE_PART="video";
    public static final String FB_STORAGE_PART="video/";
    public static final int REQUEST_CODE = 1234;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_video);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference(FB_DATABASE_PART);
        patientsInfor=FirebaseDatabase.getInstance().getReference();
        videoView = (VideoView) findViewById(R.id.selectedVideo);
        txtVideoName = (EditText)findViewById(R.id.txtVideoname);

        //Setting Toolbar Text
        //setTitle( "RESET PASSWORD" );\
        setTitle("SETTINGS");

        //Adding Toolbar
        Toolbar toolbar = findViewById(R.id.setting_upload_video_tool);
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

    public void btnSelectVideo(View v){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null && data.getData() != null){
            vidUrl = data.getData();
            String[] filePathColumns = {MediaStore.Video.Media.DATA};
            Cursor c = getContentResolver().query(vidUrl, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String videoPath = c.getString(columnIndex);
            videoView.setVideoURI(Uri.parse(videoPath));
            videoView.setMediaController(new MediaController(this));
            videoView.start();
            c.close();

        }
    }

    public String getVideoExt(Uri uri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));

    }
    public String getUid(){
        userId = FirebaseAuth.getInstance().getCurrentUser();
        String uid = userId.getUid();
        return uid;
    }

    @SuppressWarnings("VisibleForTests")
    public void btnUploadVideo(View v){
        if(vidUrl != null){
            final ProgressDialog dialog = new ProgressDialog(this);
            dialog.setTitle("Uploading Video");
            dialog.show();

            final StorageReference ref = mStorageRef.child(FB_STORAGE_PART+System.currentTimeMillis() +"."+getVideoExt(vidUrl));

            ref.putFile(vidUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                    while (!uriTask.isSuccessful());
                    Uri downloadUrl = uriTask.getResult();

                    //Dismiss dialog when success
                    dialog.dismiss();
                    //Display success toast mas
                    Toast.makeText(getApplicationContext(), "Video uploaded", Toast.LENGTH_SHORT).show();
                    VideoUpload videoUpload = new VideoUpload(txtVideoName.getText().toString(),downloadUrl.toString(),getUid());
                    //Save video info in to firebase database
                    String uploadID = mDatabaseRef.push().getKey();
                    mDatabaseRef.child(uploadID).setValue(videoUpload);

                }
            })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            //Dismiss dialog when error
                            dialog.dismiss();
                            //Display error toast msg
                            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();

                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                            //Show upload progress

                            double progress = (100 * taskSnapshot.getBytesTransferred()/taskSnapshot.getTotalByteCount());
                            dialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });

        } else {
            Toast.makeText(getApplicationContext(),"Please Select Video",Toast.LENGTH_SHORT).show();
        }
    }


}
