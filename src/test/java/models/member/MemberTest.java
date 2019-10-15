package models.member;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberTest {
    @Test
    public void alwaysTrue() {
        assertEquals(2, 2);
    }

    @Test
    public void testGetDetails() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        assertEquals("1. Tom (Phone: 91198766 | Email: tom@gmail.com)",member.getDetails());
    }

    @Test
    public void testSetIndexNumber() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        member.setIndexNumber(2);
        assertEquals("2. Tom (Phone: 91198766 | Email: tom@gmail.com)",member.getDetails());
    }

    @Test
    public void testUpdateDetails() {
        Member member = new Member("Tom","91198766", "tom@gmail.com",1);
        member.updateDetails("John", "911", "john@hotmail.com");
        assertEquals("1. John (Phone: 911 | Email: john@hotmail.com)", member.getDetails());
    }
}