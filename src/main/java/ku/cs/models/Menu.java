package ku.cs.models;

public class Menu {
    //    mn_status(sell/close), mn_option(low sugar/m/l)
    private String mn_Id;
    private String mn_name;
    private Float mn_price;
    private String mn_img;
    private String mn_status;
    private String mn_option;
    private String m_type;


    //    Constructor

    public Menu(String mn_Id, String mn_name, Float mn_price, String mn_img, String mn_status, String mn_option, String m_type) {
        this.mn_Id = mn_Id;
        this.mn_name = mn_name;
        this.mn_price = mn_price;
        this.mn_img = mn_img;
        this.mn_status = mn_status;
        this.mn_option = mn_option;
        this.m_type = m_type;
    }


    //    setter

    public void setMn_Id(MenuList menus) {
        this.mn_Id = "mn" + String.format("%03d", menus.countMenus()+1);
    }

    public void setMn_name(String mn_name) {
        this.mn_name = mn_name;
    }

    public void setMn_price(Float mn_price) {
        this.mn_price = mn_price;
    }

    public void setMn_img(String mn_img) {
        this.mn_img = mn_img;
    }

    public void setMn_status(String mn_status) {
        this.mn_status = mn_status;
    }

    public void setMn_option(String mn_option) {
        this.mn_option = mn_option;
    }

    public void setM_type(String m_type) {
        this.m_type = m_type;
    }


    //    getter


    public String getMn_Id() {
        return mn_Id;
    }

    public String getMn_name() {
        return mn_name;
    }

    public Float getMn_price() {
        return mn_price;
    }

    public String getMn_img() {
        return mn_img;
    }

    public String getMn_status() {
        return mn_status;
    }

    public String getMn_option() {
        return mn_option;
    }

    public String getM_type() {
        return m_type;
    }

    public String toCsv() {
        return mn_Id + "," + mn_name + "," + mn_price + "," + mn_img + "," +
                mn_status + "," + mn_option + "," + m_type;
    }
}
