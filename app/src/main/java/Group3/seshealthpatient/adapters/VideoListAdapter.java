package group3.seshealthpatient.adapters;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import com.bumptech.glide.Glide;

import java.util.List;

import group3.seshealthpatient.R;
import group3.seshealthpatient.activities.VideoUpload;

    public class VideoListAdapter extends ArrayAdapter <VideoUpload>{

    private Activity context;
    private List<VideoUpload> listVideo;


    public VideoListAdapter(@NonNull Activity context, int resource, @NonNull List<VideoUpload> objects) {
        super(context, resource, objects);
        this.context=context;
        listVideo=objects;

    }

        public View getView(int position, View convertView, ViewGroup parent)
        {

            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.video_item,null);
            }


            VideoView videoView = (VideoView) convertView.findViewById(R.id.vidView);
            TextView textView = (TextView)convertView.findViewById(R.id.tvVideoName);
            textView.setText(listVideo.get(position).getName());
            MediaController mc=new MediaController(context);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);
            Uri url=Uri.parse(listVideo.get(position).getUrl());
            videoView.setMediaController(mc);
            videoView.setVideoURI(url);
            videoView.start();

            //Glide.with(context).load(listVideo.get(position).getUrl()).into(videoView);

            return convertView;
        }

}
