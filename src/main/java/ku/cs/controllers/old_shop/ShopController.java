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
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.AnchorPane;
//import javafx.scene.layout.GridPane;
//import javafx.scene.layout.Region;
//import javafx.scene.layout.VBox;
//import ku.cs.App;
//import ku.cs.FXRouter;
//import ku.cs.services.DataSource;
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
//public class ShopController implements Initializable {
//    @FXML
//    private VBox chosenProductCardVBox;
//    @FXML private Label productNameLabel;
//    @FXML private Label productPriceLabel;
//    @FXML private ImageView productImageImageView;
//    @FXML private Label storeNameLabel;
//    @FXML private TextField productAmountTextField;
//    @FXML private Label productDetailLabel;
//    @FXML private Label productQuantityLabel;
//
//    //------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//    @FXML private ScrollPane scroll;
//    @FXML private GridPane grid;
//    //-----------------------------------------------------------------------------------------------------------------------------------------
//    private List<Product> productList = new ArrayList<>();
//    private MyListener myListener;
//    private Account loginAccount;
//    //---------------------------------------------------------------
//
//    //ส่ง Order ไปสรุปคำสั่งซื้อ
//    public Product chosenProduct = new Product("default","default","default",0 , 0, "default","user_default.png",String.valueOf(LocalDateTime.now()),0);
//    public Order order = new Order("-","default","default",0,0,0,"-");
//
//    //-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
////    private List<Product> getData(){
////        DataSource<ProductList> dataSource = new ProductFileDataSource();
////        ProductList products = dataSource.readData();
////        productList = products.getAllProducts();
////        return productList;
////    }
//
//    //read-Data-->getData();
//
//    //----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------
//
//    @Override
//    public void initialize(URL url, ResourceBundle resourceBundle) {
//        loginAccount = (Account) FXRouter.getData();
//        DataSource<ProductList> dataSource = new ProductFileDataSource();
//        ProductList products = dataSource.readData();
//        products.sortByTime();
//        productList = products.getAllProducts();
//        showProduct();
//    }
//
//
//    @FXML
//    private void showProduct(){
//        if (productList.size() > 0) {
//            setChosenProductCardVBox(productList.get(0));
//            myListener = new MyListener() {
//                @Override
//                public void onClickListener(Product product) {
//                    setChosenProductCardVBox(product);
//                }
//            };
//        }
//
//        int column = 2;
//        int row = 0;
//        try {
//            for(int i = 0; i< productList.size(); i++){
//
//                FXMLLoader fxmlLoader = new FXMLLoader();
//                fxmlLoader.setLocation(getClass().getResource("/ku/cs/item.fxml"));
//                AnchorPane anchorPane = fxmlLoader.load();
//
//                ItemController itemController = fxmlLoader.getController();
//                itemController.setData(productList.get(i),myListener);
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
//        DataSource<ProductList> dataSource = new ProductFileDataSource();
//        ProductList products = dataSource.readData();
//        products.sortByHeight();
//        this.productList = products.getAllProducts();
//        showProduct();
//    }
//
//
//
//    @FXML
//    void handleLowToHightButton(ActionEvent event) {
//        DataSource<ProductList> dataSource = new ProductFileDataSource();
//        ProductList products = dataSource.readData();
//        products.sortByLow();
//        this.productList = products.getAllProducts();
//        showProduct();
//    }
//
//
//    //goto-->order-->purchaseController
//    @FXML
//    void handleBuyNowButton(ActionEvent event) {
//        //สร้าง if ถ้า user ไม่กรอก จำนวนสั่งซื้อ
//        String amountStr =productAmountTextField.getText();
//
//        if(amountStr.equals(null)){
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error!!");
//            alert.setHeaderText(null);
//            alert.setContentText("Please check your information and try again.");
//        }
//
//        if( !(amountStr.equals("")) ){
//            order.setAmount(Integer.parseInt(productAmountTextField.getText())); //จำนวนชิ้นที่ถูกซื้อ
//            //มีสินค้าพอกับจำนวนสั่งซื้อ
//            if(chosenProduct.checkQuantity(order.getAmount(),chosenProduct.getQuantity())){
//                //System.out.printf("if");
//                //System.out.printf(order.toCsv());
//                try {
//                    FXRouter.goTo("purchase_order", order);
//                } catch (IOException e) {
//                    System.err.println("ไปที่หน้า purchase order ไม่ได้");
//                    System.err.println("ให้ตรวจสอบการกำหนด route");
//                }
//            }else{
//                //System.out.printf("else");
//                //System.out.printf(order.toCsv());
//                Alert alert = new Alert(Alert.AlertType.INFORMATION);
//                alert.setTitle("Error!!");
//                alert.setHeaderText(null);
//                alert.setContentText("Not enough product");
//                alert.showAndWait();
//            }
//        }else{
//            //System.out.printf("เข้า");
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error!!");
//            alert.setHeaderText(null);
//            alert.setContentText("Please check your information and try again.");
//        }
//    }
//
//
//
//    private void setChosenProductCardVBox(Product product){
//
//        productNameLabel.setText(product.getName());
//        productPriceLabel.setText(App.CURRENCY+product.getPrice1());
//        Image image = new Image("file:"+product.getImagePath(), true);
//        productImageImageView.setImage(image);
//
//        productQuantityLabel.setText(product.getQuantity1());
//
//        storeNameLabel.setText(product.getStoreName());
//        productDetailLabel.setText(product.getProductDetails());
//
//
//
//        //------------------------------------------------------------------------------------------------------------------
//        order.setName(product.getName()); //ชื่อสินค้า
//        order.setStoreName(product.getStoreName()); //ชื่อร้านค้า
//        order.setUnitPrice(product.getPrice()); //ราคาจ่อชิ้น
//        order.setUsername(loginAccount.getUsername());
//        chosenProduct.setQuantity(product.getQuantity());
//
//        //------------------------------------------------------------------------
//        // System.out.println("here"+chosenProduct.getName());
//    }
//    private void clearText() {
////        productNameLabel.setText("");
////        productPriceLabel.setText("");
//        productAmountTextField.setText("");
//        productDetailLabel.setText("");
//        //storeNameLabel.setText("");
//    }
//
//
//    @FXML
//    void handleSortByTimeButton(ActionEvent event) {
//        DataSource<ProductList> dataSource = new ProductFileDataSource();
//        ProductList products = dataSource.readData();
//        products.sortByTime();
//        this.productList = products.getAllProducts();
//        showProduct();
//    }
//
//    @FXML
//    void handleProfileButton(ActionEvent event) {
//        try {
//            FXRouter.goTo("system_for_user",loginAccount);
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า system for user  ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//
//
//    }
//
//    @FXML
//    void storeNameLabel(MouseEvent mouseEvent) {
////        System.out.println(chosenProduct.toCsv());
////       String storeNameStr = storeNameLabel.getText();
////       chosenProduct.setStore(storeNameStr);
//
//        //System.out.println(order.toCsv());
//
//       try {
//            FXRouter.goTo("store",order);
//        } catch (IOException e) {
//            System.err.println("ไปที่หน้า store  ไม่ได้");
//            System.err.println("ให้ตรวจสอบการกำหนด route");
//        }
//    }
//}
