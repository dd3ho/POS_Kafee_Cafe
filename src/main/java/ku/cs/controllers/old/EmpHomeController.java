package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.FXRouter;
import ku.cs.models.old.Employee;

import java.io.IOException;

public class EmpHomeController {

    @FXML
    public Label empNameLabel;

    //---------------------------------------------------------------------------
    public Employee empLoginAccount;

    //----------------------------------------------------------------------------
    @FXML
    public void initialize(){
        empLoginAccount = (Employee) FXRouter.getData();
        showEmpLoginData(empLoginAccount);
    }

    @FXML
    private void showEmpLoginData(Employee loginAccount){
        empNameLabel.setText(loginAccount.getEmp_name());
    }

    @FXML
    public void clickBackToLogin(Event event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //ลงทะเบียนลูกค้าใหม่
    @FXML
    void clickToRegister(ActionEvent event) {
        try {
            FXRouter.goTo("emp_registration");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_registration ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //บันทึกเอกสารรายได้
    @FXML
    void clickToEmp_Document(ActionEvent event) {
        try {
            FXRouter.goTo("emp_document");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_document ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //บันทึกสัญญาเงินกู้
    @FXML
    void clickToEmp_Loan(ActionEvent event) {
        try {
            FXRouter.goTo("emp_loan" ,empLoginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_loan ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //ตรวจสอบรายชื่อลูกหนี้
    @FXML
    void clickToEmp_checklist(ActionEvent event) {
        try {
            FXRouter.goTo("emp_checklist");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_checklist ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //ออกใบแจ้งหนี้
    @FXML
    void clickToEmp_invoice(ActionEvent event) {
        try {
            FXRouter.goTo("emp_invoice", empLoginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_invoice ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //ติดตามหนี้
    @FXML
    void clickToEmp_Debt(ActionEvent event) {
        try {
            FXRouter.goTo("emp_debt");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_Debt ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }


    //หักชำระหนี้
    @FXML
    void clickToEmp_payDebt(ActionEvent event) {
        try {
            FXRouter.goTo("emp_payDebt");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_payDebt ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    //ส่งผลรายงาน
    @FXML
    void clickToEmp_report(ActionEvent event) {
        try {
            FXRouter.goTo("emp_report");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_report ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }



}
