package Group3.seshealthpatient.Activities;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import Group3.seshealthpatient.R;

public class RecordVideoActivity extends AppCompatActivity {
    private VideoView recordVideoView;
    private ImageView recordImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);


        recordVideoView = (VideoView) findViewById(R.id.my_vv);
        recordImageView = (ImageView) findViewById(R.id.my_iv);


    }


    public void camera(View view) {
        //lunch system camera to take video
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);//take video
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode==100 && resultCode == RESULT_OK) {
            Log.e("sxl","Finish shooting");
            //get the uri
            Uri uri = data.getData();
            //get data from uri
            Cursor cursor =  getContentResolver().query(uri,null,null,null,null);


            if (cursor.moveToFirst()) {
                //get the uri from first video
                String path = cursor.getString(cursor.getColumnIndex("_data"));
                Log.d("sxl", "onActivityResult: "+path);
                //set the uri
                recordVideoView.setVideoURI(Uri.parse(path));
                //add media controller
                recordVideoView.setMediaController(new MediaController(this));
                //play video
                recordVideoView.start();
                //crate the bitmap for video
                Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(path,MediaStore.Video.Thumbnails.MINI_KIND);
                //set bitmap
                recordImageView.setImageBitmap(bitmap);
            }
        }
    }
}