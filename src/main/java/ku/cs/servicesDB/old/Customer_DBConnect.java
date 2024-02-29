package ku.cs.servicesDB.old;

import ku.cs.models.old.Customer;
import ku.cs.models.old.CustomerList;
import ku.cs.servicesDB.Database;

import java.sql.*;

public class Customer_DBConnect implements Database<Customer, CustomerList> {


    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Customer method readData
    private Customer customerReadDatabase;


    //ใช้หน้า emp_regis
    @Override
    public void insertDatabase(Customer customer) {
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
            String query1 = "INSERT INTO customer " + "VALUES ('" + customer.getCtm_Id() + "','" + customer.getCtm_cid() + "','" + customer.getCtm_firstname() + "','" + customer.getCtm_lastname() + "','" + customer.getCtm_img() + "','" + customer.getCtm_sex() + "','" + customer.getCtm_tel() + "' ,'" + customer.getCtm_address() + "','" + customer.getCtm_workplace() + "','" + customer.getCtm_bankAccount() + "')";
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
//
//    @Override
//    public void insertDatabase(User user) {
//
//    }


    @Override
    public Customer readRecord(String query) {
        //prepare data
        String id ;
        String cid;
        String firstname ;
        String lastname ;
        String img ;
        String sex ;
        String tel ;
        String address ;
        String workplace ;
        String bankAcc ;

        //DB connect
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test_loansystem", "root", "");
            System.out.println("Connection is created successfully:");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id = rs.getString(1);
                cid = rs.getString(2);
                firstname = rs.getString(3);
                lastname = rs.getString(4);
                img = rs.getString(5);
                sex = rs.getString(6);
                tel = rs.getString(7);
                address = rs.getString(8);
                workplace = rs.getString(9);
                bankAcc = rs.getString(10);

                this.customerReadDatabase = new Customer(id, cid, firstname, lastname, img, sex, tel, address, workplace, bankAcc);
//                System.out.println(empLoginAccount.toCsv());
            }
            System.out.println("Account can use from jdbc");
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

        return customerReadDatabase;
    }


    //return list
    @Override
    public CustomerList readDatabase(String query) {

        CustomerList list = new CustomerList();

        //DB connect
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test_loansystem", "root", "");
            System.out.println("Connection is created successfully:");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                String id = rs.getString(1);
                String cid = rs.getString(2);
                String firstname = rs.getString(3);
                String lastname = rs.getString(4);
                String img = rs.getString(5);
                String sex = rs.getString(6);
                String tel = rs.getString(7);
                String address = rs.getString(8);
                String workplace = rs.getString(9);
                String bankAcc = rs.getString(10);

                this.customerReadDatabase = new Customer(id, cid, firstname, lastname, img, sex, tel, address, workplace, bankAcc);
                list.addCustomer(customerReadDatabase);
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



