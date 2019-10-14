package gazeeebo.notes;

import java.util.ArrayList;

/**
 * Contains the list of Note objects for a particular period.
 */
public class NoteList {
    /** The list of Note objects for day. */
    public static ArrayList<Note> daily = new ArrayList<>();
    /** The list of Note objects for week. */
    public static ArrayList<Note> weekly = new ArrayList<>();
    /** The list of Note objects for month. */
    public static ArrayList<Note> monthly = new ArrayList<>();
}
