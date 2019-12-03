package como.example.noman.project;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class ProfilePage extends AppCompatActivity {
    TextView viewProfileName, viewProfileEmail, viewProfilePhone, editPassword;
    EditText editProfileName, editProfileEmail, editProfilePhone;
    TextView goBack ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        viewProfileName = (TextView) findViewById(R.id.profile_name);
        editProfileName = (EditText) findViewById(R.id.name_edittext);
        viewProfileEmail = (TextView) findViewById(R.id.profile_email);
        editProfileEmail = (EditText) findViewById(R.id.email_edittext);
        viewProfilePhone = (TextView) findViewById(R.id.profile_phone);
        editProfilePhone = (EditText) findViewById(R.id.phone_edittext);
        editPassword = (TextView) findViewById(R.id.profile_edit_password);
        goBack = (TextView) findViewById(R.id.profile_GoToHome);


        viewProfileName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewProfileName.setVisibility(View.GONE);
                editProfileName.setVisibility(View.VISIBLE);
            }
        });

        viewProfileEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewProfileEmail.setVisibility(View.GONE);
                editProfileEmail.setVisibility(View.VISIBLE);
            }
        });

        viewProfilePhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                viewProfilePhone.setVisibility(View.GONE);
                editProfilePhone.setVisibility(View.VISIBLE);
            }
        });

        editPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment  = new EditProfilePassword();
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.profileLayout, fragment);
                fragmentTransaction.commit();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), HomeActivity.class);
                getBaseContext().startActivity(intent);
            }
        });
    }


}
