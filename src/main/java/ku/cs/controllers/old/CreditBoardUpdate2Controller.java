package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.DocumentTOB_DBConnect;
import ku.cs.servicesDB.old.Employee_DBConnect;


import java.io.IOException;
import java.util.Optional;

public class CreditBoardUpdate2Controller {

    @FXML private Label dtb_customerId;
    @FXML private Label fnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label dtb_IdLabel;
    @FXML private CheckBox doc1Checkbox;
    @FXML private CheckBox doc2Checkbox;
    @FXML private CheckBox doc3Checkbox;
    @FXML private CheckBox doc4Checkbox;
    @FXML private ImageView dtbDocImageView;

    @FXML private Label empNameLabel;

    //---------------------------------------------------------------------
    public LoanAgreement empLoginWithCtm_idForLoan;
    private DocumentTOB documentTOB = new DocumentTOB("","");
    //-------------------------------------------------------------------
    String browseD1 = "0";
    String browseD2 = "0";
    String browseD3 = "0";
    String browseD4 = "0"; // browDocument = 1 คือกดแล้ว

    String checkD1 = "0";
    String checkD2 = "0";
    String checkD3 = "0";
    String checkD4 = "0"; //checkDocument = 1 check แล้ว

    @FXML
    public void initialize(){
        //รับ รหัสพนักงานที่ login กับ customer id ที่จะบันทีกสัญญา จาก emp_loan1
        empLoginWithCtm_idForLoan = (LoanAgreement) FXRouter.getData();
        showCustomerWhoAreSelectByCreditBoard();
    }

    private void showCustomerWhoAreSelectByCreditBoard(){

        //set empLabel
        Employee employee = new Employee("-","-","-","-");
        Database<Employee, EmployeeList> findEmp = new Employee_DBConnect();
        String findEmpQuery = " Select * FROM employee WHERE Emp_id = '"+empLoginWithCtm_idForLoan.getLoan_Emp1()+"' ";
        employee = findEmp.readRecord(findEmpQuery);

        empNameLabel.setText(employee.getEmp_name());
        dtb_customerId.setText(empLoginWithCtm_idForLoan.getLoan_customerId());

        //อ่าน database จาก customer
        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q = " Select * FROM customer WHERE Ctm_id = '" + empLoginWithCtm_idForLoan.getLoan_customerId() + "'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null
        System.out.println(customer.toCsv());

        fnameLabel.setText(customer.getCtm_firstname());
        lastnameLabel.setText(customer.getCtm_lastname());

        //อ่าน database ของ documen
        Database<DocumentTOB, DocumentTOBList> findDtb_id = new DocumentTOB_DBConnect();
        String findDtb_idQuery = " Select * FROM documenttransactionofborrow WHERE Dtb_customerId = '"+empLoginWithCtm_idForLoan.getLoan_customerId()+"'  ";
        //เอาที่อ่านจาก database มาใส่ Object
       documentTOB = findDtb_id.readRecord(findDtb_idQuery);
       dtb_IdLabel.setText(documentTOB.getDtb_id());
    }

    @FXML
    void handleDoc1Button(ActionEvent event) {
        browseD1 = "1";
        if(documentTOB.getDtb_d1().equals("")){
            dtbDocImageView.setImage(new Image("file:"+null, true));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ผู้ขอกู้รายนี้ไม่ได้ยื่นเอกสารหมายเลข1");
            alert.showAndWait();
        }else {
            dtbDocImageView.setImage(new Image("file:"+documentTOB.getDtb_d1(), true));
        }

    }

    @FXML
    void handleDoc2Button(ActionEvent event) {
        browseD2 = "1";
        if(documentTOB.getDtb_d2().equals("")){
            dtbDocImageView.setImage(new Image("file:"+null, true));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ผู้ขอกู้รายนี้ไม่ได้ยื่นเอกสารหมายเลข2");
            alert.showAndWait();
        }else {
            dtbDocImageView.setImage(new Image("file:"+documentTOB.getDtb_d2(), true));
        }
    }

    @FXML
    void handleDoc3Button(ActionEvent event) {
        browseD3 = "1";
        if(documentTOB.getDtb_d3().equals("")){
            dtbDocImageView.setImage(new Image("file:"+null, true));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ผู้ขอกู้รายนี้ไม่ได้ยื่นเอกสารหมายเลข3");
            alert.showAndWait();
        }else {
            dtbDocImageView.setImage(new Image("file:"+documentTOB.getDtb_d3(), true));
        }
    }

    @FXML
    void handleDoc4Button(ActionEvent event) {
        browseD4 = "1";
        if(documentTOB.getDtb_d4().equals("")){
            dtbDocImageView.setImage(new Image("file:"+null, true));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("ผู้ขอกู้รายนี้ไม่ได้ยื่นเอกสารหมายเลข4");
            alert.showAndWait();
        }else {
            dtbDocImageView.setImage(new Image("file:"+documentTOB.getDtb_d4(), true));
        }
    }

    @FXML
    void approveButton(ActionEvent event) {
        if (browseD1.equals("1") && browseD2.equals("1") && browseD3.equals("1") && browseD4.equals("1")){
            if(checkD1.equals("1") && checkD2.equals("1") && checkD3.equals("1") && checkD4.equals("1")){

                //update status การอนุมัติ --> เปลี่ยน status ของ dtb ที่ customer = loan_customerId
                Database<DocumentTOB, DocumentTOBList> database3 = new DocumentTOB_DBConnect();
                String queryDtb = " UPDATE  documenttransactionofborrow SET Dtb_status = '1' WHERE Dtb_customerId = '"+empLoginWithCtm_idForLoan.getLoan_customerId()+"' ";
                database3.updateDatabase(queryDtb);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("ระบบทำการอนุมัติเอกสารของคุณ " +fnameLabel.getText()+" "+lastnameLabel.getText() +" แล้ว");
                alert.showAndWait();

                try {
                    FXRouter.goTo("creditboard_home");
                } catch (IOException e) {
                    System.err.println("ไปที่หน้า menu ไม่ได้");
                    System.err.println("ให้ตรวจสอบการกำหนด route");
                }

            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("กรุณากดเลือกเอกสารให้ผ่านทุกช่องก่อนกดอนุมัติ");
                alert.showAndWait();
            }
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากดเลือกดูเอกสารให้ครบก่อนอนุมัติเอกสาร");
            alert.showAndWait();
        }

    }


    @FXML
    void handleDoc1Checkbox(ActionEvent event) {
        if(doc1Checkbox.isSelected()){
            this.checkD1 = "1";
        }
    }
    @FXML
    void handleDoc2Checkbox(ActionEvent event) {
        if(doc2Checkbox.isSelected()){
            this.checkD2 = "1";
        }
    }
    @FXML
    void handleDoc3Checkbox(ActionEvent event) {
        if(doc3Checkbox.isSelected()){
            this.checkD3 = "1";
        }
    }
    @FXML
    void handleDoc4Checkbox(ActionEvent event) {
        if(doc4Checkbox.isSelected()){
            this.checkD4 = "1";
        }
    }



    @FXML
    void disapprovedButton(ActionEvent event) {
        //ต้องการกลับไป Menu ใช่ไหม
        //if ใช--> clear text field
        // else ไม่ใช่

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("ต้องการยกเลิกใช่หรือไม่");
        //capture the dialog result of ok or cancel
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            try {
                FXRouter.goTo("creditboard_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า menu ไม่ได้");
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
