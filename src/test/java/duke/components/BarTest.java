package duke.components;

import duke.DukeException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BarTest {

    private static ArrayList<Chord> chords = new ArrayList<>();

    //@@author rohan-av
    @BeforeAll
    private static void populateChords() {
        Chord chord1 = new Chord();
        chord1.addToChord(new Note("8", Pitch.UPPER_A, true));
        chords.add(chord1);
        Chord chord2 = new Chord();
        chord2.addToChord(new Note("8", Pitch.UPPER_A, false));
        chords.add(chord2);
        chords.add(chord2);
        chords.add(chord2);
        Chord chord3 = new Chord();
        chord3.addToChord(new Note("8", Pitch.MIDDLE_C, true));
        chords.add(chord3);
        Chord chord4 = new Chord();
        chord4.addToChord(new Note("8", Pitch.MIDDLE_C, false));
        chords.add(chord4);
        chords.add(chord4);
        chords.add(chord4);
    }

    @Test
    public void testToString() throws DukeException {
        assertEquals("[[UAs],[UA],[UA],[UA],[MCs],[MC],[MC],[MC]]", new Bar(0, "2_UA 2_MC").toString());
        assertEquals("[[UAs],[UA],[UA],[UA],[MCs],[MC],[MC],[MC]]", new Bar(0, chords).toString());
    }

    @Test
    public void testCompileNotesToChords() {
        ArrayList<Note> noteList = new ArrayList<>();
        noteList.add(new Note("2", Pitch.UPPER_A, true));
        noteList.add(new Note("2", Pitch.MIDDLE_C, true));
        assertEquals(chords.toString(), Bar.compileNotesToChords(noteList).toString());
    }

    @Test
    public void testConvertStringToNotes() throws DukeException {
        ArrayList<Note> noteList = new ArrayList<>();
        noteList.add(new Note("2", Pitch.UPPER_A, true));
        noteList.add(new Note("2", Pitch.MIDDLE_C, true));
        assertEquals(noteList.toString(), Bar.convertStringToNotes("2_UA 2_MC").toString());
    }

    @Test
    public void testCheckLength() {
        try {
            ArrayList<Chord> incompleteBar = new ArrayList<>();
            Chord chord5 = new Chord();
            chord5.addToChord(new Note("8", Pitch.MIDDLE_C, true));
            incompleteBar.add(chord5);
            Bar.checkLength(incompleteBar, Bar.EIGHTH_NOTES_PER_BAR);
        } catch (Exception e) {
            assertEquals(e.getMessage(), new DukeException("addbar", "").getMessage());
        }
    }

    //@@author
    //todo: test code for updateBarChart, copy
}
