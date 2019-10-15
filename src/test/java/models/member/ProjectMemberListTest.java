package models.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ProjectMemberListTest {
    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testAddMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ProjectMemberList projectMemberList = new ProjectMemberList();
        projectMemberList.addMember(member);
        assertEquals("1. Tom (Phone: 91198766 | Email: tom@gmail.com)",projectMemberList.getMember(1).getDetails());
    }

    @Test
    public void testEditMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ProjectMemberList projectMemberList = new ProjectMemberList();
        projectMemberList.addMember(member);
        projectMemberList.editMember(1,"n/John i/91177777 e/john@gmail.com");
        assertEquals("1. John (Phone: 91177777 | Email: john@gmail.com)",projectMemberList.getMember(1).getDetails());
        projectMemberList.editMember(1,"n/John e/john@gmail.com");
        assertEquals("1. John (Phone: No phone number | Email: john@gmail.com)",projectMemberList.getMember(1).getDetails());
    }

    @Test
    public void testGetNumOfMembers() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ProjectMemberList projectMemberList = new ProjectMemberList();
        projectMemberList.addMember(member);
        assertEquals(1,projectMemberList.getNumOfMembers());
    }

    @Test
    public void testRemoveMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ProjectMemberList projectMemberList = new ProjectMemberList();
        projectMemberList.addMember(member);
        projectMemberList.removeMember(1);
        assertEquals(0,projectMemberList.getNumOfMembers());
    }
}