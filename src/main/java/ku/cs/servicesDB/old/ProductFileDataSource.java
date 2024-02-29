//package ku.cs.servicesDB.old;
//
//import ku.cs.models.Product;
//import ku.cs.models.ProductList;
//import ku.cs.servicesDB.DataSource;
//
//import java.io.*;
//public class ProductFileDataSource implements DataSource<ProductList> {
//    private String directoryName;
//    private String filename;
//
//    private ProductList products= new ProductList();
//
//    public ProductFileDataSource() {
//        this("Data","Products.csv");
//    }
//
//    //for test
//    public ProductFileDataSource(String directoryName, String filename) {
//        this.directoryName = directoryName;
//        this.filename = filename;
//        initialFileNotExist();
//    }
//
//    private void initialFileNotExist() {
//        File file = new File(directoryName);
//
//        if(!file.exists()){ //ถ้าdirectory ไม่มีอยู่ให้สร้าง
//            file.mkdir();
//        }
//        //check file --> ต้องการ path
//        String path = directoryName+File.separator+filename;
//
//        file = new File(path); //ชื่อ file.csv
//
//        //ถ้าไม่มี file ให้สร้าง file
//        if(!file.exists()){
//            try {
//                file.createNewFile();
//
//            } catch (IOException e) { e.printStackTrace(); }
//        }
//    }
//
//    @Override
//    public void writeData(ProductList productList) {
//        //วีธีการเขียน สมมติว่า รับ AccountList มา --> เราจะเขียนข้อมูลทั้งหมด ใน AccountList เลย
//
//        String path = directoryName + File.separator + filename;
//        File file = new File(path);
//
//        FileWriter writer = null;
//        BufferedWriter buffer = null;
//        // ป้องกันการเกิด Exception
//        try {
//
//            writer = new FileWriter(file);
//            buffer = new BufferedWriter(writer);
//
//
//            buffer.write(products.toCsv());
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                buffer.close();
//                writer.close();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    @Override
//    public ProductList readData() {
//
//        String path = "Data" + File.separator + "Products.csv";
//        File file = new File(path);
//
//        FileReader reader = null;
//        BufferedReader buffer = null;
//
//        try {
//            reader = new FileReader(file);
//            buffer = new BufferedReader(reader);
//
//            String line = "";
//            while ( (line = buffer.readLine() ) != null ) {
//                String[] data = line.split(",");
//                String type = data[0];
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
//            }
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return products;
//    }
//
//    //products จากที่อ่านไฟล์
//    public ProductList getAllProductList() { return products;}
//
//}
