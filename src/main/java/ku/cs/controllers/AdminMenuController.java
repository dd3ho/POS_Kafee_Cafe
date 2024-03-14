package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;
import ku.cs.models.User;

import java.io.IOException;

public class AdminMenuController {
    User user;
    @FXML
    private void initialize() {
        user = (User) FXRouter.getData();
    }
    @FXML
    private void handleSignupBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_signup",user);
    }
    @FXML
    private void handleAddMenuBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_allMenu",user);
    }
    @FXML
    private void handleAddPromotionBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_promotion",user);
    }
}
