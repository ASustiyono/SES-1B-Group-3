package group3.seshealthpatient.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import group3.seshealthpatient.activities.Patients;
import group3.seshealthpatient.activities.ViewPatientProfileActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoctorPatientSearchFragment extends Fragment {

    private RecyclerView mPatientList;

    private DatabaseReference mPatientsDatabase;

    public DoctorPatientSearchFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle("ALL PATIENTS");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind( getActivity() );

        mPatientsDatabase = FirebaseDatabase.getInstance().getReference().child( "Patients" );

        View v = inflater.inflate( R.layout.fragment_doctor_patient_search, container, false );
        mPatientList = v.findViewById(R.id.patientList);
        mPatientList.setHasFixedSize( true );
        mPatientList.setLayoutManager( new LinearLayoutManager( getActivity()));


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Patients> options = new FirebaseRecyclerOptions.Builder<Patients>()
                        .setQuery(mPatientsDatabase, Patients.class)
                        .build();

        FirebaseRecyclerAdapter<Patients, PatientsViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Patients, PatientsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull PatientsViewHolder holder, int position, @NonNull Patients patients) {
                holder.setFullName( patients.getFirstName() + " " + patients.getLastName() );
                holder.setGender( patients.getGender() );
                holder.setAge( patients.getAge() );
                holder.setHeight( patients.getHeight() );
                holder.setWeight( patients.getWeight() );
                holder.setBloodType( patients.getBloodType() );
            }

            @NonNull
            @Override
            public PatientsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.patients_single_layout, parent, false);

                return new PatientsViewHolder(view);
            }
        };

        mPatientList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class PatientsViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private PatientsViewHolder.ClickListener mClickListener;

        public PatientsViewHolder(View itemView) {
            super( itemView );

            mView = itemView;

            //listener set on ENTIRE ROW, you may set on individual components within a row.
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mClickListener != null) {
                        mClickListener.onItemClick( v, getAdapterPosition() );
                        Intent intent = new Intent( v.getContext(), ViewPatientProfileActivity.class );
                        v.getContext().startActivity( intent );
                    }
                }
            });
        }

        //Interface to send callbacks...
        public interface ClickListener{
            public void onItemClick(View view, int position);
            public void onItemLongClick(View view, int position);
        }

        public void setOnClickListener(PatientsViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

        public void setFullName(String firstName) {
            TextView patientsFullNameView = mView.findViewById( R.id.patients_single_fullName );
            patientsFullNameView.setText(firstName);
        }

        public void setGender(String gender) {
            TextView patientsGenderView = mView.findViewById( R.id.patients_single_gender );
            patientsGenderView.setText(gender);
        }

        public void setAge(String age) {
            TextView patientsAgeView = mView.findViewById( R.id.patients_single_age );
            patientsAgeView.setText(age);
        }
        public void setHeight(String height) {
            TextView patientsHeightView = mView.findViewById( R.id.patients_single_height );
            patientsHeightView.setText(height);
        }
        public void setWeight (String weight) {
            TextView patientsWeightView = mView.findViewById( R.id.patients_single_weight );
            patientsWeightView.setText(weight);
        }
        public void setBloodType(String bloodType) {
            TextView patientsBloodTypeView = mView.findViewById( R.id.patients_single_bloodType );
            patientsBloodTypeView.setText(bloodType);
        }
    }
}
