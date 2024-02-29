package ku.cs.servicesDB.old;

import ku.cs.models.old.Receipt;
import ku.cs.models.old.ReceiptList;
import ku.cs.servicesDB.Database;

import java.sql.*;

public class Receipt_DBConnect implements Database<Receipt, ReceiptList> {

    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Receipt  from method readData
    private Receipt receiptRecord;



    //ใส่ object --> insert ข้อมูลใน table
    @Override
    public void insertDatabase(Receipt receipt) {
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
            String query1 = "INSERT INTO receipt   VALUES ('" +receipt.getRec_id() + "','" + receipt.getRec_customerId() + "','" + receipt.getRec_date() + "', '"+receipt.getRec_month()+"', '"+receipt.getRec_year()+"', '" + receipt.getRec_payAmt() + "','" + receipt.getRec_balanceLoan() + "', '"+receipt.getRec_invoiceId()+"') ";
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
    public Receipt readRecord(String query) {
        //prepare data
        String id ;
        String customerId ;
        String date;
        String month;
        String year;
        int payamy ;
        int balanceLoan ;
        String invoiceId ;

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
                date = rs.getString(3);
                month = rs.getString(4);
                year = rs.getString(5);
                payamy = Integer.parseInt(rs.getString(6));
                balanceLoan = Integer.parseInt( rs.getString(7));
                invoiceId = rs.getString(8);


                this.receiptRecord = new Receipt(id, customerId, date, month, year, payamy, balanceLoan, invoiceId);

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


        return receiptRecord;
    }

    //ใส่ query return เป็น list
    @Override
    public ReceiptList readDatabase(String q) {

        ReceiptList list = new ReceiptList();

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
                String customerId = rs.getString(2);
                String date = rs.getString(3);
                String month = rs.getString(4);
                String year = rs.getString(5);
                int payamy = Integer.parseInt(rs.getString(6));
                int balanceLoan = Integer.parseInt( rs.getString(7));
                String invoiceId = rs.getString(8);

                this.receiptRecord = new Receipt(id, customerId, date, month, year, payamy, balanceLoan, invoiceId);
                list.addReceipt(receiptRecord);
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

    }
}
