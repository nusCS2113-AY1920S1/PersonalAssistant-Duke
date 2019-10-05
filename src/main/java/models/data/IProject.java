package models.data;

import models.member.MemberList;

public interface IProject {
    // TODO Add attributes such as Members, Tasks, Name
    String getDescription();

    MemberList getMembers();

    int getNumOfMembers();

}
