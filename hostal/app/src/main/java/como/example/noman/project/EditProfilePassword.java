package como.example.noman.project;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class EditProfilePassword extends Fragment {
    EditText OldPassword, newPassword, ConfirmPassword;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_edit_profile_password, container, false);

        TextView go_back;
        go_back = view.findViewById(R.id.profile_go_back);
        Button ChangePassword = view.findViewById(R.id.profile_change_password);
        newPassword = (EditText) view.findViewById(R.id.profile_new_password);
        ConfirmPassword =(EditText) view.findViewById(R.id.profile_confirm_password);
        OldPassword = (EditText) view.findViewById(R.id.profile_old_password);


        go_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProfilePage.class);
                getActivity().startActivity(intent);
            }
        });

        ChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangePassword();
            }
        });

        return view;
    }

    private void ChangePassword()
    {
        String Password = " ";        //Read old password from DataBase
        if (!OldPassword.getText().toString().equals(Password)) {
            OldPassword.setError("Incorrect Password");
            return;
        }

        if (OldPassword.getText().length() == 0) {
            OldPassword.setError("Field cannot be left blank.");
            return;
        }

        if (newPassword.getText().length() == 0) {
            newPassword.setError("Field cannot be left blank.");
            return;
        }

        if (!ConfirmPassword.getText().toString().equals(newPassword.getText().toString())) {
            ConfirmPassword.setError("Passwords don't match");
            return;
        }

        //update password Here
    }

}
