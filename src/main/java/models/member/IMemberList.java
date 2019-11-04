package models.member;

import java.util.ArrayList;

public interface IMemberList {
    void addMember(Member newMember);

    void editMember(int memberIndexNumber, String updatedMemberDetails);

    void removeMember(Member memberToRemove);

    ArrayList<String> getAllMemberDetails();

    ArrayList<String> getAllMemberDetailsForTable();

    IMember getMember(int i);

    int getNumOfMembers();
}
