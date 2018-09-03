package Group3.seshealthpatient.Fragments;


import android.app.Dialog;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

import Group3.seshealthpatient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {


    public MapFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mapactivity, container, false);
    }



        private static final String TAG = "MapFragment";

        private static final int ERROR_REQUEST = 69; //error code




        public boolean isServicesOK()
        {
        Log.d(TAG, "isServicesOK: checking if your Google Services is up to date"); //debugging

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(MapFragment.this); /*//log d = debug*/

        if(available == ConnectionResult.SUCCESS)
        {
            /*//checks if everything is fine*/
            Log.d(TAG, "isServicesOK: Google Play Services is functional");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            /* //If error occurs, there is a solution is available or its resolvable*/
            Log.d(TAG, "isServicesOK: A fixable error has occured!");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapFragment.this, available, ERROR_REQUEST);
            dialog.show();  /*//This prompts google to provide a error message and solution when a fixable error has occurred*/
        }

        else

        {
            Toast.makeText(this, "Google Play Services is dysfunctional, sorry!", Toast.LENGTH_SHORT).show(); /*//this means it cant be fixed for whatever reason lmao*/
        }
        return false;
    }

}

}
