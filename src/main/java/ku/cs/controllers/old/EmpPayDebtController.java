package ku.cs.controllers.old;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.Customer;
import ku.cs.models.old.CustomerList;
import ku.cs.models.old.Invoice;
import ku.cs.models.old.InvoiceList;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Invoice_DBConnect;

import java.io.IOException;
import java.util.Optional;

public class EmpPayDebtController {

    @FXML private ListView<String> waitToCreateReceiptListView;

    @FXML private TextField findCtmCidTextField;
    @FXML private Label firstnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label ctmIdLabel;
    @FXML private Label ctmbankAccountLabel;
    @FXML private Label invoiceCtmDebtLabel;
    @FXML private Label invoiceIdLabel;
    @FXML private Label Ctm_cidLabel;
    @FXML private Label invoiceStatusLabel;

    //ListView
    private InvoiceList waitToCreateReceiptList = new InvoiceList();
    private javafx.collections.ObservableList<String> ObservableList;
    private String id = "0";

    //
    public Invoice invoiceBill = new Invoice("-","-","-","-","-",0,"-","-", "-", "-");

    @FXML
    public void initialize(){
        clearText();
        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_status = '4'   ";
        waitToCreateReceiptList = database.readDatabase(allInvoiceQuery);

        showListView();
        handleInvoiceSelected();
    }

    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = waitToCreateReceiptList.countInvoiceElement()-1; i>=0; i--)
        {
            Invoice invoice = waitToCreateReceiptList.getInvoiceRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(invoice.getInvoice_id());
        }
        waitToCreateReceiptListView.setItems(ObservableList);
    }

    private void handleInvoiceSelected(){
        waitToCreateReceiptListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
//                        System.out.println(newValue + " is selected");
                        id = newValue; //id invoice
                        showSelectedCustomer(id);
                    }
                });
    }

    private void showSelectedCustomer(String id) {
        //ได้ invoice id มา หา customer id ได้
        Invoice invoiceTemp = new Invoice("-","-","-","-","-",0,"-","-", "-", "-");
        Database<Invoice,InvoiceList>  database1 = new Invoice_DBConnect();
        String q1 = " Select * FROM invoice Where Invoice_id = '"+ this.id +"' ";
        invoiceTemp = database1.readRecord(q1);

        //set
        invoiceIdLabel.setText(invoiceTemp.getInvoice_id());
        firstnameLabel.setText(invoiceTemp.getInvoice_ctmfirstname());
        lastnameLabel.setText(invoiceTemp.getInvoice_ctmlastname());
        invoiceCtmDebtLabel.setText(String.valueOf(invoiceTemp.getInvoice_ctmDebt()));
        ctmIdLabel.setText(invoiceTemp.getInvoice_customerId());
        if(invoiceTemp.getInvoice_status().equals("4")){
            invoiceStatusLabel.setText("จ่ายแล้ว");
        }else {
            invoiceStatusLabel.setText("-");
        }


        //set ctm_id
        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_id = '"+invoiceTemp.getInvoice_customerId()+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

        ctmbankAccountLabel.setText(customer.getCtm_bankAccount());
        Ctm_cidLabel.setText(customer.getCtm_cid());

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
                String q1 = " Select * FROM invoice WHERE Invoice_customerId = '"+customer.getCtm_Id()+"' AND Invoice_status = '4' ";
                waitToCreateReceiptList = database1.readDatabase(q1);
                showListView();
                handleInvoiceSelected();
            }

            findCtmCidTextField.setText("");
        }
    }

    //ไปออก receipt
    @FXML
    void handleCheckInfoButton(ActionEvent event) {
        //set invoice bill
        invoiceBill.setInvoice_id(invoiceIdLabel.getText());
        invoiceBill.setInvoice_ctmfirstname(firstnameLabel.getText());
        invoiceBill.setInvoice_ctmlastname(lastnameLabel.getText());
        invoiceBill.setInvoice_customerId(ctmIdLabel.getText());
        invoiceBill.setInvoice_ctmbankAccount(ctmbankAccountLabel.getText());
        invoiceBill.setInvoice_ctmDebt(Integer.parseInt(invoiceCtmDebtLabel.getText()));
        if(invoiceStatusLabel.getText().equals("จ่ายแล้ว")){
            invoiceBill.setInvoice_status("4");
        }

        try {
            FXRouter.goTo("emp_payDebt2",invoiceBill);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    @FXML
    void cancelButton(ActionEvent event) {
        //ต้องการกลับไป Menu ใช่ไหม
        //if ใช--> clear text field
        // else ไม่ใช่

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("ต้องการยกเลิกใช่หรือไม่?");
        //capture the dialog result of ok or cancel
        Optional<ButtonType> result = alert.showAndWait();


        if(result.get() == ButtonType.OK){
            try {
                FXRouter.goTo("emp_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า menu ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    void clickShowAllInvoice(MouseEvent event) {
        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_status = '4'  ";
        waitToCreateReceiptList = database.readDatabase(allInvoiceQuery);

        showListView();
        handleInvoiceSelected();
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

    private void clearText(){
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        ctmIdLabel.setText("");
        ctmbankAccountLabel.setText("");
        invoiceCtmDebtLabel.setText("");
        invoiceIdLabel.setText("");
        Ctm_cidLabel.setText("");
        invoiceStatusLabel.setText("");
    }


}
