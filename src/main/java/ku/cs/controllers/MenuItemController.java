package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ku.cs.models.Menu;

import java.io.File;


public class MenuItemController {

    @FXML
    private Rectangle menuImage;

    @FXML
    private Label name;

    @FXML
    private Label price;

    private Menu menu;

    @FXML
    public void initialize() {
        //
    }

    public void setData(Menu menu){
        this.menu = menu;
        name.setText(menu.getMn_name());
        price.setText(menu.getMn_price() + "฿");
        File file = new File(System.getProperty("user.dir") + File.separator + "menu_photo" + File.separator + menu.getMn_img());
        Image im = new Image(file.toURI().toString());
        menuImage.setFill(new ImagePattern(im));
    }
}
