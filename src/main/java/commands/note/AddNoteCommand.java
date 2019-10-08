package commands.note;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import commands.Command;
import exception.DukeException;
import notes.Note;
import notes.NoteList;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class AddNoteCommand extends Command {

    protected LocalDate processCommand(String[] command) throws DukeException{
        //addNote day/week/month yyyy-MM-dd
        //<the note they want to add>
        try {
            try {
                if (!(command[1].equals("day") || command[1].equals("week") || command[1].equals("month"))) {
                    throw new DukeException("The second word in the command has to be \'day\', \'week\' or \'month\'.");
                }
                if (command[1].equals("month")) {
                    command[2] = command[2] + "-01";
                }
            } catch (ArrayIndexOutOfBoundsException b) {
                throw new DukeException("OOPS!!! The description of an addNote cannot be empty.");
            }
            return LocalDate.parse(command[2], Note.noteFormatter);
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new DukeException("Please input a date.");
            //return;
        } catch (DateTimeParseException a) {
            if (command[1].equals("month")) {
                throw new DukeException("The date has to been in YYYY-MM format.");
            } else {
                throw new DukeException("The date has to been in YYYY-MM-DD format.");
            }
        }
    }

    private Note addToList(ArrayList<Note> listOfNotes, LocalDate userDate, String usersNote, String date) {
        boolean hasNote = false;
        Note noteInQuestion = null;
        for (Note n: listOfNotes) {
            if (n.noteDate.equals(userDate)) {
                n.notes.add(usersNote);
                noteInQuestion = n;
                hasNote = true;
                break;
            }
        }
        if (!hasNote) {
            Note newNote = new Note(date, usersNote);
            listOfNotes.add(newNote);
            noteInQuestion = newNote;
        }
        //WRITE TO DAILY.TXT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
        return noteInQuestion;
    }

    private void printConfirmationMessage(String usersNote, int size, String period) {
        System.out.println("Got it. I've added this note for the " + period +  ":");
        System.out.println(usersNote);
        System.out.println("Now you have " + size + " note(s) for this " + period + ".");
    }

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws IOException {
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command);
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }
        ui.ReadCommand();
        String usersNote = ui.FullCommand;
        Note noteSpecified;
        switch (command[1]) {
        case "day":
            noteSpecified = addToList(NoteList.daily, userDate, usersNote, command[2]);
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
            break;

        case "week":
            noteSpecified = addToList(NoteList.weekly, userDate, usersNote, command[2]);
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
            break;

        case "month":
            noteSpecified = addToList(NoteList.monthly, userDate, usersNote, command[2]);
            printConfirmationMessage(usersNote, noteSpecified.notes.size(), command[1]);
            break;
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
