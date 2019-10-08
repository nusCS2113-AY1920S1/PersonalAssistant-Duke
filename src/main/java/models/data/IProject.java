package models.data;

import models.member.Member;
import models.member.MemberList;

public interface IProject {
    // TODO Add attributes such as Members, Tasks, Name
    String getDescription();

    MemberList getMembers();

    int getNumOfMembers();

    void addMember(Member newMember);
}
