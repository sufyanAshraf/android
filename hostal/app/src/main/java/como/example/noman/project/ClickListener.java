package como.example.noman.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import static android.content.Context.MODE_PRIVATE;

public class ClickListener implements  View.OnClickListener {

    private Activity activity;  //use this activity or context when needed
    private Context context;

    ClickListener(Activity _activity, Context _context) //first constructor
    {
        activity = _activity;
        context = _context;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())              // add click function in this switch against the view id
        {
            case  R.id.signup_admin_checkbox:
                setVisibility();
                break;
            case R.id.txt_already:
                goToLogin();
                break;
            case R.id.create_account:
                createAccount();
                break;
            case R.id.NoAccount:
                goToRegister();
                break;
            case R.id.signinButton:
                signin();
                break;
            case R.id.item_image:
                readMore();
                break;
            case R.id.edit_item_image:
                if(CustomRecyclerViewForEditHostel.isEditable == true)
                    gotoEditPage();
                else
                    readMore();
                break;
            case R.id.signup_GoToHome:
            case R.id.goToHome:
                goToHome();
                break;
            case R.id.addHostel_save:
                addHostelToDatabase();
                break;

            default:
                break;
        }
    }

    //// Implement Click Functions Here ////

    private void goToAddHostel() {
        FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
        AddHostel newFragment = new AddHostel();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, newFragment).commit();
    }

    private  void setVisibility()
    {
        CheckBox CheckBox = activity.findViewById(R.id.signup_admin_checkbox);
        EditText Cnic = activity.findViewById(R.id.cnic);
        EditText Phone = activity.findViewById(R.id.Phone);
        Log.i("myInfo", "here in visibility "+Integer.toString(Signup.ID));
        Toast.makeText(context, "An Admin can add and manage his/her own hostels", Toast.LENGTH_LONG).show();

        if(CheckBox.isChecked())
        {   Signup.ID = 1;
            Cnic.setVisibility(View.VISIBLE);
            Phone.setVisibility(View.VISIBLE);
        }
        else
        {
            Signup.ID = 0;
            Cnic.setVisibility(View.GONE);
            Phone.setVisibility(View.GONE);
        }
        Log.i("myInfo", "here in visibility "+Integer.toString(Signup.ID));
    }

    private void goToLogin() {
        Intent intent = new Intent(activity, Login.class);
        activity.startActivity(intent);
    }

    private void goToHome() {
        Intent intent = new Intent(activity, HomeActivity.class);
        activity.startActivity(intent);
    }

    private void goToRegister() {
        Intent register = new Intent(activity, Signup.class);
        activity.startActivity(register);
    }

    private void createAccount() {
        EditText UserName = (EditText) activity.findViewById(R.id.user_name);
        EditText Email = (EditText) activity.findViewById(R.id.email_id);
        EditText Password = (EditText) activity.findViewById(R.id.password);
        EditText ConfirmPassword = (EditText) activity.findViewById(R.id.confirm_password);
        EditText Cnic = activity.findViewById(R.id.cnic);
        EditText Phone = activity.findViewById(R.id.Phone);

        if (UserName.getText().toString().trim().length() == 0) {
            UserName.setError("Field cannot be left blank.");
            return;
        }

        if (Email.getText().toString().trim().length() == 0) {
            Email.setError("Field cannot be left blank.");
            return;
        }

        if (Password.getText().length() == 0) {
            Password.setError("Field cannot be left blank.");
            return;
        }

        if (!ConfirmPassword.getText().toString().equals(Password.getText().toString())) {
            ConfirmPassword.setError("Passwords don't match");
            return;
        }

        SaveData(Signup.ID, UserName, Email, Password, Cnic , Phone);
    }

    private boolean SaveData(final int ID,EditText UserName, EditText Email, EditText Password, EditText Cnic, EditText Phone) {
        final String email = Email.getText().toString().trim();
        final String Name = UserName.getText().toString();
        String password = Password.getText().toString();
        String cnic = Cnic.getText().toString();
        String phone = Phone.getText().toString();

        if(ID == 0)
        {
            cnic = "";
            phone = "";
        }

        Log.i("signup_info", email+" "+Name+" "+password+" "+phone+" "+ID);

        WebService.UserObject obj = new WebService.UserObject(Name, email, password, ID, phone);

        WebService.getInstance(activity).addUser(obj, new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                {
                    SharedPreferences mPrefs = activity.getSharedPreferences("info", MODE_PRIVATE);
                    mPrefs.edit().putString("logged_in", email).apply();  //add entry in database
                    mPrefs.edit().putString("logged_in_name", Name).apply();
                    mPrefs.edit().putString("logged_in_type", Integer.toString(ID)).apply();

                    Toast.makeText(activity, "Account Created!", Toast.LENGTH_SHORT).show();
                    Intent homePage = new Intent(activity, HomeActivity.class);
                    activity.startActivity(homePage);
                }
                else
                    Toast.makeText(activity, "There is already an account with this email!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(activity, "Unable to connect", Toast.LENGTH_SHORT).show();
            }
        });

        return true;
    }

    private void signin() {
        EditText emailAddress = activity.findViewById(R.id.emailText);
        EditText passwordText = activity.findViewById(R.id.passText);

        String mail = emailAddress.getText().toString().trim();
        String p = passwordText.getText().toString();

        if (mail.isEmpty())
            emailAddress.setError("Enter your email!");
        else if (p.isEmpty())
            passwordText.setError("Enter your password!");

        else {

            WebService.getInstance(activity).getUserData(mail, p, new WebService.Callback<WebService.UserObject>() {
                @Override
                public void callbackFunctionSuccess(WebService.UserObject result) {
                    if (result.userName != null) {
                        SharedPreferences mPrefs = activity.getSharedPreferences("info", MODE_PRIVATE);
                        mPrefs.edit().putString("logged_in", result.email).apply();  //add entry in database
                        mPrefs.edit().putString("logged_in_name", result.userName).apply();
                        mPrefs.edit().putString("logged_in_type", Integer.toString(result.accountType)).apply();
                        Toast.makeText(activity, "Successfully logged in!", Toast.LENGTH_LONG).show();
                        Intent homePage = new Intent(activity, HomeActivity.class);
                        activity.startActivity(homePage);
                    } else {
                        Toast.makeText(activity, "Incorrect username or password", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void callbackFunctionFailure() {
                    Toast.makeText(activity, "Unable to connect", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //parameters for the function that follows
    public String hostelName;
    public String hostelAddress;
    public String hostelExtras;
    public int no_rooms;
    public int no_floors;
    public String owner_email;
    public int hostel_id;
    public String hostel_rating;

    private void readMore() {
        HostelDataFragment newFragment = new HostelDataFragment();
        newFragment.hostelAddress = hostelAddress;
        newFragment.hostelName = hostelName;
        newFragment.hostelRooms = no_rooms;
        newFragment.hostelFloors = no_floors;
        newFragment.hostelExtras = hostelExtras;
        newFragment.ownerMail = owner_email;
        newFragment.hostel_id = hostel_id;
        newFragment.hostel_rating = hostel_rating;
        FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, newFragment).commit();
    }

    private void gotoEditPage() {
        EditFragment newFragment = new EditFragment();
        newFragment.hostel_name = hostelName;
        newFragment.hostel_room_no = Integer.toString(no_rooms);
        newFragment.hostel_floor_no = Integer.toString(no_floors);
        newFragment.hostel_extras = hostelExtras;
        newFragment.hostelID = hostel_id;

        FragmentManager fm = ((FragmentActivity) activity).getSupportFragmentManager();
        fm.beginTransaction().addToBackStack(null).replace(R.id.frameLayout, newFragment).commit();
    }



    private void addHostelToDatabase() {

        EditText name = ((EditText) activity.findViewById(R.id.addHostel_name));
        EditText address = ((EditText) activity.findViewById(R.id.addHostel_address));
        EditText roomsE = ((EditText) activity.findViewById(R.id.addHostel_rooms));
        EditText floorsE = ((EditText) activity.findViewById(R.id.addHostel_floors));
        EditText extras = ((EditText) activity.findViewById(R.id.addHostel_extras));

        if (name.getText().toString().trim().length() == 0) {
            name.setError("Field Empty");
            return;
        }
        if (address.getText().toString().trim().length() == 0) {
            address.setError("Field Empty");
            return;
        }
        if (roomsE.getText().toString().trim().length() == 0) {
            roomsE.setError("Field Empty");
            return;
        }
        if (floorsE.getText().toString().trim().length() == 0) {
            floorsE.setError("Field Empty");
            return;
        }
        if (extras.getText().toString().trim().length() == 0) {
            extras.setError("Field Empty");
            return;
        }
        if (AddHostel._uri == null) {
            Toast.makeText(activity, "Please Select an Image", Toast.LENGTH_SHORT).show();
            return;
        }

        String hostelName_local = name.getText().toString();
        String hostelAddress_local = address.getText().toString();
        String hostelCity_local = ((Spinner) activity.findViewById(R.id.addHostel_city)).getSelectedItem().toString();
        String hostelFacilities_local = extras.getText().toString();
        int rooms = Integer.parseInt(roomsE.getText().toString());
        int floors = Integer.parseInt(floorsE.getText().toString());
        SharedPreferences mpef = activity.getSharedPreferences("info", MODE_PRIVATE);
        String owner = mpef.getString("logged_in", null);

        WebService.HostelObject obj = new WebService.HostelObject(hostelName_local, hostelAddress_local, hostelCity_local, hostelFacilities_local, rooms, floors, owner, 0);

        WebService.getInstance(activity).addHostel(obj, new WebService.Callback<Integer>() {
            @Override
            public void callbackFunctionSuccess(Integer result) {
                WebService.getInstance(activity).addHostelImage(result, AddHostel._uri, SET_DEFAULT_IMAGE.TRUE, new WebService.Callback<Boolean>() {
                    @Override
                    public void callbackFunctionSuccess(Boolean result) {
                        Toast.makeText(activity, "Successfully uploaded hostel", Toast.LENGTH_LONG).show();
                        goToHome();
                    }

                    @Override
                    public void callbackFunctionFailure() {
                        Toast.makeText(activity, "Unable to connect", Toast.LENGTH_LONG).show();
                    }
                });
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(activity, "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });
    }
}