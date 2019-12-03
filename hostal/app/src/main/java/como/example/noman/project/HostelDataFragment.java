package como.example.noman.project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class HostelDataFragment extends Fragment {

    public String hostelName = null;
    public int hostelRooms = -1;
    public int hostelFloors = -1;
    public int hostel_id = -1;
    public String hostelExtras = null;
    public String hostelAddress = null;
    public String ownerMail = null;
    public String hostel_rating;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        WebService.getInstance(getActivity()).clearQueue();

        ReviewFragment.hostelID = hostel_id;

        final View v = inflater.inflate(R.layout.hostel_data, container, false);

        if (hostelName != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_hostelName)).setText(hostelName);
        }
        if (hostelRooms != -1)
        {
            ((TextView)v.findViewById(R.id.hostelData_no_rooms)).setText(Integer.toString(hostelRooms));
        }
        if (hostelFloors != -1)
        {
            ((TextView)v.findViewById(R.id.hostelData_no_floors)).setText(Integer.toString(hostelFloors));
        }
        if (hostelExtras != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_extras)).setText(hostelExtras);
        }
        if (hostel_id != -1)
        {
            WebService.getInstance(getActivity()).getHostelProfileImage(hostel_id, 2, new WebService.Callback<Bitmap>() {
                @Override
                public void callbackFunctionSuccess(Bitmap result) {
                    if (result != null)
                        ((ImageView)v.findViewById(R.id.hostelData_image)).setImageBitmap(result);
                }

                @Override
                public void callbackFunctionFailure() {
                    Toast.makeText(getActivity(), "Unable to Load image(s)", Toast.LENGTH_LONG).show();
                }
            });
        }
        if (hostelAddress != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_hostelAddress)).setText(hostelAddress);
        }
        if (ownerMail != null)
        {
            ((TextView)v.findViewById(R.id.hostelData_ownerContact)).setText(ownerMail);
        }
        return v;
    }
}
