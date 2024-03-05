package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import ku.cs.App;
import ku.cs.models.Member;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;
import ku.cs.models.OrderDetail;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.MenuFileDataSource;
import ku.cs.servicesDB.Menu_DBConnection;
import ku.cs.servicesDB.MyListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class shopController {

    @FXML
    private VBox chosenProductCardVBox;

    @FXML
    private GridPane grid;


    @FXML
    private Label nameLabel;

    @FXML
    private Label priceLabel;


    @FXML
    private ImageView productImageImageView;

    @FXML
    private ScrollPane scroll;

    @FXML
    private Label totalLabel;

    @FXML
    private Label amountLabel;

    @FXML
    private ChoiceBox<String> milkChoiceBox;

    @FXML
    private ChoiceBox<String> sweetnessChoiceBox;

    @FXML
    private TextField detailTextField;



    //prepare data
    Database<Menu, MenuList> databaseMenu;
    MenuList menuList;
    private MyListener myListener;
    private List<Menu> menusStock = new ArrayList<>();
    private Member member;
    private OrderDetail orderDetail;


    @FXML
    public void initialize() {
        clearTextField();
        setItem();
        showProduct();
    }
    private void setItem(){
        // set DB
        databaseMenu = new Menu_DBConnection();
        menuList = new MenuList();
        String query = "SELECT * FROM menu WHERE mn_status = 'sell'";
        menuList = databaseMenu.readDatabase(query);

        // เพิ่มข้อมูลจาก menuList เข้าไปใน menusStock โดยไม่ต้องทำการแปลงชนิดข้อมูล
        menusStock.addAll(menuList.getMenuList());

        //set choice box
        milkChoiceBox.getItems().addAll("Fresh Milk", "Almond Milk", "Oat Milk", "None");
        sweetnessChoiceBox.getItems().addAll("No Sweet ", "Less Sweet", "Extra Sweet");
    }

    private void showProduct() {
        if (menusStock.size() > 0) {
            setChosenProductCardVBox(menusStock.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Menu menu) {
                    setChosenProductCardVBox(menu);
                }

            };
        }

        int column = 2;
        int row = 0;
        try {
            for(int i = 0; i< menusStock.size(); i++){

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/ku/cs/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(menusStock.get(i),myListener);

                if (column == 2) {
                    column = 0;
                    row++;
                }
                grid.add(anchorPane, column++, row); //(child,column,row)

                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        clearText();
    }

    private void clearText() {
        amountLabel.setText("0");
    }

    private void setChosenProductCardVBox(Menu menu){

        nameLabel.setText(menu.getMn_name());
        priceLabel.setText(App.CURRENCY+menu.getMn_price());

//        System.out.println("139: " + menu.getMn_img());
        Image image = new Image("file:"+menu.getMn_img());
        productImageImageView.setImage(image);
        // ตั้งค่า viewport ของ ImageView
//        Rectangle2D viewportRect = new Rectangle2D(0, 0, 173, 164);
//        productImageImageView.setViewport(viewportRect);

        productImageImageView.setFitWidth(173); // กำหนดค่าที่ต้องการสำหรับกว้าง
        productImageImageView.setFitHeight(129); // ปล่อยให้ค่านี้เป็น 0 เพื่อให้ระบบปรับขนาดอัตโนมัติตามความกว้างที่กำหนด
        productImageImageView.setPreserveRatio(true);
        productImageImageView.setSmooth(false); //

//        productQuantityLabel.setText(menu).getQuantity1());

//        storeNameLabel.setText(menu).getStoreName());
//        productDetailLabel.setText(menu).getProductDetails());



        //------------------------------------------------------------------------------------------------------------------
//        order.setName(menu.getMn_name()); //ชื่อสินค้า
//        order.setStoreName(menu).getStoreName()); //ชื่อร้านค้า
//        order.setUnitPrice(menu).getPrice()); //ราคาจ่อชิ้น
//        order.setUsername(loginAccount.getUsername());
//        chosenProduct.setQuantity(menu.getQuantity());

        //------------------------------------------------------------------------
        // System.out.println("here"+chosenProduct.getName());
    }

    private void clearTextField() {
        nameLabel.setText("");
        priceLabel.setText("");
        amountLabel.setText("0");
        detailTextField.setText("");
        productImageImageView.setImage(null);
    }

    @FXML
    void handleAddButton(ActionEvent event) {

    }

    @FXML
    void handleBackToStaffButton(ActionEvent event) {

    }

    @FXML
    void handleDessertTypeButton(ActionEvent event) {

    }

    @FXML
    void handleDrinkTypeButton(ActionEvent event) {

    }

    @FXML
    void handleMinusButton(ActionEvent event) {
        int currentAmount = Integer.parseInt(amountLabel.getText());
        if (currentAmount <= 0) {
            // แสดงข้อความแจ้งเตือน "จำนวนไม่สามารถติดลบได้"
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("Oder ไม่สามารถติดลบได้");
            alert.showAndWait();
        } else {
            currentAmount--;
            amountLabel.setText(String.valueOf(currentAmount));
        }
    }

    @FXML
    void handleOrderButton(ActionEvent event) {

    }

    @FXML
    void handleOtherButton(ActionEvent event) {

    }

    @FXML
    void handlePlusButton(ActionEvent event) {
        int currentAmount = Integer.parseInt(amountLabel.getText());
        currentAmount++;  // Increment the amount
        amountLabel.setText(String.valueOf(currentAmount));  // Update the label
    }
}
