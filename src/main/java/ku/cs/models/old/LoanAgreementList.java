package ku.cs.models.old;

import java.util.ArrayList;

public class LoanAgreementList {

    public ArrayList<LoanAgreement> loanAgreements;

    public LoanAgreementList() {
        this.loanAgreements = new ArrayList<>();
    }

    //เพิ่ม invoice
    public void addLoan(LoanAgreement loanAgreement){
        loanAgreements.add(loanAgreement);
    }

    public String toCsv(){
        String result = "";
        for(LoanAgreement loanAgreement : this.loanAgreements){
            result += loanAgreement.toCsv() + "\n";
        }
        return result;
    }

    public int countListElement() {
        return loanAgreements.size();
    }

    public LoanAgreement getLoanRecord(int i) {
        LoanAgreement loanAgreement = loanAgreements.get(i);
        return  loanAgreement;

    }
}
