package Group3.seshealthpatient.Fragments;


import android.app.Activity;
//import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import Group3.seshealthpatient.R;




 // * A simple {@link Fragment} subclass.

public class DataPacketFragment extends Fragment {

    private Button btn;
    private EditText editText;


    OnMessageSendListener messageSendListener;
    public interface OnMessageSendListener
    {

        public void onMessageSend(String message);


    }
    public DataPacketFragment() {

        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){


        View view = inflater.inflate(R.layout.fragment_data_packet, container, false);

        btn = view.findViewById(R.id.button);
        editText = view.findViewById(R.id.commentBody);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = editText.getText().toString();
                messageSendListener.onMessageSend(message);




            }
        });
        return view;

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        try {
            messageSendListener = (OnMessageSendListener) activity;
        }

        catch (ClassCastException e)
        {
            throw new ClassCastException(activity.toString() +" must implement OnMessageSend...");

        }
    }
}


//                    @Override
//                    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                            Bundle savedInstanceState) {
//                        // Inflate the layout for this fragment
//                        View v = inflater.inflate(R.layout.fragment_data_packet, container, false);
//
//                        Button btnSend = (Button)v.findViewById(R.id.button);
//                        btnSend.setOnClickListener(new View.OnClickListener(){
//                            @Override
//                            public void onClick(View v) {
//                                dataPacketReceive recievePacket = new dataPacketReceive();
//
//                                Bundle bundle = new Bundle();
//                                bundle.putString("data");
//                                bundle.putString("more data", "alice");
//                                recievePacket.setArguments(bundle);
//
//                                FragmentManager manager = getFragmentManager();
//                                manager.beginTransaction().replace(R.id.frameLayout, dataPacketReceive).commit();


// }
// });
// return v;

