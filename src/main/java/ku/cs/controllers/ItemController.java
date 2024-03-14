package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.App;
import ku.cs.models.Menu;
import ku.cs.servicesDB.MyListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class ItemController {
    @FXML
    private Label nameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private ImageView img;

    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickListener(menuTemp);
    }

    private Menu menuTemp;
    private MyListener myListener;

    public void setData(Menu menu,MyListener myListener) {
        System.out.println(menu.getMn_img());
        this.menuTemp = menu;
        this.myListener = myListener;
        nameLabel.setText(menu.getMn_name());
        priceLabel.setText(App.CURRENCY + menu.getMn_price());
        File file = new File(menu.getMn_img());
//        try (InputStream is = new FileInputStream(file)) {
//            Image image = new Image(is);
//            img.setImage(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Image image = new Image("file:"+menu.getMn_img(), true);
        img.setImage(image);
    }
}
