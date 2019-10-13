package models.member;

import java.util.ArrayList;

public interface IMemberList {
    public void addMember(Member newMember);

    public void removeMember(int memberIndex);

    public ArrayList<String> getAllMemberDetails();

}
