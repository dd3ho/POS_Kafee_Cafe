package ku.cs.servicesDB;

public interface Database<T, C> {

    //ใส่ object --> insert ข้อมูลใน table
    void insertDatabase(T t);

    T readRecord(String query);

    //ใส่ query return เป็น list
    C readDatabase(String q);

    //ใส่ query --> update table
    void updateDatabase(String q);
}
