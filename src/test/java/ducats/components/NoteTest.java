package ducats.components;

import ducats.DucatsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NoteTest {

    @Test
    void testToString() throws DucatsException {
        assertEquals("UAs", new Note("4_UA").toString());
        assertEquals("RTs", new Note("4*_RT").toString());
        assertEquals("MC", new Note("2", Pitch.MIDDLE_C, false).toString());
    }
}
