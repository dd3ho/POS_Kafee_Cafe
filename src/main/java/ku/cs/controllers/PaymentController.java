package ku.cs.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import ku.cs.FXRouter;

import java.io.IOException;

public class PaymentController {
    @FXML
    private void handleBackButton(ActionEvent event) throws IOException {
        FXRouter.goTo("pos_staff_purchase_order");
    }

    @FXML
    public void handleMoney() throws IOException{
    }

    @FXML
    public void handleCard() throws IOException{
    }

    @FXML
    public void handleQR() throws IOException{
    }
}
