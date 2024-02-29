package ku.cs.servicesDB;

import ku.cs.models.Receipt;
import ku.cs.models.ReceiptList;


import java.sql.*;


public class Receipt_DBConnection implements Database<Receipt, ReceiptList>{
    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Customer method readData
    private Receipt receiptRecord;
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
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/pm_project", "root", "");
            System.out.println("Connection is created successfully:");
            stmt = (Statement) conn.createStatement();
            String query1 = "INSERT INTO receipt " + "VALUES ('" + receipt.getR_id() + "','" + receipt.getR_promotionId() + "','" + receipt.getR_discount() + "','" + receipt.getR_totalPrice() + "','" + receipt.getR_payType() + "','" + receipt.getR_memberId()  + "')";
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
                String r_id = rs.getString(1);
                String r_promotionId = rs.getNString(2);
                float r_discount = Float.parseFloat(rs.getString(3));
                float r_totalPrice = Float.parseFloat(rs.getString(4));
                String r_payType = rs.getNString(5);
                String r_memberId = rs.getNString(6);


                this.receiptRecord = new Receipt(r_id, r_promotionId, r_discount, r_totalPrice, r_payType, r_memberId);
//                System.out.println(empLoginAccount.toCsv());
            }
            System.out.println("receiptRecord can use from jdbc");
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

        return receiptRecord;
    }

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
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/pm_project", "root", "");
            System.out.println("Connection is created successfully:");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(q);

            while (rs.next()) {
                String r_id = rs.getString(1);
                String r_promotionId = rs.getNString(2);
                float r_discount = Float.parseFloat(rs.getString(3));
                float r_totalPrice = Float.parseFloat(rs.getString(4));
                String r_payType = rs.getNString(5);
                String r_memberId = rs.getNString(6);

                this.receiptRecord = new Receipt(r_id, r_promotionId, r_discount, r_totalPrice, r_payType, r_memberId);;
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
