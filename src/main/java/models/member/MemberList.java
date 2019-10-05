package models.member;

import java.util.ArrayList;

public class MemberList {
    ArrayList<Member> memberList;

    public MemberList() {
        this.memberList = new ArrayList<Member>();
    }

    public void addMember(String name, String phone, String email) {
        Member newMember = new Member(name, phone, email, memberList.size() + 1);
        this.memberList.add(newMember);
    }

    //remove member: must remove credits, associated tasks, and recalculate
    //and add credits for other members. Index numbers might also need to change
    public void removeMember(int memberIndex) {
        this.memberList.remove(memberIndex - 1);
        if (memberIndex < memberList.size()) { //if need to reassign index numbers after removal
            for (int i = 1; i <= memberList.size(); i ++) {
                memberList.get(i-1).setIndexNumber(i);
            }
        }
    }

    public ArrayList<String> getMemberDetails() {
        ArrayList<String> memberDetails = new ArrayList<>();
        for (Member member : this.memberList) {
            memberDetails.add(member.getDetails());
        }
        return memberDetails;
    }
}
