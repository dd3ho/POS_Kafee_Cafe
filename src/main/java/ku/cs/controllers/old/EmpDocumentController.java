package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import ku.cs.FXRouter;
import ku.cs.models.old.Customer;
import ku.cs.models.old.CustomerList;
import ku.cs.models.old.DocumentTOB;
import ku.cs.models.old.DocumentTOBList;
import ku.cs.servicesDB.old.Customer_DBConnect;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.DocumentTOB_DBConnect;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.util.Optional;


public class EmpDocumentController {
    @FXML
    private TextField findCtmCidTextField;

    @FXML
    private Label showFirstnameLabel;

    @FXML
    private Label showLastnameLabel;

    @FXML
    private Label showCtmIdLabel;

    //customer ที่ต้องการ บันทึกเอกสาร
    private DocumentTOB dtbForInsertRecord = new DocumentTOB("","","","","","","","");


    @FXML
    public  void initialize(){
        clearShowLabel();
    }

    @FXML
    void findCustomerButton(ActionEvent event) {

        Customer customer = new Customer("0", "0");
        Database<Customer, CustomerList> database = new Customer_DBConnect();
        String q =" Select * FROM customer WHERE Ctm_cid = '"+findCtmCidTextField.getText()+"'  ";
        customer = database.readRecord(q); //เจอ return record ไม่เจอ return null

        if(findCtmCidTextField.getText().equals("")){

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณาใส่เลขบัตรประจำตัวประชาชนของลูกค้าที่ต้องการค้นหา");
            alert.showAndWait();


        }else{

            if (customer == null){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error!!");
                alert.setHeaderText(null);
                alert.setContentText("ไม่พบข้อมูลลูกค้าในระบบโปรดตรวจสอบข้อมูลลูกค้า");
                alert.showAndWait();

                clearShowLabel();


            }else{
                DocumentTOB documentTOB = new DocumentTOB("", "");
                Database<DocumentTOB, DocumentTOBList> databaseDtb = new DocumentTOB_DBConnect();
                String qDtb =" Select * FROM documenttransactionofborrow WHERE Dtb_customerId = '"+customer.getCtm_Id()+"'  ";
                documentTOB = databaseDtb.readRecord(qDtb); //เจอ return record ไม่เจอ return null

                //check ว่าเคยบันทึกเอกสารรายได้หรือยัง --> ยัง if เคย else
                if (documentTOB == null){
                    //for test output return from customer Table
                    System.out.println(customer.toCsv());

                    //setLabel
                    showCtmIdLabel.setText(customer.getCtm_Id());
                    showFirstnameLabel.setText(customer.getCtm_firstname());
                    showLastnameLabel.setText(customer.getCtm_lastname());

                    //set object เป็น record ของลูกค้าที่ต้องการบันทึกเอกสาร    --> ใช้ Ctm_cid หา แต่ต้องเก็บ customerId (Ctm_id)
                    dtbForInsertRecord.setDtb_customerId(showCtmIdLabel.getText());

                }else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Already Done");
                    alert.setHeaderText(null);
                    alert.setContentText("ลูกค้ารายนี้เคยบันทึกเอกสารประกอบการกู้ยืมแล้ว");
                    alert.showAndWait();
                    clearShowLabel();
                }
            }
        }
    }

    @FXML
    void recordToDocumentTableButton(ActionEvent event) {

        if(findCtmCidTextField.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณาใส่เลขบัตรประจำตัวประชาชนของลูกค้าก่อนกดบันทึก");
            alert.showAndWait();
        }else{
            //generate Dtb_id -->//* dtb_id ต้องไม่ซ้ำ

            //prepare For generate ctm_id
            DocumentTOB tempDtbChekDtb_id = new DocumentTOB("0",showCtmIdLabel.getText() );

            String dtb_idStr = null; //เลข Dtb_id 10 digits
            String checkDtb_id = "0"; // if checkDtb_id equal "0" redo to generate Dtb_id again

            while (checkDtb_id.equals("0")){
                //random ctm_id 10 digit
                dtb_idStr = tempDtbChekDtb_id.generateDtb_id();

                // ใช้ Db
                Database<DocumentTOB, DocumentTOBList> database = new DocumentTOB_DBConnect();

                //หา dtb_id ในตาราง dtb ที่ตรงกับ dtb_idStr(เลขที่สุ่ม) ถ้า เจอ--> return account ไม่เจอ return null
                String queryCheckCtm_id = " SELECT * FROM documenttransactionofborrow  WHERE Dtb_id = '"+dtb_idStr+"' ";
                tempDtbChekDtb_id = database.readRecord(queryCheckCtm_id);

                if (tempDtbChekDtb_id == null){ //หาไม่เจอ
                    checkDtb_id = "1";
                }else { //หาเจอ
                    checkDtb_id = "0";
                }
            }//while true ให้ generate Dtb_id จนกว่าจะไม่ซ้ำ

            //set วันที่ออกเอกสาร
            dtbForInsertRecord.setDtb_date();

            // ใช้ Db
            DocumentTOB documentTOB = new DocumentTOB(dtb_idStr, showCtmIdLabel.getText(),dtbForInsertRecord.getDtb_d1(),dtbForInsertRecord.getDtb_d2(),dtbForInsertRecord.getDtb_d3(),
                        dtbForInsertRecord.getDtb_d4(),dtbForInsertRecord.getDtb_date(),"0");

//            System.out.println(" documentTob: " + documentTOB.toCsv());

            //insert
            Database<DocumentTOB, DocumentTOBList> databaseDtb = new DocumentTOB_DBConnect();
            databaseDtb.insertDatabase(documentTOB);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText(null);
            alert.setContentText("ระบบบันทึกข้อมูลลูกค้าสำเร็จ");
            alert.showAndWait();

            clearShowLabel();

            try {
                FXRouter.goTo("emp_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า emp_home ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }

        }
    }

    @FXML
    void cancelButton(ActionEvent event) {
        //ต้องการกลับไป Menu ใช่ไหม
        //if ใช--> clear text field
        // else ไม่ใช่
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("ต้องการกลับสู่เมนูหลัก?");
        //capture the dialog result of ok or cancel
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
            clearShowLabel();
            try {
                FXRouter.goTo("emp_home");
            } catch (IOException e) {
                System.err.println("ไปที่หน้า menu ไม่ได้");
                System.err.println("ให้ตรวจสอบการกำหนด route");
            }
        }
    }

    @FXML
    void clickBackToEmp_home(MouseEvent event) {
        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    private void clearShowLabel() {
        showFirstnameLabel.setText("");
        showLastnameLabel.setText("");
        showCtmIdLabel.setText("");
    }

    @FXML
    void UploadFile1Button(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG PDF", "*.png", "*.jpg", "*.jpeg", "*.pdf"));

        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("DocumentTransactionOfBorrow");
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

                //setImagePath
                dtbForInsertRecord.setDtb_d1(destDir + "/" + filename);
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void UploadFile2Button(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG PDF", "*.png", "*.jpg", "*.jpeg", "*.pdf"));

        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("DocumentTransactionOfBorrow");
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
//                this.ctmImageView.setImage(new Image(target.toUri().toString()));

                //setImagePath
                dtbForInsertRecord.setDtb_d2(destDir + "/" + filename);
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void UploadFile3Button(ActionEvent event) {

        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));

        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG PDF", "*.png", "*.jpg", "*.jpeg", "*.pdf"));

        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("DocumentTransactionOfBorrow");
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
//                this.ctmImageView.setImage(new Image(target.toUri().toString()));

                //setImagePath
                dtbForInsertRecord.setDtb_d3(destDir + "/" + filename);
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());

            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void UploadFile4Button(ActionEvent event) {
        FileChooser chooser = new FileChooser();
        // SET FILECHOOSER INITIAL DIRECTORY
        chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        // DEFINE ACCEPTABLE FILE EXTENSION
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("images PNG JPG PDF", "*.png", "*.jpg", "*.jpeg", "*.pdf"));
        // GET FILE FROM FILECHOOSER WITH JAVAFX COMPONENT WINDOW
        Node source = (Node) event.getSource();
        File file = chooser.showOpenDialog(source.getScene().getWindow());
        if (file != null) {
            try {
                // CREATE FOLDER IF NOT EXIST
                File destDir = new File("DocumentTransactionOfBorrow");
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
//                this.ctmImageView.setImage(new Image(target.toUri().toString()));
                //setImagePath
                dtbForInsertRecord.setDtb_d4(destDir + "/" + filename);
                //System.out.println("Upload: "+accountForSetImagePath.getImagePath());
            }catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}