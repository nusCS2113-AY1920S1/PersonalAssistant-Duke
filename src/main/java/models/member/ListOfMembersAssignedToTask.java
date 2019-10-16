package models.member;

import java.util.ArrayList;
import java.util.HashSet;

public class ListOfMembersAssignedToTask implements IMemberList {
    private ArrayList<Member> memberList;

    public ListOfMembersAssignedToTask() {
        this.memberList = new ArrayList<>();
    }

    @Override
    public void addMember(Member newMember) {
        this.memberList.add(newMember);
    }

    //I rewrote this method because there was a bug in this particular case.
    //Imagine I assigned task to members 1 and 3, and then I want to unassign 3.
    //Using the index number 3 to unassign will result in IndexOutOfBounds.
    //So we have to remove by index of the object itself instead.
    public void removeMember(Member memberToRemove) {
        this.memberList.remove(memberToRemove);
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

    public int getNumberOfAssignees() {
        return memberList.size();
    }
}
