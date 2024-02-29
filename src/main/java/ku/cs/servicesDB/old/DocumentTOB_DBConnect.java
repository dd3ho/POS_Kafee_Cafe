package ku.cs.servicesDB.old;

import ku.cs.models.old.DocumentTOB;
import ku.cs.models.old.DocumentTOBList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.DatabaseConnection;

import java.sql.*;

public class DocumentTOB_DBConnect implements Database<DocumentTOB, DocumentTOBList> {

    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    private DatabaseConnection databaseConnection;

    //prepare for return Customer method readData
    private DocumentTOB documentReadDb;

    @Override
    public void insertDatabase(DocumentTOB document) {
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
            String query1 = "INSERT INTO documenttransactionofborrow " + "VALUES ('" + document.getDtb_id() + "','" + document.getDtb_customerId() + "','" + document.getDtb_d1()+ "','" + document.getDtb_d2() + "','" + document.getDtb_d3() + "','" + document.getDtb_d4() + "','" + document.getDtb_date()+ "' ,'" + document.getDtb_status() + "' )";
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
    public DocumentTOB readRecord(String query) {
        //prepare data
        String id ;
        String ctm_id ;
        String d1;
        String d2 ;
        String d3 ;
        String d4 ;
        String date ;
        String status;

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
                ctm_id = rs.getString(2);
                d1 = rs.getString(3);
                d2 = rs.getString(4);
                d3 = rs.getString(5);
                d4 = rs.getString(6);
                date = rs.getString(7);
                status = rs.getString(8);

                this.documentReadDb = new DocumentTOB(id, ctm_id, d1, d2, d3, d4, date, status);
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

        return documentReadDb;
    }


    //return list
    @Override
    public DocumentTOBList readDatabase(String q) {
        DocumentTOBList list = new DocumentTOBList();

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
                String ctm_id = rs.getString(2);
                String d1 = rs.getString(3);
                String d2 = rs.getString(4);
                String d3 = rs.getString(5);
                String d4 = rs.getString(6);
                String date = rs.getString(7);
                String status = rs.getString(8);

                this.documentReadDb = new DocumentTOB(id, ctm_id, d1, d2, d3, d4, date, status);
                list.addDocumentTrans(documentReadDb);
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




