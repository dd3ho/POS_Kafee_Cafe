package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import ku.cs.FXRouter;
import ku.cs.models.*;
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

    @FXML
    private Label discountText;

    public Promotion promotion = new Promotion("",0,0,"");

    public Menu menu = new Menu("0","0",0f,"0","0","","");

    PromotionList promotionList;

    MenuList menuList;

    Database<Promotion, PromotionList> database;

    Database<Menu, MenuList> databaseMenu;
    User usrLoginAccount;

    @FXML
    public void initialize() {
        clearTextField();
        //usrLoginAccount = (User) FXRouter.getData();
        database = new Promotion_DBConnect();
        databaseMenu = new Menu_DBConnection();
        promotionList = new PromotionList();
        String query = "SELECT * FROM promotion";
        promotionList = database.readDatabase(query);
        dBahtTextField.setVisible(false);
        discountText.setText("Discount %");
        dBahtTextField.setText("0");
        dPercentTextField.setText("0");
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
        FXRouter.goTo("pos_admin_menu", usrLoginAccount);
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_allPromotion", usrLoginAccount);
    }

    @FXML
    private void handleSwitchButton(ActionEvent event) throws IOException {
        if(dBahtTextField.isVisible()) {
            dBahtTextField.setVisible(false);
            dBahtTextField.setText("0");
            dPercentTextField.setVisible(true);
            discountText.setText("Discount %");
        }else{
            dBahtTextField.setVisible(true);
            dPercentTextField.setText("0");
            dPercentTextField.setVisible(false);
            discountText.setText("Discount Baht");
        }
    }

    private void clearTextField() {
        codeTextField.setText("");
        dPercentTextField.setText("");
        dBahtTextField.setText("");
        menuTextField.setText("");
    }
}
