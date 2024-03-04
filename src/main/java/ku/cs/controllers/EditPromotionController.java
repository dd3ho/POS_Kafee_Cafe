package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.models.Promotion;
import ku.cs.models.PromotionList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.Menu_DBConnection;
import ku.cs.servicesDB.Promotion_DBConnect;

import java.io.IOException;

public class EditPromotionController {
    @FXML
    private TextField codeTextField;

    @FXML
    private TextField dPercentTextField;

    @FXML
    private TextField dBahtTextField;

    @FXML
    private TextField menuTextField;

    public Promotion promotion = new Promotion("",0,0,"");

    public Menu menu = new Menu("0","0",0f,"0","0","","");

    PromotionList promotionList;

    Promotion oldPromotion;

    MenuList menuList;

    Database<Promotion, PromotionList> database;

    Database<Menu, MenuList> databaseMenu;

    @FXML
    public void initialize() {
        //clearTextField();
        database = new Promotion_DBConnect();
        databaseMenu = new Menu_DBConnection();
        promotionList = new PromotionList();
        String query = "SELECT * FROM promotion WHERE pro_code = 'discount04'";
        promotionList = database.readDatabase(query);
        oldPromotion = promotionList.getPromotion(0);
        System.out.println(oldPromotion.getPro_code());
        codeTextField.setText(oldPromotion.getPro_code());
        codeTextField.setDisable(true);
        dPercentTextField.setText(Float.toString(oldPromotion.getPro_pDiscount()));
        dBahtTextField.setText(Float.toString(oldPromotion.getPro_bDiscount()));
        MenuList promptMenuList;
        String menuQuery = "SELECT * FROM menu WHERE mn_id = '"+ oldPromotion.getPro_mnId() +"'";
        promptMenuList = databaseMenu.readDatabase(menuQuery);
        menuTextField.setText(promptMenuList.getMenu(0).getMn_name());
    }
    @FXML
    private void handleEditBtn(ActionEvent event) throws IOException {
        String menuQuery = "SELECT * FROM menu WHERE mn_name = '"+ menuTextField.getText() +"'";
        menuList = databaseMenu.readDatabase(menuQuery);
        menu = menuList.getMenu(0);
        String updateQuery = "UPDATE promotion SET pro_pDiscount = '"+ dPercentTextField.getText() +"' , pro_bDiscount = '"+ dBahtTextField.getText() +"' , pro_mnId = '"+ menu.getMn_Id() +"'";
        database.updateDatabase(updateQuery);
        System.out.println(promotion.getPro_mnId());
    }

    private void clearTextField() {
        codeTextField.setText("");
        dPercentTextField.setText("");
        dBahtTextField.setText("");
        menuTextField.setText("");
    }
}
