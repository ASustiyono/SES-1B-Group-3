package Group3.seshealthpatient.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import Group3.seshealthpatient.R;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddInfoActivity extends AppCompatActivity {

    //User Input Information
    EditText FirstName, LastName, Age, Height, Weight, BloodType;

    RadioGroup Gender;
    RadioButton MaleFemale;
    RadioButton Male, Female;
    Button save_btn;

    FirebaseAuth mAuth;
    DatabaseReference mUserRef, mDatabaseRef;
    FirebaseUser mUser;

    String mCurrentUserId, mUserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_info);

        ButterKnife.bind(this);

        mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUserId = mUser.getUid();

        mAuth = FirebaseAuth.getInstance();
        mCurrentUserId = mAuth.getCurrentUser().getUid();
        mUserRef = FirebaseDatabase.getInstance().getReference().child("Patients").child(mCurrentUserId);

        FirstName = (EditText) findViewById(R.id.firstNameEditText);
        LastName = (EditText) findViewById(R.id.lastNameEditText);
        Gender = (RadioGroup) findViewById(R.id.genderRadioGrp);
        Male = (RadioButton) findViewById(R.id.maleRadioBtn);
        Female = (RadioButton) findViewById(R.id.femaleRadioBtn);
        Age = (EditText) findViewById(R.id.ageEditText);
        Height = (EditText) findViewById(R.id.heightEditText);
        Weight  = (EditText) findViewById(R.id.weightEditText);
        BloodType = (EditText) findViewById(R.id.bloodTypeEditText);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference();

        Male.setChecked(true);
    }

    private void SaveUserInfo() {
        String firstName = FirstName.getText().toString();
        String lastName = LastName.getText().toString();
        int gender = Gender.getCheckedRadioButtonId();
        String age = Age.getText().toString();
        String height = Height.getText().toString();
        String weight = Height.getText().toString();
        String bloodType = BloodType.getText().toString();

        MaleFemale = (RadioButton) findViewById(gender);

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
            userMap.put("first name", firstName);
            userMap.put("last name", lastName);
            userMap.put("gender", MaleFemale.getText());
            userMap.put("age", age);
            userMap.put("height", height);
            userMap.put("weight", weight);
            userMap.put("blood type", bloodType);
        }
    }

    @OnClick(R.id.firstTime_save_btn)
    public void onClick(View v) {

        // TODO: FIX THIS BUTTON
        startActivity(new Intent(AddInfoActivity.this, MainActivity.class));
        finish();
    }
}
