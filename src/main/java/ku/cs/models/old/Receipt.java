package ku.cs.models.old;

import java.util.Random;

public class Receipt {
    private String rec_id;
    private String rec_customerId;
    private String rec_date;
    private String rec_month;
    private String rec_year;
    private int rec_payAmt;
    private int rec_balanceLoan;
    private String rec_invoiceId;

   //add Receipt

    public Receipt(String rec_id, String rec_customerId, String rec_date, String rec_month, String rec_year, int rec_payAmt, int rec_balanceLoan, String rec_invoiceId) {
        this.rec_id = rec_id;
        this.rec_customerId = rec_customerId;
        this.rec_date = rec_date;
        this.rec_month = rec_month;
        this.rec_year = rec_year;
        this.rec_payAmt = rec_payAmt;
        this.rec_balanceLoan = rec_balanceLoan;
        this.rec_invoiceId = rec_invoiceId;
    }



    //getter

    public String getRec_id() {
        return rec_id;
    }

    public String getRec_customerId() {
        return rec_customerId;
    }

    public String getRec_date() {
        return rec_date;
    }

    public String getRec_month() {
        return rec_month;
    }

    public String getRec_year() {
        return rec_year;
    }

    public int getRec_payAmt() {
        return rec_payAmt;
    }

    public int getRec_balanceLoan() {
        return rec_balanceLoan;
    }

    public String getRec_invoiceId() {
        return rec_invoiceId;
    }

    //
    public String generateRec_id() {
        Random rd = new Random();
        int rdNum;
        // 10 digit
        String m[] = new String[10];
        for (int i = 0; i < 10; i++) {
            rdNum = rd.nextInt(10);
            m[i] = Integer.toString(rdNum);
        }
        String id = m[0] + m[1] + m[2] + m[3] + m[4] + m[5] + m[6] + m[7] + m[8] + m[9];
        this.rec_id = id;
        return id;
    }

    public String toCsv(){
        return rec_id +","+ rec_customerId +","+ rec_date +","+ rec_payAmt +","
                + rec_balanceLoan +","+ rec_invoiceId;
    }
}
