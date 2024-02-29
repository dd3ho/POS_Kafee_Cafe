package ku.cs.models.old;

import java.util.ArrayList;

public class InvoiceList {

    public ArrayList<Invoice> invoices;

    //constructor
    public InvoiceList() { this.invoices = new ArrayList<>(); }


    //เพิ่ม invoice
    public void addInvoice(Invoice invoice){
        invoices.add(invoice);
    }

    //นับ invoice ที่อยู่ ใน list
    public  int countInvoiceElement(){
        return invoices.size();
    }

    //return invoice ใน list ตัวที่ i
    public  Invoice getInvoiceRecord(int i){
        Invoice invoice = invoices.get(i);
        return invoice;
    }

    public String toCsv(){
        String result = "";
        for(Invoice invoice : this.invoices){
            result += invoice.toCsv() + "\n";
        }
        return result;
    }
    public void removeElement(int i){
        invoices.remove(i);
    }

    public void removeAllElement(InvoiceList invoiceList){
        int n =invoiceList.countInvoiceElement();
        int i =0;
        for(i = 0; i < n; i++){
            invoiceList.removeElement(i);
        }
    }






}
