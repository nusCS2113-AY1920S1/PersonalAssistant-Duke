package note;

import gazeeebo.exception.DukeException;
import gazeeebo.UI.Ui;
import gazeeebo.commands.note.AddNoteCommand;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class AddNoteCommandTest extends AddNoteCommand {
    private ByteArrayOutputStream output = new ByteArrayOutputStream();
    private PrintStream mine = new PrintStream(output);
    private PrintStream original = System.out;

    @BeforeEach
    void setupStream() {
        System.setOut(mine);
    }

    @AfterEach
    void restoreStream(){
        System.out.flush();
        System.setOut(original);
    }

    //processCommand() tests
    @Test
    void processCommand_emptyDescription_exceptionThrown() {
        String[] command = "addNote".split(" ");
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("OOPS!!! The description of a(n) addNote cannot be empty.", d.getMessage());
        }
    }

    @Test
    void processCommand_wrongSecondWord_exceptionThrown() {
        String[] command = "addNote weekly 2019-09-09".split(" ");
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("The second word in the command has to be \'day\', \'week\' or \'month\'.", d.getMessage());
        }
    }

    @Test
    void processCommand_emptyDateField_exceptionThrown() {
        String[] command = "addNote day".split(" ");
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("Please input a date.", d.getMessage());
        }
    }

    @Test
    void processCommand_wrongDateFormatForDay_exceptionThrown() {
        String[] command = "addNote day 2019-1-1".split(" ");
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("The date has to been in YYYY-MM-DD format.", d.getMessage());
        }
    }

    @Test
    void processCommand_wrongDateFormatForWeek_exceptionThrown() {
        String[] command = "addNote week 2019-2-1".split(" "); //this date is not a Monday
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("The date has to been in YYYY-MM-DD format.", d.getMessage());
        }
    }

    @Test
    void processCommand_wrongDateFormatForMonth_exceptionThrown() {
        String[] command = "addNote month 2019-2-1".split(" ");
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("The date has to been in YYYY-MM format.", d.getMessage());
        }
    }

    @Test
    void processCommand_dateForWeekIsNotMonday_exceptionThrown() {
        String[] command = "addNote week 2019-02-01".split(" ");
        try {
            processCommand(command, "addNote");
            fail();
        } catch (DukeException d) {
            assertEquals("OOPS!!! The date provided must be a Monday.", d.getMessage());
        }
    }

    //execute() tests
    @Test
    void execute_day_success() {
        Ui ui = new Ui();
        ui.FullCommand = "addNote day 2019-10-12";
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "addNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        String usersNote = "testing note";
        Note noteSpecified;
        try {
            switch (command[1]) {
            case "day":
                noteSpecified = addToList(NoteList.daily, userDate, usersNote, command[2], "NoteDaily.txt");
                break;
            case "week":
                noteSpecified = addToList(NoteList.weekly, userDate, usersNote, command[2], "NoteWeekly.txt");
                break;
            case "month":
                noteSpecified = addToList(NoteList.monthly, userDate, usersNote, command[2], "NoteMonthly.txt");
                break;
            default:
                noteSpecified = null;
                break;
            }

            assert noteSpecified != null : "there is a bug in AddNoteCommand";
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
        } catch (IOException e) {
            System.out.println("The " + command[1] + " file cannot be opened.");
        }
        assertEquals("Got it. I've added this note to that day:\r\n"
                + "testing note\r\n" + "Now you have 1 note(s) for that day.\r\n", output.toString());
    }

    @Test
    void execute_week_success() {
        Ui ui = new Ui();
        ui.FullCommand = "addNote week 2019-10-14";
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "addNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        String usersNote = "testing note";
        Note noteSpecified;
        try {
            switch (command[1]) {
            case "day":
                noteSpecified = addToList(NoteList.daily, userDate, usersNote, command[2], "NoteDaily.txt");
                break;
            case "week":
                noteSpecified = addToList(NoteList.weekly, userDate, usersNote, command[2], "NoteWeekly.txt");
                break;
            case "month":
                noteSpecified = addToList(NoteList.monthly, userDate, usersNote, command[2], "NoteMonthly.txt");
                break;
            default:
                noteSpecified = null;
                break;
            }

            assert noteSpecified != null : "there is a bug in AddNoteCommand";
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
        } catch (IOException e) {
            System.out.println("The " + command[1] + " file cannot be opened.");
        }
        assertEquals("Got it. I've added this note to that week:\r\n"
                + "testing note\r\n" + "Now you have 1 note(s) for that week.\r\n", output.toString());
    }

    @Test
    void execute_month_success() {
        Ui ui = new Ui();
        ui.FullCommand = "addNote month 2019-12";
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "addNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }

        String usersNote = "testing note";
        Note noteSpecified;
        try {
            switch (command[1]) {
            case "day":
                noteSpecified = addToList(NoteList.daily, userDate, usersNote, command[2], "NoteDaily.txt");
                break;
            case "week":
                noteSpecified = addToList(NoteList.weekly, userDate, usersNote, command[2], "NoteWeekly.txt");
                break;
            case "month":
                noteSpecified = addToList(NoteList.monthly, userDate, usersNote, command[2], "NoteMonthly.txt");
                break;
            default:
                noteSpecified = null;
                break;
            }

            assert noteSpecified != null : "there is a bug in AddNoteCommand";
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
        } catch (IOException e) {
            System.out.println("The " + command[1] + " file cannot be opened.");
        }
        assertEquals("Got it. I've added this note to that month:\r\n"
                + "testing note\r\n" + "Now you have 1 note(s) for that month.\r\n", output.toString());
    }
}