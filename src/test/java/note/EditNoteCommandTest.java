package note;

import gazeeebo.exception.DukeException;
import gazeeebo.UI.Ui;
import gazeeebo.commands.note.EditNoteCommand;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EditNoteCommandTest extends EditNoteCommand {
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
    void editNoteInList_noteNumberNonExistent_exceptionThrown() {
        NoteList.daily.add(new Note("2019-08-11", "note to be edited"));
        LocalDate date = LocalDate.parse("2019-08-11", Note.noteFormatter);
        try {
            editNoteInList(3, NoteList.daily, date, "edited note", "day", "NoteDaily.txt");
            fail();
        } catch (DukeException d) {
            assertEquals("OOPS!!! That note number does not exist.", d.getMessage());
        }
    }

    @Test
    void editNoteInList_noNotesOnSpecifiedDay_exceptionThrown() {
        LocalDate date = LocalDate.parse("2019-06-03", Note.noteFormatter);
        try {
            editNoteInList(1, NoteList.weekly, date, "edited note", "week", "NoteWeekly.txt");
            fail();
        } catch (DukeException d) {
            assertEquals("OOPS!!! There are no notes for this week to edit from.", d.getMessage());
        }
    }

    @Test
    void execute_noteNumberNotSpecified_errorMessagePrinted() {
        ui.FullCommand = "editNote day 2019-08-11";
        String[] command = ui.FullCommand.split(" ");
        int noteNum;
        try {
            noteNum = Integer.parseInt(command[3]);
        } catch (ArrayIndexOutOfBoundsException v) {
            System.out.println("Please specify a note number.");
        }
        assertEquals("Please specify a note number.\r\n", output.toString());
    }

    @Test
    void execute_day_success() {
        NoteList.daily.add(new Note("2019-08-11", "note to be edited"));
        ui.FullCommand = "editNote day 2019-08-11 1";
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "editNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        int noteNum;
        try {
            noteNum = Integer.parseInt(command[3]);
        } catch (ArrayIndexOutOfBoundsException v) {
            System.out.println("Please specify a note number.");
            return;
        }

        String usersNote = "updated note";
        try {
            switch (command[1]) {
            case "day": editNoteInList(noteNum, NoteList.daily, userDate, usersNote, command[1], "NoteDaily.txt");
                break;
            case "week": editNoteInList(noteNum, NoteList.weekly, userDate, usersNote, command[1], "NoteWeekly.txt");
                break;
            case "month": editNoteInList(noteNum, NoteList.monthly, userDate, usersNote, command[1], "NoteMonthly.txt");
                break;
            default: System.out.println("Bug in EditNoteCommand");
                System.exit(1);
            }
            printEditSuccess(usersNote, command[1]);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
        }
        assertEquals("Got it. I've edited this note for that day:\r\n" + "updated note\r\n", output.toString());
    }

    @Test
    void execute_week_success() {
        NoteList.weekly.add(new Note("2019-10-14", "note to be edited"));
        ui.FullCommand = "editNote week 2019-10-14 1";
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "editNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        int noteNum;
        try {
            noteNum = Integer.parseInt(command[3]);
        } catch (ArrayIndexOutOfBoundsException v) {
            System.out.println("Please specify a note number.");
            return;
        }

        String usersNote = "updated note";
        try {
            switch (command[1]) {
            case "day": editNoteInList(noteNum, NoteList.daily, userDate, usersNote, command[1], "NoteDaily.txt");
                break;
            case "week": editNoteInList(noteNum, NoteList.weekly, userDate, usersNote, command[1], "NoteWeekly.txt");
                break;
            case "month": editNoteInList(noteNum, NoteList.monthly, userDate, usersNote, command[1], "NoteMonthly.txt");
                break;
            default: System.out.println("Bug in EditNoteCommand");
                System.exit(1);
            }
            printEditSuccess(usersNote, command[1]);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
        }
        assertEquals("Got it. I've edited this note for that week:\r\n" + "updated note\r\n", output.toString());
    }

    @Test
    void execute_month_success() {
        NoteList.monthly.add(new Note("2019-08-01", "note to be edited"));
        ui.FullCommand = "editNote month 2019-08 1";
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "editNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        int noteNum;
        try {
            noteNum = Integer.parseInt(command[3]);
        } catch (ArrayIndexOutOfBoundsException v) {
            System.out.println("Please specify a note number.");
            return;
        }

        String usersNote = "updated note";
        try {
            switch (command[1]) {
            case "day": editNoteInList(noteNum, NoteList.daily, userDate, usersNote, command[1], "NoteDaily.txt");
                break;
            case "week": editNoteInList(noteNum, NoteList.weekly, userDate, usersNote, command[1], "NoteWeekly.txt");
                break;
            case "month": editNoteInList(noteNum, NoteList.monthly, userDate, usersNote, command[1], "NoteMonthly.txt");
                break;
            default: System.out.println("Bug in EditNoteCommand");
                System.exit(1);
            }
            printEditSuccess(usersNote, command[1]);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
        }
        assertEquals("Got it. I've edited this note for that month:\r\n" + "updated note\r\n", output.toString());
    }
}