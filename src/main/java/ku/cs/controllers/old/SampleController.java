package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import ku.cs.servicesDB.DatabaseConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class SampleController {

    @FXML
    private Label showUsernameLabel;
    @FXML
    private Label showLastnameLabel;


    public void connectButton(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT Ctm_firstname, Ctm_lastname FROM Customer";

        try {
            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()){
                showUsernameLabel.setText(queryOutput.getString("Ctm_firstname"));
                showLastnameLabel.setText(queryOutput.getString("Ctm_lastname"));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
