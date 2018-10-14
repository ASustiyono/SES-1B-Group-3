package group3.seshealthpatient.fragments;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import group3.seshealthpatient.activities.HeartRateMonitor;
import group3.seshealthpatient.R;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeartRateFragment extends Fragment {

    private Button recordBtn;

    public HeartRateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "YOUR HEART" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_heart_rate, container, false);
        recordBtn=(Button)v.findViewById(R.id.record_heart_rate_btn);
        recordBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(getActivity(), "Camera Permission Required. Please Turn this ON in your Phone Settings.", Toast.LENGTH_LONG).show();
                }

                else {
                    Intent intent1 = new Intent(getActivity(), HeartRateMonitor.class);
                    startActivity(intent1);
                }
            }
        });
        ButterKnife.bind( getActivity() );

        return v;
    }

}
