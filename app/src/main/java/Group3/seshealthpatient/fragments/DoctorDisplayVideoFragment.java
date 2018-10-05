package group3.seshealthpatient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.OnClick;
import group3.seshealthpatient.R;
import butterknife.ButterKnife;
import group3.seshealthpatient.activities.LoginActivity;
import group3.seshealthpatient.activities.PatientChangeEmailActivity;
import group3.seshealthpatient.activities.PatientChangePasswordActivity;
import group3.seshealthpatient.activities.RegistrationActivity;
import group3.seshealthpatient.activities.VideoListActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorDisplayVideoFragment extends Fragment {


    public DoctorDisplayVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "PATIENT'S VIDEOS" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_doctor_display_video, container, false );
        ButterKnife.bind( this, v );

        return v;
    }

    @OnClick(R.id.display_video_list_btn)
    public void OnClick(View view) {
        Intent intent = new Intent( getActivity(), VideoListActivity.class );
        startActivity( intent );
    }
}
