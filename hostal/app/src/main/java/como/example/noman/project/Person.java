package como.example.noman.project;

public class Person {
    String UserName;
    String Email;
    String Password;
    String Cnic;
    String PhoneNo;
    int AccountTypeID;

    public Person(int id, String userName, String email, String password, String cnic, String phn) {
        AccountTypeID = id;
        UserName = userName;
        Email = email;
        Password = password;
        Cnic = cnic;
        PhoneNo = phn;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}


