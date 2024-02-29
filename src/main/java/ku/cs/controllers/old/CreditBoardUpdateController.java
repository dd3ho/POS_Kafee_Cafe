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

public class CreditBoardUpdateController {
    @FXML private ListView<String> waitForApproveListView;
    @FXML private Label status1Label;
    @FXML private Label status2Label;
    @FXML private Label status3Label;
    @FXML private Label status4Label;
    @FXML private Label customerIdLabel;
    @FXML private Label nameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label idLabel;
    @FXML private Label empNameLabel;
    @FXML private Label dtbDateLabel;



    //prepare data for method showSelectedDtb_status0
    private String selectedCustomer ="0";

    //listview
    private DocumentTOBList documentTOBList = new DocumentTOBList();
    private javafx.collections.ObservableList<String> ObservableList;

    //prepare data for emp who login
    public  Employee empLoginAccount;

    //prepare data for approveDtb --> ส่งข้อมูลไปหน้าต่อไป
    public LoanAgreement empLoginWithCtm_idForLoan  = new LoanAgreement("0","0");

    @FXML
    public void initialize(){

        clearLabel();
        //รับ loginAccount
        empLoginAccount = (Employee)FXRouter.getData();
        showEmpLoginData(empLoginAccount);

        //อ่าน database ของ document
        Database<DocumentTOB, DocumentTOBList> database = new DocumentTOB_DBConnect();
        String query1 = " Select * FROM documenttransactionofborrow WHERE Dtb_status = '0' ORDER BY Dtb_date DESC;  ";
        //เอาที่อ่านจาก database มาใส่ list
        documentTOBList = database.readDatabase(query1); //ได้ documentTob list

        showListView();
        handleSelectedListView();
    }

    private void clearLabel() {
        customerIdLabel.setText("");
        nameLabel.setText("");
        lastnameLabel.setText("");
        idLabel.setText("");
        dtbDateLabel.setText("");
        status1Label.setText("");
        status2Label.setText("");
        status3Label.setText("");
        status4Label.setText("");
    }


    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = documentTOBList.countDocTOBElement()-1; i>=0; i--)
        {
            DocumentTOB doc = documentTOBList.getDtbRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(doc.getDtb_id());
        }
        waitForApproveListView.setItems(ObservableList);
    }

    private void handleSelectedListView() {
        waitForApproveListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
//                        System.out.println(newValue + " is selected");
                        selectedCustomer = newValue;
                        showSelectedCustomer(newValue);
                    }
                });
    }

    private void showSelectedCustomer(String dtb_Id) {
        String dtb_id = dtb_Id;
        selectedCustomer =dtb_Id;

        //ดึง รหัสลูกค้าจาก dtb_Id
        DocumentTOB docCtmId = new DocumentTOB(dtb_Id,"");
        Database<DocumentTOB, DocumentTOBList> database1 = new DocumentTOB_DBConnect();
        String q1 = "  Select * FROM documenttransactionofborrow WHERE Dtb_id = '"+dtb_id+"'   ";
        docCtmId = database1.readRecord(q1);

        //ดึงข้อมูล ชื่อลูกค้าจาก ตาราง customer Database loan_system
        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_id = '"+docCtmId.getDtb_customerId()+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

//        System.out.println("select customer is : " + customer.getCtm_firstname());

        customerIdLabel.setText(docCtmId.getDtb_customerId());
        nameLabel.setText(customer.getCtm_firstname());
        lastnameLabel.setText(customer.getCtm_lastname());
        idLabel.setText(dtb_id);

        // Parses the date
        LocalDate dt = LocalDate.parse(docCtmId.getDtb_date());

        // Prints the day number
        String date = String.valueOf(dt.getDayOfMonth());
        String month= String.valueOf(dt.getMonthValue());
        String year = String.valueOf(dt.getYear());



        String setDocDate = date + "-" + month + "-" + year ;
        dtbDateLabel.setText(setDocDate);


        if(docCtmId.getDtb_d1().equals("")){
            status1Label.setText("ไม่มี");
        }else {
            status1Label.setText("มี");
        }
        if (docCtmId.getDtb_d2().equals("")){
            status2Label.setText("ไม่มี");
        }else{
            status2Label.setText("มี");
        }
        if (docCtmId.getDtb_d3().equals("")){
            status3Label.setText("ไม่มี");
        }else{
            status3Label.setText("มี");
        }
        if (docCtmId.getDtb_d4().equals("")){
            status4Label.setText("ไม่มี");
        }else{
            status4Label.setText("มี");
        }

        //for loan2Controller --> ส่ง รหัสพนักงานที่ login กับ customer id ที่จะบันทักสัญญา ไป loan2Controller
        empLoginWithCtm_idForLoan.setLoan_customerId(docCtmId.getDtb_customerId());
        empLoginWithCtm_idForLoan.setLoan_Emp1(empLoginAccount.getEmp_id());
    }

    private void showEmpLoginData(Employee empLoginAccount) {
        empNameLabel.setText(empLoginAccount.getEmp_name());
    }

    @FXML
    void handleApproveButton(ActionEvent event) {
        if(selectedCustomer.equals("0")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากดเลือกเอกสารประกอบการกู้ยืมเพื่ออนุมัติ");
            alert.showAndWait();
        }else{
            try {
                FXRouter.goTo("creditboard_update2", empLoginWithCtm_idForLoan);
            } catch (IOException e) {
                System.err.println("ไปที่หน้า creditboard_update2 ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    void clickBackToEmp_home(MouseEvent event) {
        try {
            FXRouter.goTo("creditboard_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า creditboard_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }
}
