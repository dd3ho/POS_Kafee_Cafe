package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import ku.cs.FXRouter;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.models.Promotion;
import ku.cs.models.PromotionList;
import ku.cs.servicesDB.*;

import java.io.IOException;

public class AllPromotionController {
    @FXML
    private GridPane proGridPane;

    private Database<Promotion, PromotionList> promotionData;

    private Database<Menu, MenuList> menuData;

    private PromotionList promotions;

    private MenuList menus;

    private PromotionListener myListener;

    private Menu menu;

    @FXML
    public void initialize() {
        menuData = new Menu_DBConnection();
        String qDrink = "SELECT * FROM `menu`";
        menus = menuData.readDatabase(qDrink);

        promotionData = new Promotion_DBConnect();
        String qDessert = "SELECT * FROM `promotion`";
        promotions = promotionData.readDatabase(qDessert);
        showPromotion();
    }

    @FXML
    public  void showPromotion(){
        myListener = new PromotionListener() {
            @Override
            public void onClickProListener(Promotion promotion) throws IOException {
                gotoEditPromotionPage(promotion);
            }
        };

        int column = 3;
        int row = 0;
        try {
            for(int i = 0; i < promotions.countPromotion(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ku/cs/pro_item.fxml"));

                AnchorPane pane = loader.load();
                PromotionItemController itemController = loader.getController();
                itemController.setData(promotions.getPromotion(i), myListener);
                if(column == 3) {
                    column = 0;
                    row++;
                }
                System.out.println(promotions.getPromotion(i).toCsv());
                proGridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    private void handleAddBtn(ActionEvent event) throws IOException {
            FXRouter.goTo("pos_promotion");
    }

    private void gotoEditPromotionPage(Promotion promotion) throws IOException {
        FXRouter.goTo("pos_edit_promotion", promotion);
    }

    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_admin_menu");
    }
}
