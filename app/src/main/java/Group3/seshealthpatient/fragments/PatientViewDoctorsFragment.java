package group3.seshealthpatient.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import group3.seshealthpatient.R;
import butterknife.ButterKnife;
import group3.seshealthpatient.activities.Doctors;
import group3.seshealthpatient.activities.Patients;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientViewDoctorsFragment extends Fragment {

    private RecyclerView mDoctorList;

    private DatabaseReference mDoctorsDatabase;

    public PatientViewDoctorsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("ALL DOCTORS");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind( getActivity() );

        mDoctorsDatabase = FirebaseDatabase.getInstance().getReference().child( "Doctors" );

        View v = inflater.inflate( R.layout.fragment_patient_view_doctors, container, false );
        mDoctorList = v.findViewById(R.id.doctorList);
        mDoctorList.setHasFixedSize( true );
        mDoctorList.setLayoutManager( new LinearLayoutManager( getActivity()));


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Doctors> options = new FirebaseRecyclerOptions.Builder<Doctors>()
                .setQuery(mDoctorsDatabase, Doctors.class)
                .build();

        FirebaseRecyclerAdapter<Doctors, DoctorsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Doctors, DoctorsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DoctorsViewHolder holder, int position, @NonNull Doctors doctors) {
                holder.setFullName( doctors.getFirstName() + " " + doctors.getLastName() );
                holder.setOccupation( doctors.getOccupation() );
                holder.setGender( doctors.getGender() );
                holder.setAge( doctors.getAge() );
                holder.setHeight( doctors.getClinicName() );
                holder.setWeight( doctors.getClinicNumber() );
                holder.setBloodType( doctors.getClinicEmail() );
            }

            @NonNull
            @Override
            public DoctorsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.doctors_single_layout, parent, false);

                return new DoctorsViewHolder(view);
            }
        };
        mDoctorList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class DoctorsViewHolder extends RecyclerView.ViewHolder {

        View mView;

        public DoctorsViewHolder(View itemView) {
            super( itemView );

            mView = itemView;

        }
        public void setFullName(String firstName) {
            TextView doctorsFullName = mView.findViewById( R.id.doctors_single_fullName );
            doctorsFullName.setText(firstName);
        }
        public void setOccupation(String bloodType) {
            TextView doctorsOccupation = mView.findViewById( R.id.doctors_single_occupation );
            doctorsOccupation.setText(bloodType);
        }
        public void setGender(String gender) {
            TextView doctorsGender = mView.findViewById( R.id.doctors_single_gender );
            doctorsGender.setText(gender);
        }
        public void setAge(String age) {
            TextView doctorsAge = mView.findViewById( R.id.doctors_single_age );
            doctorsAge.setText(age);
        }
        public void setHeight(String height) {
            TextView clinicsName = mView.findViewById( R.id.doctors_single_clinicName );
            clinicsName.setText(height);
        }
        public void setWeight (String weight) {
            TextView clinicsNumber = mView.findViewById( R.id.doctors_single_clinicNumber );
            clinicsNumber.setText(weight);
        }
        public void setBloodType(String bloodType) {
            TextView clinicsEmail = mView.findViewById( R.id.doctors_single_clinicEmail );
            clinicsEmail.setText(bloodType);
        }
    }
}
