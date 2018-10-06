package Group3.seshealthpatient.activities;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import group3.seshealthpatient.R;

public class PatientSendingMessage extends AppCompatActivity {


    private DatabaseReference myDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDatabase = FirebaseDatabase.getInstance().getReference("message");


        final TextView myText = findViewById(R.id.text);


        myDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String[] Messages = dataSnapshot.getValue().toString().split(",");

                myText.setText("");


                for (int i = 0; i < Messages.length; i++){

                    String[] finalMsg = Messages[i].split("=");
                    myText.append(finalMsg[1] + "\n");

                }




            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                myText.setText("CANCELLED");


            }
        });


    }

    public void sendMessage(View view){


        EditText myEditText = findViewById(R.id.editText);

        myDatabase.child(Long.toString(System.currentTimeMillis())).setValue(myEditText.getText().toString());
        myEditText.setText("");


    }
}

