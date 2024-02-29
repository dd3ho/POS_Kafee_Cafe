package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.*;
import ku.cs.servicesDB.old.Invoice_DBConnect;
import ku.cs.servicesDB.old.LoanAgreement_DBConnect;
import ku.cs.servicesDB.old.Receipt_DBConnect;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class EmpPayDebt2Controller {

    @FXML private Label firstnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label ctmIdLabel;
    @FXML private Label bankAccLabel;
    @FXML private Label invoice_dateLabel;
    @FXML private Label Recipt_dateLabel;
    @FXML private Label Invoice_statusLabel;
    @FXML private Label Invoice_DebtLabel;
    @FXML private Label Loan_balanceLabel;
    @FXML private Label InvoiceIdLabel;


    //
    public Invoice invoiceBill;

    @FXML
    void initialize(){
        clearLabel();

        //
        invoiceBill = (Invoice)FXRouter.getData();
        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String invoiceById = " Select * FROM invoice WHERE Invoice_id = '"+invoiceBill.getInvoice_id()+"'   ";
        invoiceBill = database.readRecord(invoiceById);

        showSelectCustomer(invoiceBill);
    }

    private void showSelectCustomer(Invoice invoice) {
        InvoiceIdLabel.setText(invoiceBill.getInvoice_id());
        firstnameLabel.setText(invoiceBill.getInvoice_ctmfirstname());
        lastnameLabel.setText(invoiceBill.getInvoice_ctmlastname());
        ctmIdLabel.setText(invoiceBill.getInvoice_customerId());
        bankAccLabel.setText(invoiceBill.getInvoice_ctmbankAccount());

        //
        Invoice_statusLabel.setText("จ่ายแล้ว");

        String dateInvoice = invoiceBill.getInvoice_date() + "-" +invoiceBill.getInvoice_month() + "-" + invoiceBill.getInvoice_year();
        invoice_dateLabel.setText(dateInvoice);

        //receipt generate date LocalDateNow
        //set date Label
        String dmyLabel = String.valueOf(LocalDate.now());
        LocalDate dt = LocalDate.parse(dmyLabel);
        String dateNowLabel = String.valueOf(dt.getDayOfMonth());
        String monthNowLabel = String.valueOf(dt.getMonthValue());
        String yearNowLabel = String.valueOf(dt.getYear());
        Recipt_dateLabel.setText(dateNowLabel+"-"+monthNowLabel+"-"+yearNowLabel);

        Invoice_DebtLabel.setText(String.valueOf(invoiceBill.getInvoice_ctmDebt()));

        //Loan balance by customer id
        Database<LoanAgreement, LoanAgreementList> database1 = new LoanAgreement_DBConnect();
        String queryCheckLoan_id = " SELECT * FROM loanagreement  WHERE Loan_customerId = '" + invoiceBill.getInvoice_customerId() + "' ";
        LoanAgreement tempLoan = database1.readRecord(queryCheckLoan_id);
        Loan_balanceLabel.setText(String.valueOf(tempLoan.getLoan_balance()));
    }

    private void clearLabel() {
        InvoiceIdLabel.setText("");
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        ctmIdLabel.setText("");
        bankAccLabel.setText("");

        Recipt_dateLabel.setText("");
        Invoice_statusLabel.setText("");
        Loan_balanceLabel.setText("");
    }


    @FXML
    void recordButton(ActionEvent event) {
        Receipt genId = new Receipt("0", invoiceBill.getInvoice_customerId(), "-", "-", "-", invoiceBill.getInvoice_ctmDebt(), Integer.parseInt(Loan_balanceLabel.getText()), invoiceBill.getInvoice_id());
        String checkId = "0";
        String idRec = null;

        while (checkId.equals("0")) {
            //random ctm_id 10 digit
            String id = genId.generateRec_id();
            idRec = id;

            // ใช้ Db
            Database<Receipt, ReceiptList> database1 = new Receipt_DBConnect();

            //หา dtb_id ในตาราง dtb ที่ตรงกับ invoice_idStr(เลขที่สุ่ม) ถ้า เจอ--> return account ไม่เจอ return null
            String queryCheckInvoiceId = " SELECT * FROM invoice  WHERE invoice_id = '" + id + "' ";
            Receipt tempReceipt = database1.readRecord(queryCheckInvoiceId);

            if (tempReceipt == null) { //หาไม่เจอ
                checkId = "1";
            } else { //หาเจอ
                checkId = "0";
            }
        }

//        System.out.println("112: " + idRec);

        //date
        int daynow = LocalDate.now().getDayOfMonth();
        String dateNow = String.format("%02d",daynow);
        int mNow =LocalDate.now().getMonthValue();
        String monthNow = String.format("%02d",mNow);
        int yNow = LocalDate.now().getYear();
        String yearNow = String.format("%04d",yNow);


//        System.out.println("122: " + invoiceBill.getInvoice_id());
        int balance1 = Integer.parseInt(Loan_balanceLabel.getText());


        Database<Receipt,ReceiptList> database = new Receipt_DBConnect();
        Receipt receipt = new Receipt(idRec, invoiceBill.getInvoice_customerId(), dateNow, monthNow, yearNow, invoiceBill.getInvoice_ctmDebt(), balance1, invoiceBill.getInvoice_id());
        database.insertDatabase(receipt);

        //update สัญญา
        int balance = Integer.parseInt(Loan_balanceLabel.getText()) - (invoiceBill.getInvoice_ctmDebt()) ;

        Database<LoanAgreement, LoanAgreementList> database2 = new LoanAgreement_DBConnect();
        String queryLoan = " UPDATE  loanagreement SET Loan_balance = '"+balance+"' WHERE Loan_customerId = '"+invoiceBill.getInvoice_customerId()+"' ";
        database2.updateDatabase(queryLoan);

        //update invoice status 5

        Database<Invoice, InvoiceList> database3 = new Invoice_DBConnect();
        String invoiceById = " UPDATE  invoice SET Invoice_status = '5' WHERE Invoice_id = '"+invoiceBill.getInvoice_id()+"'   ";
        database3.updateDatabase(invoiceById);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText("ระบบบันทึกข้อมูลลูกค้าสำเร็จ");
        alert.showAndWait();


        try {
            FXRouter.goTo("emp_home");
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
    void clickBackToEmp_home(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
