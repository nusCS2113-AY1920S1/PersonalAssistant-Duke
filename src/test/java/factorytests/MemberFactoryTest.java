package factorytests;

import models.member.IMember;
import models.member.Member;
import models.member.NullMember;
import org.junit.jupiter.api.Test;
import util.factories.MemberFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MemberFactoryTest {
    private MemberFactory memberFactory;
    private String simulatedFactoryInput;

    MemberFactoryTest() {
        this.memberFactory = new MemberFactory();
    }

    /**
     * Always true test just to test if jUnit is working.
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
        simulatedFactoryInput = "-n Thor -i 91234567 -e thor@marvel.com -x 1";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        Member expectedMember = new Member("Thor", "91234567",
                "thor@marvel.com", 1, "member");
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }

    @Test
    void memberCreation_correctPartialInputs_creationSuccess() {
        simulatedFactoryInput = "-n Iron man -i 12345678 -x 2";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        Member expectedMember = new Member("Iron man", "12345678", "--", 2, "member");
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());

        simulatedFactoryInput = "-n Dr Strange -x 3";
        simulatedMember = memberFactory.create(simulatedFactoryInput);
        expectedMember = new Member("Dr Strange", "--", "--", 3, "member");
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }

    @Test
    void memberCreation_wrongInputs_creationFailed() {
        simulatedFactoryInput = "-i 12341234 -x 1";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        NullMember expectedMember = new NullMember("Name cannot be empty! Please follow the add command "
                                    + "format in user guide! \"add member -n NAME\" "
                                    + "is the minimum requirement for add member command");
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }

    @Test
    void memberCreation_truncatedInputs_exceptionCaught() {
        simulatedFactoryInput = "-n -x 1";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        NullMember expectedMember = new NullMember("Name cannot be empty! Please follow the add command "
                                    + "format in user guide! \"add member -n NAME\" is the minimum requirement for "
                                    + "add member command");
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }

    @Test
    void memberCreation_missingMemberIndex_exceptionCaught() {
        simulatedFactoryInput = "-n Marvel -i 91234567 -e marvel@marvel.com";
        IMember simulatedMember = memberFactory.create(simulatedFactoryInput);
        NullMember expectedMember = new NullMember("Index cannot be 0! This is a bug in the internal "
                                    + "program. If this is encountered, please contact the project owners!");
        assertEquals(expectedMember.getDetails(), simulatedMember.getDetails());
        assertEquals(expectedMember.getIndexNumber(), simulatedMember.getIndexNumber());
        assertEquals(expectedMember.getName(), simulatedMember.getName());
    }
}
