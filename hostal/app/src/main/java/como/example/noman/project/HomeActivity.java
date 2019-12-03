package como.example.noman.project;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    static private boolean isLoggedIn;
    static WebService server; // Global server variable should be used everywhere

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");

        server = WebService.getInstance(this);
        server.clearQueue();

        /*WebService.UserObject obj = new WebService.UserObject("Wisaam", "wisaam.arif.99@gmail.com", "1234", 0, null);

        WebService.getInstance(this).addUser(obj, new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                {
                    Toast.makeText(getApplicationContext(), "Account Created!", Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "There is already an account with this email!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        /*server.getHostelsByCity("Gujrawala", new WebService.Callback<WebService.HostelObjectList>() {
            @Override
            public void callbackFunctionSuccess(WebService.HostelObjectList result) {
                Log.i("userHostels", (new Gson()).toJson(result));
            }

            @Override
            public void callbackFunctionFailure() {
                Log.i("User Hostels", "Error");
            }
        });

        /*server.getUserHostels("test@test.com", new WebService.Callback<WebService.HostelObjectList>() {
            @Override
            public void callbackFunctionSuccess(WebService.HostelObjectList result) {
                Log.i("userHostels", (new Gson()).toJson(result));
            }

            @Override
            public void callbackFunctionFailure() {
                Log.i("User Hostels", "Error");
            }
        });

        /*SharedPreferences mPrefs = this.getSharedPreferences("info", MODE_PRIVATE);
        mPrefs.edit().putString("logged_in", "wisaam.arif.99@ucp.edu.pk").apply();  //add entry in database
        mPrefs.edit().putString("logged_in_name", "Wisaam Arif").apply();  //add entry in database

        /*WebService.HostelObject hObj = new WebService.HostelObject("Paradise Hostel", "Nowhere", "Gujrawala", "There are none", 10, 1, null, 0);

        server.updateHostel(1, hObj, new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                    Toast.makeText(getApplicationContext(), "Updated Hostel Data", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Failed to update hostel data", Toast.LENGTH_LONG).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });

        /*server.getHostelReviews(3, new WebService.Callback<WebService.ReviewObjectList>() {
            @Override
            public void callbackFunctionSuccess(WebService.ReviewObjectList result) {
                Toast.makeText(getApplicationContext(), Integer.toString(result.reviewsStored.size()), Toast.LENGTH_LONG).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });

        /*server.addHostelReview("test@test.com", 3, 4.5f, "Average, this one", new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                    Toast.makeText(getApplicationContext(), "Added Review", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Unable to add review", Toast.LENGTH_LONG).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });

        /*WebService.UserObject test = new WebService.UserObject("Wisaam Arif", "wisaam.arif.99@ucp.edu.pk", "1234", 0, null);

        server.updateUserData(test, new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                    Toast.makeText(getApplicationContext(), "Successfully Added User", Toast.LENGTH_LONG).show();
                //else
                    //Toast.makeText(getApplicationContext(), "Failed to add user", Toast.LENGTH_LONG).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });

        /*server.verifyUser("test@test.com", "1234", new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                    Toast.makeText(getApplicationContext(), "Verified", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Not Verified", Toast.LENGTH_LONG).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });*/

        if (false)  //set this to true to add test data to the server
        {
            String[] hostelNames = {"Paradise Hostel", "Premium Alcazaba Hostel", "El Machico Hostel", "Einstein Hostel"};
            String[] hostelAddress = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
            String[] hostelCity = {"Muslim Town, Lahore", "Johar Town, Lahore", "Gulshan-e-Ravi, Lahore", "Ferozpur Road, Lahore"};
            float[] hostelRatings = {4.2f, 3.5f, 2.3f, 3.3f};
            final Integer[] hostelImagesId = {R.drawable.img_1, R.drawable.img_2, R.drawable.img_3, R.drawable.img_4};
            Integer[] hostelRooms = {20, 10, 16, 18};
            Integer[] hostelFloors = {6, 4, 3, 3};
            String[] hostelExtras = {"AC, Heater, Refrigerator", "AC, Heater, Refrigerator", "AC, Heater, Refrigerator", "AC, Heater, Refrigerator"};
            String[] hostelOwnerMail = {"test@test.com", "test@test.com", "test@test.com", "test@test.com"};

            for (int i = 0; i < 4; i++)
            {
                final int num = i;

                WebService.HostelObject hClass = new WebService.HostelObject(hostelNames[i], hostelAddress[i], hostelCity[i], hostelExtras[i], hostelRooms[i], hostelFloors[i], hostelOwnerMail[i], hostelRatings[i]);

                server.addHostel(hClass, new WebService.Callback<Integer>() {
                    @Override
                    public void callbackFunctionSuccess(Integer result) {
                        if (result != -1) {
                            Uri uri = Uri.parse("android.resource://como.example.noman.project/"+hostelImagesId[num]);
                            server.addHostelImage(result, uri, SET_DEFAULT_IMAGE.TRUE, new WebService.Callback<Boolean>() {
                                @Override
                                public void callbackFunctionSuccess(Boolean result) {
                                    Log.i("myInfoImageResultBool", Boolean.toString(result));
                                    if (result)
                                        Toast.makeText(getApplicationContext(), "Successfully Added Hostel Image", Toast.LENGTH_LONG).show();
                                    else
                                        Toast.makeText(getApplicationContext(), "Failed to Add Hostel Image", Toast.LENGTH_LONG).show();
                                }

                                @Override
                                public void callbackFunctionFailure() {
                                    Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
                                }
                            });
                            Toast.makeText(getApplicationContext(), "Successfully Uploaded Hostel", Toast.LENGTH_LONG).show();
                        }
                        else
                            Toast.makeText(getApplicationContext(), "Failed to Upload Hostel", Toast.LENGTH_LONG).show();
                    }
                    @Override
                    public void callbackFunctionFailure() {
                        Toast.makeText(getApplicationContext(), "Unable to connect", Toast.LENGTH_LONG).show();
                    }
                });
            }
        }

        /////////////////////////////////////////////////////////////


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //////////////////////////////////////////////////////////////

        //check if user is logged in or not
        SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
        if (mpef.getString("logged_in", null) == null)
            isLoggedIn = false;
        else
            isLoggedIn  = true;
        //////////////////////////////////////////////////////////////

        //checking if a user logged in to his/her account
        Menu menu = navigationView.getMenu();
        if(!isLoggedIn)
        {
            ((MenuItem) menu.findItem(R.id.nav_logout)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_addhostel)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_managehostel)).setVisible(false);
        }
        else
        {
            ((MenuItem) menu.findItem(R.id.nav_login)).setVisible(false);
            ((MenuItem) menu.findItem(R.id.nav_signup)).setVisible(false);

            int type = Integer.parseInt(mpef.getString("logged_in_type", "0"));
            if (type == 0) {
                ((MenuItem) menu.findItem(R.id.nav_addhostel)).setVisible(false);
                ((MenuItem) menu.findItem(R.id.nav_managehostel)).setVisible(false);
            }
        }


        Fragment fragment = new HomeFragment();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment);
        fragmentTransaction.commit();
    }

    static public boolean getLoginStatus()
    {
        return isLoggedIn;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if(drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else if(getSupportFragmentManager().getBackStackEntryCount() > 0)
            getSupportFragmentManager().popBackStack();
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            getSupportActionBar().setTitle("Home");
            startActivity(i);
        }
        else if (id == R.id.nav_login) {
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
        }
        else if (id == R.id.nav_signup) {
            Intent i = new Intent(getApplicationContext(), Signup.class);
            startActivity(i);
        }
        else if (id == R.id.nav_addhostel)
        {
            Fragment fragment = new AddHostel();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            getSupportActionBar().setTitle("Add Hostel");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_find)
        {
            Fragment fragment = new SearchResultFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            getSupportActionBar().setTitle("Find Hostel");
            fragmentTransaction.commit();
        }
        else if (id == R.id.nav_managehostel) {
            Fragment fragment = new ManageHostelFragment();
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.frameLayout, fragment);
            fragmentTransaction.addToBackStack(null);
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            getSupportActionBar().setTitle("Edit your hostel");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_logout) {
            SharedPreferences mpef = getSharedPreferences("info", MODE_PRIVATE);
            mpef.edit().putString("logged_in", null).apply();
            mpef.edit().putString("logged_in_out", null).apply();
            mpef.edit().putString("logged_in_type", null).apply();
            isLoggedIn = false;
            Toast.makeText(this, "Logged Out", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(getApplicationContext(), HomeActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
