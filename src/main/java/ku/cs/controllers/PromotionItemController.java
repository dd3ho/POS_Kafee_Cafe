package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import ku.cs.App;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.models.Promotion;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.Menu_DBConnection;
import ku.cs.servicesDB.PromotionListener;

import java.io.File;
import java.io.IOException;

public class PromotionItemController {
    @FXML
    private Label code;
    @FXML
    private Label discount;
    @FXML
    private ImageView img;
    private Promotion promotion;
    MenuList menuList;
    Database<Menu, MenuList> databaseMenu;
    PromotionListener myListener;

    @FXML
    private void click(MouseEvent mouseEvent) throws IOException {
        myListener.onClickProListener(promotion);
    }



    @FXML
    public void initialize() {

    }

    public void setData(Promotion promotion, PromotionListener myListener){
        this.promotion = promotion;
        this.myListener = myListener;
        code.setText(promotion.getPro_code());
        if(promotion.getPro_bDiscount() == 0f){
            discount.setText(promotion.getPro_pDiscount() + "%");
        }
        else{
            discount.setText(promotion.getPro_bDiscount() + "B");
        }
        databaseMenu = new Menu_DBConnection();
        String menuQuery = "SELECT * FROM menu WHERE mn_id = '"+ promotion.getPro_mnId() +"'";
        menuList = databaseMenu.readDatabase(menuQuery);
        File file = new File(menuList.getMenu(0).getMn_img());
//        try (InputStream is = new FileInputStream(file)) {
//            Image image = new Image(is);
//            img.setImage(image);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Image image = new Image("file:"+menuList.getMenu(0).getMn_img(), true);
        img.setImage(image);
    }
}
