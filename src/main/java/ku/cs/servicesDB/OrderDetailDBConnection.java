package ku.cs.servicesDB;

import ku.cs.models.OrderDetail;
import ku.cs.models.OrderDetailList;

import java.sql.*;


public class OrderDetailDBConnection implements Database<OrderDetail, OrderDetailList>{
    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Customer method readData
    private OrderDetail orderRecord;

    @Override
    public void insertDatabase(OrderDetail order_detail) {
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
            String query1 = "INSERT INTO order_deatail " + "VALUES ('" + order_detail.getO_Id() + "','" + order_detail.getO_receiptId() + "','" + order_detail.getO_mnId() + "','" + order_detail.getO_amount() + "','" + order_detail.getO_price() + "')";
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
    public OrderDetail readRecord(String query) {

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
                String o_Id = rs.getString(1);
                String o_receiptId = rs.getNString(2);
                String o_mnId = rs.getString(3);
                int o_amount = Integer.parseInt(rs.getString(4));
                float o_price = Float.parseFloat(rs.getNString(5));


                this.orderRecord = new OrderDetail(o_Id, o_receiptId, o_mnId, o_amount, o_price);
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

        return orderRecord;
    }

    @Override
    public OrderDetailList readDatabase(String q) {
        OrderDetailList list = new OrderDetailList();

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
                String o_Id = rs.getString(1);
                String o_receiptId = rs.getNString(2);
                String o_mnId = rs.getString(3);
                int o_amount = Integer.parseInt(rs.getString(4));
                float o_price = Float.parseFloat(rs.getNString(5));


                this.orderRecord = new OrderDetail(o_Id, o_receiptId, o_mnId, o_amount, o_price);
                list.addOrder(orderRecord);
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
