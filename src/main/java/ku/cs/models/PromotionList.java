package ku.cs.models;

import ku.cs.servicesDB.DataSource;
import ku.cs.servicesDB.PromotionFileDataSource;

import java.io.IOException;
import java.util.ArrayList;

public class PromotionList {
    private ArrayList<Promotion> promotions;
    public PromotionList(){
        promotions = new ArrayList<>();
    }

    //เพิ่ม Member
    public void addPromotion(Promotion promotion){
        promotions.add(promotion);
    }
    public int countPromotion() {
        return promotions.size();
    }
    public Promotion getPromotion(int i){ return promotions.get(i);}
    public String toCsv() {
        String result = "";
        for (Promotion promotion : this.promotions){
            result += promotion.toCsv() + "\n";
        }
        return result;
    }
    //return OrderDetail ใน list ตัวที่ i
    public  Promotion getPromotionRecord(int i){
        Promotion promotion = promotions.get(i);
        return promotion;
    }

//    clear list
    public void clearListPromotion(){
        promotions.clear();
    }


}
