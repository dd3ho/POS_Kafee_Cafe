package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.*;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.old.Employee_DBConnect;
import ku.cs.servicesDB.old.Invoice_DBConnect;
import ku.cs.servicesDB.old.LoanAgreement_DBConnect;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class EmpInvoice2Controller {

    @FXML private TextField ctmDebtTextField;
    @FXML private Label dateLabel;
    @FXML private Label customerIdLabel;
    @FXML private Label fnameLabel;
    @FXML private Label lnameLabel;
    @FXML private Label bankAccLabel;
    @FXML private Label Loan_balanceLabel;
    @FXML private Label empNameLabel;

    public LoanAgreement loan_Invoice1transfer;
    public Employee empLogin;

    //for insert record
    private Invoice invoiceInsertRecord = new Invoice("","","","","",0,"","","","");

    @FXML
    public void initialize(){
        loan_Invoice1transfer = (LoanAgreement) FXRouter.getData();
//        System.out.println(loan_Invoice1transfer.getLoan_Emp1());
        showEmpLoginData();
        showCustomerData();
    }

    private void showCustomerData() {
        Customer customer;
        String idCustomer = loan_Invoice1transfer.getLoan_customerId();
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q = "SELECT * From customer Where Ctm_id = '"+idCustomer+"'";
        customer = database.readRecord(q);
        customerIdLabel.setText(customer.getCtm_Id());
        fnameLabel.setText(customer.getCtm_firstname());
        lnameLabel.setText(customer.getCtm_lastname());
        bankAccLabel.setText(customer.getCtm_bankAccount());

        LoanAgreement loanAgreement;
        Database<LoanAgreement,LoanAgreementList> database1 = new LoanAgreement_DBConnect();
        String q1 = "SELECT * From loanagreement Where Loan_customerId = '"+loan_Invoice1transfer.getLoan_customerId()+"' ";
        loanAgreement = database1.readRecord(q1);
        Loan_balanceLabel.setText(String.valueOf(loanAgreement.getLoan_balance()));

        //set date Label
        String dmyLabel = String.valueOf(LocalDate.now());
        LocalDate dt = LocalDate.parse(dmyLabel);
        String dateNowLabel = String.valueOf(dt.getDayOfMonth());
        String monthNowLabel = String.valueOf(dt.getMonthValue());
        String yearNowLabel = String.valueOf(dt.getYear());
        dateLabel.setText(dateNowLabel+"-"+monthNowLabel+"-"+yearNowLabel);

    }

    private void showEmpLoginData() {
        String idEmp = loan_Invoice1transfer.getLoan_Emp1();
        Database <Employee, EmployeeList> database = new Employee_DBConnect();
        String q = " Select * FROM employee WHERE Emp_id = '"+idEmp+"'  ";
        empLogin = database.readRecord(q);
//        System.out.println(empLogin.toCsv());
        empNameLabel.setText(empLogin.getEmp_name());
    }


    @FXML
    void handleRecordInvoiceButton(ActionEvent event) {
        if (ctmDebtTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");
            alert.showAndWait();
        }else{
            //generate invoice Id
            Invoice tempInvoice = new Invoice("","","","","",0,"","","","");
            String checkInvoice_id = "0";
            String invoice_idStr = null;

            while (checkInvoice_id.equals("0")){
                //random ctm_id 10 digit
                invoice_idStr = tempInvoice.generateInvoice_id();

                // ใช้ Db
                Database<Invoice, InvoiceList> database1 = new Invoice_DBConnect();

                //หา dtb_id ในตาราง dtb ที่ตรงกับ invoice_idStr(เลขที่สุ่ม) ถ้า เจอ--> return account ไม่เจอ return null
                String queryCheckInvoiceId = " SELECT * FROM invoice  WHERE invoice_id = '"+invoice_idStr+"' ";
                tempInvoice = database1.readRecord(queryCheckInvoiceId);

                if (tempInvoice == null){ //หาไม่เจอ
                    checkInvoice_id = "1";
                }else { //หาเจอ
                    checkInvoice_id = "0";
                }
            }//while true ให้ generate Dtb_id จนกว่าจะไม่ซ้ำ

            //set id invoice
            invoiceInsertRecord.setInvoice_id(invoice_idStr);
            invoiceInsertRecord.setInvoice_customerId(customerIdLabel.getText());
            invoiceInsertRecord.setInvoice_ctmfirstname(fnameLabel.getText());
            invoiceInsertRecord.setInvoice_ctmlastname(lnameLabel.getText());
            invoiceInsertRecord.setInvoice_ctmbankAccount(bankAccLabel.getText());
            invoiceInsertRecord.setInvoice_ctmDebt(Integer.parseInt(ctmDebtTextField.getText()));
           //set วัน
            int daynow = LocalDate.now().getDayOfMonth();
            String dateNow = String.format("%02d",daynow);
            int mNow =LocalDate.now().getMonthValue();
            String monthNow = String.format("%02d",mNow);
            int yNow = LocalDate.now().getYear();
            String yearNow = String.format("%04d",yNow);

            invoiceInsertRecord.setInvoice_date(dateNow);
            invoiceInsertRecord.setInvoice_month(monthNow);
            invoiceInsertRecord.setInvoice_year(yearNow);
            invoiceInsertRecord.setInvoice_status("0");

            //Insert
            Database<Invoice,InvoiceList> database = new Invoice_DBConnect();
            database.insertDatabase(invoiceInsertRecord);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("บันทึกสำเร็จ");
            alert.showAndWait();

            try {
                FXRouter.goTo("emp_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า emp_home ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
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

    @FXML void clickBackToLogin(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
