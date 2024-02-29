package model;

import ku.cs.models.Customer;
import ku.cs.models.CustomerList;
import ku.cs.models.DocumentTOB;
import ku.cs.models.DocumentTOBList;
import ku.cs.servicesDB.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.DocumentTOB_DBConnect;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class DocumentTOBTest {
    @Test
    void testRandomDtb10Digit(){
        DocumentTOB randDtb_id = new DocumentTOB("");
        randDtb_id.generateDtb_id();
        System.out.println("result : "+ randDtb_id.getDtb_id());
    }

    @Test
    void testReadDataReturnRecord(){


        Customer customer = new Customer("3723502", "0");

        String id = "3723502";

        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_id = '"+id+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

        System.out.println("1: " + customer);

//        DocumentTOB documentTOB = new DocumentTOB("", "");
//        Database<DocumentTOB, DocumentTOBList> databaseDtb = new DocumentTOB_DBConnect();
//        String qDtb =" Select * FROM documenttransactionofborrow WHERE Dtb_customerId = '"+customer.getCtm_Id()+"'  ";
//        documentTOB = databaseDtb.readDatabase(documentTOB,qDtb); //เจอ return record ไม่เจอ return null
//
//        System.out.println(documentTOB.toCsv());

    }


    @Test
    void testReadDtb_TOBReturnRecord(){


        Customer customer = new Customer("3723502", "0");

        String id = "7594490440";

        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_id = '"+id+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

        System.out.println("1: " + customer);

        DocumentTOB documentTOB = new DocumentTOB("", "");
        Database<DocumentTOB, DocumentTOBList> databaseDtb = new DocumentTOB_DBConnect();
        String qDtb =" Select * FROM documenttransactionofborrow WHERE Dtb_customerId = '4323232'  ";
        documentTOB = databaseDtb.readRecord(qDtb); //เจอ return record ไม่เจอ return null

        System.out.println(documentTOB.toCsv());

    }


    @Test
    void testRandomDtb15Digit() {
        Random random = new Random();
        long n = (long) (100000000000000L + random.nextFloat() * 900000000000000L);
        System.out.println(n);
    }

}
