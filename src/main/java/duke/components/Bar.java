package duke.components;

import duke.DukeException;
import duke.commands.CopyObject;

import java.io.ByteArrayOutputStream;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.io.IOException;
import java.util.ArrayList;

public class Bar extends CopyObject<Bar> implements Serializable {

    private ArrayList<Chord> chords;
    private int id;
    private ArrayList<String> barChart;
    static final int EIGHTH_NOTES_PER_BAR = 8;

    //@@author rohan-av
    /**
     * Constructor takes in a String representing a list of notes.
     *
     * @param id the ID of the Bar in the Song
     * @param notes the String representing the list of notes that compose a bar
     */
    public Bar(int id, String notes) throws DukeException {
        this.id = id;
        this.chords = compileNotesToChords(convertStringToNotes(notes));
        checkLength(chords, EIGHTH_NOTES_PER_BAR);
        this.barChart = new ArrayList<>();
        updateBarChart();
    }

    /**
     * Alternate constructor for the Bar instance in the case that the Chord data is present.
     *
     * @param id the ID of the Bar in the Song
     * @param chords an ArrayList of Chord objects that compose the Bar
     */
    public Bar(int id, ArrayList<Chord> chords) throws DukeException {
        this.id = id;
        this.chords = chords;
        checkLength(this.chords, EIGHTH_NOTES_PER_BAR);
        this.barChart = new ArrayList<>();
        updateBarChart();
    }

    //@@author
    /**
     * the method that allows this item to be copied.
     *
     * @param object the object to be copied, which in this case is bar.
     */
    public Bar copy(Bar object) throws DukeException, IOException,ClassNotFoundException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bos);
        out.writeObject(object);
        // And then deserializing it from memory using ByteArrayOutputStream instead of FileInputStream,
        // Deserialization process will create a new object with the same state as in the serialized object.
        ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
        ObjectInputStream in = new ObjectInputStream(bis);
        return (Bar) in.readObject();
    }

    //@@author rohan-av
    /**
     * Returns an ArrayList of Note objects from the input String of the constructor.
     *
     * @param notes the input String representing the list of notes that compose a bar
     * @return an ArrayList of Note objects corresponding to the above notes
     */
    static ArrayList<Note> convertStringToNotes(String notes) throws DukeException {
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
    static ArrayList<Chord> compileNotesToChords(ArrayList<Note> noteList) {
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

    //@@author rohan-av
    /**
     * Returns a String representation of the Bar to be used in persistent storage.
     *
     * @return a storage-friendly String representation
     */
    public String toString() {

        StringBuilder result = new StringBuilder();
        result.append("[");
        for (Chord chord: chords) {
            result.append(chord.toString()).append(",");
        }
        result.setLength(result.length() - 1); // removes the last comma
        result.append("]");
        return result.toString();
    }

    /**
     * Checks the length of the Bar and whether it matches the correct length as specified by the time signature
     * (modification of time signature to be introduced in v2).
     *
     * @param chords the ArrayList of Chord objects that make up the Bar
     * @param eighthNotesPerBar the number of eighth notes that should be in the bar
     * @throws DukeException an exception indicating the incorrect adding of a Bar due to invalid format
     */
    public static void checkLength(ArrayList<Chord> chords, int eighthNotesPerBar) throws DukeException {
        if (chords.size() != eighthNotesPerBar) {
            throw new DukeException("addbar", "");
        }
    }
}
