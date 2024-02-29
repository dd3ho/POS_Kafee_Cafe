package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.User_DBConnect;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PosLoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    UserList userList;

    public User usrLoginAccount;

    public User UserDB = new User("0","0","0","0","0");

    Database<User, UserList> database;

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    @FXML
    public void initialize() {
        clearTextField();
        database = new User_DBConnect();
        userList = new UserList();
        String query = "SELECT * FROM user";
        userList = database.readDatabase(query);
    }

    @FXML
    private void handleLoginButton(ActionEvent event) throws IOException {
        String usr_IdLoginStr = usernameField.getText();
        String usr_passwordStr = passwordField.getText();

        UserDB.setLoginU_Id(usr_IdLoginStr);
        UserDB.setU_password(usr_passwordStr);

        String query = "SELECT * FROM user  WHERE u_id = '"+usr_IdLoginStr+"'  AND  u_password = '"+usr_passwordStr+"'";
        Database<User, UserList> database = new User_DBConnect();
        usrLoginAccount = database.readRecord(query);

        if(usrLoginAccount == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ตรวจสอบชื่อผู้ใช้งานกับรหัสผ่านอีกครั้งเนื่องจากข้อมูลไม่ถูกต้อง");
            alert.showAndWait();
        }else{
            FXRouter.goTo("pos_signup", usrLoginAccount);
        }
    }


    private void clearTextField() {
        usernameField.setText("");
        passwordField.setText("");

    }
}
