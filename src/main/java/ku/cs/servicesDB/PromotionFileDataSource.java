package ku.cs.servicesDB;

import ku.cs.models.OrderDetail;
import ku.cs.models.OrderDetailList;
import ku.cs.models.Promotion;
import ku.cs.models.PromotionList;

import java.io.*;

public class PromotionFileDataSource implements DataSource<PromotionList>{
    private String directoryName;
    private String filename;

    private PromotionList promotions= new PromotionList();

    public PromotionFileDataSource() {
        this("Data_csv","PromotionAll.csv");
    }




    //for test --> OrderNow.CSV
    public PromotionFileDataSource(String directoryName, String filename) {
        this.directoryName = directoryName;
        this.filename = filename;
        initialFileNotExist();
    }

    private void initialFileNotExist() {
        File file = new File(directoryName);

        if(!file.exists()){ //ถ้าdirectory ไม่มีอยู่ให้สร้าง
            file.mkdir();
        }
        //check file --> ต้องการ path
        String path = directoryName+File.separator+filename;

        file = new File(path); //ชื่อ file.csv

        //ถ้าไม่มี file ให้สร้าง file
        if(!file.exists()){
            try {
                file.createNewFile();

            } catch (IOException e) { e.printStackTrace(); }
        }
    }


    @Override
    public void writeData(PromotionList promotionList) {
        //วีธีการเขียน สมมติว่า รับ AccountList มา --> เราจะเขียนข้อมูลทั้งหมด ใน AccountList เลย

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;
        // ป้องกันการเกิด Exception
        try {

            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(promotions.toCsv());


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public PromotionList readData(String directoryName, String fileName) {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ( (line = buffer.readLine() ) != null ) {
                String[] data = line.split(",");
                //Promotion
                // [1] pro_code , [2] pro_pDiscount, [3] pro_bDiscount, [4] pro_mnId
                promotions.addPromotion(new Promotion(
                        data[0],
                        (float) Double.parseDouble(data[1]),
                        Float.parseFloat(data[2]),
                        data[3]

                ));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return promotions;
    }
}
