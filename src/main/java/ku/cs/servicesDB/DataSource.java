package ku.cs.servicesDB;

import ku.cs.models.OrderDetailList;

public interface DataSource<T> {

    void writeData(T t);
    T readData(String directoryName, String fileName);

}
