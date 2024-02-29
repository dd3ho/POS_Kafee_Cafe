package ku.cs.models.old;

public class Employee {

    private String emp_id;
    private String emp_name;
    private String emp_jTitle;
    private String emp_password;

    //add employee
    public Employee(String emp_id, String emp_name, String emp_jTitle, String emp_password) {
        this.emp_id = emp_id;
        this.emp_name = emp_name;
        this.emp_jTitle = emp_jTitle;
        this.emp_password = emp_password;
    }

    //for check emploan2
    public Employee(String em2IdStr) {
        this.emp_id = em2IdStr;
    }

    public void setEmp_id(String emp_id) {
        this.emp_id = emp_id;
    }

    public void setEmp_name(String emp_name) {
        this.emp_name = emp_name;
    }

    public void setEmp_jTitle(String emp_jTitle) {
        this.emp_jTitle = emp_jTitle;
    }

    public void setEmp_password(String emp_password) {
        this.emp_password = emp_password;
    }

    public String getEmp_id() {
        return emp_id;
    }

    public String getEmp_name() {
        return emp_name;
    }

    public String getEmp_jTitle() {
        return emp_jTitle;
    }

    public String getEmp_password() {
        return emp_password;
    }



    public String toCsv() {
        return emp_id + "," + emp_name + "," + emp_jTitle + ","  + emp_password;
    }



}
