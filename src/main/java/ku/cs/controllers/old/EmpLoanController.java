package ku.cs.controllers.old;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.DocumentTOB_DBConnect;

import java.io.IOException;
import java.time.LocalDate;

public class EmpLoanController {
    @FXML
    private ListView<String> borrowerListView;

    @FXML
    private Label firstnameLabel;

    @FXML
    private Label lastnameLabel;

    @FXML
    private Label dateDtbLabel;

    @FXML
    private Label dtb_idLabel;

    @FXML
    private Label dtb_CtmIdLabel;

    @FXML
    private Label empNameLabel;


    //prepare data for showSelectedBorrower
    private String selectedDocumentTOB_dtb_id;


    //prepare data for emp who login
    public Employee empLoginAccount;
    public LoanAgreement empLoginWithCtm_idForLoan;


    //prepare list from table loanAgreement Database for listview
    private javafx.collections.ObservableList<String> ObservableList;
    private DocumentTOBList documentTOBList = new DocumentTOBList();

    @FXML
    public void initialize(){
        clearLabel();
        //รับ loginAccount
        empLoginAccount = (Employee)FXRouter.getData();
        showEmpLoginData(empLoginAccount);

        //อ่าน database ของ document
        Database<DocumentTOB, DocumentTOBList> database = new DocumentTOB_DBConnect();
        String query1 = " Select * FROM documenttransactionofborrow WHERE Dtb_status = '1' ORDER BY Dtb_date DESC;   ";
        //เอาที่อ่านจาก database มาใส่ list
        documentTOBList = database.readDatabase(query1); //ได้ documentTob list

        showListView();
        handleSelectedListView();
    }

    private void clearLabel() {
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        dateDtbLabel.setText("");
        dtb_idLabel.setText("");
        dtb_CtmIdLabel.setText("");
    }

    private void showEmpLoginData(Employee loginAccount) {
        empNameLabel.setText(loginAccount.getEmp_name());
    }

    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = documentTOBList.countDocTOBElement()-1; i>=0; i--)
        {
          DocumentTOB doc = documentTOBList.getDtbRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(doc.getDtb_id());
        }
        borrowerListView.setItems(ObservableList);
    }

    private void handleSelectedListView() {
        borrowerListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {

//                        System.out.println(newValue + " is selected");

                        selectedDocumentTOB_dtb_id = newValue;
//                        System.out.println("This is dtb_id " + selectedDocumentTOB_dtb_id);
                        showSelectedBorrower(selectedDocumentTOB_dtb_id);

                    }
                });
    }



    private void showSelectedBorrower(String dtb_IdBorrowerId){
        String dtb_id = dtb_IdBorrowerId;

        //ดึง รหัสลูกค้าจาก dtb_customerId
        DocumentTOB docCtmId = new DocumentTOB(dtb_IdBorrowerId,"-","-","-","-","-","-","-");
        Database<DocumentTOB, DocumentTOBList> database1 = new DocumentTOB_DBConnect();
        String q1 = "  Select * FROM documenttransactionofborrow WHERE Dtb_id = '"+dtb_id+"'   ";
        docCtmId = database1.readRecord(q1);
//        System.out.println("1: " + docCtmId.toCsv());

        //ดึงข้อมูล ชื่อลูกค้าจาก ตาราง customer Database loan_system
        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_id = '"+docCtmId.getDtb_customerId()+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

//        System.out.println("select customer is : " + customer.getCtm_firstname());

        firstnameLabel.setText(customer.getCtm_firstname());
        lastnameLabel.setText(customer.getCtm_lastname());


        // Parses the date
        LocalDate dt = LocalDate.parse(docCtmId.getDtb_date());

        // Prints the day number
        String date = String.valueOf(dt.getDayOfMonth());
       String month= String.valueOf(dt.getMonthValue());
       String year = String.valueOf(dt.getYear());



        String setDocDate = date + "-" + month + "-" + year ;
        dateDtbLabel.setText(setDocDate);




//        System.out.println( "Date : "+docCtmId.getDtb_date());



        dtb_idLabel.setText(docCtmId.getDtb_id());
        dtb_CtmIdLabel.setText(docCtmId.getDtb_customerId());

        //for loan2Controller --> ส่ง รหัสพนักงานที่ login กับ customer id ที่จะบันทักสัญญา ไป loan2Controller
        empLoginWithCtm_idForLoan = new LoanAgreement("0","0");
        empLoginWithCtm_idForLoan.setLoan_customerId(docCtmId.getDtb_customerId());
        empLoginWithCtm_idForLoan.setLoan_Emp1(empLoginAccount.getEmp_id());

//        System.out.println(employeeLoginWithCustomerLoan.getLoan_Emp1()+","+employeeLoginWithCustomerLoan.getLoan_customerId());

    }

    @FXML
    void handleRecordLoanButton(ActionEvent event) {

        int contList = documentTOBList.countDocTOBElement(); //นับจำนวนคนขอกู้ที่ส่งเอกสารผ่าน
        if (contList == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ไม่มีรายชื่อผู้ขอกู้ที่ได้รับการอนุมัติจาก credit board");
            alert.showAndWait();
        }else{
            if(empLoginWithCtm_idForLoan == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("กรุณากดเลือกเอกสารประกอบการกู้ยืมเพื่อทำรายการบันทึกการกู้ยืม");
                alert.showAndWait();
            }else{
                try {
                    FXRouter.goTo("emp_loan2", empLoginWithCtm_idForLoan);
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า emp_loan2 ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }
            }
        }
    }

    public void clickBackToEmp_home(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home",empLoginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


}
