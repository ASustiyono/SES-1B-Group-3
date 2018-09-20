package group3.seshealthpatient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
public class PatientInformationFragment extends Fragment {


    // Note how Butter Knife also works on Fragments, but here it is a little different
    //@BindView(R.id.blank_frag_msg)
    TextView blankFragmentTV;

    DatabaseReference databaseReference;
    FirebaseUser userId;
    String uid;
    TextView FirstName, LastName, Gender, Age, Height, Weight, BloodType;

    public PatientInformationFragment() {
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
        View v = inflater.inflate( R.layout.fragment_patient_information, container, false );

        // Note how we are telling butter knife to bind during the on create view method
        ButterKnife.bind( this, v );

        FirstName = v.findViewById( R.id.userInfo_firstName_textView );
        LastName = v.findViewById( R.id.userInfo_lastName_textView );
        Gender = v.findViewById( R.id.userInfo_gender_textView );
        Age = v.findViewById( R.id.userInfo_age_textView );
        Height = v.findViewById( R.id.userInfo_height_textView );
        Weight = v.findViewById( R.id.userInfo_weight_textView );
        BloodType = v.findViewById( R.id.userInfo_bloodType_textView );
        userId = FirebaseAuth.getInstance().getCurrentUser();
        uid = userId.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirstName.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "firstName" ).getValue( String.class ) );
                LastName.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "lastName" ).getValue( String.class ) );
                Gender.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "gender" ).getValue( String.class ) );
                Age.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "age" ).getValue( String.class ) );
                Height.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "height" ).getValue( String.class ) );
                Weight.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "weight" ).getValue( String.class ) );
                BloodType.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "bloodType" ).getValue( String.class ) );
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

    @OnClick(R.id.edit_btn)
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
