package ai.kortnevdmitriy.msafiri.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ai.kortnevdmitriy.msafiri.R;
import ai.kortnevdmitriy.msafiri.utilities.Constants;
import de.hdodenhof.circleimageview.CircleImageView;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int REQUEST_INVITE = 0;
    private static final String TAG = Home.class.getSimpleName();
    public String extractedTravelRoute;
    private NavigationView navigationView;
    private AutoCompleteTextView destinationFrom, destinationTo;
    private ArrayAdapter<String> adapter;
    private String separator = "-";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                extractTravelRoute();
                /*Snackbar.make(view, "Searching the database", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        updateNavigationViewUI();
        searchUI();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(getApplicationContext(), Settings.class));
            return true;
        }
        if (id == R.id.action_signout) {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(getApplicationContext(), Signin.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_view_all) {
            startActivity(new Intent(getApplicationContext(), ViewAll.class));
        } else if (id == R.id.nav_account) {
            startActivity(new Intent(getApplicationContext(), Account.class));
        } else if (id == R.id.nav_my_tickets) {
            startActivity(new Intent(getApplicationContext(), Tickets.class));
        } else if (id == R.id.nav_share) {
            onInviteClicked();
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /* This methods update the navigation views with the account owner details.
   Picture, name & email*/
    public void updateNavigationViewUI() {
        View header = navigationView.getHeaderView(0);
        CircleImageView picView = header.findViewById(R.id.picView);
        TextView nameView = header.findViewById(R.id.nameView);
        TextView emailView = header.findViewById(R.id.emailView);

        FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        nameView.setText(mFirebaseUser.getDisplayName());
        emailView.setText(mFirebaseUser.getEmail());
        //Picasso.with(this).load(mFirebaseUser.getPhotoUrl()).into(picView);
    }

    public void searchUI() {
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, Constants.DESTINATIONS);
        destinationFrom = findViewById(R.id.autoCompleteDestinationFrom);
        destinationTo = findViewById(R.id.autoCompleteDestinationTo);
        destinationFrom.setAdapter(adapter);
        destinationTo.setAdapter(adapter);
    }

    public void extractTravelRoute() {
        String destFrom = destinationFrom.getText().toString().trim();
        String destTo = destinationTo.getText().toString().trim();

        if (TextUtils.isEmpty(destFrom)) {
            Toast.makeText(getApplicationContext(), "Fill Travelling From!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(destTo)) {
            Toast.makeText(getApplicationContext(), "Fill Travelling To", Toast.LENGTH_SHORT).show();
            return;
        }

        extractedTravelRoute = destFrom + separator + destTo;
        Log.i("Extracted Route:", extractedTravelRoute);

        Intent intent = new Intent(getApplicationContext(), Search.class);
        intent.putExtra("keyName", extractedTravelRoute);
        startActivity(intent);
    }

    // [START on_invite_clicked]

    private void onInviteClicked() {

        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))

                .setDeepLink(Uri.parse(getString(R.string.invitation_deep_link)))

                .setCallToActionText(getString(R.string.invitation_cta))

                .build();

        startActivityForResult(intent, REQUEST_INVITE);

    }

    // [END on_invite_clicked]


    // [START on_activity_result]

    @Override

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d(TAG, "onActivityResult: requestCode=" + requestCode + ", resultCode=" + resultCode);
        if (requestCode == REQUEST_INVITE) {
            if (resultCode == RESULT_OK) {
                // Get the invitation IDs of all sent messages
                String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                for (String id : ids) {
                    Log.d(TAG, "onActivityResult: sent invitation " + id);
                }

            } else {
                // Sending failed or it was canceled, show failure message to the user
                // [START_EXCLUDE]
                // [END_EXCLUDE]
            }

        }

    }
}
