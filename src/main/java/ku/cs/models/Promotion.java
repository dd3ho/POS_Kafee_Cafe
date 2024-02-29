package ku.cs.models;

public class Promotion {
    private String pro_code;
    private float pro_pDiscount;
    private float pro_bDiscount;
    private String pro_mnId;

    public Promotion(String pro_code, float pro_pDiscount, float pro_bDiscount, String pro_mnId) {
        this.pro_code = pro_code;
        this.pro_pDiscount = pro_pDiscount;
        this.pro_bDiscount = pro_bDiscount;
        this.pro_mnId = pro_mnId;
    }

    public void setPro_code(String pro_code) {
        this.pro_code = pro_code;
    }

    public void setPro_pDiscount(float pro_pDiscount) {
        this.pro_pDiscount = pro_pDiscount;
    }

    public void setPro_bDiscount(float pro_bDiscount) {
        this.pro_bDiscount = pro_bDiscount;
    }

    public void setPro_mnId(String pro_mnId) {
        this.pro_mnId = pro_mnId;
    }

    public String getPro_code() {
        return pro_code;
    }

    public float getPro_pDiscount() {
        return pro_pDiscount;
    }

    public float getPro_bDiscount() {
        return pro_bDiscount;
    }

    public String getPro_mnId() {
        return pro_mnId;
    }

    public String toCsv() {
        return pro_code + "," + pro_pDiscount + "," + pro_bDiscount + "," + pro_mnId;
    }
}
