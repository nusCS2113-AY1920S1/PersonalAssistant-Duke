package models.member;

import java.util.ArrayList;

public interface IMemberList {
    void addMember(Member newMember);

    void removeMember(Member memberToRemove);

    ArrayList<String> getAllMemberDetails();

    Member getMember(int i);
}
