package note;

import gazeeebo.UI.Ui;
import gazeeebo.commands.note.DeleteNoteCommand;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class DeleteNoteCommandTest extends DeleteNoteCommand {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;
    private Ui ui = new Ui();

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }

    @Test
    void execute_day_success() throws IOException {
        Note note = new Note("2020-09-11", "note 1");
        note.notes.add("note 2");
        NoteList.daily.add(note);
<<<<<<< HEAD
        ui.fullCommand = "deleteNote day 2020-09-11 2";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "deleteNote day 2020-09-11 2";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("Got it. I've deleted this note for that day:\r\n"
                + "note 2\r\n", output.toString());
    }

    @Test
    void execute_week_success() throws IOException {
        Note note = new Note("2020-01-06", "note 1");
        note.notes.add("note 2");
        NoteList.weekly.add(note);
<<<<<<< HEAD
        ui.fullCommand = "deleteNote week 2020-01-06 2";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "deleteNote week 2020-01-06 2";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("Got it. I've deleted this note for that week:\r\n"
                + "note 2\r\n", output.toString());
    }

    @Test
    void execute_month_success() throws IOException {
        Note note = new Note("2020-03-01", "note 1");
        note.notes.add("note 2");
        NoteList.monthly.add(note);
<<<<<<< HEAD
        ui.fullCommand = "deleteNote month 2020-03 2";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "deleteNote month 2020-03 2";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("Got it. I've deleted this note for that month:\r\n"
                + "note 2\r\n", output.toString());
    }

    @Test
    void execute_noteNumberNotSpecified_errorMessagePrinted() throws IOException {
<<<<<<< HEAD
        ui.fullCommand = "deleteNote month 2020-03";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "deleteNote month 2020-03";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("Please specify a note number.\r\n", output.toString());
    }

    @Test
    void execute_noteNumberNonExistent_errorMessagePrinted() throws IOException {
        Note note = new Note("2020-09-11", "note 1");
        note.notes.add("note 2");
        NoteList.daily.add(note);
<<<<<<< HEAD
        ui.fullCommand = "deleteNote day 2020-09-11 3";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "deleteNote day 2020-09-11 3";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("OOPS!!! That note number does not exist.\r\n", output.toString());
    }

    @Test
    void execute_noNotesOnSpecifiedDay_errorMessagePrinted() throws IOException {
<<<<<<< HEAD
        ui.fullCommand = "deleteNote day 2020-12-29 3";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "deleteNote day 2020-12-29 3";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("OOPS!!! There are no notes for this day to delete.\r\n", output.toString());
    }
}