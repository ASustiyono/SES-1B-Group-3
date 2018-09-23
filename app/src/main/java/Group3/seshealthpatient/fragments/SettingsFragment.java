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

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsFragment extends Fragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "SETTINGS" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_settings, container, false );
        ButterKnife.bind( this, v );

        return v;
    }

    @OnClick({R.id.settings_changeEmail_btn, R.id.settings_changePassword_btn})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_changeEmail_btn:
                startActivity( new Intent( getActivity(), PatientChangeEmailActivity.class ) );
                break;
            case R.id.settings_changePassword_btn:
                startActivity( new Intent( getActivity(), PatientChangePasswordActivity.class ) );
                break;
        }
    }
}
