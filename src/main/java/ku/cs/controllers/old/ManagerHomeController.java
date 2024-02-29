package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class ManagerHomeController {



    @FXML
    void handleGotoAssignButton(ActionEvent event) {
        try {
            FXRouter.goTo("manager_assign");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า manager_assign ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
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
}
