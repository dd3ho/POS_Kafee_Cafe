package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.servicesDB.*;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class PaymentController {
    Receipt receipt;
    // r_payType(cash/debit/qr_code)

    // prepare DB
    ReceiptList receiptList;
    Database<Receipt, ReceiptList> databaseReceipt;

    // o_id
    OrderDetailList orderDetailListFromDatabase;
    Database<OrderDetail, OrderDetailList> databaseOrderDetail;
    private int o_idLastNumberFromDb;

    Connection conn = null;
    Statement stmt = null;
    ResultSet rs = null;

    // List From OrdersNow.csv
    OrderDetailList orderDetailCsvList;

    @FXML
    private void initialize() {
        receipt = (Receipt) FXRouter.getData();
        receipt.setR_memberId("");
        System.out.println("Payment");
        System.out.println(receipt.toCsv());
    }
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_staff_purchase_order");
    }

    @FXML
    public void handleMoney() throws IOException{

        receipt.setR_payType("cash");

    }

    @FXML
    public void handleCard() throws IOException{

        receipt.setR_payType("debit");
    }

    @FXML
    public void handleQR() throws IOException{

        receipt.setR_payType("qr_code");

    }
    @FXML
    void handlePayButton(ActionEvent event) {

        // รับ receipt จาก scene ก่อนหน้า
        databaseReceipt = new Receipt_DBConnection();
        receiptList = new ReceiptList();
        String query = "SELECT * FROM receipt";
        receiptList = databaseReceipt.readDatabase(query);

        // บันทึก receipt ไปยังฐานข้อมูล
        databaseReceipt.insertDatabase(receipt);

        // อ่าน order details จากฐานข้อมูล
        databaseOrderDetail = new OrderDetailDBConnection();
        orderDetailListFromDatabase = new OrderDetailList();
        String query1 = "SELECT * FROM order_detail";
        orderDetailListFromDatabase = databaseOrderDetail.readDatabase(query1);
        System.out.println("orderDetailListFromDatabase:  "+ orderDetailListFromDatabase.toCsv());

        // อ่านรายการ order จากไฟล์ CSV
        orderDetailCsvList = new OrderDetailList();
        DataSource<OrderDetailList> dataSource = new OrderDetailFileDataSource("data", "ordersNow.csv");
        orderDetailCsvList = dataSource.readData("data", "OrdersNow.csv");

        // รับเลข order สุดท้ายจากฐานข้อมูล
        o_idLastNumberFromDb = orderDetailListFromDatabase.countOrders() + 1; // +1 เพื่อเริ่มต้นจากเลขถัดไป
        System.out.println("o_idLastNumberFromDb:  "+o_idLastNumberFromDb);

        // วนลูปบันทึกทุกรายการ order จาก CSV ไปยังฐานข้อมูล
        for (OrderDetail order : orderDetailCsvList.getOrderDetailList()) {
            OrderDetail orderDetailToSaveDB = new OrderDetail();
            orderDetailToSaveDB.setO_IdPayment(o_idLastNumberFromDb++);
            orderDetailToSaveDB.setO_receiptId(receipt.getR_id());
            orderDetailToSaveDB.setO_mnId(order.getO_mnId());
            orderDetailToSaveDB.setO_mnName(order.getO_mnName());
            orderDetailToSaveDB.setO_amount(order.getO_amount());
            orderDetailToSaveDB.setO_priceTotal(order.getO_priceTotal());
            orderDetailToSaveDB.setO_priceByUnit(order.getO_priceByUnit());
            orderDetailToSaveDB.setO_sweet(order.getO_sweet());
            orderDetailToSaveDB.setO_milk(order.getO_milk());
            orderDetailToSaveDB.setO_detail(order.getO_detail());
            databaseOrderDetail.insertDatabase(orderDetailToSaveDB);
        }

        // แสดง Alert เมื่อการชำระเงินเสร็จสิ้น
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("ชำระเงินสำเร็จ");
        alert.showAndWait();

        // redirect
        // FXRouter.goTo("pos_finish");
    }
//    @FXML
//    void handlePayButton(ActionEvent event) {
//
//        databaseReceipt = new Receipt_DBConnection();
//        receiptList = new ReceiptList();
//        String query = "SELECT * FROM receipt";
//        receiptList = databaseReceipt.readDatabase(query);
//
//        // Insert to receipt Db
//        databaseReceipt.insertDatabase(receipt);
//
//        //Read orderdetial in Db
//        databaseOrderDetail = new OrderDetailDBConnection();
//        orderDetailListFromDatabase = new OrderDetailList();
//        String query1 = "SELECT * FROM order_detail";
//        orderDetailListFromDatabase = databaseOrderDetail.readDatabase(query1);
//
//        //Csv save to db
//        orderDetailCsvList = new OrderDetailList();
//        //เขียนอ่านไฟล์
//        DataSource<OrderDetailList> dataSource = new OrderDetailFileDataSource("data", "ordersNow.csv");
//        orderDetailCsvList = dataSource.readData("data", "OrdersNow.csv");
//
//        //เลขสุดท้ายของ o-id บน database
//        o_idLastNumberFromDb = orderDetailListFromDatabase.countOrders();
//
//
//        for (OrderDetail order : orderDetailCsvList.getOrderDetailList()) {
//            //o_Id + "," + o_receiptId + "," + o_mnId + "," + o_mnName + "," + o_amount + "," +
//            //o_priceTotal+ "," + o_priceByUnit + "," + o_sweet + "," + o_milk+","+o_detail;
//            //none,none,mn002,ชาเขียวมัทฉะกรีนที,3,195.0,65.0,No Sweet ,Fresh Milk,-
//
//            OrderDetail orderDetailToSaveDB = null;
//            orderDetailToSaveDB.setO_IdPayment(o_idLastNumberFromDb);
//            o_idLastNumberFromDb+=1;
//            orderDetailToSaveDB.setO_receiptId(receipt.getR_id());
//            orderDetailToSaveDB.setO_mnId(order.getO_mnId());
//            orderDetailToSaveDB.setO_mnName(order.getO_mnName());
//            orderDetailToSaveDB.setO_amount(order.getO_amount());
//            orderDetailToSaveDB.setO_priceTotal(order.getO_priceTotal());
//            orderDetailToSaveDB.setO_priceByUnit(order.getO_priceByUnit());
//            orderDetailToSaveDB.setO_sweet(order.getO_sweet());
//            orderDetailToSaveDB.setO_milk(order.getO_milk());
//            orderDetailToSaveDB.setO_detail(order.getO_detail());
//            databaseOrderDetail.insertDatabase(orderDetailToSaveDB);
//        }
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setTitle("Success");
//        alert.setHeaderText(null);
//        alert.setContentText("ชำระเงินสำเร็จ");
//        alert.showAndWait();
//    }
}
