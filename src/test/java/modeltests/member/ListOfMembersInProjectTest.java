package modeltests.member;

import models.member.MemberList;
import models.member.Member;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListOfMembersInProjectTest {
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    void testAddMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        MemberList listOfMembersInProject = new MemberList();
        listOfMembersInProject.addMember(member);
        assertEquals("1. Tom (Phone: 91198766 | Email: tom@gmail.com)",
                listOfMembersInProject.getMember(1).getDetails());
    }

    @Test
    void testEditMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        MemberList listOfMembersInProject = new MemberList();
        listOfMembersInProject.addMember(member);
        listOfMembersInProject.editMember(1,"n/John i/91177777 e/john@gmail.com");
        assertEquals("1. John (Phone: 91177777 | Email: john@gmail.com)",
                listOfMembersInProject.getMember(1).getDetails());
        listOfMembersInProject.editMember(1,"n/Matthew e/matthew@gmail.com");
        assertEquals("1. Matthew (Phone: 91177777 | Email: matthew@gmail.com)",
                listOfMembersInProject.getMember(1).getDetails());
    }

    @Test
    void testGetNumOfMembers() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        MemberList listOfMembersInProject = new MemberList();
        listOfMembersInProject.addMember(member);
        assertEquals(1, listOfMembersInProject.getNumOfMembers());
    }

    @Test
    void testRemoveMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        MemberList listOfMembersInProject = new MemberList();
        listOfMembersInProject.addMember(member);
        Member toBeRemoved = listOfMembersInProject.getMember(1);
        listOfMembersInProject.removeMember(toBeRemoved);
        assertEquals(0, listOfMembersInProject.getNumOfMembers());
    }
}