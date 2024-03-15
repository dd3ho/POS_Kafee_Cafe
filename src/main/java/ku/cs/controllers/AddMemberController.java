package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.servicesDB.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class AddMemberController {
    @FXML
    private TextField firstNameField;

    @FXML
    private TextField lastNameField;

    @FXML
    private TextField phoneField;

    @FXML
    private ImageView memberPhoto;

    private String imageName;
    MemberList memberList;

    Member member = new Member("","","","",0,"","");

    Database<Member, MemberList> database;
    User user;
    File selectedFile;

    @FXML
    public void initialize() {
        clearTextField();
        user = (User) FXRouter.getData();
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
            member.setM_img("member_photo/"+ imageName);
            database.insertDatabase(member);
            System.out.println("Register successfully:");

            File file = new File("member_photo");
            Path path = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
            Files.copy(selectedFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);

            // write to csv --- null
            MemberList memberListToCsv = database.readDatabase("SELECT * FROM member");
            System.out.println(memberListToCsv.toCsv());
            DataSource<MemberList> dataSource = new MemberFileDataSource();
            dataSource.writeData(memberListToCsv);

            FXRouter.goTo("pos_staff_menu", user);
        }
    }
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_staff_menu", user);
    }

    @FXML
    private void handleAddPhotoButton(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image", "*.jpg", "*.png"));
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if(selectedFile != null) {
            String filenameWithFullPath = selectedFile.toString();
            File file = new File(filenameWithFullPath);
            imageName = file.getName();
            Image image = new Image("file:///" + selectedFile.getAbsolutePath());
            memberPhoto.setImage(image);
        }
    }

    private void clearTextField() {
        firstNameField.setText("");
        lastNameField.setText("");
        phoneField.setText("");
    }
}
