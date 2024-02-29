package ku.cs.controllers.old;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

import ku.cs.FXRouter;
import ku.cs.models.old.Customer;
import ku.cs.models.old.CustomerList;
import ku.cs.models.old.Invoice;
import ku.cs.models.old.InvoiceList;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Invoice_DBConnect;

public class EmpCheckListController {

    @FXML
    private ListView<String> InvoiceListView;

    @FXML private Label invoiceIdLabel;
    @FXML private Label firstnameLabel;
    @FXML private Label LastnameLabel;
    @FXML private Label Ctm_cidLabel;
    @FXML private Label invoiceCtmDebt;
    @FXML private Label dateLabel;
    @FXML private Label invoice_statusLabel;



    @FXML private TextField findCtmCidTextField;

    //ListView
    private InvoiceList waitToTrackDownDebtsList = new InvoiceList();
    private javafx.collections.ObservableList<String> ObservableList;
    private String id = "0";



    @FXML
    void initialize(){
        clearLabel();
        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_status = '1' || Invoice_status = '2' || Invoice_status = '0' || Invoice_status = '3'  ";
        waitToTrackDownDebtsList = database.readDatabase(allInvoiceQuery);

        showListView();
        handleInvoiceSelected();
    }

    private void clearLabel() {
        invoiceIdLabel.setText("");
        firstnameLabel.setText("");
        LastnameLabel.setText("");
        Ctm_cidLabel.setText("");
        invoiceCtmDebt.setText("");
        invoice_statusLabel.setText("");
        dateLabel.setText("");
    }


    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = waitToTrackDownDebtsList.countInvoiceElement()-1; i>=0; i--)
        {
            Invoice invoice = waitToTrackDownDebtsList.getInvoiceRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(invoice.getInvoice_id());
        }
        InvoiceListView.setItems(ObservableList);
    }



    private void handleInvoiceSelected() {
        InvoiceListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
//                        System.out.println(newValue + " is selected");
                        id = newValue;
                        showSelectedCustomer(id);
                    }
                });
    }

    private void showSelectedCustomer(String id) {
        //id = invoice id

        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_id = '"+id+"'  ";
        Invoice invoice = database.readRecord(allInvoiceQuery);


        invoiceIdLabel.setText(invoice.getInvoice_id());
        firstnameLabel.setText(invoice.getInvoice_ctmfirstname());
        LastnameLabel.setText(invoice.getInvoice_ctmlastname());

        //หาเลขบัตรประชาชน

        Database<Customer, CustomerList> database1 = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_id = '"+invoice.getInvoice_customerId()+"'  ";
        Customer customer = database1.readRecord(q); //เจอ return record ไม่เจอ return null

        Ctm_cidLabel.setText(customer.getCtm_cid());

        //status_invoice  0 = ไม่จ่าย 1=ไม่จ่าย1เดือน 2=ไม่จ่าย2เดือน 3=ไม่จ่าย3เดือน 4=จ่ายแล้ว 5=จ่ายแล้วออกใบเสร็จแล้ว
        if (invoice.getInvoice_status().equals("0")){
            invoice_statusLabel.setText("ไม่จ่าย");
        }
        else if (invoice.getInvoice_status().equals("1")){
            invoice_statusLabel.setText("ไม่จ่าย 1 เดือน");
        }
        else if (invoice.getInvoice_status().equals("2")){
            invoice_statusLabel.setText("ไม่จ่าย 2 เดือน");
        }
        else if (invoice.getInvoice_status().equals("3")){
            invoice_statusLabel.setText("ไม่จ่าย 3 เดือน");
        }

        invoiceCtmDebt.setText(String.valueOf(invoice.getInvoice_ctmDebt()));
        //set date

        //set วันที่ ที่ออกใบแจ้งหนี้
        String day = invoice.getInvoice_date();
        String month = invoice.getInvoice_month();
        String year = invoice.getInvoice_year();

        String date = day+"-"+month+"-"+year;

        dateLabel.setText(date);

    }


    @FXML
    void clickBackToEmp_home(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void clickShowAllInvoice(MouseEvent event) {
        clearLabel();
        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_status = '1' || Invoice_status = '2' || Invoice_status = '0' || Invoice_status = '3'  ";
        waitToTrackDownDebtsList = database.readDatabase(allInvoiceQuery);

        showListView();
        handleInvoiceSelected();
    }

    @FXML
    void findCustomerButton(ActionEvent event) {

        String ctm_id = findCtmCidTextField.getText();
        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * From customer WHERE Ctm_cid = '"+ctm_id+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

        if(findCtmCidTextField.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณาใส่เลขบัตรประจำตัวประชาชนของลูกค้าที่ต้องการค้นหา");
            alert.showAndWait();

        }else{
            if (customer == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("ไม่พบข้อมูลลูกค้าในระบบโปรดตรวจสอบข้อมูลลูกค้า");
                alert.showAndWait();

//                clearShowLabel();

            }else{
                Database<Invoice,InvoiceList>  database1 = new Invoice_DBConnect();
                String q1 = " Select * FROM invoice WHERE Invoice_customerId = '"+customer.getCtm_Id()+"' ";
                waitToTrackDownDebtsList = database1.readDatabase(q1);
                showListView();
                handleInvoiceSelected();
            }

            findCtmCidTextField.setText("");
        }
    }

    @FXML
    void handleBackToEmpHomeButton(ActionEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
