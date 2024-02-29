package ku.cs.controllers.old;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Employee_DBConnect;
import ku.cs.servicesDB.old.Invoice_DBConnect;
import ku.cs.servicesDB.old.LoanAgreement_DBConnect;

import java.io.IOException;
import java.time.LocalDate;

public class EmpReportController {


    @FXML private Label firstnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label ctmIdLabel;
    @FXML private Label invoice_statusLabel;
    @FXML private Label dateLabel;
    @FXML private Label emp_nameLabel;
    @FXML private Label emp_idLabel;

    @FXML private Label dateInvoiceLabel;
    @FXML private Label invoiceIdLabel;
    @FXML private ListView<String> reportOfDebtorListView;

    //ListView
    private InvoiceList reportOfDebtorList = new InvoiceList();
    private javafx.collections.ObservableList<String> ObservableList;
    private String id = "0";

    @FXML
    public void initialize(){
        clearLabel();
        //อ่าน database ของ invoice โดยที่ invoice ต้องเป็น invoice ที่ออกวันนี้

        //set วันที่ออก invoice
        int daynow = LocalDate.now().getDayOfMonth();
        String dateNow = String.format("%02d",daynow);
        int mNow =LocalDate.now().getMonthValue();
        String monthNow = String.format("%02d",mNow);
        int yNow = LocalDate.now().getYear();
        String yearNow = String.format("%04d",yNow);

        //อ่าน database เข้า listView
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_date = '"+dateNow+"' AND Invoice_month = '"+monthNow+"' AND Invoice_year = '"+yearNow+"'  ";
        reportOfDebtorList = database.readDatabase(allInvoiceQuery);

        showListView();
        handleInvoiceSelected();
    }

    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = reportOfDebtorList.countInvoiceElement()-1; i>=0; i--)
        {
            Invoice invoice = reportOfDebtorList.getInvoiceRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(invoice.getInvoice_id());
        }
        reportOfDebtorListView.setItems(ObservableList);
    }

    private void handleInvoiceSelected() {
        reportOfDebtorListView.getSelectionModel().selectedItemProperty().addListener(
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

    private void showSelectedCustomer(String invoiceId) {

        Database<Invoice,InvoiceList>  database = new Invoice_DBConnect();
        String q = " Select * FROM invoice WHERE Invoice_id = '"+invoiceId+"' ";
        Invoice invoiceTemp = database.readRecord(q);

        invoiceIdLabel.setText(invoiceId);
        firstnameLabel.setText(invoiceTemp.getInvoice_ctmfirstname());
        lastnameLabel.setText(invoiceTemp.getInvoice_ctmlastname());
        ctmIdLabel.setText(invoiceTemp.getInvoice_customerId());

        //set วันที่ออก invoice
        String dayInvoice = invoiceTemp.getInvoice_date();
        String monthInvoice = invoiceTemp.getInvoice_month();
        String yearInvoice = invoiceTemp.getInvoice_year();

        String dateOfInvoice = dayInvoice + "-" + monthInvoice + "-" + yearInvoice;

        dateInvoiceLabel.setText(dateOfInvoice);




        //status_invoice  0 = ไม่จ่าย 1=ไม่จ่าย1เดือน 2=ไม่จ่าย2เดือน 3=ไม่จ่าย3เดือน 4=จ่ายแล้ว 5=จ่ายแล้วออกใบเสร็จแล้ว
        if (invoiceTemp.getInvoice_status().equals("0")){
            invoice_statusLabel.setText("ไม่จ่าย");
        }
        else if (invoiceTemp.getInvoice_status().equals("1")){
            invoice_statusLabel.setText("ไม่จ่าย 1 เดือน");
        }
        else if (invoiceTemp.getInvoice_status().equals("2")){
            invoice_statusLabel.setText("ไม่จ่าย 2 เดือน");
        }
        else if (invoiceTemp.getInvoice_status().equals("3")){
            invoice_statusLabel.setText("ไม่จ่าย 3 เดือน");
        }else if(invoiceTemp.getInvoice_status().equals("4")){
            invoice_statusLabel.setText(" จ่าย ");
        }else if (invoiceTemp.getInvoice_status().equals("5")){
            invoice_statusLabel.setText(" ออกใบแจ้งหนี้แล้ว ");

        }

        //set Date
        //set date Label
        String dmyLabel = String.valueOf(LocalDate.now());
        LocalDate dt = LocalDate.parse(dmyLabel);
        String dateNowLabel = String.valueOf(dt.getDayOfMonth());
        String monthNowLabel = String.valueOf(dt.getMonthValue());
        String yearNowLabel = String.valueOf(dt.getYear());
        dateLabel.setText(dateNowLabel+"-"+monthNowLabel+"-"+yearNowLabel);

        //อ่านสัญญาหาพนักงานสอง ที่ customerId ตรงกับ invoiceCustomerId
        Database<LoanAgreement, LoanAgreementList>  database1 = new LoanAgreement_DBConnect();
        String q1 = " Select * FROM loanagreement WHERE Loan_customerId = '"+invoiceTemp.getInvoice_customerId()+"' ";
        LoanAgreement loanAgreement = database1.readRecord(q1);

        emp_idLabel.setText(loanAgreement.getLoan_Emp2());


        //เอา id ไปหาชื่อพนักงาน
        Database<Employee, EmployeeList>  database2 = new Employee_DBConnect();
        String q2 = " Select * FROM employee WHERE Emp_id = '"+loanAgreement.getLoan_Emp2()+"' ";
        Employee employee = database2.readRecord(q2);

        emp_nameLabel.setText(employee.getEmp_name());




    }

    private void clearLabel() {
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        ctmIdLabel.setText("");
        invoice_statusLabel.setText("");
        dateLabel.setText("");
        emp_nameLabel.setText("");
        emp_idLabel.setText("");

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


}
