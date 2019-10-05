package models.member;

import java.util.ArrayList;

public class MemberList {
    private ArrayList<Member> memberList;

    /**
     * Class representing a list with all members and their details.
     */
    public MemberList() {
        this.memberList = new ArrayList<Member>();
    }

    /**
     * Adds a new member to the member list of this project.
     * @param newMember A new member to be added to the project.
     */
    public void addMember(Member newMember) {
        newMember.setIndexNumber(this.memberList.size() + 1);
        this.memberList.add(newMember);
    }

    //remove member: must remove credits, associated tasks, and recalculate
    //and add credits for other members. Index numbers might also need to change

    /**
     * Removes a member from the list.
     * @param memberIndex THe index number of the member to be removed.
     */
    public void removeMember(int memberIndex) {
        this.memberList.remove(memberIndex - 1);
        if (memberIndex < memberList.size()) { //if need to reassign index numbers after removal
            for (int i = 1; i <= memberList.size(); i++) {
                memberList.get(i - 1).setIndexNumber(i);
            }
        }
    }

    /**
     * Returns an ArrayList with String descriptions of members details.
     * @return An ArrayList with String descriptions of members details.
     */
    public ArrayList<String> getAllMemberDetails() {
        ArrayList<String> memberDetails = new ArrayList<>();
        for (Member member : this.memberList) {
            memberDetails.add(member.getDetails());
        }
        return memberDetails;
    }

}
