package ku.cs.controllers.old;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.Employee;
import ku.cs.models.old.EmployeeList;
import ku.cs.models.old.LoanAgreement;
import ku.cs.models.old.LoanAgreementList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Employee_DBConnect;
import ku.cs.servicesDB.old.LoanAgreement_DBConnect;

import java.io.IOException;
import java.util.Optional;

public class ManagerAssignController {

    @FXML
    private ListView<String> loanAgreementListView;

    //ชื่อลูกค้าตามสัญญา
    @FXML private Label firstnameLabel;
    @FXML private Label lastnameLabel;
    @FXML private Label ctmIdLabel;

    //พนักงาน 1
    @FXML private Label emp_nameLabel;
    @FXML private Label emp_idLabel;

    //พนักงาน 2
    @FXML private TextField emp_IdTextField;
    @FXML private Label emp_nameLabel1;


    //listview
    private LoanAgreementList loanAgreementList = new LoanAgreementList();
    private javafx.collections.ObservableList<String> ObservableList;
    private String selectedLoan_id = "0";

    @FXML
    public void initialize(){
        clearLabel();
        //อ่าน database ของ loan Agreement
        Database<LoanAgreement, LoanAgreementList> database = new LoanAgreement_DBConnect();
        String query = " Select * From LoanAgreement Where Loan_balance != 0";
        loanAgreementList = database.readDatabase(query);
        showListView();
        handleLoanSelected();
    }



    private void showListView() {
        ObservableList = FXCollections.observableArrayList();
        for(int i = loanAgreementList.countListElement()-1; i>=0; i--)
        {
            LoanAgreement loanAgreement = loanAgreementList.getLoanRecord(i);
//          ObservableList.add("No."+doc.getDtb_id()+" CustomerId : "+doc.getDtb_customerId()+"  Date : "+doc.getDtb_date());

            ObservableList.add(loanAgreement.getLoan_id());
        }
        loanAgreementListView .setItems(ObservableList);
    }


    private void handleLoanSelected() {
        loanAgreementListView.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable,
                                        String oldValue, String newValue) {
//                        System.out.println(newValue + " is selected");
                        selectedLoan_id = newValue;
                        showSelectedLoan(selectedLoan_id);
                    }
                });
    }

    // id Loan Agreement
    private void showSelectedLoan(String id) {
        //ดึง รหัสลูกค้า จาก loan_id เพื่อเเสดงชื่อ กับ นามสกุล
        LoanAgreement loanAgreement = new LoanAgreement(id,"0","-","-","-",0,"-",0,0,"-","-","-","-");
        Database<LoanAgreement, LoanAgreementList> database = new LoanAgreement_DBConnect();
        String q = "  Select * FROM loanagreement WHERE Loan_id = '"+id+"'   ";
        loanAgreement = database.readRecord(q);

        firstnameLabel.setText(loanAgreement.getLoan_firstname());
        lastnameLabel.setText(loanAgreement.getLoan_lastname());
        ctmIdLabel.setText(loanAgreement.getLoan_customerId());
        emp_idLabel.setText(loanAgreement.getLoan_Emp2());


        Database<Employee, EmployeeList> database1 = new Employee_DBConnect();
        String query = " Select * From employee Where Emp_Id =   '"+loanAgreement.getLoan_Emp2()+"'   ";
        Employee employee = database1.readRecord(query);

        emp_nameLabel.setText(employee.getEmp_name());

    }

    private void clearLabel() {
        firstnameLabel.setText("");
        lastnameLabel.setText("");
        ctmIdLabel.setText("");
        emp_idLabel.setText("");
        emp_nameLabel.setText("");
        emp_nameLabel1.setText("");
        
    }

    @FXML
    void recordButton(ActionEvent event) {
        if(emp_nameLabel1.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณาใส่รหัสพนักงานที่ต้องการมอบหมายให้ดูเเลสัญญาลูกค้า");
            alert.showAndWait();
        }else{
            Database<LoanAgreement, LoanAgreementList> database2 = new LoanAgreement_DBConnect();
            String queryLoan = " UPDATE  loanagreement SET Loan_Emp2 = '"+emp_IdTextField.getText()+"' WHERE Loan_id = '"+selectedLoan_id+"' ";
            database2.updateDatabase(queryLoan);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("บันทึกสำเร็จ");
            alert.showAndWait();

            try {
                FXRouter.goTo("manager_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า manager_home ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    void clickBackToManagerHome(MouseEvent event) {
        try {
            FXRouter.goTo("manager_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า manager_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void findEmployeeButton(ActionEvent event) {
        if(emp_IdTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณกรอกรหัสพนักงานในช่องค้นหา");
            alert.showAndWait();

        }else{
            Database<Employee, EmployeeList> database = new Employee_DBConnect();
            String query = " Select * From employee Where Emp_Id =   '"+emp_IdTextField.getText()+"'   ";
            Employee employee = database.readRecord(query);

            if (employee == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("ไม่มีรหัสพนักงานนี้ในระบบ");
                alert.showAndWait();
            }else{
                emp_nameLabel1.setText(employee.getEmp_name());
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("ค้นหาสำเร็จ");
                alert.showAndWait();
            }

        }
    }

    @FXML
    void handleBackButton(ActionEvent event) {
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
                FXRouter.goTo("manager_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า manager_home ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }
}
