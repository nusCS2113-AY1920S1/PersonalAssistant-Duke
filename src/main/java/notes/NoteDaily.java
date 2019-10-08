package notes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class NoteDaily {
    protected LocalDate noteDate; //yyyy-MM-dd
    protected ArrayList<String> notes;
    public static DateTimeFormatter noteFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public NoteDaily(String dateOfNote) {
        noteDate = LocalDate.parse(dateOfNote, noteFormatter);
        notes = new ArrayList<String>();
    }
}
