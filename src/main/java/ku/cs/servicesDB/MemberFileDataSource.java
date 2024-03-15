package ku.cs.servicesDB;

import ku.cs.models.Member;
import ku.cs.models.MemberList;
import ku.cs.models.Menu;
import ku.cs.models.MenuList;

import java.io.*;

public class MemberFileDataSource implements DataSource<MemberList>{
    private String directoryName;
    private String filename;

    private MemberList members= new MemberList();

    public MemberFileDataSource() {
        this("Data_csv","Member.csv");
    }


    //for test
    public MemberFileDataSource(String directoryName, String filename) {
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
    public void writeData(MemberList memberList) {
        //วีธีการเขียน สมมติว่า รับ AccountList มา --> เราจะเขียนข้อมูลทั้งหมด ใน AccountList เลย

        String path = directoryName + File.separator + filename;
        File file = new File(path);

        FileWriter writer = null;
        BufferedWriter buffer = null;
        // ป้องกันการเกิด Exception
        try {

            writer = new FileWriter(file);
            buffer = new BufferedWriter(writer);

            buffer.write(memberList.toCsv());


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
    public MemberList readData(String directoryName, String fileName) {
        String path = "Data_csv" + File.separator + "Member.csv";
        File file = new File(path);

        FileReader reader = null;
        BufferedReader buffer = null;

        try {
            reader = new FileReader(file);
            buffer = new BufferedReader(reader);

            String line = "";
            while ( (line = buffer.readLine() ) != null ) {
                String[] data = line.split(",");
                // Menu(0:String mn_Id, 1:String mn_name, 2:Float mn_price,
                // 3:String mn_img, 4:String mn_status, 5:String mn_option, 6:String m_type)
                members.addMember(new Member(
                        data[0],
                        data[1],
                        data[2],
                        data[3],
                        Integer.parseInt(data[4]),
                        data[5],
                        data[6]

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
        return members;
    }


    // menus จากที่อ่าน file
    public MemberList getAllMemberListDataSource() { return members;}
}
