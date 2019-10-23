package duke.components;

import java.util.ArrayList;

public class Bar {

    private ArrayList<Chord> chords;
    private int id;
    private ArrayList<String> barChart;

    public Object clone()throws CloneNotSupportedException {
        return super.clone();
    }

    //@@author rohan-av
    /**
     * Constructor takes in a String representing a list of notes.
     *
     * @param id the ID of the Bar in the Song
     * @param notes the String representing the list of notes that compose a bar
     */
    public Bar(int id, String notes) {
        this.id = id;
        this.chords = compileNotesToChords(convertStringToNotes(notes));
        this.barChart = new ArrayList<>();
        updateBarChart();
    }

    /**
     * Returns an ArrayList of Note objects from the input String of the constructor.
     *
     * @param notes the input String representing the list of notes that compose a bar
     * @return an ArrayList of Note objects corresponding to the above notes
     */
    private ArrayList<Note> convertStringToNotes(String notes) {
        ArrayList<Note> result = new ArrayList<>();
        String[] notesArray = notes.split(" ");
        for (String note: notesArray) {
            result.add(new Note(note));
        }
        return result;
    }

    /**
     * Compiles an ArrayList of Note objects together to create an ArrayList of Chord
     * objects that compose the Bar.
     *
     * @param noteList an ArrayList of Note objects, which can be of different durations
     * @return an ArrayList of Chord objects with the specified duration of an 1/8th note
     */
    private ArrayList<Chord> compileNotesToChords(ArrayList<Note> noteList) {
        ArrayList<Chord> result = new ArrayList<>();
        for (Note note: noteList) {
            for (int i = 0; i < note.getRelativeUnitDuration(); i++) {
                Chord newChord = new Chord();
                Note note1 = note.getUnitNote();
                if (i != 0) {
                    note1.setStart(false);
                }
                newChord.addToChord(note1);
                result.add(newChord);
            }
        }
        return result;
    }

    public ArrayList<Chord> getChords() {
        return chords;
    }

    //@@author
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public ArrayList<String> getBarChart() {
        return barChart;
    }

    private void updateBarChart() {
        for (Chord chord: chords) {
            barChart.add(chord.getChordString());
        }
    }

    /**
     * Returns a String representation of the Bar to be used in persistent storage.
     *
     * @return a storage-friendly String representation
     */
    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("[").toString();
        for (Chord chord: chords) {
            result.append(chord.toString()).append(",");
        }
        result.setLength(result.length() - 1); // removes the last comma
        result.append("]");
        return result.toString();
    }

}
