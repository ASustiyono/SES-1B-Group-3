package Group3.seshealthpatient.Activities;

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

import Group3.seshealthpatient.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EditInfoActivity extends AppCompatActivity {

    //User Input Information
    EditText FirstName, LastName, Age, Height, Weight, BloodType;

    RadioGroup Gender;
    RadioButton MaleFemale;
    RadioButton Male, Female;
    Button save_btn;

    FirebaseAuth mAuth;
    DatabaseReference mUserRef, mDatabaseRef;
    FirebaseUser mUser;

    DatabaseReference databaseReference;
    FirebaseUser userId;
    String uid;

    String mCurrentUserId, mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_edit_info );

        ButterKnife.bind(this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = mUser.getUid();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(mCurrentUserId);

        FirstName = findViewById(R.id.edit_firstNameEditText);
        LastName = findViewById(R.id.edit_lastNameEditText);
        Gender = findViewById(R.id.edit_genderRadioGrp);
        Male = findViewById(R.id.edit_maleRadioBtn);
        Female = findViewById(R.id.edit_femaleRadioBtn);
        Age = findViewById(R.id.edit_ageEditText);
        Height = findViewById(R.id.edit_heightEditText);
        Weight  = findViewById(R.id.edit_weightEditText);
        BloodType = findViewById(R.id.edit_bloodTypeEditText);

        save_btn = findViewById(R.id.edit_save_btn);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        Male.setChecked(true);

        userId = FirebaseAuth.getInstance().getCurrentUser();
        uid = userId.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirstName.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "firstName" ).getValue( String.class ) );
                LastName.setText( dataSnapshot.child( "Patients" ).child( uid ).child( "lastName" ).getValue( String.class ) );
                //Gender.setText(dataSnapshot.child("Patients").child(uid).child("gender").getValue(String.class));
                String gender = dataSnapshot.child( "Patients" ).child( uid ).child( "gender" ).getValue().toString();
                if (gender.equalsIgnoreCase( "Male" )) { Male.setChecked( true );
                } else if (gender.equalsIgnoreCase( "Female" )) { Female.setChecked( true ); }
                Age.setText(dataSnapshot.child("Patients").child(uid).child("age").getValue(String.class));
                Height.setText(dataSnapshot.child("Patients").child(uid).child("height").getValue(String.class));
                Weight.setText(dataSnapshot.child("Patients").child(uid).child("weight").getValue(String.class));
                BloodType.setText(dataSnapshot.child("Patients").child(uid).child("bloodType").getValue(String.class));
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                //Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    @OnClick({R.id.edit_save_btn,R.id.edit_close_btn})
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.edit_save_btn:
                SaveUserInfo();
                break;
            case R.id.edit_close_btn:
                finish();
                break;
        }
    }
    private void SaveUserInfo() {
        String firstName = FirstName.getText().toString();
        String lastName = LastName.getText().toString();
        int gender = Gender.getCheckedRadioButtonId();
        String age = Age.getText().toString();
        String height = Height.getText().toString();
        String weight = Height.getText().toString();
        String bloodType = BloodType.getText().toString();

        MaleFemale = findViewById(gender);

        HashMap userMap = new HashMap();

        if(TextUtils.isEmpty(firstName)) {
            FirstName.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(lastName)) {
            LastName.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(age)) {
            Age.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(height)) {
            Height.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(weight)) {
            Weight.setError("Required");
            return;
        }
        if(TextUtils.isEmpty(bloodType)) {
            BloodType.setError("Required");
            return;
        } else {
            userMap.put("firstName", firstName);
            userMap.put("lastName", lastName);
            userMap.put("gender", MaleFemale.getText());
            userMap.put("age", age);
            userMap.put("height", height);
            userMap.put("weight", weight);
            userMap.put("bloodType", bloodType);
        }

        mUserRef.updateChildren(userMap).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if(task.isSuccessful()){
                    Toast.makeText(getApplicationContext(), "Entered user info successfully!", Toast.LENGTH_SHORT).show();
                    finish();
                }else{
                    String message = task.getException().getMessage();
                    Toast.makeText(getApplicationContext(), "Oh no! Something went wrong OWO.\n" + message, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
