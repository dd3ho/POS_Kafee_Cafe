package ku.cs.models.old;

import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class LoanAgreement {
    private String loan_id;
    private String loan_customerId;
    private String loan_firstname;
    private String loan_lastname;
    private String loan_type; //รูปแบบเงินกู้
    private int loan_term; //อายุสัญญา
    private String loan_date; //วันที่ออกสัญญา
    private int loan_balance;//ยอดคงเหลือที่ต้องชำระ
    private int loan_amount;// จำนวนเงินกู้
    private String loan_witness1;
    private String loan_witness2;
    private String loan_Emp1;
    private String loan_Emp2;


    //constructor
    public LoanAgreement(String id, String customerId, String fname, String lname, String type,int term, String date,
                         int balance, int amount, String witness1, String witness2, String emp1, String emp2)
    {
        this.loan_id = id;
        this.loan_customerId = customerId;
        this.loan_firstname = fname;
        this.loan_lastname = lname;
        this.loan_type = type;
        this.loan_term = term;
        this.loan_date = date;
        this.loan_balance = balance;
        this.loan_amount = amount;
        this.loan_witness1 = witness1;
        this.loan_witness2 = witness2;
        this.loan_Emp1 = emp1;
        this.loan_Emp2 = emp2;
    }

    //ใช้หน้า EmpLoan ในการส่ง รหัสพนักที่ login ในระบบ กับ customer id ที่จะ ทำสัญญา
    public LoanAgreement(String customerId, String emp1) {
        this.loan_customerId = customerId;
        this.loan_Emp1 = emp1;
    }

    //for query of insert loan emp_controller
    public LoanAgreement(String id, String loan_customerId, String loan_firstname) {
        this.loan_id = id;
        this.loan_customerId = loan_customerId;
        this.loan_firstname = loan_firstname;
    }


    //setter

    public void setLoan_id(String loan_id) {
        this.loan_id = loan_id;
    }

    public void setLoan_customerId(String loan_customerId) {
        this.loan_customerId = loan_customerId;
    }



    public String setLoan_date() {
        long millis=System.currentTimeMillis();
        // creating a new object of the class Dte
        java.sql.Date date = new java.sql.Date(millis);
        this.loan_date = String.valueOf(date);
        return loan_date;
    }


    public void setLoan_Emp1(String loan_Emp1) {
        this.loan_Emp1 = loan_Emp1;
    }



    //Getter

    public String getLoan_id() {
        return loan_id;
    }

    public String getLoan_customerId() {
        return loan_customerId;
    }

    public String getLoan_firstname() {
        return loan_firstname;
    }

    public String getLoan_lastname() {
        return loan_lastname;
    }

    public String getLoan_type() {
        return loan_type;
    }

    public int getLoan_term() {
        return loan_term;
    }

    public String getLoan_date() {
        return loan_date;
    }

    public int getLoan_balance() {
        return loan_balance;
    }

    public int getLoan_amount() {
        return loan_amount;
    }

    public String getLoan_witness1() {
        return loan_witness1;
    }

    public String getLoan_witness2() {
        return loan_witness2;
    }

    public String getLoan_Emp1() {
        return loan_Emp1;
    }

    public String getLoan_Emp2() {
        return loan_Emp2;
    }

    //generateLoan_id
    public String generateLoan_id() {
        Random random = new Random();
        long n = (long) (100000000000000L + random.nextFloat() * 900000000000000L);
        return String.valueOf(n);
    }

    public String toCsv() {
        return loan_id +","+ loan_customerId +","+ loan_firstname +","+ loan_lastname +","+ loan_type +","+ loan_type
                +","+ loan_date +","+ loan_balance +","+ loan_amount +","+ loan_witness1 +","+ loan_witness2
                +","+ loan_Emp1 +","+ loan_Emp2;
    }
}
