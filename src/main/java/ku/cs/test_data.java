package ku.cs;

import ku.cs.models.OrderDetail;
import ku.cs.models.OrderDetailList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.OrderDetailDBConnection;

public class test_data {

    public static void main(String[] args) {
        OrderDetailList orderDetailList = new OrderDetailList();
        Database<OrderDetail, OrderDetailList> orderDatabase = new OrderDetailDBConnection();
        String q = "SELECT * FROM order_detail";
        orderDetailList = orderDatabase.readDatabase(q);
        System.out.println("count : " + orderDetailList.countOrders());

    }
}
