package model;

import ku.cs.models.Customer;
import ku.cs.models.CustomerList;
import ku.cs.servicesDB.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import org.junit.jupiter.api.Test;

public class DBConnectTest {

    // return list
    @Test
    void readDatabaseList(){
        CustomerList customerList = new CustomerList();
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_sex = '2' ";
        customerList = database.readDatabase(q);
        System.out.println(customerList.toCsv());
    }

    //return record (Object)
    @Test
    void readCustomerFromCtm_cid(){

        Customer customer = new Customer("0", "1105100167850");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_cid = '"+customer.getCtm_cid()+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null
        System.out.println(customer.toCsv());
    }


}
