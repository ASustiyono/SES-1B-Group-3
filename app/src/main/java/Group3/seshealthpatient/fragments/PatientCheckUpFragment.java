package group3.seshealthpatient.fragments;


import android.content.Intent;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import group3.seshealthpatient.R;
import butterknife.ButterKnife;
import group3.seshealthpatient.activities.Patients;
import group3.seshealthpatient.activities.ViewPatientProfileActivity;
import group3.seshealthpatient.model.DataPacket;

/**
 * A simple {@link Fragment} subclass.
 */
public class PatientCheckUpFragment extends Fragment {

    private RecyclerView mCheckUpList;

    private DatabaseReference mCheckUpsDatabase;
    private String mCurrentUserId;

    public PatientCheckUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "YOUR CHECKUPS" );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind( getActivity() );

        View v =  inflater.inflate( R.layout.fragment_patient_checkup, container, false );
        
        mCurrentUserId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        mCheckUpsDatabase = FirebaseDatabase.getInstance().getReference().child( "Patients" ).child(mCurrentUserId).child("CheckUp Packets");
        mCheckUpList = v.findViewById(R.id.checkUpList);
        mCheckUpList.setHasFixedSize( true );
        mCheckUpList.setLayoutManager( new LinearLayoutManager( getActivity()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<DataPacket> options = new FirebaseRecyclerOptions.Builder<DataPacket>()
                .setQuery(mCheckUpsDatabase, DataPacket.class)
                .build();

        FirebaseRecyclerAdapter<DataPacket, DataPacketViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<DataPacket, DataPacketViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull DataPacketViewHolder holder, int position, @NonNull DataPacket dataPacket) {
                holder.setDate( dataPacket.getDate() );
                holder.setDescription( dataPacket.getDescription() );
                holder.setTitle( dataPacket.getTitle() );
            }

            @NonNull
            @Override
            public PatientCheckUpFragment.DataPacketViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.checkup_single_layout, parent, false);

                return new PatientCheckUpFragment.DataPacketViewHolder(view);
            }
        };

        mCheckUpList.setAdapter(firebaseRecyclerAdapter);
        firebaseRecyclerAdapter.startListening();
    }

    public static class DataPacketViewHolder extends RecyclerView.ViewHolder {

        private View mView;

        private PatientCheckUpFragment.DataPacketViewHolder.ClickListener mClickListener;

        public DataPacketViewHolder(View itemView) {
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

        public void setOnClickListener(PatientCheckUpFragment.DataPacketViewHolder.ClickListener clickListener){
            mClickListener = clickListener;
        }

        public void setTitle(String title) {
            TextView tvTitle = mView.findViewById( R.id.cu_title_textview );
            tvTitle.setText(title);
        }

        public void setDescription(String description) {
            TextView tvDesc = mView.findViewById( R.id.cu_desc_textview );
            tvDesc.setText(description);
        }

        public void setDate(String date) {
            TextView tvDate = mView.findViewById( R.id.cu_date_textview );
            tvDate.setText(date);
        }
    }
}
