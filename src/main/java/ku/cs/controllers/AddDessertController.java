package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import ku.cs.FXRouter;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.servicesDB.DataSource;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.MenuFileDataSource;
import ku.cs.servicesDB.Menu_DBConnection;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class AddDessertController {
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
    private Button addPhotoBtn;

    @FXML
    private ImageView dessertPhoto;

    @FXML
    private String imageName;

    File selectedFile;

    public Menu menu = new Menu("0","0",0f,"0","sell","normal","dessert") ;

    MenuList menuList;

    Database<Menu, MenuList> database;

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    @FXML
    public void initialize() {
        clearTextField();
        database = new Menu_DBConnection();
        menuList = new MenuList();
        String query = "SELECT * FROM menu";
        menuList = database.readDatabase(query);
    }

//    @FXML
//    private void handleNoSweetnessBtn(ActionEvent event) throws IOException {
//        menu.setMn_option("no");
//        noSweetBtn.setStyle("-fx-background-color: #632f15");
//        lessSweetBtn.setStyle("-fx-background-color: #b68e78");
//        extraSweetBtn.setStyle("-fx-background-color: #b68e78");
//    }
//
//    @FXML
//    private void handleLessSweetnessBtn(ActionEvent event) throws IOException {
//        menu.setMn_option("less");
//        lessSweetBtn.setStyle("-fx-background-color: #632f15");
//        noSweetBtn.setStyle("-fx-background-color: #b68e78");
//        extraSweetBtn.setStyle("-fx-background-color: #b68e78");
//    }
//
//    @FXML
//    private void handleExtraSweetnessBtn(ActionEvent event) throws IOException {
//        menu.setMn_option("extra");
//        extraSweetBtn.setStyle("-fx-background-color: #632f15");
//        lessSweetBtn.setStyle("-fx-background-color: #b68e78");
//        noSweetBtn.setStyle("-fx-background-color: #b68e78");
//    }

    @FXML
    private void handleBackBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_allMenu");
    }

    @FXML
    private void handleAddBtn(ActionEvent event) throws IOException {

        if (nameField.getText().equals("") || priceField.getText().equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");

            alert.showAndWait();

        } else {
            String nameStr = nameField.getText();
            String priceStr = priceField.getText();

            menu.setMn_name(nameStr);
            menu.setMn_price(Float.valueOf(priceStr));
            menu.setMn_img("menu_photo/"+ imageName);

            menu.setMn_Id(menuList);
            database.insertDatabase(menu);
            System.out.println(menu.getMn_Id() + "," + menu.getMn_name() + "," + menu.getMn_option());

            // save image
            File file = new File("menu_photo");
            Path path = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
            Files.copy(selectedFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);

            // write to csv --- null
            MenuList menuListToCsv = database.readDatabase("SELECT * FROM menu");
            //System.out.println(menuListToCsv.toCsv());
            DataSource<MenuList> dataSource = new MenuFileDataSource();
            dataSource.writeData(menuListToCsv);

            FXRouter.goTo("pos_allMenu");
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
            dessertPhoto.setImage(image);
            addPhotoBtn.setText("Change Photo");
        }
    }

    private void clearTextField() {
        nameField.setText("");
        priceField.setText("");
    }
}
