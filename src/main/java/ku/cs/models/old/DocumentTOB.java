package ku.cs.models.old;

import java.sql.Date;
import java.util.Random;

public class DocumentTOB {

    private String dtb_id;
    private String dtb_customerId;
    private String dtb_d1;
    private String dtb_d2;
    private String dtb_d3;
    private String dtb_d4;
    private String dtb_date;
    private String dtb_status;

    //add DocumentTrans
    public DocumentTOB(String dtb_id, String dtb_customerId, String dtb_document1, String dtb_document2, String dtb_document3, String dtbd4, String dtb_date, String dtb_status) {
        this.dtb_id = dtb_id;
        this.dtb_customerId = dtb_customerId;
        this.dtb_d1 = dtb_document1;
        this.dtb_d2 = dtb_document2;
        this.dtb_d3 = dtb_document3;
        this.dtb_d4 = dtbd4;
        this.dtb_date =dtb_date;
        this.dtb_status = dtb_status;
    }

    //tempDtb for check Dtb_id, Dtb_customerId Is Exits?

    public DocumentTOB(String dtb_id, String dtb_customerId) {
        this.dtb_id = dtb_id;
        this.dtb_customerId = dtb_customerId;
    }

    //test RandomDtb_id
    public DocumentTOB(String randDtb_id){
        this.dtb_id = randDtb_id;
    }

    public String generateDtb_id() {
        Random rd = new Random();
        int rdNum;
        // 10 digit
        String m[] = new String[10];
        for (int i = 0; i < 10; i++) {
            rdNum = rd.nextInt(10);
            m[i] = Integer.toString(rdNum);
        }
        String dtb_id = m[0] + m[1] + m[2] + m[3] + m[4] + m[5] + m[6] + m[7] + m[8] + m[9];
        this.dtb_id = dtb_id;
        return dtb_id;
    }



    public void setDtb_customerId(String dtb_customerId) {
        this.dtb_customerId = dtb_customerId;
    }

    public void setDtb_d1(String dtb_d1) {
        this.dtb_d1 = dtb_d1;
    }

    public void setDtb_d2(String dtb_d2) {
        this.dtb_d2 = dtb_d2;
    }

    public void setDtb_d3(String dtb_d3) {
        this.dtb_d3 = dtb_d3;
    }

    public void setDtb_d4(String dtb_d4) {
        this.dtb_d4 = dtb_d4;
    }

    public void setDtb_date() {
        long millis=System.currentTimeMillis();
        // creating a new object of the class Dte
        java.sql.Date date = new java.sql.Date(millis);
        dtb_date = String.valueOf(date);
    }




    //getter
    public String getDtb_id() {
        return dtb_id;
    }

    public String getDtb_customerId() {
        return dtb_customerId;
    }

    public String getDtb_d1() {
        return dtb_d1;
    }

    public String getDtb_d2() {
        return dtb_d2;
    }

    public String getDtb_d3() {
        return dtb_d3;
    }

    public String getDtb_d4() {
        return dtb_d4;
    }

    public String getDtb_date() {
        return String.valueOf(dtb_date);
    }

    public String getDtb_status() {
        return dtb_status;
    }

    public String toCsv() {
        return dtb_id + "," + dtb_customerId + "," + dtb_d1 + "," + dtb_d2 + "," + dtb_d3 + "," + dtb_d4 + "," +dtb_date + "," + dtb_status;
    }

}
