package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.User_DBConnect;

import java.io.IOException;

public class PosSignupController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField confirmPasswordField;

    UserList userList;

    Database<User, UserList> database;

    @FXML
    public void initialize() {
        clearTextField();
        database = new User_DBConnect();
        userList = new UserList();
        String query = "SELECT * FROM user";
        userList = database.readDatabase(query);
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) throws IOException {

        // todo: Register
        String firstNameStr = firstNameField.getText();
        String lastNameStr = lastNameField.getText();
        String passwordStr = passwordField.getText();
        String confirmPasswordStr = confirmPasswordField.getText();

        if (firstNameStr.equals("") || lastNameStr.equals("") || passwordStr.equals("") || confirmPasswordStr.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");

            alert.showAndWait();

        } else if (!passwordStr.equals(confirmPasswordStr)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("รหัสผ่านไม่ตรงกัน");

            alert.showAndWait();
        } else {
            String id = String.format("%04d", userList.countUsers()+1);
            User user = new User(passwordField.getText(),firstNameField.getText(), lastNameField.getText(), "staff");
            user.setU_id(userList);
            database.insertDatabase(user);
            System.out.println("Register successfully:");
        }

    }

    private void clearTextField() {
        firstNameField.setText("");
        lastNameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
    }
}
