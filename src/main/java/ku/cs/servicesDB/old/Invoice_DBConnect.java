package ku.cs.servicesDB.old;

import ku.cs.models.old.Invoice;
import ku.cs.models.old.InvoiceList;
import ku.cs.servicesDB.Database;

import java.sql.*;

public class Invoice_DBConnect implements Database<Invoice, InvoiceList> {

    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Receipt  from method readData
    private Invoice invoiceRecord;

    //ใส่ object --> insert ข้อมูลใน table
    @Override
    public void insertDatabase(Invoice invoice) {

        //database connect
        Connection conn = null;
        Statement stmt = null;
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test_loansystem", "root", "");
            System.out.println("Connection is created successfully:");
            stmt = (Statement) conn.createStatement();

            String query1 = "INSERT INTO invoice " + "VALUES ('" +invoice.getInvoice_id()+ "','" + invoice.getInvoice_customerId() + "'" +
                    ",'" + invoice.getInvoice_ctmfirstname() + "','" + invoice.getInvoice_ctmlastname() + "','" + invoice.getInvoice_ctmbankAccount() + "'" +
                    ",'" + invoice.getInvoice_ctmDebt() + "','" + invoice.getInvoice_date() + " ', '"+invoice.getInvoice_month()+"', '"+invoice.getInvoice_year()+"', '" +invoice.getInvoice_status()+ "')";

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
    public Invoice readRecord(String query) {
        //prepare data
        String id ;
        String customerId ;
        String fname;
        String lname;
        String bankAcc ;
        int debt ;
        String date;
        String month;
        String year;
        String status ;

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
                customerId = rs.getString(2);
                fname = rs.getString(3);
                lname = rs.getString(4);
                bankAcc = rs.getString(5);
                debt = Integer.parseInt(rs.getString(6));
                date = rs.getString(7);
                month = rs.getString(8);
                year = rs.getString(9);
                status = rs.getString(10);

                this.invoiceRecord = new Invoice(id, customerId, fname, lname, bankAcc, debt, date, month, year, status);

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


        return invoiceRecord;
    }

    //ใส่ query return เป็น list
    @Override
    public InvoiceList readDatabase(String q) {

        InvoiceList list = new InvoiceList();

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
            rs = stmt.executeQuery(q);

            while (rs.next()) {
                String id = rs.getString(1);
                String customerId = rs.getNString(2);
                String fname = rs.getString(3);
                String lname = rs.getString(4);
                String bankAcc = rs.getString(5);
                int debt = Integer.parseInt(rs.getString(6));
                String date = rs.getNString(7);
                String month = rs.getString(8);
                String year = rs.getString(9);
                String status = rs.getString(10);

                this.invoiceRecord = new Invoice(id, customerId, fname, lname, bankAcc, debt, date, month, year,status);
                list.addInvoice(invoiceRecord);
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

    //ใส่ query --> update table
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
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/test_loansystem", "root", "");
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

