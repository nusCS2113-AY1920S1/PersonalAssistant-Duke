package gazeeebo.notes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * Maps notes to a particular date.
 */
public class Note {
    /** Stores the date of the note
     *  If the note is for a particular day, it will store that day's date.
     *  If the note is for a particular week, it will store the date of that week's Monday.
     *  If the note is for a particular month, it will store the first date of that month.
     *  */
    public LocalDate noteDate; //yyyy-MM-dd
    /** The list that contains all the notes for a particular period ie day, week or month. */
    public ArrayList<String> notes;
    public static DateTimeFormatter noteFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    /**
     * Constructor that maps a date to the first note for that date.
     *
     * @param dateOfNote the starting date of the period
     * @param firstNote the first note to be added to the specified period
     */
    public Note(String dateOfNote, String firstNote) {
        noteDate = LocalDate.parse(dateOfNote, noteFormatter);
        notes = new ArrayList<String>();
        notes.add(firstNote);
    }
}
