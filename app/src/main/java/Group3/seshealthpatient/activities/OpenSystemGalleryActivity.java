package Group3.seshealthpatient.Activities;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;

import java.io.File;

import Group3.seshealthpatient.R;


public class OpenSystemGalleryActivity extends AppCompatActivity {

    private static final int VIDEO = 1;
    private VideoView galleryVideoView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_gallery);
    }

    public void onClick(View v) {
        //open the system galley
        galleryVideoView = (VideoView) findViewById(R.id.gallery_video_view);
        Intent intent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, VIDEO );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //get the video uri
        if (requestCode == VIDEO  && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedVideo = data.getData();
            String[] filePathColumns = {MediaStore.Video.Media.DATA};
            Cursor c = getContentResolver().query(selectedVideo, filePathColumns, null, null, null);
            c.moveToFirst();
            int columnIndex = c.getColumnIndex(filePathColumns[0]);
            String videoPath = c.getString(columnIndex);
            galleryVideoView.setVideoURI(Uri.parse(videoPath));
            galleryVideoView.setMediaController(new MediaController(this));
            galleryVideoView.start();
            c.close();
        }
    }

}
