package ku.cs.models;

public class OrderDetail {
    private String o_Id;
    private String o_receiptId;
    private String o_mnId;
    private int o_amount;
    private float o_price;

    public OrderDetail(String o_Id, String o_receiptId, String o_mnId, int o_amount, float o_price) {
        this.o_Id = o_Id;
        this.o_receiptId = o_receiptId;
        this.o_mnId = o_mnId;
        this.o_amount = o_amount;
        this.o_price = o_price;
    }

    public void setO_Id(String o_Id) {
        this.o_Id = o_Id;
    }

    public void setO_receiptId(String o_receiptId) {
        this.o_receiptId = o_receiptId;
    }

    public void setO_mnId(String o_mnId) {
        this.o_mnId = o_mnId;
    }

    public void setO_amount(int o_amount) {
        this.o_amount = o_amount;
    }

    public void setO_price(float o_price) {
        this.o_price = o_price;
    }

    public String getO_Id() {
        return o_Id;
    }

    public String getO_receiptId() {
        return o_receiptId;
    }

    public String getO_mnId() {
        return o_mnId;
    }

    public int getO_amount() {
        return o_amount;
    }

    public float getO_price() {
        return o_price;
    }

    @Override
    public String toString() {
        return o_Id + "," + o_receiptId + "," + o_mnId + "," + o_amount + "," +
                o_price;
    }

    public String toCsv() {
        return o_Id + "," + o_receiptId + "," + o_mnId + "," + o_amount + "," +
                o_price;
    }

}
