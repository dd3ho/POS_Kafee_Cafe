package ku.cs.models;

import java.util.ArrayList;
import java.util.List;

public class OrderDetailList {
    private ArrayList<OrderDetail> orders;
    public OrderDetailList(){
        orders = new ArrayList<>();
    }

    //เพิ่ม Member
    public void addOrder(OrderDetail order){
        orders.add(order);
    }
    public int countOrders() {
        return orders.size();
    }
    public  String toCsv() {
        String result = "";
        for (OrderDetail order : this.orders){
            result += order.toCsv() + "\n";
        }
        return result;
    }
    public List<OrderDetail> getOrderDetailList() {
        return orders;
    }

    public void clear() {
        orders.clear();
    }

    public int countElement() {
        return orders.size();
    }
    //return OrderDetail ใน list ตัวที่ i
    public  OrderDetail getOrderDetailRecord(int i){
        OrderDetail orderDetail = orders.get(i);
        return orderDetail;
    }
}
