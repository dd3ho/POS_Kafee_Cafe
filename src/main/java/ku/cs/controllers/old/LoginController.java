package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.old.Employee;
import ku.cs.models.old.EmployeeList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Employee_DBConnect;

import java.io.IOException;
import java.sql.*;

public class LoginController {


    @FXML
    private TextField usernameTextField;

    @FXML
    private PasswordField passwordField;


    //เป็น account ที่ไว้ใช้ login
    public Employee empLoginAccount;

    //emp database
    public Employee employeeDB = new Employee("0","0","0","0");


    //database connect
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    @FXML
    void handleLoginButton(ActionEvent event) {

        String emp_IdLoginStr = usernameTextField.getText();
        String emp_passwordStr = passwordField.getText();

        employeeDB.setEmp_id(emp_IdLoginStr);
        employeeDB.setEmp_password(emp_passwordStr);

//        System.out.println("1 : " +employeeDB.toCsv());

        // ใช้ Db
        String query = "SELECT * FROM employee  WHERE Emp_id = '"+emp_IdLoginStr+"'  AND  Emp_password = '"+emp_passwordStr+"'";
        Database<Employee, EmployeeList> database = new Employee_DBConnect();
        empLoginAccount = database.readRecord(query);

//        System.out.println("2 : " + employeeDB.toCsv());
//        System.out.println("3 : " + empLoginAccount.toCsv());

        //for test login ห้ามเอา comment ออกมันบัค ยกเว้นใส่ข้อมูล login ถูก
//        System.out.println("Employee Who login is "+empLoginAccount.getEmp_id()+":"+ empLoginAccount.getEmp_name());

        if(empLoginAccount == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ตรวจสอบชื่อผู้ใช้งานกับรหัสผ่านอีกครั้งเนื่องจากข้อมูลไม่ถูกต้อง");
            alert.showAndWait();
        }else{
            if(empLoginAccount.getEmp_jTitle().equals("1")){
                try {
                    FXRouter.goTo("emp_home", empLoginAccount);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า emp_home ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }else {
                if (empLoginAccount.getEmp_jTitle().equals("2")){
                    try {
                        FXRouter.goTo("manager_home", empLoginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า manager_home ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }else {
                    try {
                        FXRouter.goTo("creditboard_home", empLoginAccount);
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า creditboard_home ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }
                }
                }
            }
        }
    }



