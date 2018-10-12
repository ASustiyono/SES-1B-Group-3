package group3.seshealthpatient.fragments;


import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.widget.Toast;
import android.util.Log;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.app.Dialog;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import android.Manifest;
import android.support.annotation.NonNull;
import group3.seshealthpatient.R;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment implements OnMapReadyCallback{

    private static final String TAG = "MapFragment";

    private Boolean LocationPermissionsGranted = false;  //boolean for location method
    private GoogleMap Map;
    private static final int ERROR_REQUEST = 69; //error code relates to isservicesOK method
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private FusedLocationProviderClient FusedLocationProviderClient;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //TODO: Instead of hardcoding the title perhaps take the user name from somewhere?
        // Note the use of getActivity() to reference the Activity holding this fragment
        getActivity().setTitle( "YOUR CLINICS" );
        isServicesOK();
        getLocationPermission();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        ButterKnife.bind( getActivity() );

        return inflater.inflate( R.layout.fragment_map, container, false );


    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(getActivity(), "Maps is ready", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "Map is ready");
        Map = googleMap;
        if (LocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            Map.setMyLocationEnabled(true);
            Map.getUiSettings().setMyLocationButtonEnabled(true);


        }
    }
    private void initMap() {
        Log.d(TAG, "initializing map...");
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(MapFragment.this);
    }

    private void getLocationPermission()
    {
        Log.d(TAG, "Retrieving location permissions");
        String[] permissions = {android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(), //ignore
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (ContextCompat.checkSelfPermission(this.getActivity().getApplicationContext(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                LocationPermissionsGranted = true;
                initMap();
            } else {
               requestPermissions(permissions,
                        LOCATION_PERMISSION_CODE);
            }
        } else {
            requestPermissions(permissions,
                    LOCATION_PERMISSION_CODE);
        }
    }
    private void getDeviceLocation(){
        Log.d(TAG, "getDeviceLocation: getting the devices current location");

        FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try{
            if(LocationPermissionsGranted){

                final Task location = FusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG, "onComplete: found location!");
                            Location currentLocation = (Location) task.getResult();

                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    DEFAULT_ZOOM);

                        }else{
                            Log.d(TAG, "onComplete: current location is null");
                            Toast.makeText(getActivity(), "unable to get current location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch (SecurityException e){
            Log.e(TAG, "getDeviceLocation: SecurityException: " + e.getMessage() );
        }
    }

    private void moveCamera(LatLng latLng, float zoom){
        Log.d(TAG, "moveCamera: moving the camera to: lat: " + latLng.latitude + ", lng: " + latLng.longitude );
        Map.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        Log.d(TAG, "onRequestPermissionsResult: called.");
        LocationPermissionsGranted = false;

        switch (requestCode)
        {
            case LOCATION_PERMISSION_CODE:
            {
                if (grantResults.length > 0)
                {
                    for (int i = 0; i < grantResults.length; i++) //ignore
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
    }


    // integrate this method with the start of the application eg after a button has been clicked
    // Debugging method- checks if google services is available/functional
    public boolean isServicesOK ()
    {
        Log.d(TAG, "isServicesOK: checking if your Google Services is up to date"); //debugging

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(getActivity()); /*//log d = debug*/

        if (available == ConnectionResult.SUCCESS)
        {
            /*//checks if everything is fine*/
            Log.d(TAG, "isServicesOK: Google Play Services is functional");
            return true;
        }
        else if (GoogleApiAvailability.getInstance().isUserResolvableError(available))
        {
            /* //If an error occurs, and it is resolvable*/
            Log.d(TAG, "isServicesOK: A fixable error has occured!");
            Dialog dialog = GoogleApiAvailability.getInstance().getErrorDialog(getActivity(), available, ERROR_REQUEST);
            dialog.show();  /*//This prompts google to provide a error message and solution when a fixable error has occurred*/
        }

        else
        {
            Toast.makeText(getActivity(), "Google Play Services is dysfunctional, sorry!", Toast.LENGTH_SHORT).show(); /*//if it cant be fixed for whatever reason lmao*/
        }
        return false;
    }

}
