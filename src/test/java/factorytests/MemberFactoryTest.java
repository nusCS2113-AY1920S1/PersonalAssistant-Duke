package factorytests;

import models.member.IMember;
import models.member.Member;
import models.member.NullMember;
import org.junit.jupiter.api.Test;
import util.factories.MemberFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberFactoryTest {
    private MemberFactory memberFactory = new MemberFactory();
    private String simulatedFactoryInput;

    /**
     * Alwqys true test just to test if jUnit is working.
     */
    @Test
    void alwaysTrue() {
        assertEquals(2, 2);
    }

    /**
     * This test is to test if user gives a valid input, whether MemberFactory
     * can create the desired Member properly.
     */
    @Test
    void memberCreation_fullCorrectInputs_creationSuccess() {
        simulatedFactoryInput = "n/Thor i/91234567 e/thor@marvel.com x/1";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        Member expectedMember = new Member("Thor", "91234567",
                "thor@marvel.com", 1);
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }

    @Test
    void memberCreation_correctPartialInputs_creationSuccess() {
        simulatedFactoryInput = "n/Iron man i/12345678 x/2";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        Member expectedMember = new Member("Iron man", "12345678", "No email address", 2);
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());

        simulatedFactoryInput = "n/Dr Strange x/3";
        simulatedMember = memberFactory.create(simulatedFactoryInput);
        expectedMember = new Member("Dr Strange", "No phone number", "No email address", 3);
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }

    @Test
    void memberCreation_wrongInputs_errorReturned() {
        simulatedFactoryInput = "i/12341234 x/1";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        NullMember expectedMember = new NullMember();
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }
}
