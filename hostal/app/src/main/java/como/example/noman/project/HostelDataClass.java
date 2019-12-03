package como.example.noman.project;

public class HostelDataClass {
    public String hostelName;
    public String hostelAddress;
    public String hostelCity;
    public String hostelExtras;
    public String rating;
    public int no_rooms;
    public int no_floors;
    public int image_source;
    public String owner_email;
    public int hostel_id;

    HostelDataClass(String _name, String _address, String _city, String _extras, int _rooms, int _floors, String _owner, int _image, String _rating, int _id)
    {
        hostelName = _name;
        hostelAddress = _address;
        hostelCity = _city;
        hostelExtras = _extras;
        no_rooms = _rooms;
        no_floors = _floors;
        owner_email = _owner;
        image_source = _image;
        rating = _rating;
        hostel_id = _id;
    }

}
