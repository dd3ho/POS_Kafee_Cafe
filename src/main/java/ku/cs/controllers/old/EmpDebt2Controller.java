package ku.cs.controllers.old;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ku.cs.FXRouter;
import ku.cs.models.old.Invoice;
import ku.cs.models.old.InvoiceList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.old.Invoice_DBConnect;

import java.io.IOException;
import java.util.Optional;

public class EmpDebt2Controller {

    @FXML private Label firstnameLabel1;
    @FXML private Label lastnameLabel;
    @FXML private Label invoiceIdLabel1;
    @FXML private Label invoiceCtmDebt1;
    @FXML private Label status_invoiceLabel;
    @FXML private ChoiceBox<String> statusChoiceBox;
    @FXML private Label dateLabel;


    //ส่งต่อไปหน้า emp_debt_2
    private Invoice invoiceEmp_debt2 = new Invoice("-","-","-","-","-",0,"-","-","-","-");

    @FXML
    public void initialize(){
        invoiceEmp_debt2 = (Invoice) FXRouter.getData();
        showInvoiceData(invoiceEmp_debt2);

        //set ChoiceBox
        statusChoiceBox.getItems().add("1: ไม่จ่าย 1 เดือน");
        statusChoiceBox.getItems().add("2: ไม่จ่าย 2 เดือน");
        statusChoiceBox.getItems().add("3: ไม่จ่าย 3 เดือน");
        statusChoiceBox.getItems().add("4: จ่าย ");

    }

    private void showInvoiceData(Invoice invoice) {
        invoiceIdLabel1.setText(invoice.getInvoice_id());
        firstnameLabel1.setText(invoice.getInvoice_ctmfirstname());
        lastnameLabel.setText(invoice.getInvoice_ctmlastname());
        invoiceCtmDebt1.setText(String.valueOf(invoice.getInvoice_ctmDebt()));

        //status_invoice  0 = ไม่จ่าย 1=ไม่จ่าย1เดือน 2=ไม่จ่าย2เดือน 3=ไม่จ่าย3เดือน 4=จ่ายแล้ว 5=จ่ายแล้วออกใบเสร็จแล้ว
        if (invoice.getInvoice_status().equals("0")){
            status_invoiceLabel.setText("ไม่จ่าย");
        }
        else if (invoice.getInvoice_status().equals("1")){
            status_invoiceLabel.setText("ไม่จ่าย 1 เดือน");
        }
        else if (invoice.getInvoice_status().equals("2")){
            status_invoiceLabel.setText("ไม่จ่าย 2 เดือน");
        }
        else if (invoice.getInvoice_status().equals("3")){
            status_invoiceLabel.setText("ไม่จ่าย 3 เดือน");
        }


        Invoice invoiceTemp = new Invoice(invoiceEmp_debt2.getInvoice_id(),"","","-","-",0,"","-","-","-");
        //อ่าน database ของ invoice
        Database<Invoice, InvoiceList> database = new Invoice_DBConnect();
        String allInvoiceQuery = " Select * FROM invoice WHERE Invoice_id = '"+invoiceEmp_debt2.getInvoice_id()+"'   ";
        invoiceTemp = database.readRecord(allInvoiceQuery);

        //set วันที่ ที่ออกใบแจ้งหนี้
        String day = invoiceTemp.getInvoice_date();
        String month = invoiceTemp.getInvoice_month();
        String year = invoiceTemp.getInvoice_year();

        String date = day+"-"+month+"-"+year;

        dateLabel.setText(date);

    }

    @FXML
    void recordButton(ActionEvent event) {

        String statusInvoice = statusChoiceBox.getValue();

        //Update
        Database<Invoice, InvoiceList> database= new Invoice_DBConnect();

        if(statusInvoice.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error!!");
            alert.setHeaderText(null);
            alert.setContentText("กรุณาเลือกสถานะของใบแจ้งหนี้ก่อนกดบันทึก");
            alert.showAndWait();
        }else{
            if(statusInvoice.equals("1: ไม่จ่าย 1 เดือน")){
                String queryDtb = " UPDATE  invoice SET Invoice_status = '1' WHERE Invoice_id = '"+invoiceEmp_debt2.getInvoice_id()+"' ";
                database.updateDatabase(queryDtb);
            }else{
                if(statusInvoice.equals("2: ไม่จ่าย 2 เดือน")){
                    String queryDtb = " UPDATE  invoice SET Invoice_status = '2' WHERE Invoice_id = '"+invoiceEmp_debt2.getInvoice_id()+"' ";
                    database.updateDatabase(queryDtb);
                }else{
                    if (statusInvoice.equals("3: ไม่จ่าย 3 เดือน")){
                        String queryDtb = " UPDATE  invoice SET Invoice_status = '3' WHERE Invoice_id = '"+invoiceEmp_debt2.getInvoice_id()+"' ";
                        database.updateDatabase(queryDtb);
                    }else{
                        String queryDtb = " UPDATE  invoice SET Invoice_status = '4' WHERE Invoice_id = '"+invoiceEmp_debt2.getInvoice_id()+"' ";
                        database.updateDatabase(queryDtb);
                    }
                }
            }
        }
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("");
        alert.setHeaderText(null);
        alert.setContentText("บันทึกสำเร็จ");
        alert.showAndWait();

        try {
            FXRouter.goTo("emp_home");
        } catch (IOException e) {
            System.err.println("ไปที่หน้า emp_home ไม่ได้");
            System.err.println("ให้ตรวจสอบการกำหนด route");
        }
    }

    @FXML
    void cancelButton(ActionEvent event) {

        //ต้องการกลับไป Menu ใช่ไหม
        //if ใช--> clear text field
        // else ไม่ใช่

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setContentText("ต้องการยกเลิกใช่หรือไม่?");
        //capture the dialog result of ok or cancel
        Optional<ButtonType> result = alert.showAndWait();

        if(result.get() == ButtonType.OK){
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


}
