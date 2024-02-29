package ku.cs.models;

public class User {


    private String u_Id;

    private String u_password;

    private String u_firstname;

    private String u_lastname;

    private String u_role;

    public User(String u_password, String u_firstname, String u_lastname, String u_role) {
        this.u_password = u_password;
        this.u_firstname = u_firstname;
        this.u_lastname = u_lastname;
        this.u_role = u_role;
    }

    public User(String u_Id, String u_password, String u_firstname, String u_lastname, String u_role) {
        this.u_Id = u_Id;
        this.u_password = u_password;
        this.u_firstname = u_firstname;
        this.u_lastname = u_lastname;
        this.u_role = u_role;
    }


    //setter
    public void setU_id(UserList users) {
        this.u_Id = "u" + String.format("%04d", users.countUsers()+1);
    }

    public void setLoginU_Id(String u_Id) {
        this.u_Id = u_Id;
    }


    // Java Password or String Encryption and Decryption lather
    public void setU_password(String u_password) {
        this.u_password = u_password;
    }

    public void setU_firstname(String u_firstname) {
        this.u_firstname = u_firstname;
    }

    public void setU_lastname(String u_lastname) {
        this.u_lastname = u_lastname;
    }

    public void setUStaff_role(String u_role) {
        this.u_role = "staff";
    }
    public void setUAdmin_role(String u_role) {
        this.u_role = "admin";
    }


    //getter
    public String getU_id() {
        return u_Id;
    }


    public String getU_password() {
        return u_password;
    }

    public String getU_firstname() {
        return u_firstname;
    }

    public String getU_lastname() {
        return u_lastname;
    }

    public String getU_role() {
        return u_role;
    }

    @Override
    public String toString() {
        return u_Id + "," + u_password + "," + u_firstname + "," + u_lastname + "," +
                u_role;
    }

    public String toCsv() {
        return u_Id + "," + u_password + "," + u_firstname + "," + u_lastname + "," +
                u_role;
    }
}
