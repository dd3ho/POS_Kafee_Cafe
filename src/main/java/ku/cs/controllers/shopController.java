package ku.cs.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.models.Menu;
import ku.cs.servicesDB.*;


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

    @FXML
    private ListView<String> orderListview;



    //prepare data
    Database<Menu, MenuList> databaseMenu;
    MenuList menuList;
    private MyListener myListener;
    private List<Menu> menusStock = new ArrayList<>();
    private Member member;
    private OrderDetail orderDetail;

    //ข้อมูลสำหรับการเพิ่มเข้า Listview
    private Menu menuOrder;

    // Listview
    private javafx.collections.ObservableList<String> ObservableList;
    private OrderDetailList orderNowList = new OrderDetailList();
    private String selectItem;

    //data for sum totalPriceInListview
    private float totalPriceOrder;


    @FXML
    public void initialize() {
        clearTextField();
        setItem();
        showProduct();
        handleOrderInListviewSelected();
    }
    private void setItem(){
        // set DB
        databaseMenu = new Menu_DBConnection();
        menuList = new MenuList();
        String query = "SELECT * FROM menu WHERE mn_status = 'sell'";
        menuList = databaseMenu.readDatabase(query);
        // เพิ่มข้อมูลจาก menuList เข้าไปใน menusStock โดยไม่ต้องทำการแปลงชนิดข้อมูล
        menusStock.addAll(menuList.getMenuList());

        // This will clear the data in the "data/OrdersNow.csv" file
        OrderDetailFileDataSource dataSourceOrderNow = new OrderDetailFileDataSource("data", "OrdersNow.csv");
        dataSourceOrderNow.clearData(); // Delete the file first
        OrderDetailList orders = new OrderDetailList(); // Create an empty list
        dataSourceOrderNow.writeData(orders); // Write an empty list to the file

        float total = 0;
        for (OrderDetail order : orders.getOrderDetailList()) {
            System.out.println("get-price");
            total += order.getO_priceTotal();
        }
        totalLabel.setText(String.valueOf(total));



        //set choice box
        milkChoiceBox.getItems().addAll("Fresh Milk", "Almond Milk", "Oat Milk", "None");
        sweetnessChoiceBox.getItems().addAll("No Sweet ", "Less Sweet", "Normal Sweet", "Extra Sweet");
        //set text
        totalLabel.setText("0");

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

        menuOrder = menu;
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
        milkChoiceBox.setValue(null);
        sweetnessChoiceBox.setValue(null);

    }

    @FXML
    void handleAddButton(ActionEvent event) {
        //เขียนอ่านไฟล์
        DataSource<OrderDetailList> dataSource = new OrderDetailFileDataSource("data", "ordersNow.csv");
        OrderDetailList orders = dataSource.readData("data", "OrdersNow.csv");
        //Order Detail
        // [1] o_Id , [2] o_receiptId, [3] o_mnId, [4] o_mnName
        // [5] o_amount, [6] o_priceTotal [7] o_priceByUnit [8] o_sweet
        // [9] o_milk

//        System.out.println("orderList");
//        System.out.println(orders.toCsv());
//        System.out.println("MenuOrder");
//        System.out.println(menuOrder.toCsv());
        String detail = detailTextField.getText();

        if (detailTextField.getText().equals("")){
            detail = "-";
        }

        orders.addOrder(new OrderDetail(
                "none",
                "none",
                menuOrder.getMn_Id(),
                menuOrder.getMn_name(),
                Integer.parseInt(amountLabel.getText()),
                Integer.parseInt(amountLabel.getText())*menuOrder.getMn_price(),
                menuOrder.getMn_price(),
                sweetnessChoiceBox.getValue(),
                milkChoiceBox.getValue(),
                detail

        ));
        dataSource.writeData(orders);
        showListView(orders);
        totalPriceOrder += Integer.parseInt(amountLabel.getText())*menuOrder.getMn_price();
        totalLabel.setText(String.valueOf(totalPriceOrder));
        // clearText
        clearTextField();


    }
    private void showListView(OrderDetailList orders) {
        // EmpPayDebtController.java
        ObservableList = FXCollections.observableArrayList();
        orderNowList = orders;
        for(int i = orderNowList.countElement()-1; i>=0; i--)
        {
            OrderDetail order = orderNowList.getOrderDetailRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(order.toCsv());
        }
        orderListview.setItems(ObservableList);
    }
    private void handleOrderInListviewSelected() {
        System.out.println("เข้า");
        orderListview.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    System.out.println("เข้า1");
                    if (newValue != null) { // Check for a valid selection
                        // Ensure newValue is an OrderDetail object (not a String)

                        String selectedOrder =  newValue;

                        String id = selectedOrder; // Get the ID from the OrderDetail object
//                        System.out.println("show selected");
//                        System.out.println(id);
                        selectItem = id;

                        // Process the selected order's ID (e.g., call showSelectedCustomer)
                        // showSelectedCustomer(id);
                    } else {
                        // Handle the case where no item is selected (e.g., display a message)
                        System.out.println("No item selected");
                        selectItem = "No item selected";
                    }
                });
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        if (selectItem.equals("No item selected")) {
            System.out.println("No item selected for delete");
        } else {
//            String selectItem = "none,none,mn001,ลาเต้,1,0.0,60.0,Less Sweet,Almond Milk";
//                                  [0] [1] ....

            String[] splitted = selectItem.split(",");

//            for (String s : splitted) {
//                System.out.println(s);
//            }
            // ค้นหา OrderDetail ใน OrderDetailList โดยใช้ selectItem ซึ่งก็คือ ID
            OrderDetail toDelete = null;
            for (OrderDetail order : orderNowList.getOrderDetailList()) {
                if (order.getO_mnName().equals(splitted[3]) && order.getO_amount() == Integer.parseInt(splitted[4])) {
                    toDelete = order;
                    break;
                }
            }

//             ถ้าเราหาเจอ OrderDetail ที่ต้องการลบ เราจะลบมันออกจาก OrderDetailList
            if (toDelete != null) {
                // ลบราคาสินค้าออกจากราคาสรุป
                if(totalPriceOrder>0){
                    totalPriceOrder -= toDelete.getO_priceTotal();
                }

                totalLabel.setText(String.valueOf(totalPriceOrder));
                // ลบสินค้า
                orderNowList.removeOrder(toDelete);
                DataSource<OrderDetailList> dataSource = new OrderDetailFileDataSource("data", "ordersNow.csv");
                dataSource.writeData(orderNowList); // เขียนข้อมูลที่อัพเดทไปยังไฟล์
                showListView(orderNowList); // แสดง ListView ที่อัพเดทแล้ว
                System.out.println("Deleted: " + selectItem);
            } else {
                System.out.println("Item to delete not found");
            }
        }
    }




    @FXML
    void handleBackToStaffButton(ActionEvent event) {
        try {
            FXRouter.goTo("pos_staff_menu");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า pos_staff_menu ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void handleDessertTypeButton(ActionEvent event) {
        menusStock.clear();
        // set DB
        databaseMenu = new Menu_DBConnection();
        menuList = new MenuList();

        String query = "SELECT * FROM menu WHERE m_type = 'dessert' AND mn_status = 'sell' ";

        menuList = databaseMenu.readDatabase(query);

        System.out.println("MenuList From DB: "+menuList.toCsv());
        // เพิ่มข้อมูลจาก menuList เข้าไปใน menusStock โดยไม่ต้องทำการแปลงชนิดข้อมูล
        menusStock.addAll(menuList.getMenuList());
        showProduct();

    }

    @FXML
    void handleDrinkTypeButton(ActionEvent event) {
        menusStock.clear();
        // set DB
        databaseMenu = new Menu_DBConnection();
        menuList = new MenuList();
        String query = "SELECT * FROM menu WHERE m_type = 'drink' AND mn_status = 'sell' ";
        menuList = databaseMenu.readDatabase(query);
        System.out.println("MenuList From DB: "+menuList.toCsv());
        menusStock.addAll(menuList.getMenuList());
        showProduct();
    }

    @FXML
    void handleOtherButton(ActionEvent event) {
        menusStock.clear();
        // set DB
        databaseMenu = new Menu_DBConnection();
        menuList = new MenuList();
        String query = "SELECT * FROM menu WHERE mn_status = 'sell' ";
        menuList = databaseMenu.readDatabase(query);
        System.out.println("MenuList From DB: "+menuList.toCsv());
        menusStock.addAll(menuList.getMenuList());
        showProduct();
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
    void handlePlusButton(ActionEvent event) {
        int currentAmount = Integer.parseInt(amountLabel.getText());
        currentAmount++;  // Increment the amount
        amountLabel.setText(String.valueOf(currentAmount));  // Update the label
    }

    @FXML
    void handleOrderButton(ActionEvent event) {
        try {
            FXRouter.goTo("pos_staff_purchase_order");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า pos_staff_purchase_order ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }

    }
}
