package ku.cs.models;

public class Receipt {
    private String r_id;
    private String r_promotionId;
    private float r_discount;
    private float r_totalPrice;
    private String r_payType;
    private String r_memberId;

    public Receipt(String r_id, String r_promotionId, float r_discount, float r_totalPrice, String r_payType, String r_memberId) {
        this.r_id = r_id;
        this.r_promotionId = r_promotionId;
        this.r_discount = r_discount;
        this.r_totalPrice = r_totalPrice;
        this.r_payType = r_payType;
        this.r_memberId = r_memberId;
    }

    public void setR_id(String r_id) {
        this.r_id = r_id;
    }

    public void setR_promotionId(String r_promotionId) {
        this.r_promotionId = r_promotionId;
    }

    public void setR_discount(float r_discount) {
        this.r_discount = r_discount;
    }

    public void setR_totalPrice(float r_totalPrice) {
        this.r_totalPrice = r_totalPrice;
    }

    public void setR_payType(String r_payType) {
        this.r_payType = r_payType;
    }

    public void setR_memberId(String r_memberId) {
        this.r_memberId = r_memberId;
    }

    public String getR_id() {
        return r_id;
    }

    public String getR_promotionId() {
        return r_promotionId;
    }

    public float getR_discount() {
        return r_discount;
    }

    public float getR_totalPrice() {
        return r_totalPrice;
    }

    public String getR_payType() {
        return r_payType;
    }

    public String getR_memberId() {
        return r_memberId;
    }

    public String toCsv() {
        return r_id + "," + r_promotionId + "," + r_discount + "," + r_totalPrice + "," +
                r_payType+ "," + r_memberId;
    }
}
