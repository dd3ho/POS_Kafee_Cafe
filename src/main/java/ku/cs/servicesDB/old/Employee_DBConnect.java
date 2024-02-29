package ku.cs.servicesDB.old;

import ku.cs.models.old.Employee;
import ku.cs.models.old.EmployeeList;
import ku.cs.servicesDB.Database;

import java.sql.*;

public class Employee_DBConnect implements Database<Employee, EmployeeList> {

    //database connect
    public Connection conn = null;
    public Statement stmt = null;
    public ResultSet rs = null;

    //prepare for return readData
    private Employee employeeReadData;

    @Override
    public void insertDatabase(Employee employee) {
    }




    @Override
    public Employee readRecord(String query) {
        //prepare data
        String empId;
        String empName;
        String empJTitle;
        String empPassword;

        //DB connect
        try {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
            } catch (Exception e) {
                System.out.println(e);
            }
            conn = (Connection) DriverManager.getConnection("jdbc:mysql://localhost:3306/test_loansystem", "root", "");
            System.out.println("Connection is created successfully:");

            stmt = conn.createStatement();
            rs = stmt.executeQuery(query);

            while (rs.next()){
                empId = rs.getString(1);
                empName = rs.getNString(2);
                empJTitle = rs.getString(3);
                empPassword = rs.getString(4);


                this.employeeReadData = new Employee (empId, empName, empJTitle, empPassword);
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

        return employeeReadData;
    }


    @Override
    public EmployeeList readDatabase(String q) {
        return null;
    }

    @Override
    public void updateDatabase(String q) {

    }


}
