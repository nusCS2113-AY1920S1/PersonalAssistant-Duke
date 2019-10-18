package note;

import gazeeebo.UI.Ui;
import gazeeebo.commands.note.ListNoteCommand;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import static org.junit.jupiter.api.Assertions.*;

class ListNoteCommandTest extends ListNoteCommand {
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
    void execute_notesExistForTheSpecifiedPeriod_success() throws IOException {
        Note note = new Note("2019-11-12", "note 1");
        note.notes.add("note 2");
        note.notes.add("note 3");
        NoteList.daily.add(note);
<<<<<<< HEAD
        ui.fullCommand = "listNote day 2019-11-12";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "listNote day 2019-11-12";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("Here are your notes for that day:\r\n" + "1. note 1\r\n2. note 2\r\n3. note 3\r\n", output.toString());
    }

    @Test
    void execute_noNotesExistForTheSpecifiedPeriod_success() throws IOException {
<<<<<<< HEAD
        ui.fullCommand = "listNote month 2019-10";
        execute(null, ui, null, null, null);
=======
        ui.FullCommand = "listNote month 2019-10";
        execute(null, ui, null, null, null,null);
>>>>>>> 19887703625c9982bc0a65fae6c2d21574397f74
        assertEquals("There are no notes for that month.\r\n", output.toString());
    }
}