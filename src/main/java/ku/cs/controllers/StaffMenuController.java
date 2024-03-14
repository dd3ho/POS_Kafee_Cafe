package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import ku.cs.FXRouter;
import ku.cs.models.User;

import java.awt.event.MouseEvent;
import java.io.IOException;

public class StaffMenuController {

    User user;

    @FXML
    private void initialize() {
        user = (User) FXRouter.getData();
    }

    @FXML
    private void handleSignupBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_addMember",user);
    }

    @FXML
    private void handleAddOrderBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("shop",user);
    }

    @FXML
    public void handleLogout() throws IOException{
        FXRouter.goTo("pos_login");
    }

    @FXML
    private void handleChangePasswordBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_change_password",user);
    }
}
