package model;

import ku.cs.models.Invoice;
import ku.cs.models.InvoiceList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.Invoice_DBConnect;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class InvoiceDBTest {
    @Test
    void queryWhereConditionAsMount(){
        String customerId = "9002000";
        Invoice acc = new Invoice("-","-","-","-","-",0,"-","-", "-", "-");
        Database<Invoice, InvoiceList> database1 = new Invoice_DBConnect();
        String q1 = " Select * FROM invoice WHERE Invoice_customerId = '"+customerId+"' AND Invoice_month = '11' AND Invoice_year = '2022' ";
        acc = database1.readRecord(q1);
        System.out.println(acc.toCsv());
        int month = LocalDate.now().getMonthValue();
        String mothFormat = String.format("%02d",month);
        System.out.println(mothFormat);
        int year = LocalDate.now().getYear();
        System.out.println(year);

        int daynow = LocalDate.now().getDayOfMonth();
        String dNow = String.format("%02d",daynow);
        System.out.println(dNow);
    }

    @Test
    void readDatabase(){
        Invoice invoiceTemp = new Invoice("-","-","-","-","-",0,"-","-", "-", "-");
        Database<Invoice,InvoiceList>  database1 = new Invoice_DBConnect();
        String q1 = " Select * FROM invoice Where Invoice_id = '0417542412' ";
        invoiceTemp = database1.readRecord(q1);
        System.out.println(invoiceTemp.toCsv());
    }

    @Test
    void date(){
                //set วันที่ออก invoice
        String dayInvoice = String.valueOf(LocalDate.now().getDayOfMonth());
        String monthInvoice = String.valueOf(LocalDate.now().getMonthValue());
        String yearInvoice = String.valueOf(LocalDate.now().getYear());

        System.out.println("49: "+ dayInvoice);
        System.out.println(monthInvoice);
        System.out.println(yearInvoice);
    }
}
