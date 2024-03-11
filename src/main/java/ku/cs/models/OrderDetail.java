package ku.cs.models;

public class OrderDetail {
    private String o_Id;
    private String o_receiptId;
    private String o_mnId;

    private String o_mnName;
    private int o_amount;
    private float o_priceTotal;
    private float o_priceByUnit;

    private String o_sweet;
    private String o_milk;

    private String o_detail;

    public OrderDetail(String o_Id, String o_receiptId, String o_mnId, String o_mnName, int o_amount, float o_priceTotal, float o_priceByUnit, String o_sweet, String o_milk, String o_detail) {
        this.o_Id = o_Id;
        this.o_receiptId = o_receiptId;
        this.o_mnId = o_mnId;
        this.o_mnName = o_mnName;
        this.o_amount = o_amount;
        this.o_priceTotal = o_priceTotal;
        this.o_priceByUnit = o_priceByUnit;
        this.o_sweet = o_sweet;
        this.o_milk = o_milk;
        this.o_detail = o_detail;
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

    public String getO_mnName() {
        return o_mnName;
    }

    public int getO_amount() {
        return o_amount;
    }

    public float getO_priceTotal() {
        return o_priceTotal;
    }

    public float getO_priceByUnit() {
        return o_priceByUnit;
    }

    public String getO_sweet() {
        return o_sweet;
    }

    public String getO_milk() {
        return o_milk;
    }

    public String getO_detail() {
        return o_detail;
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

    public void setO_mnName(String o_mnName) {
        this.o_mnName = o_mnName;
    }

    public void setO_amount(int o_amount) {
        this.o_amount = o_amount;
    }

    public void setO_priceTotal(float o_priceTotal) {
        this.o_priceTotal = o_priceTotal;
    }

    public void setO_priceByUnit(float o_priceByUnit) {
        this.o_priceByUnit = o_priceByUnit;
    }

    public void setO_sweet(String o_sweet) {
        this.o_sweet = o_sweet;
    }

    public void setO_milk(String o_milk) {
        this.o_milk = o_milk;
    }

    public void setO_detail(String o_detail) {
        this.o_detail = o_detail;
    }

    @Override
    public String toString() {
        return  o_Id + "," + o_receiptId + "," + o_mnId + "," + o_amount + "," +
                o_priceTotal+ "," + o_priceByUnit + "," + o_sweet + "," + o_milk+","+o_detail;
    }

    public String toCsv() {
        return o_Id + "," + o_receiptId + "," + o_mnId + "," + o_mnName + "," + o_amount + "," +
                o_priceTotal+ "," + o_priceByUnit + "," + o_sweet + "," + o_milk+","+o_detail;
    }

}
