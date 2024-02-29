package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.models.User;
import ku.cs.models.UserList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.Menu_DBConnection;
import ku.cs.servicesDB.User_DBConnect;


import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddDrinkController {
    @FXML
    private TextField nameField;

    @FXML
    private TextField priceField;

    @FXML
    private TextArea descField;

    @FXML
    private Button noSweetBtn;

    @FXML
    private Button lessSweetBtn;

    @FXML
    private Button extraSweetBtn;
    @FXML
    private Button freshMilkBtn;

    @FXML
    private Button almondMilkBtn;

    @FXML
    private Button oatMilkBtn;

    @FXML
    private Button addPhotoBtn;

    @FXML
    private ImageView drinkPhoto;

    @FXML
    private String imageName;

    File selectedFile;

    public Menu menu = new Menu("0","0",0f,"0","0","normal","drink") ;

    MenuList menuList;

    Database<Menu, MenuList> database;

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    String milk = "";
    String sweet = "";



    @FXML
    public void initialize() {
        clearTextField();
        database = new Menu_DBConnection();
        menuList = new MenuList();
        String query = "SELECT * FROM menu";
        menuList = database.readDatabase(query);
    }

    @FXML
    private void handleNoSweetnessBtn(ActionEvent event) throws IOException {
        sweet = "no";
        noSweetBtn.setStyle("-fx-background-color: #632f15");
        lessSweetBtn.setStyle("-fx-background-color: #b68e78");
        extraSweetBtn.setStyle("-fx-background-color: #b68e78");
    }

    @FXML
    private void handleLessSweetnessBtn(ActionEvent event) throws IOException {
        sweet = "less";
        lessSweetBtn.setStyle("-fx-background-color: #632f15");
        noSweetBtn.setStyle("-fx-background-color: #b68e78");
        extraSweetBtn.setStyle("-fx-background-color: #b68e78");
    }

    @FXML
    private void handleExtraSweetnessBtn(ActionEvent event) throws IOException {
        sweet = "extra";
        extraSweetBtn.setStyle("-fx-background-color: #632f15");
        lessSweetBtn.setStyle("-fx-background-color: #b68e78");
        noSweetBtn.setStyle("-fx-background-color: #b68e78");
    }

    @FXML
    private void handleFreshMilkBtn(ActionEvent event) throws IOException {
        milk = "fresh";
        freshMilkBtn.setStyle("-fx-background-color: #632f15");
        almondMilkBtn.setStyle("-fx-background-color: #b68e78");
        oatMilkBtn.setStyle("-fx-background-color: #b68e78");
    }

    @FXML
    private void handleAlmondMilkBtn(ActionEvent event) throws IOException {
        milk = "almond";
        almondMilkBtn.setStyle("-fx-background-color: #632f15");
        freshMilkBtn.setStyle("-fx-background-color: #b68e78");
        oatMilkBtn.setStyle("-fx-background-color: #b68e78");
    }

    @FXML
    private void handleOatMilkBtn(ActionEvent event) throws IOException {
        milk = "oat";
        oatMilkBtn.setStyle("-fx-background-color: #632f15");
        freshMilkBtn.setStyle("-fx-background-color: #b68e78");
        almondMilkBtn.setStyle("-fx-background-color: #b68e78");
    }

    @FXML
    private void handleBackBtn(ActionEvent event) throws IOException {

    }

    @FXML
    private void handleAddBtn(ActionEvent event) throws IOException {
        String nameStr = nameField.getText();
        String priceStr = priceField.getText();
        String descStr = descField.getText();

        menu.setMn_name(nameStr);
        menu.setMn_price(Float.valueOf(priceStr));
        menu.setMn_img("test");
        menu.setM_type("drink");
        menu.setMn_status("sell");
        menu.setMn_option(sweet + "," + milk);
        menu.setMn_img(imageName);

        if (nameField.equals("") || priceField.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");

            alert.showAndWait();

        } else {
            menu.setMn_Id(menuList);
            database.insertDatabase(menu);
            System.out.println(menu.getMn_Id() + "," + menu.getMn_name() + "," + menu.getMn_option());
        }
    }

    @FXML
    private void handleAddPhotoBtn(ActionEvent event) throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("image", "*.jpg", "*.png"));
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if(selectedFile != null) {
            String filenameWithFullPath = selectedFile.toString();
            File file = new File(filenameWithFullPath);
            imageName = file.getName();
            System.out.println(imageName);
            Image image = new Image("file:///" + selectedFile.getAbsolutePath());
            drinkPhoto.setImage(image);
            addPhotoBtn.setText("Change Photo");
        }
    }

    private void clearTextField() {
        nameField.setText("");
        priceField.setText("");
        descField.setText("");
    }
}
