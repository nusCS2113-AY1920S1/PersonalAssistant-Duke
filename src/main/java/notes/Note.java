package notes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Note {
    /** Stores the date of the note
     *  If the note is for a particular day, it will store that day's date.
     *  If the note is for a particular week, it will store the date of that week's Monday.
     *  If the note is for a particular month, it will store the first date of that month.
     *  */
    public LocalDate noteDate; //yyyy-MM-dd
    public ArrayList<String> notes;
    public static DateTimeFormatter noteFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Note(String dateOfNote, String firstNote) {
        noteDate = LocalDate.parse(dateOfNote, noteFormatter);
        notes = new ArrayList<String>();
        notes.add(firstNote);
    }
}
