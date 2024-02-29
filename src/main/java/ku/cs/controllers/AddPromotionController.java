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

public class AddPromotionController {
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

    MenuList menuList;

    Database<Promotion, PromotionList> database;

    Database<Menu, MenuList> databaseMenu;

    @FXML
    public void initialize() {
        clearTextField();
        database = new Promotion_DBConnect();
        databaseMenu = new Menu_DBConnection();
        promotionList = new PromotionList();
        String query = "SELECT * FROM promotion";
        promotionList = database.readDatabase(query);
    }
    @FXML
    private void handleAddBtn(ActionEvent event) throws IOException {
        promotion.setPro_code(codeTextField.getText());
        promotion.setPro_pDiscount(Float.parseFloat(dPercentTextField.getText()));
        promotion.setPro_bDiscount(Float.parseFloat(dBahtTextField.getText()));
        String menuQuery = "SELECT * FROM menu WHERE mn_name = '"+ menuTextField.getText() +"'";
        menuList = databaseMenu.readDatabase(menuQuery);
        menu = menuList.getMenu(0);
        promotion.setPro_mnId(menu.getMn_Id());
        database.insertDatabase(promotion);
        System.out.println(promotion.getPro_mnId());
    }

    private void clearTextField() {
        codeTextField.setText("");
        dPercentTextField.setText("");
        dBahtTextField.setText("");
        menuTextField.setText("");
    }
}
