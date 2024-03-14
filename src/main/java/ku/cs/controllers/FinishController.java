package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class FinishController {
    @FXML
    private void handleFinishButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_staff_menu");
    }
}
