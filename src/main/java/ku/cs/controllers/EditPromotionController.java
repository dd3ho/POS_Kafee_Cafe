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

public class EditPromotionController {
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

    Promotion oldPromotion;

    MenuList menuList;

    Database<Promotion, PromotionList> database;

    Database<Menu, MenuList> databaseMenu;
    User usrLoginAccount;
    @FXML
    public void initialize() {
        clearTextField();
        usrLoginAccount = (User) FXRouter.getData();
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
        dBahtTextField.setVisible(false);
        discountText.setText("Discount %");
        dBahtTextField.setText("0");
        dPercentTextField.setText("0");
    }
    @FXML
    private void handleEditBtn(ActionEvent event) throws IOException {
        String menuQuery = "SELECT * FROM menu WHERE mn_name = '"+ menuTextField.getText() +"'";
        menuList = databaseMenu.readDatabase(menuQuery);
        menu = menuList.getMenu(0);
        String updateQuery = "UPDATE promotion SET pro_pDiscount = '"+ dPercentTextField.getText() +"' , pro_bDiscount = '"+ dBahtTextField.getText() +"' , pro_mnId = '"+ menu.getMn_Id() +"' WHERE pro_code = '"+oldPromotion.getPro_code()+"'";
        database.updateDatabase(updateQuery);
        System.out.println(promotion.getPro_mnId());
        FXRouter.goTo("pos_admin_menu", usrLoginAccount);
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_admin_menu", usrLoginAccount);
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
