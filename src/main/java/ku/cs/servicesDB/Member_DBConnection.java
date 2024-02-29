package ku.cs.servicesDB;

import ku.cs.models.*;

import java.sql.*;

public class Member_DBConnection implements Database<Member, MemberList>{
    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return Customer method readData
    private Member memberRecord;
    @Override
    public void insertDatabase(Member member) {
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
            String query1 = "INSERT INTO member " + "VALUES ('" + member.getM_Id() + "','" + member.getM_firstname() + "','" + member.getM_lastname() + "','" + member.getM_tel() + "','" + member.getM_points() + "','" + member.getM_img() + "','" + member.getM_date_join() + "','" + member.getM_lasted()  + "')";
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
    public Member readRecord(String query) {
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
                String m_Id = rs.getString(1);
                String m_firstname = rs.getNString(2);
                String m_lastname = rs.getString(3);
                String m_tel = rs.getString(4);
                int m_points = Integer.valueOf(rs.getNString(5));
                String m_date_join = rs.getNString(6);
                String m_lasted = rs.getNString(7);


                this.memberRecord = new Member(m_Id, m_firstname, m_lastname, m_tel, m_points, m_date_join, m_lasted);
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

        return memberRecord;
    }

    @Override
    public MemberList readDatabase(String q) {
        MemberList list = new MemberList();

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
                String m_Id = rs.getString(1);
                String m_firstname = rs.getNString(2);
                String m_lastname = rs.getString(3);
                String m_tel = rs.getString(4);
                int m_points = Integer.valueOf(rs.getString(5));
                String m_date_join = rs.getNString(6);
                String m_lasted = rs.getString(7);
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
