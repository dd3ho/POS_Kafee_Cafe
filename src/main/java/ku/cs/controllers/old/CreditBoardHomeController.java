package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.Employee;

import java.io.IOException;

public class CreditBoardHomeController {

    @FXML public Label empNameLabel;
    //---------------------------------------------------------------------------
    public Employee empLoginAccount;

    //----------------------------------------------------------------------------
    @FXML
    public void initialize(){
        empLoginAccount = (Employee) FXRouter.getData();
        showEmpLoginData(empLoginAccount);
    }

    private void showEmpLoginData(Employee empLoginAccount) {
        empNameLabel.setText(empLoginAccount.getEmp_name());

    }


    @FXML
    void clickBackToLogin(MouseEvent event) {
        try {
            FXRouter.goTo("login");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า login ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void handle1Button(ActionEvent event) {
        try {
            FXRouter.goTo("creditboard_update", empLoginAccount);
        } catch (IOException e) {
            System.err.println("ไปที่หน้า creditboard_update ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

}
