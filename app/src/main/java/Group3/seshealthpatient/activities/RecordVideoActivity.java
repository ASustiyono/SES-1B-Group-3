<<<<<<< HEAD:app/src/main/java/Group3/seshealthpatient/Activities/RecordVideoActivity.java
package Group3.seshealthpatient.Activities;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.ThumbnailUtils;
=======
package group3.seshealthpatient.activities;

import android.app.Activity;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
>>>>>>> 47d6d779c923a8203e79fbf47a0d3971f51848d9:app/src/main/java/Group3/seshealthpatient/activities/RecordVideoActivity.java
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import group3.seshealthpatient.R;

<<<<<<< HEAD:app/src/main/java/Group3/seshealthpatient/Activities/RecordVideoActivity.java
public class RecordVideoActivity extends AppCompatActivity {
    private VideoView recordVideoView;
    private ImageView recordImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_video);


        recordVideoView = (VideoView) findViewById(R.id.my_vv);
        recordImageView = (ImageView) findViewById(R.id.my_iv);

=======
import static android.content.ContentValues.TAG;

public class RecordVideoActivity extends Activity implements SurfaceHolder.Callback {

    private SurfaceView mSurfaceview;
    private Button mBtnStartStop;
    private Button mBtnPlay;
    private boolean mStartedFlg = false;
    private boolean mIsPlay = false;
    private MediaRecorder mRecorder;
    private SurfaceHolder mSurfaceHolder;
    private ImageView mImageView;
    private Camera camera;
    private MediaPlayer mediaPlayer;
    private String path;
    private TextView textView;
    private int text = 0;

