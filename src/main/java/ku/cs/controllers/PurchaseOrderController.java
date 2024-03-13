package ku.cs.controllers;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ku.cs.FXRouter;
import ku.cs.models.*;
import ku.cs.servicesDB.DataSource;
import ku.cs.servicesDB.OrderDetailFileDataSource;

import java.io.IOException;


public class PurchaseOrderController {
    @FXML
    private Label discountLabel;

    @FXML
    private Label nameOfProductLabel;

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




    // Listview
    private javafx.collections.ObservableList<String> ObservableList;
    private OrderDetailList orderNowList = new OrderDetailList();


    @FXML
    public void initialize() {
        clearText();
        setItem();
    }
    private void clearText(){
        discountLabel.setText("");
        nameOfProductLabel.setText("");
        orderIdLabel.setText("");
        pointLabel.setText("");
        subTotalLabel.setText("");
        totalLabel.setText("");
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
        float total = 0;
        for (OrderDetail order : orders.getOrderDetailList()) {
            System.out.println("get-price");
            total += order.getO_priceTotal();
        }
        subTotalLabel.setText(String.valueOf(total));

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





}
