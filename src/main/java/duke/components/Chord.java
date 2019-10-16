package duke.components;

import java.util.ArrayList;

public class Chord {

    private ArrayList<Note> notes;
    private ArrayList<String> chordChart;

    /**
     * A Chord has the set relative duration of 1/8, and is comprised of all the Note objects that are to be played
     * in that specific duration of time.
     *
     * @param notes the ArrayList of Note objects to be played in the relative duration of the chord (1/8)
     */
    public Chord(ArrayList<Note> notes) {
        this.notes = notes;
    }

    public Chord() {
        notes = new ArrayList<>();
        chordChart = new ArrayList<>();
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void addToChord(Note note) {
        notes.add(note);
    }
}
