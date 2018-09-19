package group3.seshealthpatient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group3.seshealthpatient.activities.EditInfoActivity;
import butterknife.ButterKnife;
import group3.seshealthpatient.R;
import butterknife.OnClick;

/**
 * Class: PatientInformationFragment
 * Extends: {@link Fragment}
 * Author: Carlos Tirado < Carlos.TiradoCorts@uts.edu.au> and YOU!
 * Description:
 * <p>
 * This fragment's job will be that to display patients information, and be able to edit that
 * information (either edit it in this fragment or a new fragment, up to you!)
 * <p>
 */
public class DoctorProfileFragment extends Fragment {


    // Note how Butter Knife also works on Fragments, but here it is a little different
    //@BindView(R.id.blank_frag_msg)
    TextView blankFragmentTV;

    DatabaseReference databaseReference;
    FirebaseUser userId;
    String uid;
<<<<<<< HEAD
    TextView FirstName, LastName, Gender, Age;

    //Clinic Information
    EditText ClinicName, ClinicNumber, ClinicEmail;

    //Clinic Address
    EditText ClinicAddressLine1, ClinicAddressLine2, ClinicCity, ClinicPostcode, ClinicCountry, ClinicState;
=======
    //TextView FirstName, LastName, Gender, Age, Height, Weight, BloodType;
    TextView FullName, Gender, Age, Height, Weight, BloodType;
>>>>>>> eeff83571f9fe014b054dadf2416d9570d006ec6

    public DoctorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "YOUR PROFILE" );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate( R.layout.fragment_doctor_profile, container, false );

        // Note how we are telling butter knife to bind during the on create view method
        ButterKnife.bind( this, v );

<<<<<<< HEAD
        //Doctor Info
        FirstName = v.findViewById( R.id.doctor_userInfo_firstName_textView );
        LastName = v.findViewById( R.id.doctor_userInfo_lastName_textView );
=======
        //FirstName = v.findViewById( R.id.doctor_userInfo_firstName_textView );
        //LastName = v.findViewById( R.id.doctor_userInfo_lastName_textView );
        FullName = v.findViewById( R.id.doctor_userInfo_fullName_textView );
>>>>>>> eeff83571f9fe014b054dadf2416d9570d006ec6
        Gender = v.findViewById( R.id.doctor_userInfo_gender_textView );
        Age = v.findViewById( R.id.doctor_userInfo_age_textView );

        //Clinic Info
        ClinicName = v.findViewById( R.id.doctor_name_textView );
        ClinicNumber = v.findViewById( R.id.doctor_number_textView );
        ClinicEmail = v.findViewById( R.id.doctor_email_textView );

        //Clinic Address
        ClinicAddressLine1 = v.findViewById( R.id.doctor_addressLine1_textView);
        ClinicAddressLine2 = v.findViewBy( R.id.doctor_addressLine2_textView);
        ClinicCity = v.findViewById(R.id.doctor_city_textView);
        ClinicPostcode = v.findViewById(R.id.doctor_postcode_textView);
        ClinicCountry = v.findViewById(R.id.doctor_country_textView);
        ClinicState = v.findViewByVIew(R.id.doctor_state_textView);


        userId = FirebaseAuth.getInstance().getCurrentUser();
        uid = userId.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //FirstName.setText( "Dr. " + dataSnapshot.child( "Patients" ).child( uid ).child( "firstName" ).getValue( String.class ) );
                //LastName.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "lastName" ).getValue( String.class ) );
                FullName.setText("Dr. " + dataSnapshot.child( "Patients" ).child( uid ).child( "firstName" ).getValue( String.class ) + " " + dataSnapshot.child( "Patients" ).child( uid ).child( "lastName" ).getValue( String.class ) );
                Gender.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "gender" ).getValue( String.class ) );
                Age.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "age" ).getValue( String.class ) );
                //Height.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "height" ).getValue( String.class ) );
                //Weight.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "weight" ).getValue( String.class ) );
                //BloodType.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "bloodType" ).getValue( String.class ) );
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        } );

        return v;
    }

/*
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Now that the view has been created, we can use butter knife functionality
        //blankFragmentTV.setText("Welcome to this fragment");
    }
    */

    @OnClick(R.id.doctor_edit_btn)
    public void OnClick(View view) {
        Intent intent = new Intent( getActivity(), EditInfoActivity.class );
        startActivity( intent );
    }

    /***
     @OnClick({R.id.edit_btn,R.id.new_btn}) public void OnClick(View view) {
     switch(view.getId()) {
     case R.id.edit_btn:
     //method
     break;
     case R.id.new_btn:
     //method
     break;
     }
     }
     }
     */
}
