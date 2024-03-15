package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import ku.cs.FXRouter;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.MenuFileDataSource;
import ku.cs.servicesDB.Menu_DBConnection;
import ku.cs.servicesDB.MyListener;

import java.io.IOException;

public class AllMenuController {

    @FXML
    private GridPane drinkGridPane;

    @FXML
    private GridPane dessertGridPane;

    @FXML
    private Button addDrinkBtn;

    @FXML
    private Button addDessertBtn;

    private Database<Menu, MenuList> drinkData;

    private Database<Menu, MenuList> dessertData;

    private MenuList drinks;

    private MenuList desserts;

    private MyListener myListener;

    private Menu menu;

    @FXML
    public void initialize() {
        drinkData = new Menu_DBConnection();
        String qDrink = "SELECT * FROM `menu` WHERE m_type = \"drink\"";
        drinks = drinkData.readDatabase(qDrink);
        showDrink();

        dessertData = new Menu_DBConnection();
        String qDessert = "SELECT * FROM `menu` WHERE m_type = \"dessert\"";
        desserts = dessertData.readDatabase(qDessert);
        showDessert();
    }

    @FXML
    public  void showDrink(){
        myListener = new MyListener() {
            @Override
            public void onClickListener(Menu menu) throws IOException{
                gotoEditDrinkPage(menu);
            }
        };

        int column = 3;
        int row = 0;
        try {
            for(int i = 0; i < drinks.countMenus(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ku/cs/item.fxml"));

                AnchorPane pane = loader.load();
                ItemController itemController = loader.getController();
                itemController.setData(drinks.getMenu(i), myListener);
                if(column == 3) {
                    column = 0;
                    row++;
                }
                //System.out.println(drinks.getMenu(i).toCsv());
                drinkGridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showDessert() {
        myListener = new MyListener() {
            @Override
            public void onClickListener(Menu menu) throws IOException{
                gotoEditDessertPage(menu);
            }

        };

        int column = 3;
        int row = 0;
        try {
            for(int i = 0; i < desserts.countMenus(); i++){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/ku/cs/item.fxml"));

                AnchorPane pane = loader.load();
                ItemController itemController = loader.getController();
                itemController.setData(desserts.getMenu(i), myListener);
                if(column == 3) {
                    column = 0;
                    row++;
                }
                //System.out.println(drinks.getMenu(i).toCsv());
                dessertGridPane.add(pane, column++, row);
                GridPane.setMargin(pane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void handleBtn(ActionEvent event) throws IOException {
        if(event.getSource() == addDrinkBtn) {
            FXRouter.goTo("pos_addDrink");
        } else if (event.getSource() == addDessertBtn) {
            FXRouter.goTo("pos_addDessert");
        }
    }

    @FXML
    private void handleBackBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_admin_menu");

    }

    private void gotoEditDrinkPage(Menu menu) throws IOException {
        FXRouter.goTo("pos_editDrink", menu);
    }

    private void gotoEditDessertPage(Menu menu) throws IOException {
        FXRouter.goTo("pos_editDessert", menu);
    }
}


