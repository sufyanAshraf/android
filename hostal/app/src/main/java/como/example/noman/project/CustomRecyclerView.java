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


public class CustomRecyclerView extends  RecyclerView.Adapter<CustomRecyclerView.MyViewHolder> {

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

    public CustomRecyclerView(Activity context, String[] hostelNames, String[] hostelAddress, String[] hostelRatings, String[] hostelCity, Integer[] hostelRooms, Integer[] hostelFloors, String[] hostelExtras, String[] hostelOwnerMail, int[] hostelIDs) {

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
        View itemView = layoutInflater.inflate(R.layout.list_item, parent, false);

        // Create and return our customRecycler View Holder object.
        MyViewHolder viewHolder = new MyViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        holder.name.setText(hostelNames[position]);
        holder.rating.setText(hostelRatings[position]);
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

        public TextView name = null;
        public TextView rating = null;
        public ImageView image = null;

        public MyViewHolder(View view) {
            super(view);

            if(view != null)
            {
                name = (TextView) view.findViewById(R.id.item_name);
                rating = (TextView) view.findViewById(R.id.item_rating);
                image = (ImageView) view.findViewById(R.id.item_image);
            }
        }

    }
}
