package model;

import ku.cs.models.Customer;
import org.junit.jupiter.api.Test;

public class CustomerTest {

    @Test
    void testGenerrateCtmid(){
        //gennerate ctm_id
        Customer randomCtm_id = new Customer("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        String ctm_idStr = randomCtm_id.generateCtm_id();
        System.out.println("ctm_idStr1" + ctm_idStr);
    }

    @Test
    void Isdigit(){
        //gennerate ctm_id
        Customer isDigit = new Customer("0", "0", "0", "0", "0", "0", "0", "0", "0", "0");
        String strTest = "1235555555556";
        System.out.println("1:--> " + String.valueOf(strTest.length()));

        String check = "0";
        while(check.equals("0")){
            for(int i = 0  ; i < strTest.length(); i++){
//            System.out.println(strTest.charAt(i));
                char s = strTest.charAt(i);
//            System.out.println("s = " + s);
                if(s =='0'|| s == '1' || s == '2' || s == '3' || s =='4' || s == '5' || s == '6' || s == '7' || s == '8' || s == '9'){
                    if(i == strTest.length()-1){
                        System.out.println("the last digit is:  "+ s);
                      check = "1";
                    }
                }else{
                    check = "1";
//                    System.out.println("Isn't Digit is : " + s);
//                    System.out.println("here ");
                    break;
                }
            }
        }
        System.out.println("out");
        if (check.equals('1')){

        }else{

        }
    }

    @Test
    public void isNumeric() {
//        String str = "000f";
//        String check = "0";
//        String checkIsDigit = "-"; //isDigit =1 isnotDigit =0
//        while(check.equals("0")){
//            for(int i = 0  ; i < str.length(); i++){
//                char s = str.charAt(i);
//                if(s =='0'|| s == '1' || s == '2' || s == '3' || s =='4' || s == '5' || s == '6' || s == '7' || s == '8' || s == '9'){
//                    if(i == str.length()-1){
//                        check = "1";
//                        checkIsDigit = "1";
//                    }
//                }else{
//                    System.out.println("เข้า 279");
//                    checkIsDigit = "0";
//                    check = "1";
//                    break;
//                }
//            }
//        }
//        System.out.println("285  :"+checkIsDigit);
//        if (checkIsDigit.equals('1')){
//            System.out.println("เข้า286");
////            return true;
//        }
////        return false;

    }




}
