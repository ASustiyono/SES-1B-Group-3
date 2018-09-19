package group3.seshealthpatient.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import group3.seshealthpatient.R;
import butterknife.ButterKnife;

public class DoctorUpdateInfoActivity extends AppCompatActivity {

    //Doctor Information
    EditText FirstName, LastName, Age;
    RadioGroup Gender;
    RadioButton MaleFemale;
    RadioButton Male, Female;

    //Clinic Information
    EditText ClinicName, ClinicNumber, ClinicEmail;

    //Clinic Address
    EditText ClinicAddress, ClinicCity, ClinicPostcode, ClinicCountry, ClinicState;

    //Button
    Button save_btn;

    //Firebase Authentication
    FirebaseAuth mAuth;
    DatabaseReference mUserRef, mDatabaseRef;
    FirebaseUser mUser;

    String mCurrentUserId, mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_doctor_add_info );

        //Butterknife Button
        ButterKnife.bind( this );

        //Firebase Auth
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = mUser.getUid();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child( "Doctors" ).child( mCurrentUserId );
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        //Doctor Information
        FirstName = findViewById( R.id.doctorFirstNameEditText );
        LastName = findViewById( R.id.doctorLastNameEditText );
        Gender = findViewById( R.id.doctorGenderRadioGrp );
        Male = findViewById( R.id.doctorMaleRadioBtn );
        Female = findViewById( R.id.doctorFemaleRadioBtn );
        Age = findViewById( R.id.doctorAgeEditText );

        //Clinic Information
        ClinicName = findViewById( R.id.doctorClinicNameEdtTxt);
        ClinicNumber = findViewById( R.id.doctorNumberEdtTxt);
        ClinicEmail = findViewById( R.id.doctorEmailEdtTxt);

        //Clinic Address
        ClinicAddress = findViewById(R.id.doctorClinicAdEdtTxt);
        ClinicCity = findViewById(R.id.doctorClinicCitydtTxt);
        ClinicPostcode = findViewById(R.id.doctorClinicPstCddtTxt);
        ClinicCountry = findViewById(R.id.doctorClinicCntrydtTxt);
        ClinicState = findViewById(R.id.doctorClinicSttEdtTxt);

        //Save User Information
        save_btn = findViewById( R.id.doctor_firstTime_save_btn );
        save_btn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SaveUserInfo();
            }
        } );

        //Tick Male Button Default as True
        Male.setChecked( true );

        //SetText for User Information
        mDatabaseRef.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirstName.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "firstName" ).getValue( String.class ) );
                LastName.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "lastName" ).getValue( String.class ) );
                String gender = dataSnapshot.child( "Doctors" ).child( mUserId ).child( "gender" ).getValue().toString();
                if (gender.equalsIgnoreCase( "Male" )) {
                    Male.setChecked( true );
                } else if (gender.equalsIgnoreCase( "Female" )) {
                    Female.setChecked( true );
                }
                Age.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "age" ).getValue( String.class ) );
                ClinicName.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicName" ).getValue( String.class ) );
                ClinicNumber.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicNumber" ).getValue( String.class ) );
                ClinicEmail.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicEmail" ).getValue( String.class ) );
                ClinicAddress.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicAddress" ).getValue( String.class ) );
                ClinicCity.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicCity" ).getValue( String.class ) );
                ClinicPostcode.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicPostcode" ).getValue( String.class ) );
                ClinicCountry.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicCountry" ).getValue( String.class ) );
                ClinicState.setText( dataSnapshot.child( "Doctors" ).child( mUserId ).child( "clinicState" ).getValue( String.class ) );

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        } );
    }

    private void SaveUserInfo() {

        //Doctor Info
        String firstName = FirstName.getText().toString();
        String lastName = LastName.getText().toString();
        int gender = Gender.getCheckedRadioButtonId();
        MaleFemale = (RadioButton) findViewById( gender );
        String age = Age.getText().toString();

        //Clinic Info
        String clinicName = ClinicName.getText().toString();
        String clinicNumber = ClinicNumber.getText().toString();
        String clinicEmail = ClinicEmail.getText().toString();

        //Clinic Address
        String addressLine1 = ClinicAddress.getText().toString();
        String city = ClinicCity.getText().toString();
        String postcode = ClinicPostcode.getText().toString();
        String country = ClinicCountry.getText().toString();
        String state = ClinicState.getText().toString();

        //Storing User Info as a Map on Firebase
        HashMap userMap = new HashMap();

        //Error message if fields are returned as null
        if (TextUtils.isEmpty( firstName )) {
            FirstName.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( lastName )) {
            LastName.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( age )) {
            Age.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( clinicName )) {
            ClinicName.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( clinicNumber )) {
            ClinicNumber.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( clinicEmail )) {
            ClinicEmail.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( addressLine1 )) {
            ClinicAddress.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( city )) {
            ClinicCity.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( postcode )) {
            ClinicPostcode.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( country )) {
            ClinicCountry.setError( "Required" );
            return;
        }
        if (TextUtils.isEmpty( state )) {
            ClinicState.setError( "Required" );
            return;
        } else {
            userMap.put( "firstName", firstName );
            userMap.put( "lastName", lastName );
            userMap.put( "gender", MaleFemale.getText() );
            userMap.put( "age", age );
            userMap.put( "clinicName", clinicName );
            userMap.put( "clinicNumber", clinicNumber );
            userMap.put( "clinicEmail", clinicEmail );
            userMap.put( "clinicAddress", addressLine1);
            userMap.put( "clinicCity", city);
            userMap.put( "clinicPostcode", postcode);
            userMap.put( "clinicCountry", country);
            userMap.put( "clinicState", state);

        }

        //Fields are updated into the user's id or rejected
        mUserRef.updateChildren( userMap ).addOnCompleteListener( new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText( getApplicationContext(), "Entered user info successfully!", Toast.LENGTH_SHORT ).show();
                    startActivity( new Intent( DoctorUpdateInfoActivity.this, DoctorMainActivity.class ) );
                    finish();
                } else {
                    String message = task.getException().getMessage();
                    Toast.makeText( getApplicationContext(), "Oh no! Something went wrong OWO.\n" + message, Toast.LENGTH_SHORT ).show();
                }
            }
        } );
    }
}
