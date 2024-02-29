package model;

import ku.cs.models.DocumentTOB;
import ku.cs.models.DocumentTOBList;
import ku.cs.servicesDB.Database;
import ku.cs.servicesDB.DocumentTOB_DBConnect;
import org.junit.jupiter.api.Test;

public class LoanAgreementTest {

    @Test
    void TestUpdate (){
        String loanInsert = "3723502";
        //เปลี่ยน status ของ dtb ที่ customer = loan_customerId
        DocumentTOB documentTOB = new DocumentTOB("-",loanInsert);
        Database<DocumentTOB, DocumentTOBList> database3 = new DocumentTOB_DBConnect();
        String queryDtb = " UPDATE  documenttransactionofborrow SET Dtb_status = '2' WHERE Dtb_customerId = '"+loanInsert+"' ";
        database3.updateDatabase(queryDtb);
    }



}
