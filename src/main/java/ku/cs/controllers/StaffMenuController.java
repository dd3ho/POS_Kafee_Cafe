package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;
import ku.cs.models.User;

import java.io.IOException;

public class StaffMenuController {

    User user;

    @FXML
    private void initialize() {
        user = (User) FXRouter.getData();
    }

    @FXML
    private void handleSignupBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_addMember");
    }

    @FXML
    private void handleAddOrderBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("shop");
    }

    @FXML
    private void handleChangePasswordBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_change_password",user);
    }
}
