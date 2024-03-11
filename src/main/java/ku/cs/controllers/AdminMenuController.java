package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class AdminMenuController {
    @FXML
    private void handleSignupBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_signup");
    }
    @FXML
    private void handleAddMenuBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_allMenu");
    }
    @FXML
    private void handleAddPromotionBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_promotion");
    }
}
