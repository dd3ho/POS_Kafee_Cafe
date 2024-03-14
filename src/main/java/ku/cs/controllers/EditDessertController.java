package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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

public class EditDessertController {
    @FXML private TextField nameTextfield;

    @FXML private TextField priceTextfield;

    @FXML private ImageView dessertImg;

    @FXML private Button imgBtn;

    Menu menu;
    File selectedFile;
    String imageName;
    @FXML
    public void initialize() {
        menu = (Menu) FXRouter.getData();
        System.out.println(menu.toCsv());
        nameTextfield.setText(menu.getMn_name());
        priceTextfield.setText(menu.getMn_price().toString());
        imageName = menu.getMn_img();

        if(menu.getMn_img() != null){
            imgBtn.setText("Change Photo");
            Image img = new Image("file:" + menu.getMn_img(), true);
            dessertImg.setImage(img);
        }
    }

    @FXML
    public void handleEditBtn(ActionEvent event) throws IOException {
        String name = nameTextfield.getText();
        Float price = Float.parseFloat(priceTextfield.getText());

        this.menu.setMn_name(name);
        menu.setMn_price(price);
        menu.setM_type("dessert");
        menu.setMn_status("sell");
        menu.setMn_option("-");
        menu.setMn_img(imageName);


        if (nameTextfield.equals("") || priceTextfield.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");

            alert.showAndWait();

        } else {
            Database<Menu, MenuList> menuListDatabase = new Menu_DBConnection();
            System.out.println(menu.getMn_Id() + "," + menu.getMn_name() + "," + menu.getMn_option());
            String q = "Update menu Set mn_name = '" + menu.getMn_name() + "', mn_price = " + menu.getMn_price() + ",mn_status = '" + menu.getMn_status() + "', mn_option = '"
                    + menu.getMn_option() + "', m_type = '" + menu.getM_type() + "', mn_img = 'menu_photo/" + menu.getMn_img() + "' WHERE mn_id = '" + menu.getMn_Id() + "';";
            menuListDatabase.updateDatabase(q);
            System.out.println(selectedFile.toString());
            // save image
            File file = new File("menu_photo");
            Path path = FileSystems.getDefault().getPath(file.getAbsolutePath() + "\\" + imageName);
            Files.copy(selectedFile.toPath(), path, StandardCopyOption.REPLACE_EXISTING);

            // write to csv --- null
            MenuList menuListToCsv = menuListDatabase.readDatabase("SELECT * FROM menu");
            System.out.println(menuListToCsv.toCsv());
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
            dessertImg.setImage(image);
            imgBtn.setText("Change Photo");
        }
    }

    @FXML
    private void handleBackBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_allMenu");
    }
}
