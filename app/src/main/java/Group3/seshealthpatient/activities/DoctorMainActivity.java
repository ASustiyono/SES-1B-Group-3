package group3.seshealthpatient.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import group3.seshealthpatient.fragments.DoctorClinicFragment;
import group3.seshealthpatient.fragments.DoctorMessengerFragment;
import group3.seshealthpatient.fragments.DoctorPatientSearchFragment;
import group3.seshealthpatient.fragments.DoctorProfileFragment;
import group3.seshealthpatient.R;


/**
 * Class: MainActivity
 * Extends:  {@link AppCompatActivity}
 * Author:  Carlos Tirado < Carlos.TiradoCorts@uts.edu.au>, and YOU!
 * Description:
 * <p>
 * For this project I encourage you to use Fragments. It is up to you to build up the app as
 * you want, but it will be a good practice to learn on how to use Fragments. A very good tutorial
 * on how to use fragments can be found on this site:
 * http://www.vogella.com/tutorials/AndroidFragments/article.html
 * <p>
 * I basically chose to use fragments because of the design of the app, again, you can choose to change
 * completely the design of the app, but for this design specifically I will use Fragments.
 * <p>
 */
public class DoctorMainActivity extends AppCompatActivity {

    /**
     * A basic Drawer layout that helps you build the side menu. I followed the steps on how to
     * build a menu from this site:
     * https://developer.android.com/training/implementing-navigation/nav-drawer
     * I recommend you to have a read of it if you need to do any changes to the code.
     */
    private DrawerLayout mDrawerLayout;

    /**
     * A reference to the toolbar
     */
    private Toolbar toolbar;

    /**
     * Helps to manage the fragment that is being used in the main view.
     */
    private FragmentManager fragmentManager;

    /**
     * TAG to use
     */
    private static String TAG = "MainActivity";

    /**
     * I am using this enum to know which is the current fragment being displayed, you will see
     * what I mean with this later in this code.
     */
    private enum MenuStates {
        DOCTOR_PROFILE, DOCTOR_SEARCH, MESSENGER, CLINIC_LOCATION, LOG_OUT
    }

    /**
     * The current fragment being displayed.
     */
    private MenuStates currentState;

    private FirebaseAuth Auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_doctor_main );

        // the default fragment on display is the patient information
        currentState = MenuStates.DOCTOR_PROFILE;

        // go look for the main drawer layout
        mDrawerLayout = findViewById( R.id.doctor_main_drawer_layout );

        toolbar = findViewById( R.id.doctor_toolbar );
        setSupportActionBar( toolbar );

        Auth = FirebaseAuth.getInstance();

        // Set up the menu button
        ActionBar actionbar = getSupportActionBar();
        actionbar.setDisplayHomeAsUpEnabled( true );
        actionbar.setHomeAsUpIndicator( R.drawable.ic_menu );

        // Setup the navigation drawer, most of this code was taken from:
        // https://developer.android.com/training/implementing-navigation/nav-drawer
        NavigationView navigationView = findViewById( R.id.doctor_nav_view );
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        // set item as selected to persist highlight
                        menuItem.setChecked( true );
                        // close drawer when item is tapped
                        mDrawerLayout.closeDrawers();

                        // Using a switch to see which item on the menu was clicked
                        switch (menuItem.getItemId()) {
                            // You can find these id's at: res -> menu -> drawer_view.xml
                            case R.id.nav_doctor_profile:
                                // If the user clicked on a different item than the current item
                                if (currentState != MenuStates.DOCTOR_PROFILE) {
                                    // change the fragment to the new fragment
                                    ChangeFragment( new DoctorProfileFragment() );
                                    currentState = MenuStates.DOCTOR_PROFILE;
                                }
                                break;
                            case R.id.nav_doctor_search:
                                // If the user clicked on a different item than the current item
                                if (currentState != MenuStates.DOCTOR_SEARCH) {
                                    // change the fragment to the new fragment
                                    ChangeFragment( new DoctorPatientSearchFragment());
                                    currentState = MenuStates.DOCTOR_SEARCH;
                                }
                                break;
                            case R.id.nav_doctor_messenger:
                                if (currentState != MenuStates.MESSENGER) {
                                    ChangeFragment( new DoctorMessengerFragment() );
                                    currentState = MenuStates.MESSENGER;
                                }
                                break;
                            case R.id.nav_doctor_clinics:
                                if (currentState != MenuStates.CLINIC_LOCATION) {
                                    ChangeFragment( new DoctorClinicFragment() );
                                    currentState = MenuStates.CLINIC_LOCATION;
                                }
                                break;
                            case R.id.nav_doctor_logout:
                                if (currentState != MenuStates.LOG_OUT) {
                                    //ChangeFragment(new MapFragment());
                                    currentState = MenuStates.LOG_OUT;

                                    logoutUser();
                                    SharedPreferences pref = getSharedPreferences( "PREFERENCE",
                                            Context.MODE_PRIVATE );
                                    SharedPreferences.Editor editor = pref.edit();
                                    editor.putBoolean( "isRemembered", false );
                                    editor.commit();
                                    startActivity( new Intent( DoctorMainActivity.this, LoginActivity.class ) );
                                    finish();

                                }
                                break;
                        }

                        return true;
                    }
                } );

        // If you need to listen to specific events from the drawer layout.
        mDrawerLayout.addDrawerListener(
                new DrawerLayout.DrawerListener() {
                    @Override
                    public void onDrawerSlide(View drawerView, float slideOffset) {
                        // Respond when the drawer's position changes
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        // Respond when the drawer is opened
                    }

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        // Respond when the drawer is closed
                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        // Respond when the drawer motion state changes
                    }
                }
        );


        // More on this code, check the tutorial at http://www.vogella.com/tutorials/AndroidFragments/article.html
        fragmentManager = getFragmentManager();

        // Add the default Fragment once the user logged in
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add( R.id.doctor_fragment_container, new DoctorProfileFragment() );
        ft.commit();
    }

    /**
     * Called when one of the items in the toolbar was clicked, in this case, the menu button.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawerLayout.openDrawer( GravityCompat.START );
                return true;
        }
        return super.onOptionsItemSelected( item );
    }

    /**
     * This function changes the title of the fragment.
     *
     * @param newTitle The new title to write in the
     */
    public void ChangeTitle(String newTitle) {
        toolbar.setTitle( newTitle );
    }


    /**
     * This function allows to change the content of the Fragment holder
     *
     * @param fragment The fragment to be displayed
     */
    private void ChangeFragment(Fragment fragment) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace( R.id.doctor_fragment_container, fragment );
        transaction.addToBackStack( null );
        transaction.commit();
    }

    public void logoutUser() {
        FirebaseAuth.getInstance().signOut();
    }

}
