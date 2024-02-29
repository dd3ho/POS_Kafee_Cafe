package ku.cs.servicesDB;

import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.models.User;
import ku.cs.models.UserList;

import java.sql.*;

public class Menu_DBConnection implements Database<Menu, MenuList> {
    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Menu method readData
    private Menu menuRecord;

    @Override
    public void insertDatabase(Menu menu) {
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
            String query1 = "INSERT INTO menu " + "VALUES ('" + menu.getMn_Id() + "','" + menu.getMn_name() + "','" + menu.getMn_price() + "','" + menu.getMn_img() + "','" + menu.getMn_status() + "','" + menu.getMn_option() + "','" + menu.getM_type()  + "')";
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
    public Menu readRecord(String query) {

        //prepare Data
        String mn_Id;
        String mn_name;
        Float mn_price;
        String mn_img;
        String mn_status;
        String mn_option;
        String m_type;

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
                mn_Id = rs.getString(1);
                mn_name = rs.getNString(2);
                mn_price = Float.valueOf(rs.getString(3));
                mn_img = rs.getString(4);
                mn_status = rs.getNString(5);
                mn_option = rs.getNString(6);
                m_type = rs.getNString(7);


                this.menuRecord = new Menu(mn_Id, mn_name, mn_price, mn_img, mn_status, mn_option, m_type);
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

        return menuRecord;
    }

    @Override
    public MenuList readDatabase(String query) {
        //prepare Data
        String mn_Id;
        String mn_name;
        Float mn_price;
        String mn_img;
        String mn_status;
        String mn_option;
        String m_type;
        MenuList list = new MenuList();

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
                mn_Id = rs.getString(1);
                mn_name = rs.getNString(2);
                mn_price = Float.valueOf(rs.getString(3));
                mn_img = rs.getString(4);
                mn_status = rs.getNString(5);
                mn_option = rs.getNString(6);
                m_type = rs.getNString(7);

                this.menuRecord = new Menu(mn_Id, mn_name, mn_price, mn_img, mn_status, mn_option, m_type);
                list.addMenu(menuRecord);
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
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/pm_project", "root", "");
            System.out.println("Connection is created successfully:");
            stmt = (Statement) conn.createStatement();
            String query1 = q;
            stmt.executeUpdate(query1);
            System.out.println("Record has been updated in the table successfully..................");
        } catch (SQLException excep) {
            excep.printStackTrace();
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
        System.out.println("Please check it in the MySQL Table. Record is now updated.......");
    }
}
