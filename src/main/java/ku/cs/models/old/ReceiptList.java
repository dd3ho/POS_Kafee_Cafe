package ku.cs.models.old;

import java.util.ArrayList;

public class ReceiptList {
    public ArrayList<Receipt> receipts;

    public ReceiptList() {
        this.receipts = new ArrayList<>();
    }


    //เพิ่ม emp
    public void addReceipt(Receipt receipt){
        receipts.add(receipt);
    }

    public String toCsv(){
        String result = "";
        for(Receipt receipt : this.receipts){
            result += receipt.toCsv() + "\n";
        }
        return result;
    }

}