    private android.os.Handler handler = new android.os.Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            text++;
            textView.setText( text + "" );
            handler.postDelayed( this, 1000 );
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        requestWindowFeature( Window.FEATURE_NO_TITLE );
        setContentView( R.layout.activity_record_video );
        mSurfaceview = (SurfaceView) findViewById( R.id.surfaceview );
        mImageView = (ImageView) findViewById( R.id.imageview );
        mBtnStartStop = (Button) findViewById( R.id.btnStartStop );
        mBtnPlay = (Button) findViewById( R.id.btnPlayVideo );
        textView = (TextView) findViewById( R.id.text );
        mBtnStartStop.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIsPlay) {
                    if (mediaPlayer != null) {
                        mIsPlay = false;
                        mediaPlayer.stop();
                        mediaPlayer.reset();
                        mediaPlayer.release();
                        mediaPlayer = null;
                    }
                }
                if (!mStartedFlg) {
                    handler.postDelayed( runnable, 1000 );
                    mImageView.setVisibility( View.GONE );
                    if (mRecorder == null) {
                        mRecorder = new MediaRecorder();
                    }

                    camera = Camera.open( Camera.CameraInfo.CAMERA_FACING_BACK );
                    if (camera != null) {
                        camera.setDisplayOrientation( 90 );
                        camera.unlock();
                        mRecorder.setCamera( camera );
                    }

                    try {
                        //set audio and video source
                        mRecorder.setAudioSource( MediaRecorder.AudioSource.CAMCORDER );
                        mRecorder.setVideoSource( MediaRecorder.VideoSource.CAMERA );

                        // Set output file format
                        mRecorder.setOutputFormat( MediaRecorder.OutputFormat.MPEG_4 );

                        // set encoder for audio and video
                        mRecorder.setAudioEncoder( MediaRecorder.AudioEncoder.AMR_NB );
                        mRecorder.setVideoEncoder( MediaRecorder.VideoEncoder.MPEG_4_SP );
                        //set video size and rate
                        mRecorder.setVideoSize( 640, 480 );
                        mRecorder.setVideoFrameRate( 30 );
                        mRecorder.setVideoEncodingBitRate( 3 * 1024 * 1024 );
                        mRecorder.setOrientationHint( 90 );
                        //set maximum during time(millisecond)
                        mRecorder.setMaxDuration( 30 * 1000 );
                        mRecorder.setPreviewDisplay( mSurfaceHolder.getSurface() );

                        path = getSDPath();
                        if (path != null) {
                            File dir = new File( path + "/recordtest" );
                            if (!dir.exists()) {
                                dir.mkdir();
                            }
                            path = dir + "/" + getDate() + ".mp4";
                            mRecorder.setOutputFile( path );
                            mRecorder.prepare();
                            mRecorder.start();
                            mStartedFlg = true;
                            mBtnStartStop.setText( "Stop" );
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    //stop
                    if (mStartedFlg) {
                        try {
                            handler.removeCallbacks( runnable );
                            mRecorder.stop();
                            mRecorder.reset();
                            mRecorder.release();
                            mRecorder = null;
                            mBtnStartStop.setText( "Start" );
                            if (camera != null) {
                                camera.release();
                                camera = null;
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    mStartedFlg = false;
                }
            }
        } );

        mBtnPlay.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mIsPlay = true;
                mImageView.setVisibility( View.GONE );
                if (mediaPlayer == null) {
                    mediaPlayer = new MediaPlayer();
                }
                mediaPlayer.reset();
                Uri uri = Uri.parse( path );
                mediaPlayer = MediaPlayer.create( RecordVideoActivity.this, uri );
                mediaPlayer.setAudioStreamType( AudioManager.STREAM_MUSIC );
                mediaPlayer.setDisplay( mSurfaceHolder );
                try {
                    mediaPlayer.prepare();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                mediaPlayer.start();
            }
        } );

        SurfaceHolder holder = mSurfaceview.getHolder();
        holder.addCallback( this );
        holder.setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!mStartedFlg) {
            mImageView.setVisibility( View.VISIBLE );
        }
    }

    /**
     * get system time
     */
    public static String getDate() {
        Calendar ca = Calendar.getInstance();
        int year = ca.get( Calendar.YEAR );
        int month = ca.get( Calendar.MONTH );
        int day = ca.get( Calendar.DATE );
        int minute = ca.get( Calendar.MINUTE );
        int hour = ca.get( Calendar.HOUR );
        int second = ca.get( Calendar.SECOND );

        String date = "" + year + (month + 1) + day + hour + minute + second;
        Log.d( TAG, "date:" + date );
>>>>>>> 47d6d779c923a8203e79fbf47a0d3971f51848d9:app/src/main/java/Group3/seshealthpatient/activities/RecordVideoActivity.java

    }

<<<<<<< HEAD:app/src/main/java/Group3/seshealthpatient/Activities/RecordVideoActivity.java
=======
    /**
     * get sd part
     */
    public String getSDPath() {
        File sdDir = null;
        boolean sdCardExist = Environment.getExternalStorageState()
                .equals( android.os.Environment.MEDIA_MOUNTED ); // judge sd card existing or not
        if (sdCardExist) {
            sdDir = Environment.getExternalStorageDirectory();// get root directory
            return sdDir.toString();
        }

        return null;
    }
>>>>>>> 47d6d779c923a8203e79fbf47a0d3971f51848d9:app/src/main/java/Group3/seshealthpatient/activities/RecordVideoActivity.java

    public void camera(View view) {
        //lunch system camera to take video
        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_VIDEO_CAPTURE);//take video
        startActivityForResult(intent,100);
    }


    @Override
<<<<<<< HEAD:app/src/main/java/Group3/seshealthpatient/Activities/RecordVideoActivity.java
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
=======
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mSurfaceview = null;
        mSurfaceHolder = null;
        handler.removeCallbacks( runnable );
        if (mRecorder != null) {
            mRecorder.release(); // Now the object cannot be reused
            mRecorder = null;
            Log.d( TAG, "surfaceDestroyed release mRecorder" );
        }
        if (camera != null) {
            camera.release();
            camera = null;
        }
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
>>>>>>> 47d6d779c923a8203e79fbf47a0d3971f51848d9:app/src/main/java/Group3/seshealthpatient/activities/RecordVideoActivity.java
        }
    }
}