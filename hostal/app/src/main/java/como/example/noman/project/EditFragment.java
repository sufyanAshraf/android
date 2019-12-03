package como.example.noman.project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class EditFragment extends Fragment {

    EditText name, room_no, floor_no, extras;
    Button save_btn, del_btn;
    public String hostel_name, hostel_room_no, hostel_floor_no, hostel_extras;
    public int hostelID;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_edit, container, false);

        name = view.findViewById(R.id.hostelData_hostelName);
        room_no = view.findViewById(R.id.hostelData_no_rooms);
        floor_no = view.findViewById(R.id.hostelData_no_floors);
        extras = view.findViewById(R.id.hostelData_extras);
        save_btn = view.findViewById(R.id.SaveButton);
        del_btn = view.findViewById(R.id.Delete);

        name.setText(hostel_name);
        room_no.setText(hostel_room_no);
        floor_no.setText(hostel_floor_no);
        extras.setText(hostel_extras);

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });

        del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete_hostel();
            }
        });

        return view;
    }

    public void delete_hostel(){

        WebService.getInstance(getActivity()).deleteHostel(hostelID, new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                    Toast.makeText(getActivity(), "Successfully deleted Hostel", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
                switch_page();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getActivity(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void switch_page()
    {
        Intent homePage = new Intent(getActivity(), HomeActivity.class);
        getActivity().startActivity(homePage);
    }

    public void save()
    {
        hostel_name = name.getText().toString();
        hostel_room_no = room_no.getText().toString();
        hostel_floor_no = floor_no.getText().toString();
        hostel_extras = extras.getText().toString();

        WebService.HostelObject obj = new WebService.HostelObject(hostel_name, null, null, hostel_extras, Integer.parseInt(hostel_room_no), Integer.parseInt(hostel_floor_no), null, 0);

        WebService.getInstance(getActivity()).updateHostel(hostelID, obj, new WebService.Callback<Boolean>() {
            @Override
            public void callbackFunctionSuccess(Boolean result) {
                if (result)
                    Toast.makeText(getActivity(), "Successfully Updated Hostel", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getActivity(), "An error occurred", Toast.LENGTH_LONG).show();
                switch_page();
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(getActivity(), "Unable to connect", Toast.LENGTH_LONG).show();
            }
        });
    }
}