package ku.cs.models;

import java.util.ArrayList;

public class ReceiptList {
    private ArrayList<Receipt> receipts;
    public ReceiptList(){
        receipts = new ArrayList<>();
    }

    //เพิ่ม Member
    public void addReceipt(Receipt receipt){
        receipts.add(receipt);
    }
    public int countReceipts() {
        return receipts.size();
    }
    public String toCsv() {
        String result = "";
        for (Receipt receipt : this.receipts){
            result += receipt.toCsv() + "\n";
        }
        return result;
    }
}
