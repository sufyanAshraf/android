package como.example.noman.project;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.TextView;


public class HomeFragment extends Fragment {

    ////////////// Getting Data of Hostels //////////////////
    private String[] hostelNames;
    private String[] hostelAddress;
    private String[] hostelRatings;
    private String[] hostelCity;
    private Integer[] hostelRooms;
    private Integer[] hostelFloors;
    private String[] hostelExtras;
    private String[] hostelOwnerMail;
    private int[] hostelIDs;
    ////////////////////////////////////////////////////////////

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_home, container, false);
        final LayoutInflater inflaterForInner = inflater;
        final ProgressBar prog_bar = (ProgressBar) view.findViewById(R.id.progress_bar_home);
        prog_bar.setVisibility(view.VISIBLE);

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Home");

        /////////////// Getting Data for Hotels //////////////////////////////

        String[] city = {"Lahore", "Islamabad", "Faisalabad", "Sialkot", "Multan"};

        for (int index = 0; index < 5; index++) {

            final String cityName = city[index];
            final int i = index;

            WebService.getInstance(getActivity()).getHostelsByCity(city[i], new WebService.Callback<WebService.HostelObjectList>() {
                @Override
                public void callbackFunctionSuccess(WebService.HostelObjectList hl) {

                    prog_bar.setVisibility(view.GONE);

                    hostelNames = new String[hl.hostelsStored.size()];
                    hostelAddress = new String[hl.hostelsStored.size()];
                    hostelRatings = new String[hl.hostelsStored.size()];
                    hostelCity = new String[hl.hostelsStored.size()];
                    hostelRooms = new Integer[hl.hostelsStored.size()];
                    hostelFloors = new Integer[hl.hostelsStored.size()];
                    hostelExtras = new String[hl.hostelsStored.size()];
                    hostelOwnerMail = new String[hl.hostelsStored.size()];
                    hostelIDs = new int[hl.hostelsStored.size()];

                    for (int i = 0; i < hl.hostelsStored.size(); i++) {
                        hostelNames[i] = hl.hostelsStored.get(i).hostelName;
                        hostelAddress[i] = hl.hostelsStored.get(i).hostelAddress;
                        hostelRatings[i] = Float.toString(hl.hostelsStored.get(i).rating);
                        hostelCity[i] = hl.hostelsStored.get(i).hostelCity;
                        hostelRooms[i] = hl.hostelsStored.get(i).no_rooms;
                        hostelFloors[i] = hl.hostelsStored.get(i).no_floors;
                        hostelExtras[i] = hl.hostelsStored.get(i).hostelExtras;
                        hostelOwnerMail[i] = hl.hostelsStored.get(i).owner_email;
                        hostelIDs[i] = hl.hostelsStored.get(i).hostel_id;

                        ///////////////////////////////////////////////
                    }

                    ViewGroup parent = (ViewGroup) view.findViewById(R.id.home_fragment_table);
                    LinearLayout v = (LinearLayout) inflaterForInner.inflate(R.layout.table_row_home_category, parent);
                    v.getChildAt(i).setId(View.generateViewId());

                    TextView textView = v.getChildAt(i).findViewById(R.id.list_heading);
                    RecyclerView listView = (RecyclerView) view.findViewById(R.id.fragment_home_recycler_view);

                    //randomly setting ids to views
                    int id1 = View.generateViewId();
                    int id2 = View.generateViewId();

                    textView.setId(id1);
                    listView.setId(id2);

                    listView = (RecyclerView) view.findViewById(id2);

                    //Change heading text here
                    textView.setText(cityName);

                    /////////////////// setting adapter here ////////////////////
                    CustomRecyclerView adapter = new CustomRecyclerView(getActivity(), hostelNames, hostelAddress, hostelRatings, hostelCity, hostelRooms, hostelFloors, hostelExtras, hostelOwnerMail, hostelIDs);
                    LinearLayoutManager horizontalLayoutManager = new LinearLayoutManager(getActivity().getBaseContext(), LinearLayoutManager.HORIZONTAL, false);
                    listView.setLayoutManager(horizontalLayoutManager);
                    listView.setAdapter(adapter);
                    /////////////////////////////////////////////////////////////
                }

                @Override
                public void callbackFunctionFailure() {
                    Toast.makeText(getContext(), "Unable to connect", Toast.LENGTH_LONG).show();
                }
            });
        }

        return view;
    }
}