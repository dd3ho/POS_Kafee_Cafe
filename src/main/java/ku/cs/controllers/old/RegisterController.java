package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.models.old.Customer;
import ku.cs.models.old.CustomerList;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.Database;


import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.time.LocalDate;
import java.util.Optional;


public class RegisterController {

    @FXML
    private ImageView ctmImageView;

    @FXML
    private TextField firstnameTextField;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private TextField IdNumberTextField;

    @FXML
    private CheckBox femaleCheckBox;

    @FXML
    private CheckBox maleCheckBox;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField bankAccountNumberTextField;

    @FXML
    private TextField addressTextField;

    @FXML
    private TextField workplaceTextField;

    //-------------------------------------------------------------------------------------------------------------------------------------
    private String sexCheckBoxStr = "0"; //female = 1, male = 2, is not Selected = 0
    private Customer ctmForSetImageView = new Customer("0");


    private Customer ctmInsertToDB;


    @FXML
    void handleFemaleCheckBox(ActionEvent event) {
        maleCheckBox.setSelected(false);
        if (femaleCheckBox.isSelected()) {
            this.sexCheckBoxStr = "1"; //female = 1, male = 2
        }
    }

    @FXML
    void handleMaleCheckBox(ActionEvent event) {
        femaleCheckBox.setSelected(false); //เมื่อกด check ที่ maleCheckbox ต้อง setSelected(false) เพื่อให้ check ได้แค่กล่องเดียว
        if (maleCheckBox.isSelected()) {
            this.sexCheckBoxStr = "2"; //female = 1, male = 2
        }
    }

    @FXML
    void handleUploadImageButton(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG", "*.png", "*.jpg", "*.jpeg"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("images");
                if (!destDir.exists()) destDir.mkdirs();
                // RENAME FILE
                String[] fileSplit = file.getName().split("\\.");
                String filename = LocalDate.now() + "_" + System.currentTimeMillis() + "."
                        + fileSplit[fileSplit.length - 1];
                Path target = FileSystems.getDefault().getPath(
                        destDir.getAbsolutePath() + System.getProperty("file.separator") + filename
                );
                // COPY WITH FLAG REPLACE FILE IF FILE IS EXIST
                Files.copy(file.toPath(), target, StandardCopyOption.REPLACE_EXISTING);
                // SET NEW FILE PATH TO IMAGE
                this.ctmImageView.setImage(new Image(target.toUri().toString()));
                //setImagePath
                ctmForSetImageView.setCtmImagePath(destDir + "/" + filename);
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());
            }catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @FXML
    void handleRecordButton(ActionEvent event) throws SQLException {


        String idNumberStr = IdNumberTextField.getText();
        String firstnameStr = firstnameTextField.getText();
        String lastnameStr = lastNameTextField.getText();
        String phoneNumStr = phoneNumberTextField.getText();
        String bankAccNumStr = bankAccountNumberTextField.getText();
        String addressStr = addressTextField.getText();
        String workplaceStr = workplaceTextField.getText();

        if (firstnameStr.equals("") || lastnameStr.equals("") || idNumberStr.equals("") || sexCheckBoxStr.equals("0")
                || phoneNumStr.equals("") || bankAccNumStr.equals("") || addressStr.equals("") || workplaceStr.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน");

            alert.showAndWait();

        } else {

            //ถ้าไม่ได้ upload รูป ให้ alert ว่า ใส่รูปด้วย
            if (ctmForSetImageView.getCtm_img().equals("0")) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("ใส่รูปของลูกค้าก่อนกดบันทึก");
                alert.showAndWait();

            } else {

                //prepare For generate ctm_id
                Customer tempCustomerForCheckCid = new Customer("0", idNumberStr);
                Customer tempCustomerForCheckCtm_cid = new Customer("0",idNumberStr);

                String ctm_idStr = null;
                String checkCtm_id = "0";


                while (checkCtm_id.equals("0")){
                    //random ctm_id 7 digit
                    ctm_idStr = tempCustomerForCheckCid.generateCtm_id();

                    // ใช้ Db
                    Database<Customer, CustomerList> database = new Customer_DBConnect();
                    //หา Ctm_id ในตาราง customer ที่ตรงกับ ctm_idStr(เลขที่สุ่ม) ถ้า เจอ--> return account ไม่เจอ return null
                    String queryCheckCtm_id = " SELECT * FROM customer  WHERE Ctm_id = '"+ctm_idStr+"' ";
                    tempCustomerForCheckCid = database.readRecord(queryCheckCtm_id);

                    if (tempCustomerForCheckCid == null){ //หาไม่เจอ
                        checkCtm_id = "1";
                    }else { //หาเจอ
                        checkCtm_id = "0";
                    }
                }//while true ให้ generate ctm_id จนกว่าจะไม่ซ้ำ

                //check ctm_cid ว่าซ้ำกับที่มีอยู่ไหม ถ้าซ้าเข้า if / ไม่ซ้ำ เข้า else
                Database<Customer, CustomerList> database1 = new Customer_DBConnect();
                String queryCheckCtm_cid = " SELECT * FROM customer  WHERE Ctm_cid = '"+idNumberStr+"' ";
                tempCustomerForCheckCtm_cid = database1.readRecord(queryCheckCtm_cid);

                //มีซ้ำเข้า if (เจอ)
                // ไม่ซ้ำ else (ไม่เจอ)

                if (tempCustomerForCheckCtm_cid == null){

                    // ใช้ Db
                    Database<Customer, CustomerList> database2 = new Customer_DBConnect();
                    ctmInsertToDB = new Customer(ctm_idStr,idNumberStr,firstnameStr,lastnameStr,ctmForSetImageView.getCtm_img(),sexCheckBoxStr,phoneNumStr,addressStr,workplaceStr,bankAccNumStr);
                    database2.insertDatabase(ctmInsertToDB);

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error!!");
                    alert.setHeaderText(null);
                    alert.setContentText("ระบบบันทึกข้อมูลลูกค้าสำเร็จ");
                    alert.showAndWait();

                    try {
                        FXRouter.goTo("emp_home");
                    } catch (IOException e) {
                        System.err.println("ไปที่หน้า menu ไม่ได้");
                        System.err.println("ให้ตรวจสอบการกำหนด route");
                    }

                }else{
                    clearTextField();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Error!!");
                    alert.setHeaderText(null);
                    alert.setContentText("ระบบมีฐานข้อมูลของลูกค้ารายนี้แล้ว");
                    alert.showAndWait();
                }
            }
        }
    }

    @FXML
    void handleBackButton(ActionEvent event) {

        //ต้องการกลับไป Menu ใช่ไหม
        //if ใช--> clear text field
        // else ไม่ใช่

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("ต้องการยกเลิกใช่หรือไม่?");
        //capture the dialog result of ok or cancel
        Optional<ButtonType> result = alert.showAndWait();


        if(result.get() == ButtonType.OK){
            clearTextField();
            try {
                FXRouter.goTo("emp_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า menu ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    private void clearTextField() {
        IdNumberTextField.setText("");
        firstnameTextField.setText("");
        lastNameTextField.setText("");
        ctmImageView.setImage(null);
        femaleCheckBox.setSelected(false);
        maleCheckBox.setSelected(false);
        phoneNumberTextField.setText("");
        addressTextField.setText("");
        workplaceTextField.setText("");
        bankAccountNumberTextField.setText("");
    }



}
