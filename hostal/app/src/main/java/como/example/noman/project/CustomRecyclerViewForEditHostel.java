package como.example.noman.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class CustomRecyclerViewForEditHostel extends  RecyclerView.Adapter<CustomRecyclerViewForEditHostel.MyViewHolder> {

    private String[] hostelNames;
    private String[] hostelAddress;
    private String[] hostelRatings;
    private Activity context;
    private String[] hostelCity;
    private Integer[] hostelRooms;
    private Integer[] hostelFloors;
    private String[] hostelExtras;
    private String[] hostelOwnerMail;
    private int[] hostelIDs;
    static public boolean isEditable;

    public CustomRecyclerViewForEditHostel(Activity context, String[] hostelNames, String[] hostelAddress, String[] hostelRatings, String[] hostelCity, Integer[] hostelRooms, Integer[] hostelFloors, String[] hostelExtras, String[] hostelOwnerMail, int[] hostelIDs) {

        this.context = context;
        this.hostelNames = hostelNames;
        this.hostelAddress = hostelAddress;
        this.hostelRatings = hostelRatings;
        this.hostelCity = hostelCity;
        this.hostelRooms = hostelRooms;
        this.hostelFloors = hostelFloors;
        this.hostelExtras = hostelExtras;
        this.hostelOwnerMail = hostelOwnerMail;
        this.hostelIDs = hostelIDs;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Get LayoutInflater object.
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        // Inflate the RecyclerView item layout xml.
        View itemView = layoutInflater.inflate(R.layout.list_item_edit_hostel, parent, false);

        // Create and return our customRecycler View Holder object.
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(hostelNames[position]);
        holder.address.setText(hostelAddress[position]);
        holder.city.setText(hostelCity[position]);
        holder.rooms.setText(Integer.toString(hostelRooms[position]));
        holder.floors.setText(Integer.toString(hostelFloors[position]));
        holder.extras.setText(hostelExtras[position]);
        WebService.getInstance(context).getHostelProfileImage(hostelIDs[position], 1, new WebService.Callback<Bitmap>() {
            @Override
            public void callbackFunctionSuccess(Bitmap result) {
                if (result != null)
                    holder.image.setImageBitmap(result);
            }

            @Override
            public void callbackFunctionFailure() {
                Toast.makeText(context, "Unable to Load image(s)", Toast.LENGTH_LONG).show();
            }
        });

        ClickListener cl = new ClickListener(context, context.getApplicationContext());
        // setting parameters for click listener //
        cl.hostelName = hostelNames[position];
        cl.hostelAddress = hostelAddress[position];
        cl.hostelExtras = hostelExtras[position];
        cl.no_rooms = hostelRooms[position];
        cl.no_floors = hostelFloors[position];
        cl.owner_email = hostelOwnerMail[position];
        cl.hostel_id = hostelIDs[position];
        cl.hostel_rating = hostelRatings[position];
        /////////////////////////////////////////

        holder.image.setOnClickListener(cl);  //setting onclick listener to read more textview
    }

    @Override
    public int getItemCount() {
        return hostelNames.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        public TextView name ;
        public TextView address;
        public TextView city;
        public TextView rooms;
        public TextView floors;
        public TextView extras;
        public ImageView image;

        public MyViewHolder(View view) {
            super(view);

            if(view != null)
            {
                name = (TextView) view.findViewById(R.id.edit_item_name);
                address = (TextView) view.findViewById(R.id.edit_item_address);
                city = (TextView) view.findViewById(R.id.edit_item_city);
                rooms = (TextView) view.findViewById(R.id.edit_item_rooms);
                floors = (TextView) view.findViewById(R.id.edit_item_floors);
                extras = (TextView) view.findViewById(R.id.edit_item_extras);
                image = (ImageView) view.findViewById(R.id.edit_item_image);
            }
        }

    }
}
