package models.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ListOfMembersInProjectTest {
    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testAddMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ListOfMembersInProject listOfMembersInProject = new ListOfMembersInProject();
        listOfMembersInProject.addMember(member);
        assertEquals("1. Tom (Phone: 91198766 | Email: tom@gmail.com)",
                listOfMembersInProject.getMember(1).getDetails());
    }

    @Test
    public void testEditMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ListOfMembersInProject listOfMembersInProject = new ListOfMembersInProject();
        listOfMembersInProject.addMember(member);
        listOfMembersInProject.editMember(1,"n/John i/91177777 e/john@gmail.com");
        assertEquals("1. John (Phone: 91177777 | Email: john@gmail.com)",
                listOfMembersInProject.getMember(1).getDetails());
        listOfMembersInProject.editMember(1,"n/John e/john@gmail.com");
        assertEquals("1. John (Phone: No phone number | Email: john@gmail.com)",
                listOfMembersInProject.getMember(1).getDetails());
    }

    @Test
    public void testGetNumOfMembers() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ListOfMembersInProject listOfMembersInProject = new ListOfMembersInProject();
        listOfMembersInProject.addMember(member);
        assertEquals(1, listOfMembersInProject.getNumOfMembers());
    }

    @Test
    public void testRemoveMember() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        ListOfMembersInProject listOfMembersInProject = new ListOfMembersInProject();
        listOfMembersInProject.addMember(member);
        Member toBeRemoved = listOfMembersInProject.getMember(1);
        listOfMembersInProject.removeMember(toBeRemoved);
        assertEquals(0, listOfMembersInProject.getNumOfMembers());
    }
}