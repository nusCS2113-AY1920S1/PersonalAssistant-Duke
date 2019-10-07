package models.data;

import models.member.Member;
import models.member.MemberList;

public class Project implements IProject {
    private String description;
    private MemberList memberList;

    public Project(String description, String members) {
        this.description = description;
        this.memberList = new MemberList();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    @Override
    public MemberList getMembers() {
        return this.memberList;
    }

    @Override
    public int getNumOfMembers() {
        return 0;
    }

    @Override
    public void addMember(Member newMember) {
        this.memberList.addMember(newMember);
    }

}
