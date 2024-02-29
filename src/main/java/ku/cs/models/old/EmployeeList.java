package ku.cs.models.old;

import java.util.ArrayList;

public class EmployeeList {

    public ArrayList<Employee> employees;

    public EmployeeList() {
        employees = new ArrayList<>();
    }

//    //เพิ่ม emp
//    public void addEmployee(Employee employee){
//        employees.add(employee);
//    }

    public String toCsv(){
        String result = "";
        for(Employee employee : this.employees){
            result += employee.toCsv() + "\n";
        }
        return result;
    }

}
