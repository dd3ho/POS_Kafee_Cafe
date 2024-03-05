package ku.cs.servicesDB;


import ku.cs.models.*;

import java.io.*;

public class OrderDetailFileDataSource implements DataSource<OrderDetailList>{
    private String directoryName;
    private String filename;

    private OrderDetailList orders= new OrderDetailList();

    public OrderDetailFileDataSource() {
        this("Data_csv","OrderALL.csv");
    }


    //for test --> OrderNow.CSV
    public OrderDetailFileDataSource(String directoryName, String filename) {
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
    public void writeData(OrderDetailList orders) {
        //วีธีการเขียน สมมติว่า รับ AccountList มา --> เราจะเขียนข้อมูลทั้งหมด ใน AccountList เลย

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;
        // ป้องกันการเกิด Exception
        try {

            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(orders.toCsv());


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
    public OrderDetailList readData(String directoryName, String filename) {
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
                //Order Detail
                // [1] o_Id , [2] o_receiptId, [3] o_mnId, [4] o_mnName
                // [5] o_amount, [6] o_priceTotal [7] o_priceByUnit [8] o_sweet
                // [9] o_milk
                orders.addOrder(new OrderDetail(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Integer.valueOf(data[4]),
                        Float.valueOf(data[5]),
                        Float.valueOf(data[6]),
                        data[7],
                        data[8]

                ));
//                if(type.equals("Seller")){
//                    products.addProduct(new Product(
//                            data[1],
//                            data[2],
//                            data[3],
//                            Double.parseDouble(data[4]),
//                            Integer.parseInt(data[5]),
//                            data[6],
//                            data[7],
//                            data[8],
//                            Integer.parseInt(data[9])
//
//                    ));
//                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return orders;
    }

    // menus จากที่อ่าน file
    public OrderDetailList getAllOrderDetailListDataSource() { return orders;}

    public void clearData() {
        String path = directoryName + File.separator + filename;
        File file = new File(path);

        // Check if the file exists before clearing
        if (file.exists()) {
            try {
                // Delete the existing file
                file.delete();

                // Recreate an empty file
                file.createNewFile();

                // Clear the in-memory list (optional)
                orders.clear();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
