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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import group3.seshealthpatient.R;
import group3.seshealthpatient.activities.VideoUpload;

    public class VideoListAdapter extends ArrayAdapter <VideoUpload>{

    private Activity context;
    private List<VideoUpload> listVideo;
    //private TextView tvUserName;
    //private String uID;

    public VideoListAdapter(@NonNull Activity context, int resource, @NonNull List<VideoUpload> objects) {
        super(context, resource, objects);
        this.context=context;
        listVideo=objects;

    }
@Override
        public View getView(int position, View convertView, ViewGroup parent)
        {

            if(convertView == null){
                convertView = LayoutInflater.from(context).inflate(R.layout.video_item,null);
            }

            VideoView videoView = (VideoView) convertView.findViewById(R.id.vidView);
            TextView tvName = (TextView)convertView.findViewById(R.id.tvVideoName);
            tvName.setText("Video Name: "+listVideo.get(position).getName());
            Uri url=Uri.parse(listVideo.get(position).getUrl());

            MediaController mc = new MediaController(context);
            mc.setAnchorView(videoView);
            mc.setMediaPlayer(videoView);

            videoView.setMediaController(mc);
            videoView.setVideoURI(url);
            videoView.start();

            final String uID = (listVideo.get(position).getUserId());

            final TextView tvUserName = (TextView)convertView.findViewById(R.id.tvUserName);
            DatabaseReference userInfro = FirebaseDatabase.getInstance().getReference();
            userInfro.addValueEventListener( new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    tvUserName.setText("Patient Name: "+dataSnapshot.child("Patients").child(uID).child("firstName").getValue(String.class) + " "+
                            dataSnapshot.child("Patients").child(uID).child("lastName").getValue(String.class)
                    );

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });








            //Glide.with(context).load(listVideo.get(position).getUrl()).into(videoView);

            return convertView;
        }



}
