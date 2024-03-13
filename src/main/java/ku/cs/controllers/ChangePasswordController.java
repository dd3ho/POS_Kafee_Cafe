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

public class ChangePasswordController {
    @FXML
    private TextField oldPasswordField;

    @FXML
    private PasswordField newPasswordField;

    @FXML
    private PasswordField confirmNewPasswordField;

    UserList userList;

    User oldUser;

    Database<User, UserList> database;
    @FXML
    public void initialize() {
        clearTextField();
        database = new User_DBConnect();
        oldUser = (User) FXRouter.getData();
        System.out.println(oldUser.getU_id());
    }

    @FXML
    private void handleConfirmButton(ActionEvent event) throws IOException {
        String updateQuery = "UPDATE user SET u_password = '"+ newPasswordField.getText() +"' WHERE u_id = '" + oldUser.getU_id() + "'";
        database.updateDatabase(updateQuery);
        System.out.println(updateQuery);
    }

    private void clearTextField() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmNewPasswordField.setText("");
    }
}
