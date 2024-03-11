package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class StaffMenuController {

    @FXML
    private void handleSignupBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_addMember");
    }

    @FXML
    private void handleAddOrderBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("shop");
    }
}
