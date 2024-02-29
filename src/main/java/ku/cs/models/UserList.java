package ku.cs.models;

import java.util.ArrayList;

public class UserList {

    private ArrayList<User> users;

    public UserList(){
        users = new ArrayList<>();
    }

    //เพิ่ม Member
    public void addUser(User user){
        users.add(user);
    }

    public int countUsers(){
        return users.size();
    }
    public String toCsv() {
        String result = "";
        for (User user : this.users){
            result += user.toCsv() + "\n";
        }
        return result;
    }
}
