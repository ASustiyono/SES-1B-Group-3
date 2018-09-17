package Group3.seshealthpatient.Fragments;


import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;

import Group3.seshealthpatient.Activities.OpenSystemGalleryActivity;
import Group3.seshealthpatient.Activities.RecordVideoActivity;
//import Group3.seshealthpatient.Activities.UploadVideoActivity;
import Group3.seshealthpatient.R;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordVideoFragment extends Fragment {

    private Button recordBtn;
    private Button openGalleryBtn;
    private Button uploadVideoBtn;
    private ImageView iv_user_photo;
    private String fileName = "";
    private File tempFile;
    private static final int IMAGE = 1;
    public RecordVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        //getActivity().setTitle("Message Doctor");
        //ButterKnife.bind(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_record_video, container, false);
        recordBtn=(Button)v.findViewById(R.id.record_video_btn);
        recordBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getActivity(), RecordVideoActivity.class);
                startActivity(intent1);
            }
        });
        openGalleryBtn=(Button)v.findViewById(R.id.open_gallery_btn);
        openGalleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(getActivity(),OpenSystemGalleryActivity.class);
                startActivity(intent2);
            }
        });
       /* uploadVideoBtn=(Button)v.findViewById(R.id.upload_video_btn);
        uploadVideoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent3 = new Intent(getActivity(), UploadVideoActivity.class);
                startActivity(intent3);
            }
        });*/
        ButterKnife.bind(this, v);


        return v;
    }

    //@OnClick(R.id.record_video_btn)
    //public void OnClick(View view) {
    //    Intent intent1 = new Intent(getActivity(), RecordVideoActivity.class);
    //    startActivity(intent1);
    //}




}
