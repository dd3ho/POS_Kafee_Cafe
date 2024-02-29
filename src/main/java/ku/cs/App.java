package ku.cs;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;
//    public static final String CURRENCY = "$";
    @Override
    public void start(Stage stage) throws IOException {
        FXRouter.bind(this, stage, "Coffee POS",669, 473);
        configRoute();

        FXRouter.goTo("pos_addDessert");

    }

    private void configRoute() {
        //เรียงด้วยนะ
        String packageStr = "ku/cs/";

        //face detection
        FXRouter.when("signup",packageStr+"signup.fxml");

        //Login
        FXRouter.when("pos_login",packageStr+"pos_login.fxml");

        //coffee POS Admin
        FXRouter.when("pos_signup",packageStr+"pos_signup.fxml");
        FXRouter.when("pos_addDrink",packageStr+"pos_adddrink.fxml");
        FXRouter.when("pos_addDessert",packageStr+"pos_adddessert.fxml");
        FXRouter.when("pos_addMember",packageStr+"pos_member_signup.fxml");
        //coffee POS Staff





        // old
        // String packageStr = "ku/cs/old/";
        FXRouter.when("creditboard_home",packageStr+"creditboard_home.fxml");
        FXRouter.when("creditboard_update",packageStr+"creditboard_update.fxml");
        FXRouter.when("creditboard_update2",packageStr+"creditboard_update2.fxml");

        FXRouter.when("emp_checklist",packageStr+"emp_checklist.fxml");
        FXRouter.when("emp_debt",packageStr+"emp_debt.fxml");
        FXRouter.when("emp_debt_2",packageStr+"emp_debt_2.fxml");
        FXRouter.when("emp_document",packageStr+"emp_document.fxml");
        FXRouter.when("emp_home",packageStr+"emp_home.fxml");
        FXRouter.when("emp_invoice",packageStr+"emp_invoice.fxml");
        FXRouter.when("emp_invoice2",packageStr+"emp_invoice2.fxml");
        FXRouter.when("emp_loan",packageStr+"emp_loan.fxml");
        FXRouter.when("emp_loan2",packageStr+"emp_loan2.fxml");
        FXRouter.when("emp_payDebt",packageStr+"emp_payDebt.fxml");
        FXRouter.when("emp_payDebt2",packageStr+"emp_payDebt2.fxml");
        FXRouter.when("emp_registration", packageStr+"emp_registration.fxml");
        FXRouter.when("emp_report", packageStr+"emp_report.fxml");


        FXRouter.when("login", packageStr+"login.fxml");


        FXRouter.when("manager_assign",packageStr+"manager_assign.fxml");
        FXRouter.when("manager_home",packageStr+"manager_home.fxml");



        FXRouter.when("menu",packageStr+"menu.fxml");
        FXRouter.when("home",packageStr+"home.fxml");


    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    public static void main(String[] args) {
        launch();
    }

}