//package ku.cs.controllers.old_shop;
//
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.fxml.Initializable;
//import javafx.geometry.Insets;
//import javafx.scene.control.Alert;
//import javafx.scene.control.Label;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.VBox;
//import ku.cs.App;
//import ku.cs.FXRouter;
//import ku.cs.services.AccountFileDataSource;
//import ku.cs.services.MyListener;
//import ku.cs.services.ProductFileDataSource;
//
//import java.io.IOException;
//import java.net.URL;
//import java.time.LocalDateTime;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class StoreController implements Initializable {
//    @FXML
//    private VBox chosenProductCardVBox;
//    @FXML
//    private Label productNameLabel;
//    @FXML
//    private Label productPriceLabel;
//    @FXML
//    private ImageView productImageImageView;
//    @FXML
//    private Label storeNameLabel;
//    @FXML
//    private TextField productAmountTextField;
//    @FXML
//    private Label productDetailLabel;
//
//    @FXML
//    private Label productQuantityLabel;
//    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//    @FXML
//    private ScrollPane scroll;
//    @FXML
//    private GridPane grid;
//    //-----------------------------------------------------------------------------------------------------------------------------------------
//
//    private List<Product> productList = new ArrayList<>();
//    private MyListener myListener;
//    public Order order;
//    public Account loginAccount;
//
//
//    //-----------------------------------------------------------------------------------------------------------------------------
//    private ProductFileDataSource dataSource;
//    private ProductList products;
//    private AccountFileDataSource dataSource1;
//    private AccountList accounts;
//    //------------------------------------------------------------------------------
//    public Product chosenProduct = new Product("default","default","default",0 , 0, "default","user_default.png",String.valueOf(LocalDateTime.now()),0);
//    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//       order = (Order) FXRouter.getData();
////        System.out.println(storeNameStr);
//
//        dataSource = new ProductFileDataSource("Data", "StockOfProduct.csv");
//        dataSource.readData();
//        dataSource.writeData(products);
//
//        //------------------------------------
//        products = dataSource.getAllProductList();
//
//
//////        Product product = new Product("username6","store6","room spray",150,20,"300ml");
//        Product productTemp = products.searchStoreName(order.getStoreName());
//        products.removeProduct(productTemp);
//        products.sortByTime();
//
//        this.productList = products.getAllProducts();
//
//        showProduct();
//    }
//
//
//    @FXML
//    private void showProduct() {
//        if (productList.size() > 0) {
//
//            setChosenProductCardVBox(productList.get(0));
//            myListener = new MyListener() {
//                @Override
//                public void onClickListener(Product product) {
//                    setChosenProductCardVBox(product);
//                }
//            };
//        }
//        int column = 2;
//        int row = 0;
//        try {
//            for (int i = 0; i < productList.size(); i++) {
//
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/ku/cs/item.fxml"));
//                AnchorPane anchorPane = fxmlLoader.load();
//
//                ItemController itemController = fxmlLoader.getController();
//                itemController.setData(productList.get(i), myListener);
//
//                if (column == 2) {
//                    column = 0;
//                    row++;
//                }
//                grid.add(anchorPane, column++, row); //(child,column,row)
//
//                //set grid width
//                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
//                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
//                grid.setMaxWidth(Region.USE_PREF_SIZE);
//
//                //set grid height
//                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
//                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
//                grid.setMaxHeight(Region.USE_PREF_SIZE);
//
//                GridPane.setMargin(anchorPane, new Insets(10));
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        clearText();
//    }
//
//    @FXML
//    void handleHightToLowButton(ActionEvent event) {
//        dataSource = new ProductFileDataSource("Data", "StockOfProduct.csv");
//        dataSource.readData();
//        dataSource.writeData(products);
//
//        //------------------------------------
//        products = dataSource.getAllProductList();
//
//
//////        Product product = new Product("username6","store6","room spray",150,20,"300ml");
//        Product productTemp = products.searchStoreName(order.getStoreName());
//        products.removeProduct(productTemp);
//        products.sortByLow();
//
//        this.productList = products.getAllProducts();
//
//        showProduct();
//    }
//
//    @FXML
//    void handleLowToHightButton(ActionEvent event) {
//        dataSource = new ProductFileDataSource("Data", "StockOfProduct.csv");
//        dataSource.readData();
//        dataSource.writeData(products);
//
//        //------------------------------------
//        products = dataSource.getAllProductList();
//
//
//////        Product product = new Product("username6","store6","room spray",150,20,"300ml");
//        Product productTemp = products.searchStoreName(order.getStoreName());
//        products.removeProduct(productTemp);
//        products.sortByHeight();
//
//        this.productList = products.getAllProducts();
//        showProduct();
//    }
//
//
//    private void setChosenProductCardVBox(Product product) {
//
//        productNameLabel.setText(product.getName());
//        productPriceLabel.setText(App.CURRENCY + product.getPrice1());
//        Image image = new Image("file:" + product.getImagePath(), true);
//        productImageImageView.setImage(image);
//        storeNameLabel.setText(product.getStoreName());
//        productDetailLabel.setText(product.getProductDetails());
//        productQuantityLabel.setText((product.getQuantity1()));
//
//        //------------------------------------------------------------------------------------------------------------------
//        order.setName(product.getName()); //ชื่อสินค้า
//        order.setStoreName(product.getStoreName()); //ชื่อร้านค้า
//        order.setUnitPrice(product.getPrice()); //ราคาจ่อชิ้น
//        order.setUsername(order.getUsername());
//        chosenProduct.setQuantity(product.getQuantity());
//
//        //------------------------------------------------------------------------
//        // System.out.println("here"+chosenProduct.getName());
//    }
//
//    private void clearText() {
//        productNameLabel.setText("");
//        productPriceLabel.setText("");
//        productAmountTextField.setText("");
//        productDetailLabel.setText("");
//        storeNameLabel.setText("");
//    }
//
//
//    //goto-->order-->purchaseController
//    @FXML
//    void handleBuyNowButton(ActionEvent event) {
//        //สร้าง if ถ้า user ไม่กรอก จำนวนสั่งซื้อ
//        String amountStr = productAmountTextField.getText();
//
//        if (amountStr.equals(null)) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error!!");
//            alert.setHeaderText(null);
//            alert.setContentText("Please check your information and try again.");
//        }
//
//        if (!(amountStr.equals(""))) {
//            order.setAmount(Integer.parseInt(productAmountTextField.getText())); //จำนวนชิ้นที่ถูกซื้อ
//            //มีสินค้าพอกับจำนวนสั่งซื้อ
//            if (chosenProduct.checkQuantity(order.getAmount(), chosenProduct.getQuantity())) {
//                try {
//                    FXRouter.goTo("purchase_order", order);
//                } catch (IOException e) {
//                    System.err.println("ไปที่หน้า purchase order ไม่ได้");
//                    System.err.println("ให้ตรวจสอบการกำหนด route");
//                }
//            } else {
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Error!!");
//                alert.setHeaderText(null);
//                alert.setContentText("Not enough product");
//                alert.showAndWait();
//            }
//        } else {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error!!");
//            alert.setHeaderText(null);
//            alert.setContentText("Please check your information and try again.");
//        }
//    }
//
//
//    @FXML
//    void handleSortByTimeButton(ActionEvent event) {
//
//        dataSource = new ProductFileDataSource("Data", "StockOfProduct.csv");
//        dataSource.readData();
//        dataSource.writeData(products);
//
//        //------------------------------------
//        products = dataSource.getAllProductList();
//
//
//////        Product product = new Product("username6","store6","room spray",150,20,"300ml");
//        Product productTemp = products.searchStoreName(order.getStoreName());
//        products.removeProduct(productTemp);
//        products.sortByTime();
//
//        this.productList = products.getAllProducts();
//
//        showProduct();
//    }
//
//    @FXML
//    void handleBackToUserButton(ActionEvent event) {
//        dataSource1 = new AccountFileDataSource();
//        dataSource1.readData();
//
//        accounts = dataSource1.getAllAccountList();
//        this.loginAccount = accounts.searchUsername(order.getUsername());
//
//        try {
//            FXRouter.goTo("shop",loginAccount);
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า system_for_seller ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//
//
//}
//
