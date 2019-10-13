package models.member;

import java.util.ArrayList;
import util.ParserUtil;

public class ProjectMemberList implements IMemberList{
    private ArrayList<Member> memberList;

    /**
     * Class representing a list with all members and their details.
     */
    public ProjectMemberList() {
        this.memberList = new ArrayList<Member>();
    }

    /**
     * Adds a new member to the member list of this project.
     * @param newMember A new member to be added to the project.
     */
    @Override
    public void addMember(Member newMember) {
        newMember.setIndexNumber(this.memberList.size() + 1);
        this.memberList.add(newMember);
    }

    /**
     * Updates the member details of an existing member using the index number of the member.
     * @param memberIndexNumber The index number of the member whose details are to be updated.
     * @param updatedMemberDetails The updated member details.
     */
    public void editMember(int memberIndexNumber, String updatedMemberDetails) {
        String [] memberDetails = ParserUtil.memberCreation(updatedMemberDetails);
        String name = memberDetails[0];
        String phone = memberDetails[1];
        String email = memberDetails[2];

        for (int i = 0; i < memberList.size(); i++) {
            Member currentMember = memberList.get(i);
            if (currentMember.getIndexNumber() == memberIndexNumber) {
                currentMember.updateDetails(name,phone,email);
                break;
            }
        }
    }
    //remove member: must remove credits, associated tasks, and recalculate
    //and add credits for other members. Index numbers might also need to change

    /**
     * Removes a member from the list.
     * @param memberIndex THe index number of the member to be removed.
     */
    @Override
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
    @Override
    public ArrayList<String> getAllMemberDetails() {
        ArrayList<String> memberDetails = new ArrayList<>();
        for (Member member : this.memberList) {
            memberDetails.add(member.getDetails());
        }
        return memberDetails;
    }

    /**
     * Returns the size of the current member list.
     * @return The size of the current member list.
     */
    public int getNumOfMembers() {
        return memberList.size();
    }

}
