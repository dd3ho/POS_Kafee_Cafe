package ku.cs.controllers.old;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Invoice_DBConnect;
import ku.cs.servicesDB.old.LoanAgreement_DBConnect;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class EmpInvoiceController {

    @FXML private ListView <String> waitToCreateInvoice;
    @FXML private Label empNameLabel;
    @FXML private Label firstnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label dateLabel;
    @FXML private Label statusLabel;
    @FXML private Label balanceLabel;
    @FXML private Label loan_customerIdLabel;


    public Employee empLoginAccount;
    //listview
    private LoanAgreementList loanAgreementList = new LoanAgreementList();
    private javafx.collections.ObservableList<String> ObservableList;
    private String selectedLoan_id = "0";

    //about invoice---------------------------------------------------------------------
    int statusInvoice = 0; // 0 = ยังไม่ได้ออกใบแจ้งหนี้ของเดือนนี้ / 1 = ออกใบแจ้งหนี้ของลูกค้าสัญญานี้แล้ว
    public LoanAgreement loanAgreementInvoice2 = new LoanAgreement("-","-");
    //----------------------------------------------------------------------------
    @FXML
    public void initialize(){
        clearLabel();
        empLoginAccount = (Employee) FXRouter.getData();
        showEmpLoginData(empLoginAccount);

        //อ่าน database ของ loan Agreement
        Database<LoanAgreement, LoanAgreementList> database = new LoanAgreement_DBConnect();
        String query = " Select * From LoanAgreement Where Loan_balance != 0";
        loanAgreementList = database.readDatabase(query);
        showListView();
        handleInvoiceSelected();
    }

    private void clearLabel() {
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        dateLabel.setText("");
        balanceLabel.setText("");
        loan_customerIdLabel.setText("");
        statusLabel.setText("");
    }

    private void handleInvoiceSelected() {
        waitToCreateInvoice.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
//                        System.out.println(newValue + " is selected");
                        selectedLoan_id = newValue;
                        showSelectedCustomer(selectedLoan_id);
                    }
                });
    }

    private void showSelectedCustomer(String id) {
        String loan_id = id;

        //ดึง รหัสลูกค้า จาก loan_id เพื่อเเสดงชื่อ กับ นามสกุล
        LoanAgreement loanAgreement = new LoanAgreement(id,"0","-","-","-",0,"-",0,0,"-","-","-","-");
        Database<LoanAgreement, LoanAgreementList> database = new LoanAgreement_DBConnect();
        String q = "  Select * FROM loanagreement WHERE Loan_id = '"+loan_id+"'   ";
        loanAgreement = database.readRecord(q);

//        System.out.println(loanAgreement.toCsv());
        firstnameLabel.setText(loanAgreement.getLoan_firstname());
        lastnameLabel.setText(loanAgreement.getLoan_lastname());
        loan_customerIdLabel.setText(loanAgreement.getLoan_customerId());
//-------------------------------------------------------------------------------------------------------------------------------------------------------
        // ของเดือนที่เช็ค คือ todayStr mount year
        String monthNow = String.format("%02d",LocalDate.now().getMonthValue());
        String yearNow = String.format("%04d",LocalDate.now().getYear());

        //set date Label
        String dmyLabel = String.valueOf(LocalDate.now());
        LocalDate dt = LocalDate.parse(dmyLabel);
        String dateNowLabel = String.valueOf(dt.getDayOfMonth());
        String monthNowLabel = String.valueOf(dt.getMonthValue());
        String yearNowLabel = String.valueOf(dt.getYear());
        dateLabel.setText(dateNowLabel+"-"+monthNowLabel+"-"+yearNowLabel);


        //year month
        Invoice invoiceTemp = new Invoice("-","-","-","-","-",0,"-","-", "-", "-");
        Database<Invoice, InvoiceList>  database1 = new Invoice_DBConnect();
        String q1 = " Select * FROM invoice WHERE Invoice_customerId = '"+loanAgreement.getLoan_customerId()+"' AND Invoice_month = '"+monthNow+"' AND Invoice_year = '"+yearNow+"' ";
        invoiceTemp = database1.readRecord(q1);
//        System.out.println(invoiceTemp.toCsv());
        if(invoiceTemp == null){
            // invoice Temp null = ยังไม่ได้สร้าง ใบแจ้งหนี้ของเดือนนี้
//            System.out.println("null invoice temp");
            statusLabel.setText("ยังไม่ได้ออกใบเเจ้งหนี้");
            statusInvoice = 0;

        }else{
            statusLabel.setText("ออกใบแจ้งหนี้แล้ว");
            statusInvoice = 1;
        }

        //set balance
        balanceLabel.setText(String.valueOf(loanAgreement.getLoan_balance()));
    }

    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = loanAgreementList.countListElement()-1; i>=0; i--)
        {
            LoanAgreement loanAgreement = loanAgreementList.getLoanRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(loanAgreement.getLoan_id());
        }
        waitToCreateInvoice.setItems(ObservableList);

    }

    private void showEmpLoginData(Employee empLoginAccount) {
        empNameLabel.setText(empLoginAccount.getEmp_name());
    }

    @FXML
    void clickBackToLogin(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void handleInvoiceButton(ActionEvent event) {
        if(statusInvoice == 0){
            //ไม่มีใบแจ้งหนี้สำหรับเดือนนี้ ไปออกหน้า emp_invoice2
            loanAgreementInvoice2.setLoan_customerId(loan_customerIdLabel.getText());
            loanAgreementInvoice2.setLoan_Emp1(empLoginAccount.getEmp_id());

            try {
                FXRouter.goTo("emp_invoice2", loanAgreementInvoice2);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า emp_invoice2 ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }

        }else{

            //ต้องการกลับไป Menu ใช่ไหม
            //if ใช--> clear text field
            // else ไม่ใช่

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm");
            alert.setContentText("ลูกค้ารายนี้ออกใบแจ้งหนี้สำหรับเดือนนี้แล้ว ต้องการกลับสู่หน้าหลักหรือไม่?");
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
    }
}
