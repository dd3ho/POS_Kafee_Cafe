package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.servicesDB.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class PurchaseOrderController {
    @FXML
    private Label discountLabel;

    @FXML
    private Label nameOfProductLabel;

    @FXML
    private Label memberIdLabel;

    @FXML
    private Label nameMemberLabel;

    @FXML
    private Label orderIdLabel;

    @FXML
    private ListView<String> orderListview;

    @FXML
    private Label pointLabel;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label totalLabel;


    @FXML
    private TextField promotionTextField;

    @FXML
    private Button addButton;

    @FXML
    private ListView<String> promotionListview;

    @FXML
    private Label vatLabel;


    // prepare for db Receipt
    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    Database<Receipt, ReceiptList> databaseOfReceipt;

    // Object of Receipt Temporary
    private Receipt receipt = new Receipt("-", "-",'-',0,"-","-");



    // Listview Order
    private javafx.collections.ObservableList<String> ObservableList;
    private OrderDetailList orderNowList = new OrderDetailList();

    // Listview Promotion
    private javafx.collections.ObservableList<String> ObservablePromotionList;
    private PromotionList promotionsList = new PromotionList();

    //ส่วนลด
    // discount = ราคาเต็ม x (100 - เปอร์เซ็นต์ส่วนลด) / 100 = subTotal * (100-%)/100
    private float discountAfterAddPromotion = 0;


    // ราคารวมสุทธิ  (subtotal- discount) + vat7%
    private float total = 0;

    //vat7% = (subtotal-discount) x (100 + 7) / 100
    private float vat = 0;

    //point
    private int points = 0;


    @FXML
    public void initialize() {
        clearText();
        setItem();
    }
    private void clearText(){
        discountLabel.setText("");
//        nameOfProductLabel.setText("");
        orderIdLabel.setText("");
        pointLabel.setText("");
        subTotalLabel.setText("");
        totalLabel.setText("");
        memberIdLabel.setText("");
        nameMemberLabel.setText("");
        vatLabel.setText("");


    }
    private void setItem()  {
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
        float total = 0;
        for (OrderDetail order : orders.getOrderDetailList()) {
            System.out.println("get-price");
            total += order.getO_priceTotal();
        }
        subTotalLabel.setText(String.valueOf(total));
        showListView(orders);

        //set เลข order
        databaseOfReceipt = new Receipt_DBConnection();
        ReceiptList receiptList = new ReceiptList();
        String query = "SELECT * FROM receipt";
        receiptList = databaseOfReceipt.readDatabase(query);
        System.out.println("receiptList");
        System.out.println(receiptList.toCsv());
        // set id receipt
        receipt.setR_id(receiptList);
        orderIdLabel.setText(receipt.getR_id());

        // เปิดใช้งานปุ่ม addButton
        addButton.setDisable(false);
        discountAfterAddPromotion = 0;
        vat = (float) ((Float.parseFloat(subTotalLabel.getText()) - discountAfterAddPromotion) * 0.07);
        total = (Float.parseFloat(subTotalLabel.getText()) - discountAfterAddPromotion) + vat;
        points = (int)(total / 100); // โดยที่ไม่มีการปัดเศษ

        // ปรับปรุงแสดงผลบนหน้าจอ
        vatLabel.setText(String.format("%.2f", vat));
        totalLabel.setText(String.format("%.2f", total));
        discountLabel.setText(String.format("%.2f", discountAfterAddPromotion));
        pointLabel.setText(String.valueOf(points));



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

    private void showPromotionListView(PromotionList promotions) {
        // EmpPayDebtController.java
        ObservablePromotionList = FXCollections.observableArrayList();
        promotionsList = promotions;
        for(int i = promotionsList.countPromotion()-1; i>=0; i--)
        {
            Promotion promotion = promotionsList.getPromotionRecord(i);
//          ObservablePromotionList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservablePromotionList.add(promotion.toCsv());
        }
        promotionListview.setItems(ObservablePromotionList);
    }
    @FXML
    void handleBackToSellerButton(ActionEvent event) {
        // This will clear the data in the "data/OrdersNow.csv" file
        OrderDetailFileDataSource dataSourceOrderNow = new OrderDetailFileDataSource("data", "OrdersNow.csv");
        dataSourceOrderNow.clearData(); // Delete the file first
        OrderDetailList orders = new OrderDetailList(); // Create an empty list
        dataSourceOrderNow.writeData(orders); // Write an empty list to the file

        //search username  return acc --> assign ค่า ให้ loginAccount ส่งกลับไปที่ system user
        try {
            FXRouter.goTo("shop");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า system_for_user ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void handleCheckoutButton(ActionEvent event) {



    }

    @FXML
    void handleMemberButton(ActionEvent event) {

    }
    @FXML
    void handleAddButton(ActionEvent event) throws IOException {
        //promotionTextField: เก็บรหัสโปรโมชันที่ผู้ใช้ป้อน
        //promotionsList: เก็บรายการโปรโมชัน
        //discountAfterAddPromotion: เก็บจำนวนเงินส่วนลด
        //charge: เก็บค่าธรรมเนียม
        //total: เก็บราคารวมสุทธิ
        //vat: เก็บจำนวนเงินภาษีมูลค่าเพิ่ม

        discountAfterAddPromotion = 0;
        vat = 0;
        total = 0;

        String code = promotionTextField.getText();
        if (code == null || code.isEmpty()) {
            System.out.println("กรุณาระบุรหัสโปรโมชัน");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("กรุณาระบุรหัสโปรโมชัน");
            alert.showAndWait();
            return;
        }
        if (promotionsList.countPromotion() <= 0) {

            // ค้นหาข้อมูลโปรโมชันจากฐานข้อมูล
            String query = "SELECT * FROM promotion  WHERE pro_code = '" + code + "'";
            Database<Promotion, PromotionList> promotionlistDatabase = new Promotion_DBConnect();
            Promotion promotionRecord = promotionlistDatabase.readRecord(query);

            if (promotionRecord != null) {
                // ค้นหาข้อมูลโปรโมชันจากฐานข้อมูล [เจอ]

                // เพิ่มโปรโมชัน
                promotionsList.addPromotion(new Promotion(
                        promotionRecord.getPro_code(),
                        promotionRecord.getPro_pDiscount(),
                        promotionRecord.getPro_bDiscount(),
                        promotionRecord.getPro_mnId()
                ));

                // เขียน file
                DataSource<PromotionList> dataSource = new PromotionFileDataSource("data", "PromotionsOrderNow.csv");
                dataSource.writeData(promotionsList);
                showPromotionListView(promotionsList);
                // ปิดใช้งานปุ่ม addButton
                addButton.setDisable(true);

                // คิดส่วนลด
                if (promotionRecord.getPro_pDiscount() > 0) {
                    // คิดส่วนลดจาก %
                    discountAfterAddPromotion = Float.parseFloat(subTotalLabel.getText()) * (promotionRecord.getPro_pDiscount() / 100);
                } else if (promotionRecord.getPro_bDiscount() > 0) {
                    // คิดส่วนลดจากจำนวนเงิน
                    discountAfterAddPromotion = promotionRecord.getPro_bDiscount();
                }
            } else {
                // ค้นหาข้อมูลโปรโมชันจากฐานข้อมูล [ไม่เจอ]
                System.out.println("ไม่มี promotion_code ในฐานข้อมูล");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("error");
                alert.setHeaderText(null);
                alert.setContentText("ไม่มี promotion_code ในฐานข้อมูล");
                alert.showAndWait();
            }

            // คำนวณ VAT
            vat = (float) ((Float.parseFloat(subTotalLabel.getText()) - discountAfterAddPromotion) * 0.07);

            // ตั้งค่าราคารวมสุทธิ
            total = (Float.parseFloat(subTotalLabel.getText()) - discountAfterAddPromotion) + vat;
            points = (int)(total / 100); // โดยที่ไม่มีการปัดเศษ

            // ปรับปรุงแสดงผลบนหน้าจอ
            vatLabel.setText(String.format("%.2f", vat));
            totalLabel.setText(String.format("%.2f", total));
            discountLabel.setText(String.format("%.2f", discountAfterAddPromotion));
            pointLabel.setText(String.valueOf(points));
        } else {
            System.out.println("มี promotion ในรายการถูกเพิ่มอยู่แล้ว");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("error");
            alert.setHeaderText(null);
            alert.setContentText("มี promotion ในรายการถูกเพิ่มอยู่แล้ว");
            alert.showAndWait();
        }
    }

    @FXML
    void handleDeleteButton(ActionEvent event) {
        promotionsList.clearListPromotion();
        showPromotionListView(promotionsList);
        // เปิดใช้งานปุ่ม addButton
        addButton.setDisable(false);
        discountAfterAddPromotion = 0;
        vat = (float) ((Float.parseFloat(subTotalLabel.getText()) - discountAfterAddPromotion) * 0.07);
        total = (Float.parseFloat(subTotalLabel.getText()) - discountAfterAddPromotion) + vat;
        points = (int)(total / 100); // โดยที่ไม่มีการปัดเศษ


        // ปรับปรุงแสดงผลบนหน้าจอ
        vatLabel.setText(String.format("%.2f", vat));
        totalLabel.setText(String.format("%.2f", total));
        discountLabel.setText(String.format("%.2f", discountAfterAddPromotion));
        pointLabel.setText(String.valueOf(points));

    }

}
