package ku.cs.servicesDB;

public interface DataSource<T> {

    void writeData(T t);
    T readData();

}
