package ku.cs.models;

import java.util.ArrayList;

public class MemberList {
    private ArrayList<Member> members;

    public MemberList(){
        members = new ArrayList<>();
    }

    //เพิ่ม Member
    public void addMember(Member member){
        members.add(member);
    }

    public int countMembers(){
        return members.size();
    }
    public String toCsv() {
        String result = "";
        for (Member member : this.members){
            result += member.toCsv() + "\n";
        }
        return result;
    }
}
