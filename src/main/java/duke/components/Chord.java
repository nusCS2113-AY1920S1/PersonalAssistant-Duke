package duke.components;

import java.util.ArrayList;

public class Chord {

    private ArrayList<Note> notes;
    private String chordString;

    /**
     * A Chord has the set relative duration of 1/8, and is comprised of all the Note objects that are to be played
     * in that specific duration of time.
     *
     * @param notes the ArrayList of Note objects to be played in the relative duration of the chord (1/8)
     */
    public Chord(ArrayList<Note> notes) {
        this.notes = notes;
        for (Note note: notes) {
            updateChordString(note);
        }
    }

    public Chord() {
        this.notes = new ArrayList<>();
        this.chordString = "";
    }

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public String getChordString() {
        return chordString;
    }

    public void addToChord(Note note) {
        notes.add(note);
        updateChordString(note);
    }

    private void updateChordString(Note note) {
        if (note.isStart()) {
            this.chordString += (note.getPitch() + " ");
        } else {
            this.chordString += ("- ");
        }
    }

    /**
     * Returns a String representation for the Chord to be used in persistent storage.
     *
     * @return a storage-friendly String representation
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[");

        for (int i = 0; i < notes.size(); i++) {
            result.append(notes.get(i).toString());
            if (i != notes.size() - 1) {
                result.append(";");
            }
        }


        return result.toString();
    }

}
