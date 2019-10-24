package duke.components;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChordTest {

    //@@author rohan-av
    @Test
    void testToString() {
        ArrayList<Note> noteList = new ArrayList<>();
        noteList.add(new Note("8", Pitch.UPPER_A, true));
        noteList.add(new Note("8", Pitch.MIDDLE_C, false));
        Chord chord = new Chord(noteList);
        assertEquals("[UAs;MC]", chord.toString());
    }

    //@@author
    //todo: addToChord, updateChordString

}