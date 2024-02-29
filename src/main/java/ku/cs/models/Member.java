package ku.cs.models;
import java.sql.Blob;

public class Member {

    private String m_Id;
    private String m_firstname;
    private String m_lastname;
    private String m_tel;
    private String m_img;
    private Integer m_points;

    private String m_date_join;

    private String m_lasted;

    //variable
    int memberCount;

    //constructor
    public Member(String mId, String mFirstname, String mLastname, String mTel, int mPoints, String mDateJoin, String mLasted) {
        this.m_Id =mId;
        this.m_firstname = mFirstname;
        this.m_lastname = mLastname;
        this.m_tel = mTel;
        this.m_points = mPoints;
        this.m_date_join = mDateJoin;
        this.m_lasted = mLasted;
    }


    // Getter, Setter


    public void setM_Id(MemberList memberList) {
        this.m_Id = "m" + String.format("%06d", memberList.countMembers()+1);
    }

    public void setM_firstname(String m_firstname) {
        this.m_firstname = m_firstname;
    }

    public void setM_lastname(String m_lastname) {
        this.m_lastname = m_lastname;
    }

    public void setM_tel(String m_tel) {
        this.m_tel = m_tel;
    }

    public void setM_img(String m_img) {
        this.m_img = m_img;
    }

    public void setM_points(Integer m_points) {
        this.m_points = m_points;
    }

    public void setM_date_join() {
        long millis=System.currentTimeMillis();
        // creating a new object of the class Dte
        java.sql.Date date = new java.sql.Date(millis);
        this.m_date_join = String.valueOf(date);
    }

    public void setM_lasted() {
        long millis=System.currentTimeMillis();
        // creating a new object of the class Dte
        java.sql.Date date = new java.sql.Date(millis);
        this.m_lasted = String.valueOf(date);
    }

    public String getM_Id() {
        return m_Id;
    }

    public String getM_firstname() {
        return m_firstname;
    }

    public String getM_lastname() {
        return m_lastname;
    }

    public String getM_tel() {
        return m_tel;
    }

    public String getM_img() {
        return m_img;
    }

    public Integer getM_points() {
        return m_points;
    }

    public String getM_date_join() {
        return m_date_join;
    }

    public String getM_lasted() {
        return m_lasted;
    }

    @Override
    public String toString() {
        return m_Id + "," + m_firstname + "," + m_lastname + "," + m_tel + "," +
                m_points + "," + m_date_join + "," + m_lasted;
    }

    public String toCsv() {
        return m_Id + "," + m_firstname + "," + m_lastname + "," + m_tel + "," +
                m_points + "," + m_date_join + "," + m_lasted;
    }

    // other method

//    private String DateTimeNow() {
//        String dateTime;
//        long millis=System.currentTimeMillis();
//        // creating a new object of the class Dte
//        java.sql.Date date = new java.sql.Date(millis);
//        dateTime = String.valueOf(date);
//        return dateTime;
//    }


}