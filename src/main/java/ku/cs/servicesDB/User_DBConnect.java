package ku.cs.servicesDB;

import ku.cs.models.User;
import ku.cs.models.UserList;

import java.sql.*;

public class User_DBConnect implements Database<User, UserList>{
    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Customer method readData
    private User userRecord;

    @Override
    public void insertDatabase(User user) {
        //database connect
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/pm_project", "root", "");
            System.out.println("Connection is created successfully:");
            stmt = (Statement) conn.createStatement();
            String query1 = "INSERT INTO user " + "VALUES ('" + user.getU_id() + "','" + user.getU_password() + "','" + user.getU_firstname() + "','" + user.getU_lastname() + "','" + user.getU_role()  + "')";
            stmt.executeUpdate(query1);
            System.out.println("Record is inserted in the table successfully..................");
        } catch (Exception excep) {
            excep.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Please check it in the MySQL Table......... ……..");
    }

    @Override
    public User readRecord(String query) {

        //prepare Data
        String u_id;
        String u_password;
        String u_firstname;
        String u_lastname;
        String u_role;

        //DB connect
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/pm_project", "root", "");
            System.out.println("Connection is created successfully:");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                u_id = rs.getString(1);
                u_password = rs.getNString(2);
                u_firstname = rs.getString(3);
                u_lastname = rs.getString(4);
                u_role = rs.getNString(5);


                this.userRecord = new User(u_id, u_password, u_firstname, u_lastname, u_role);
//                System.out.println(empLoginAccount.toCsv());
            }
            System.out.println("loginAccount can use from jdbc");
        } catch (Exception excep) {
            excep.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {}
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Please check it in the MySQL Table......... ……..");

        return userRecord;
    }

    @Override
    public UserList readDatabase(String query) {
        UserList list = new UserList();

        //DB connect
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/pm_project", "root", "");
            System.out.println("Connection is created successfully:");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String u_Id = rs.getString(1);
                String u_password = rs.getString(2);
                String u_firstname = rs.getString(3);
                String u_lastname = rs.getString(4);
                String u_role = rs.getString(5);

                this.userRecord = new User(u_Id, u_password, u_firstname, u_lastname, u_role);
                list.addUser(userRecord);
//                System.out.println(empLoginAccount.toCsv());
            }
            System.out.println("list can use from jdbc");
        } catch (Exception excep) {
            excep.printStackTrace();
        } finally {
            try {
                if (stmt != null)
                    conn.close();
            } catch (SQLException se) {
            }
            try {
                if (conn != null)
                    conn.close();
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        System.out.println("Please check it in the MySQL Table......... ……..");

        return list;
    }

    @Override
    public void updateDatabase(String q) {
    }
}
