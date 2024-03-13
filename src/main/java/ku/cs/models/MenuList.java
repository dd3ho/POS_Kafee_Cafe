package ku.cs.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MenuList {
    private ArrayList<Menu> menus;
    public MenuList(){
        menus = new ArrayList<>();
    }

    //เพิ่ม Member
    public void addMenu(Menu menu){
        menus.add(menu);
    }
    public int countMenus() {
        return menus.size();
    }
    public Menu getMenu(int i){ return menus.get(i);}
    public String toCsv() {
        String result = "";
        for (Menu menu : this.menus){
            result += menu.toCsv() + "\n";
        }
        return result;
    }

    public List<Menu> getMenuList() {
        return menus;
    }
    public void clear() {
        menus.clear();
    }


}
