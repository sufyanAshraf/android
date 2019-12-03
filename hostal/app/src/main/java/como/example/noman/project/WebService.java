// This singleton class acts as an interface to interact with the server
// Different services can be requested from the remote server
// Like retrieving and submitting data to the database etc.

/*
    NOTE:
        Some functions require a callback object to be passed as well in which the callbackFunction should be overridden.
        (telling it what to do once the data is retrieved from the server)
        This interface is implemented at the bottom of this class
*/

package como.example.noman.project;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import android.net.Uri;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

enum SET_DEFAULT_IMAGE
{
    TRUE, FALSE
}

public class WebService {

    private static final int TIMEOUT = 10000;

    private Activity context;
    private RequestQueue queue;
    private String domain;
    private static WebService instance = null;

    private WebService(Activity _context)   //class constructor, requires activity context
    {
        context = _context;
        queue = Volley.newRequestQueue(context);
        domain = "https://zoning-partitions.000webhostapp.com";
        //domain = "http://192.168.10.4/mad-proj";
    }

    public void clearQueue()
    {
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                return true;
            }
        });
    }

    public static WebService getInstance(Activity _context)
    {
        if (instance == null)
            instance = new WebService(_context);
        return instance;
    }

    public void initialize_server()   //should be called once in the beginning, initializes the server database if needed
    {
        String url = domain+"/init.php";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Unable to connect",Toast.LENGTH_LONG).show();
            }
        });

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void getAllHostels(final Callback<HostelObjectList> _callback)
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HostelObjectList result = (new Gson()).fromJson(response, HostelObjectList.class);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_all_hostel_data", "");
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void getHostelsByCity(final String _city, final Callback<HostelObjectList> _callback)
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HostelObjectList result = (new Gson()).fromJson(response, HostelObjectList.class);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_city_hostel_data", _city);
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void getUserHostels(final String _userEmail, final Callback<HostelObjectList> _callback)
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                HostelObjectList result = (new Gson()).fromJson(response, HostelObjectList.class);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_user_hostel_data", _userEmail);
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void getHostelReviews(final int _hostelID, final Callback<ReviewObjectList> _callback)
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("hostelRev", response);
                ReviewObjectList result = (new Gson()).fromJson(response, ReviewObjectList.class);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_hostel_reviews", Integer.toString(_hostelID));
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void verifyUser(final String _email, final String _password, final Callback<Boolean> _callback) //returns true/false in the callback function after verifying
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("verify_user_data", "");
                parameter.put("email", _email);
                parameter.put("pwd", _password);
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void getUserData(final String _email, final String _password, final Callback<UserObject> _callback) //returns user data
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserObject result = (new Gson()).fromJson(response, UserObject.class);
                _callback.callbackFunctionSuccess(result);   //if no user was found or incorrect credentials were given then all fields of result will contain 'null'
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_user_data", "");
                parameter.put("email", _email);
                parameter.put("pwd", _password);
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void getUserDataNoCheck(final String _email, final Callback<UserObject> _callback) //returns user data
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                UserObject result = (new Gson()).fromJson(response, UserObject.class);
                _callback.callbackFunctionSuccess(result);   //if no user was found or incorrect credentials were given then all fields of result will contain 'null'
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_user_data_no_check", "");
                parameter.put("email", _email);
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public void addHostel(HostelObject _hostel, final Callback<Integer> _callback)
    {
        String url = domain+"/push_data.php";
        final String hostelObjJson = (new Gson()).toJson(_hostel);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("checkAdd", response);
                int result = Integer.parseInt(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //SharedPreferences spref = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
                //spref.edit().putString("json", hostelObjJson).apply();
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("add_hostel_data", "");
                parameter.put("hostel_data", hostelObjJson);
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void addUser(UserObject _user, final Callback<Boolean> _callback)
    {
        Log.i("myInfo", "addHostel Called");
        String url = domain+"/push_data.php";
        final String userObjJson = (new Gson()).toJson(_user);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("singnup_info2", response);
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //SharedPreferences spref = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
                //spref.edit().putString("json", hostelObjJson).apply();
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("add_user_data", "");
                parameter.put("user_data", userObjJson);
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void updateUserData(UserObject _user, final Callback<Boolean> _callback)
    {
        Log.i("myInfo", "addHostel Called");
        String url = domain+"/push_data.php";
        final String userObjJson = (new Gson()).toJson(_user);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //SharedPreferences spref = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
                //spref.edit().putString("json", hostelObjJson).apply();
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("update_user_data", "");
                parameter.put("user_data", userObjJson);
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void addHostelReview(final String _userEmail, final int _hostelID, final float _rating, final String _comment, final Callback<Boolean> _callback)
    {
        Log.i("myInfo", "addHostel Called");
        String url = domain+"/push_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //SharedPreferences spref = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
                //spref.edit().putString("json", hostelObjJson).apply();
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("add_hostel_review", "");
                parameter.put("userEmail", _userEmail);
                parameter.put("comment", _comment);
                parameter.put("hostelID", Integer.toString(_hostelID));
                parameter.put("rating", Float.toString(_rating));
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void addHostelImage(final int _hostelID, Uri _image, final SET_DEFAULT_IMAGE flag, final Callback<Boolean> _callback)
    {
        Log.i("myInfoImageResult", "addImageCalled");
        String url = domain+"/push_data.php";

        //////////////////// creating different resolution images /////////////////
        InputStream inp, inp1, inp2;                                    //for opening input streams
        ByteArrayOutputStream os;                                       //outputstream to write compressed image to
        final byte[] res1B;                                                   //byte arrays to store the compressed image
        final byte[] res2B;
        try
        {
            inp = context.getContentResolver().openInputStream(_image);
            inp1 = context.getContentResolver().openInputStream(_image);
            inp2 = context.getContentResolver().openInputStream(_image);
        }
        catch(Exception e) {
            Toast.makeText(context, "Image file not found", Toast.LENGTH_LONG).show();
            return;
        }
        BitmapFactory.Options bitOptions = new BitmapFactory.Options();
        bitOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeStream(inp, null, bitOptions);
        int res1 = getSampleSize(bitOptions, 250, 250);
        int res2 = getSampleSize(bitOptions, 500, 500);
        BitmapFactory.Options finalOptions = new BitmapFactory.Options();

                //////// RES1 ///////////////////
        finalOptions.inSampleSize = res1;
        os = new ByteArrayOutputStream();
        BitmapFactory.decodeStream(inp1, null, finalOptions).compress(Bitmap.CompressFormat.JPEG, 50, os);
        res1B = os.toByteArray();

                /////////// RES2 //////////////////
        finalOptions.inSampleSize = res2;
        os = new ByteArrayOutputStream();
        BitmapFactory.decodeStream(inp2, null, finalOptions).compress(Bitmap.CompressFormat.JPEG, 50, os);
        res2B = os.toByteArray();
        ////////////////////////////////////////////////////////////////////////////////////////////

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("myInfoaddres", response);
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("add_hostel_image", Integer.toString(_hostelID));
                parameter.put("image_res1", Base64.encodeToString(res1B, Base64.DEFAULT));
                parameter.put("image_res2", Base64.encodeToString(res2B, Base64.DEFAULT));
                if (flag == SET_DEFAULT_IMAGE.TRUE)
                    parameter.put("set_default", "1");
                else
                    parameter.put("set_default", "0");
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void getHostelProfileImage(final int _hostelID, final int _imageResLevel, final Callback<Bitmap> _callback)
    {
        String url = domain+"/retrieve_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("myInfoImage", response);
                Bitmap result = null;
                if (!response.equals("NULL"))
                {
                    byte[] b = Base64.decode(response, Base64.DEFAULT);
                    result = BitmapFactory.decodeByteArray(b, 0, b.length);
                }
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("get_hostel_image", Integer.toString(_hostelID));
                parameter.put("get_hostel_image_res", Integer.toString(_imageResLevel));
                return parameter;
            }
        };

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        queue.add(stringRequest);
    }

    public static int getSampleSize(BitmapFactory.Options _options, int _reqHeight, int _reqWidth)  //static method for getting info fir image resizing and compression
    {
        // Raw height and width of image
        final int height = _options.outHeight;
        final int width = _options.outWidth;
        int inSampleSize = 1;

        if (height > _reqHeight || width > _reqWidth) {

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((height / inSampleSize) > _reqHeight
                    || (width / inSampleSize) > _reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public void updateHostel(final int _hostelID, HostelObject _hostel, final Callback<Boolean> _callback)
    {
        Log.i("myInfo", "addHostel Called");
        String url = domain+"/push_data.php";
        final String hostelObjJson = (new Gson()).toJson(_hostel);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //SharedPreferences spref = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
                //spref.edit().putString("json", hostelObjJson).apply();
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("update_hostel_data", Integer.toString(_hostelID));
                parameter.put("hostel_data", hostelObjJson);
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    public void deleteHostel(final int _hostelID, final Callback<Boolean> _callback)
    {
        Log.i("myInfo", "addHostel Called");
        String url = domain+"/push_data.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("myInfodel", response);
                boolean result = Boolean.parseBoolean(response);
                _callback.callbackFunctionSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                _callback.callbackFunctionFailure();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //SharedPreferences spref = context.getSharedPreferences("test", Activity.MODE_PRIVATE);
                //spref.edit().putString("json", hostelObjJson).apply();
                Map<String, String> parameter = new HashMap<String, String>();
                parameter.put("delete_hostel_data", Integer.toString(_hostelID));
                return parameter;
            }
        };
        stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT,
                0,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(stringRequest);
    }

    // this is the Callback interface (abstract class) which is required by certain methods from the WebService class
    // you will override the callbackFunction which tells the WebService class what to do after the data is finally
    // fetched from the server. It takes as input the object containing retrieved data which will be different for each method

    interface Callback<T>
    {
        void callbackFunctionSuccess(T result);
        void callbackFunctionFailure();
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // below are different types of objects that the WebService will return/take as input

    static class HostelObject   //Object containing the hostel data
    {
        public String hostelName;
        public String hostelAddress;
        public String hostelCity;
        public String hostelExtras;
        public float rating;
        public int no_rooms;
        public int no_floors;
        public String owner_email;
        public int hostel_id;
        public int hostel_img;

        HostelObject(String _name, String _address, String _city, String _extras, int _rooms, int _floors, String _owner, float _rating) {
            hostelName = _name;
            hostelAddress = _address;
            hostelCity = _city;
            hostelExtras = _extras;
            no_rooms = _rooms;
            no_floors = _floors;
            owner_email = _owner;
            rating = _rating;
            hostel_id = -1;
            hostel_img = -1;
        }
    }

    static class HostelObjectList  //Object containing the data of multiple hostels (this one is used if multiple hostels are returned from the webservice)
    {
        public List<HostelObject> hostelsStored;

        HostelObjectList()
        {
            hostelsStored = new ArrayList<>();
        }
    }

    static class UserObject   //object to store the user data
    {
        public String userName;
        public String email;
        public String password;
        public String phoneNumber;
        public Integer accountType; // 0 for customer 1 for admin

        public UserObject(String _userName, String _email, String _password, int _accountType, String _phone) {
            userName = _userName;
            email = _email;
            password = _password;
            accountType = _accountType;
            phoneNumber = _phone;
        }
    }

    static class ReviewObject   //object to store a user review
    {
        public String userName;
        public String userEmail;
        public String comment;
        public float rating;
        int hostel_id;

        public ReviewObject(String _userEmail, String _userName, float _rating, int _hostelID, String _comment) {
            userName = _userName;
            rating = _rating;
            hostel_id = _hostelID;
            userEmail = _userEmail;
            comment = _comment;
        }
    }

    static class ReviewObjectList  //Object containing the data of multiple reviews
    {
        public List<ReviewObject> reviewsStored;

        ReviewObjectList()
        {
            reviewsStored = new ArrayList<>();
        }
    }
}
