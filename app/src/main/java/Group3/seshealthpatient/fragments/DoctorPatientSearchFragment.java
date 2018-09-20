package group3.seshealthpatient.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import group3.seshealthpatient.R;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorPatientSearchFragment extends Fragment {


    public DoctorPatientSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        //getActivity().setTitle("YOUR CLINICS");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind( getActivity() );

        return inflater.inflate( R.layout.fragment_doctor_patient_search, container, false );
    }

}
