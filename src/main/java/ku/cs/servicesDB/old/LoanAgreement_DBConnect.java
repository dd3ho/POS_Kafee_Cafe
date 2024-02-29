package ku.cs.servicesDB.old;

import ku.cs.models.old.LoanAgreement;
import ku.cs.models.old.LoanAgreementList;
import ku.cs.servicesDB.Database;

import java.sql.*;

public class LoanAgreement_DBConnect implements Database<LoanAgreement, LoanAgreementList> {

    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Receipt  from method readData
    private LoanAgreement loanAgreementRecord;


    @Override
    public void insertDatabase(LoanAgreement loanAgreement) {

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

            String query1 = "INSERT INTO loanagreement " + "VALUES ('" +loanAgreement.getLoan_id()+ "','" + loanAgreement.getLoan_customerId() + "'" +
                    ",'" + loanAgreement.getLoan_firstname() + "','" + loanAgreement.getLoan_lastname() + "','" + loanAgreement.getLoan_type() + "'" +
                    ",'" + loanAgreement.getLoan_term() + "','" + loanAgreement.getLoan_date() + " ', '" +loanAgreement.getLoan_balance()+ "', " +
                    "'"+loanAgreement.getLoan_amount()+"', '"+loanAgreement.getLoan_witness1()+"', '"+loanAgreement.getLoan_witness2()+"', " +
                    "'"+loanAgreement.getLoan_Emp1()+"', '"+loanAgreement.getLoan_Emp2()+"')";

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
    public LoanAgreement readRecord(String query) {
        //prepare data
        String id;
        String customerId;
        String fname;
        String lname;
        String type ;
        int term ;
        String date;
        int balance;
        int amount;
        String witness1 ;
        String witness2 ;
        String emp1 ;
        String emp2 ;

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
                type = rs.getString(5);
                term = Integer.parseInt(rs.getString(6));
                date = rs.getString(7);
                balance = Integer.parseInt(rs.getString(8));
                amount = Integer.parseInt(rs.getString(9));
                witness1 = rs.getString(10);
                witness2 = rs.getString(11);
                emp1 = rs.getString(12);
                emp2 = rs.getString(13);

                this.loanAgreementRecord = new LoanAgreement(id, customerId, fname, lname, type,term, date, balance, amount,
                        witness1, witness2, emp1, emp2);

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
        return loanAgreementRecord;
    }

    //ใส่ query return เป็น list
    @Override
    public LoanAgreementList readDatabase(String q) {
        LoanAgreementList list = new LoanAgreementList();

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
                String type = rs.getString(5);
                int term = Integer.parseInt(rs.getString(6));
                String date = rs.getNString(7);
                int balance = Integer.parseInt(rs.getString(8));
                int amount = Integer.parseInt(rs.getString(9));
                String witness1 = rs.getString(10);
                String witness2 = rs.getString(11);
                String emp1 = rs.getString(12);
                String emp2 = rs.getString(13);

                this.loanAgreementRecord = new LoanAgreement(id, customerId, fname, lname, type,term, date, balance, amount,
                        witness1, witness2, emp1, emp2);

                list.addLoan(loanAgreementRecord);
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


