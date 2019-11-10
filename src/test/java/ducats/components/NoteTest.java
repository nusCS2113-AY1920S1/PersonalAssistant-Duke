package ducats.components;

import ducats.Ducats;
import ducats.DucatsException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class NoteTest {

    @Test
    void testToString() throws DucatsException {
        assertEquals("UAs", new Note("4_UA").toString());
        assertEquals("RTs", new Note("4*_RT").toString());
        assertEquals("MC", new Note("2", Pitch.MIDDLE_C, false).toString());
    }

    @Test
    void testNote() throws DucatsException {
        assertEquals(Pitch.LOWER_C, new Note("4_LC").getPitch());
        assertEquals(Pitch.LOWER_D, new Note("4_LD").getPitch());
        assertEquals(Pitch.LOWER_E, new Note("4_LE").getPitch());
        assertEquals(Pitch.LOWER_F, new Note("4_LF").getPitch());
        assertEquals(Pitch.LOWER_G, new Note("4_LG").getPitch());
        assertEquals(Pitch.LOWER_A, new Note("4_LA").getPitch());
        assertEquals(Pitch.LOWER_B, new Note("4_LB").getPitch());
        assertEquals(Pitch.MIDDLE_C, new Note("4*_MC").getPitch());
        assertEquals("4*", new Note("4*_MC").getDuration());
        assertEquals(Pitch.UPPER_D, new Note("4_UD").getPitch());
        assertEquals(Pitch.UPPER_E, new Note("4_UE").getPitch());
        assertEquals(Pitch.UPPER_F, new Note("4_UF").getPitch());
        assertEquals(Pitch.UPPER_G, new Note("4_UG").getPitch());
        assertEquals(Pitch.UPPER_A, new Note("4_UA").getPitch());
        assertEquals(Pitch.UPPER_B, new Note("4_UB").getPitch());
        assertEquals(Pitch.UPPER_C, new Note("4_UC").getPitch());
        assertEquals(Pitch.REST, new Note("4_RT").getPitch());
        assertThrows(DucatsException.class, () -> new Note("4_LT"));
    }

    @Test
    void testGetRelativeUnitDuration() throws DucatsException {
        assertEquals(8, new Note("1_MC").getRelativeUnitDuration());
        assertEquals(6, new Note("2*_MC").getRelativeUnitDuration());
        assertEquals(4, new Note("2_MC").getRelativeUnitDuration());
        assertEquals(3, new Note("4*_MC").getRelativeUnitDuration());
        assertEquals(2, new Note("4_MC").getRelativeUnitDuration());
        assertEquals(1, new Note("8_MC").getRelativeUnitDuration());
        assertEquals(1, new Note("8_MC").getRelativeUnitDuration());
        assertThrows(DucatsException.class, () -> new Note("3_MC").getRelativeUnitDuration());
    }

    @Test
    void testNumericalDuration() throws DucatsException {
        assertEquals(0.375, new Note("4*_MC").getNumericalDuration());
        assertDoesNotThrow(() -> new Note("0_RT").getNumericalDuration());
    }
}
