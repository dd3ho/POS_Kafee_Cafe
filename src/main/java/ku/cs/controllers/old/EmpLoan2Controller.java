package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.*;
import ku.cs.servicesDB.*;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.old.DocumentTOB_DBConnect;
import ku.cs.servicesDB.old.Employee_DBConnect;
import ku.cs.servicesDB.old.LoanAgreement_DBConnect;

import java.io.IOException;
import java.util.Optional;

public class EmpLoan2Controller {

    @FXML
    private TextField termTextField;
    @FXML
    private TextField amountTextField;
    @FXML
    private TextField witness1TextField;
    @FXML
    private TextField witness2TextField;
    @FXML
    private TextField emp2TextField;
    @FXML
    private ChoiceBox<String> typeChoiceBox;
    @FXML
    private Label firstnameLabel;
    @FXML
    private Label lastnameLabel;
    @FXML
    private Label ctmIdLabel;
    @FXML
    private Label ctm_cidLabel;
    @FXML
    private Label emp1Label;


    //    prepare data for emp who login and record loan Agreement with customer Id who is borrower
    public LoanAgreement empLoginWithCtm_idForLoan;

    @FXML
    public void initialize() {
        //รับ รหัสพนักงานที่ login กับ customer id ที่จะบันทีกสัญญา จาก emp_loan1
        empLoginWithCtm_idForLoan = (LoanAgreement) FXRouter.getData();
        showEmpLoginWithCtm_idForLoan(empLoginWithCtm_idForLoan);

        //set ChoiceBox
        typeChoiceBox.getItems().add("0: ไม่มีคนค้ำ");
        typeChoiceBox.getItems().add("1: มีคนค้ำ");


    }

    private void showEmpLoginWithCtm_idForLoan(LoanAgreement empLoginWithCtm_idForLoan) {

        emp1Label.setText(empLoginWithCtm_idForLoan.getLoan_Emp1());
        ctmIdLabel.setText(empLoginWithCtm_idForLoan.getLoan_customerId());

        //อ่าน database จาก customer
        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q = " Select * FROM customer WHERE Ctm_id = '" + empLoginWithCtm_idForLoan.getLoan_customerId() + "'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null
//        System.out.println(customer.toCsv());

        firstnameLabel.setText(customer.getCtm_firstname());
        lastnameLabel.setText(customer.getCtm_lastname());
        ctm_cidLabel.setText(customer.getCtm_cid());

        //set a default value
        typeChoiceBox.setValue("0: ไม่มีคนค้ำ");
    }

    @FXML
    void recordButton(ActionEvent event) {

        if(termTextField.getText().equals("") || amountTextField.getText().equals("") || emp2TextField.getText().equals("") || witness1TextField.getText().equals("") || witness2TextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลลูกค้าให้ครบทุกช่อง");
            alert.showAndWait();
        }else{
            int term = Integer.parseInt(termTextField.getText());
            int amount = Integer.parseInt(amountTextField.getText());
            int balance = amount;
            String em2IdStr = emp2TextField.getText();
            String witness1 = witness1TextField.getText();
            String witness2 = witness2TextField.getText();

            //typeChoiceBox get value
            String type = typeChoiceBox.getValue();
//        System.out.println(type);

            if (type.equals("0: ไม่มีคนค้ำ")) {
                type = "0";
            } else {
                type = "1";
            }

            LoanAgreement loanInsert = new LoanAgreement("0", ctmIdLabel.getText(), firstnameLabel.getText(),
                    lastnameLabel.getText(), type, term, "-", balance, amount, witness1, witness2,
                    emp1Label.getText(), em2IdStr);
            loanInsert.setLoan_date();

//        System.out.println(loanInsert.getLoan_date());


            //tempLoan to get query result
            LoanAgreement tempLoan;
            tempLoan = loanInsert;

            String checkLoan_id = "0";
            String loan_id = "-";

            //generate loan_id then check loan_id IsNotExits?
            while (checkLoan_id.equals("0")) {
                //random ctm_id 15 digit
                loan_id = tempLoan.generateLoan_id();
//            System.out.println("1: "+loan_id);

                // ใช้ Db
                Database<LoanAgreement, LoanAgreementList> database = new LoanAgreement_DBConnect();

                //หา Ctm_id ในตาราง customer ที่ตรงกับ ctm_idStr(เลขที่สุ่ม) ถ้า เจอ--> return account ไม่เจอ return null
                String queryCheckLoan_id = " SELECT * FROM loanagreement  WHERE Loan_id = '" + loan_id + "' ";
                tempLoan = database.readRecord(queryCheckLoan_id);

                if (tempLoan == null) { //หาไม่เจอ
                    checkLoan_id = "1";
                } else { //หาเจอ
                    checkLoan_id = "0";
                }
            }//while true ให้ generate ctm_id จนกว่าจะไม่ซ้ำ

            //check loan_id ว่าซ้ำกับที่มีอยู่ไหม ถ้าซ้ำเข้า if / ไม่ซ้า เข้า else  ใช้ Db
            Database<LoanAgreement, LoanAgreementList> database1 = new LoanAgreement_DBConnect();
            //หา Ctm_id ในตาราง customer ที่ตรงกับ ctm_idStr(เลขที่สุ่ม) ถ้า เจอ--> return account ไม่เจอ return null
            String queryCheckLoan_id = " SELECT * FROM loanagreement  WHERE Loan_customerId = '" + ctmIdLabel.getText() + "' ";
            tempLoan= database1.readRecord(queryCheckLoan_id);

            if (tempLoan == null) {
                //check รหัสพนักงาน emp2 ว่ามีอยู่ไหม
                Employee empTempForCheckEmp2IsExist = new Employee(em2IdStr,"-","-","-");
                Database <Employee, EmployeeList> checkEmp2IsExits = new Employee_DBConnect();
                String queryEmp = " SELECT * FROM employee WHERE Emp_id = '"+em2IdStr+"' ";
                empTempForCheckEmp2IsExist =checkEmp2IsExits.readRecord(queryEmp);

                if(empTempForCheckEmp2IsExist == null){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error!!");
                    alert.setHeaderText(null);
                    alert.setContentText("ไม่มีรหัสพนักงานนี้ในระบบ");
                    alert.showAndWait();

                }else{
                    //insert
                    Database<LoanAgreement, LoanAgreementList> database2 = new LoanAgreement_DBConnect();
                    loanInsert.setLoan_date();
                    loanInsert.setLoan_id(loan_id);
                    database2.insertDatabase(loanInsert);

                    //เปลี่ยน status ของ dtb ที่ customer = loan_customerId
                    Database<DocumentTOB, DocumentTOBList> database3 = new DocumentTOB_DBConnect();
                    String queryDtb = " UPDATE  documenttransactionofborrow SET Dtb_status = '2' WHERE Dtb_customerId = '"+loanInsert.getLoan_customerId()+"' ";
                    database3.updateDatabase(queryDtb);


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

            }else{
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Already Done");
                alert.setHeaderText(null);
                alert.setContentText("ระบบมีข้อมูลกู้ยืมของลูกค้ารายนี้แล้ว");
                alert.showAndWait();
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

    @FXML
    void clickBackToLogin(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
