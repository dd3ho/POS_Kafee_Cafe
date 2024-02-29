package ku.cs.models.old;

import ku.cs.models.old.Customer;

import java.util.ArrayList;

public class CustomerList {

    private  ArrayList<Customer> customers;

    public CustomerList(){
        customers = new ArrayList<>();
    }

    //เพิ่ม customer
    public void addCustomer(Customer customer){
        customers.add(customer);
    }

    public String toCsv() {
        String result = "";
        for (Customer customer : this.customers){
            result += customer.toCsv() + "\n";
        }
        return result;
    }


}
