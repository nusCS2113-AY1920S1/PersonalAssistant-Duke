package models.member;

import java.util.ArrayList;
import java.util.HashSet;

public class ListOfMemebersAssignedToTask implements IMemberList {
    private ArrayList<Member> memberList;

    public ListOfMemebersAssignedToTask() {
        this.memberList = new ArrayList<>();
    }

    @Override
    public void addMember(Member newMember) {
        this.memberList.add(newMember);
    }

    @Override
    public void removeMember(int memberIndex) {
        this.memberList.remove(this.memberList.get(memberIndex));
    }

    @Override
    public ArrayList<String> getAllMemberDetails() {
        ArrayList<String> memberDetails = new ArrayList<>();
        for (Member member : this.memberList) {
            memberDetails.add(member.getDetails());
        }
        return memberDetails;
    }

    @Override
    public Member getMember(int index) {
        return this.memberList.get(index - 1);
    }

    /**
     * Returns a set of integers representing the index numbers of members who were assigned
     * the task.
     * @return a set of index numbers of members who were assigned the task.
     */
    public HashSet<Integer> getAssignedMembersIndexNumbers() {
        HashSet<Integer> assignedMemberIndexes = new HashSet<>();
        for (Member member : this.memberList) {
            assignedMemberIndexes.add(member.getIndexNumber());
        }
        return assignedMemberIndexes;
    }
}
