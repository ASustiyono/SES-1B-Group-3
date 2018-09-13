package Group3.seshealthpatient.Fragments;


import android.app.Dialog;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import android.Manifest;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;



import Group3.seshealthpatient.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback
{
//variables and globals

    private static final String TAG = "MapFragment";

    private Boolean LocationPermissionsGranted = false;  //boolean for location method
    private GoogleMap jacobsMap;
    private static final int ERROR_REQUEST = 69; //error code relates to isservicesOK method
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_CODE = 1234;

    public MapFragment()
    {
        // Required empty public constructor
    }



    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        Toast.makeText(this, "Maps is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Map is ready");
        jacobsMap = googleMap;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.mapactivity, container, false);
    }

    private void initMap()
    {
        Log.d(TAG, "initializing map...");
        SupportMapFragment mapFragment = (SupportMapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapFragment.this);
    }

    private void getLocationPermission()
    {
        Log.d(TAG, "Retrieving location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                LocationPermissionsGranted = true;
            }
            else
                {
                requestPermissions( permissions,
                        LOCATION_PERMISSION_CODE);
                }
        }else{
            requestPermissions( permissions,
                    LOCATION_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        LocationPermissionsGranted = false;

        switch(requestCode)
        {
            case LOCATION_PERMISSION_CODE:
                {
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < grantResults.length; i++)
                    {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED)
                        {
                            LocationPermissionsGranted = false;
                            Log.d(TAG, "onRequestPermissionsResult: permission failed");
                            return;
                        }
                    }
                    Log.d(TAG, "onRequestPermissionsResult: permission granted");
                    LocationPermissionsGranted = true;
                    //if permissions are granted we initialize the map
                    initMap();
                }
            }
        }


    // integrate this method with the start of the application eg after a button has been clicked
    // Debugging method- checks if google services is available/functional
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
            /* //If an error occurs, and it is resolvable*/
            Log.d(TAG, "isServicesOK: A fixable error has occured!");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(MapFragment.this, available, ERROR_REQUEST);
            dialog.show();  /*//This prompts google to provide a error message and solution when a fixable error has occurred*/
        }

        else

        {
            Toast.makeText(this, "Google Play Services is dysfunctional, sorry!", Toast.LENGTH_SHORT).show(); /*//if it cant be fixed for whatever reason lmao*/
        }
        return false;
    }



}


