package ku.cs.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
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
    Database<Menu, MenuList> drinkData;

    Database<Menu, MenuList> dessertData;

    MenuList drinks;

    MenuList desserts;

    MyListener myListener;

    private Menu menuItem;

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
                GridPane.setMargin(pane, new Insets(12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void showDessert() {
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
                GridPane.setMargin(pane, new Insets(12));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setChosenMenu(Menu menu) {
        menuItem = menu;
    }
}


