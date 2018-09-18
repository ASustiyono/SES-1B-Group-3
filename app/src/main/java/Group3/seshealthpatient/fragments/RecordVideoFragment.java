package group3.seshealthpatient.fragments;


import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group3.seshealthpatient.activities.RecordVideoActivity;
import group3.seshealthpatient.R;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class RecordVideoFragment extends Fragment {

    public RecordVideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "YOUR SNIPPETS" );
        //ButterKnife.bind(getActivity());

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_record_video, container, false );

        ButterKnife.bind( this, v );

        return v;
    }

    @OnClick(R.id.record_video_btn)
    public void OnClick(View view) {
        Intent intent = new Intent( getActivity(), RecordVideoActivity.class );
        startActivity( intent );
    }

}
