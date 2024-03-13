package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.servicesDB.DataSource;
import ku.cs.servicesDB.Menu_DBConnection;
import ku.cs.servicesDB.OrderDetailFileDataSource;

import java.io.IOException;


public class PurchaseOrderController {
    @FXML
    private Label nameOfProductLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private ListView<String> orderListview;
    // Listview
    private javafx.collections.ObservableList<String> ObservableList;
    private OrderDetailList orderNowList = new OrderDetailList();


    @FXML
    public void initialize() {
        setItem();

    }
    private void setItem(){
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

        showListView(orders);

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
    @FXML
    void handleBackToSellerButton(ActionEvent event) {
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





}
