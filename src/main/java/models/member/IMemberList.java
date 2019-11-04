package models.member;

import java.util.ArrayList;

public interface IMemberList {
    void addMember(Member newMember);

    void removeMember(Member memberToRemove);

    ArrayList<String> getAllMemberDetails();

    Member getMember(int i);

    public void editMember(int memberIndexNumber, String updatedMemberDetails);

    public ArrayList<String> getAllMemberDetailsForTable();

    public int getNumOfMembers();

    public boolean contains(IMember newMember);

    public int getIndexOfMember(IMember member);
}
