package ku.cs.models;

import java.util.ArrayList;

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
    public String toCsv() {
        String result = "";
        for (OrderDetail order : this.orders){
            result += order.toCsv() + "\n";
        }
        return result;
    }
}
