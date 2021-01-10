package com.teamsar.eventure.activity_home;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import com.teamsar.eventure.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.teamsar.eventure.activity_addnewevent.AddNewEvent;
import com.teamsar.eventure.activity_home.fragments_bnb.HomeFragment;
import com.teamsar.eventure.activity_home.fragments_bnb.NotificationFragment;
import com.teamsar.eventure.activity_home.fragments_bnb.ProfileFragment;
import com.teamsar.eventure.activity_home.fragments_bnb.TimelineFragment;
import com.teamsar.eventure.activity_login.LoginActivity;

public class HomeActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    // Log Tag
    public final String LOG_TAG="context(HomeActivity)";

    // Firebase Auth Objects
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;

    // UI elements
    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        /* SET UP UI ELEMENTS */
        // configure BNB
        bottomNavigationView = findViewById(R.id.bottomNavigationBar);
        // implemented BottomNavigationView.OnNavigationItemSelectedListener for adding listener
        bottomNavigationView.setOnNavigationItemSelectedListener(this);

        // firstly load HomeFragment as default opened fragment for BNB
        switchToFragment(new HomeFragment());

        /* CONFIGURE FIREBASE AUTH */
        // instantiate firebase auth instance
        mAuth=FirebaseAuth.getInstance();
        // configure request for Google Sign In
        createRequest();
    }

    private void createRequest() {
        // build GoogleSignInOption using oAuth Client ID
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.oAuth_client_id))    // providing oAuth Client ID
                .requestEmail() // requesting Email to be selected
                .build();
        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);    // Creating googleSignInClient from options specified in gso

    }



    // this method inflates activity menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // get menu inflater for activity
        MenuInflater menuInflater=getMenuInflater();
        // inflate the home_activity_menu in the menu provider
        menuInflater.inflate(R.menu.home_activity_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.signout_menu_item: // if logout button is pressed
                signOut();  // sign out
            default: return super.onOptionsItemSelected(item);
        }
    }

    public void signOut() {
        mAuth.signOut();    // signout the user from FirebaseAuth
        // but signing out from FirebaseAuth is not enough for Google Sign in
        // we also need to signout the user using Google Sign in Client
        mGoogleSignInClient.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                // switch to home fragment once sign out
                switchToFragment(new HomeFragment());
            }
        });

    }

    boolean switchToFragment(Fragment fragment) {
        if(fragment!=null) {
            // replace the fragment using fragment manager
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragments_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // switch to fragment depending on BNB item selected
        switch (item.getItemId()){
            case R.id.home:
                return switchToFragment(new HomeFragment());
            case R.id.timeline:
                return switchToFragment(new TimelineFragment());
            case R.id.add:
                //return switchToFragment(new NewEventFragment());
                startActivity(new Intent(HomeActivity.this, AddNewEvent.class));
                return true;
            case R.id.notifications:
                return switchToFragment(new NotificationFragment());
            case R.id.profile:
                // if user is logged in, show him profile
                if(mAuth.getCurrentUser()!=null) {
                    return switchToFragment(new ProfileFragment());
                }   // otherwise launch login activity
                else {
                    launchLoginActivity();
                    return true;
                }
        }
        return false;
    }

    private void launchLoginActivity() {
        // launch LoginActivity using intent
        Intent intentToLoginActivity=new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(intentToLoginActivity);
    }
}