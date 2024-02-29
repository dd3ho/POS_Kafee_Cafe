package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ku.cs.models.Member;
import ku.cs.models.MemberList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.Member_DBConnection;
import ku.cs.servicesDB.User_DBConnect;

import java.io.IOException;

public class AddMemberController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    MemberList memberList;

    Member member = new Member("","","","",0,"","");

    Database<Member, MemberList> database;

    @FXML
    public void initialize() {
        clearTextField();
        database = new Member_DBConnection();
        memberList = new MemberList();
        String query = "SELECT * FROM member";
        memberList = database.readDatabase(query);
    }

    @FXML
    private void handleSignUpButton(ActionEvent event) throws IOException {
        String firstNameStr = firstNameField.getText();
        String lastNameStr = lastNameField.getText();
        String phoneStr = phoneField.getText();

        if (firstNameStr.equals("") || lastNameStr.equals("") || phoneStr.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");

            alert.showAndWait();

        } else {
            member.setM_Id(memberList);
            member.setM_firstname(firstNameStr);
            member.setM_lastname(lastNameStr);
            member.setM_date_join();
            member.setM_lasted();
            member.setM_tel(phoneStr);
            member.setM_img("test");
            database.insertDatabase(member);
            System.out.println("Register successfully:");
            System.out.println(member.getM_Id());
        }
    }

    private void clearTextField() {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
    }
}
