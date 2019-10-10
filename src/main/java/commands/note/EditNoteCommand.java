package commands.note;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import notes.Note;
import Exception.DukeException;
import notes.NoteList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Edits an existing note for a particular day, week or month.
 */
public class EditNoteCommand extends AddNoteCommand {
    /**
     * Edits the specified note if it exists. Else it throws an exception.
     *
     * @param noteNumber the index of the note that the user wants to edit
     * @param listToEdit the list of Notes that contains the note to edit depending on if its a day, week or month
     * @param dateToEdit the start date of the period of the note to edit
     * @param editedNote the edited version of the note
     * @param period is either day, week or month
     * @throws DukeException if the note to edit does not exist
     */
    private void editNoteInList(int noteNumber, ArrayList<Note> listToEdit, LocalDate dateToEdit,
                                String editedNote, String period) throws DukeException{
        for (Note n: listToEdit) {
            if (n.noteDate.equals(dateToEdit)) {
                try {
                    n.notes.set(noteNumber-1, editedNote);
                    //WRITE TO FILE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    return;
                } catch (IndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! That note number does not exist.");
                }
            }
        }
        throw new DukeException("OOPS!!! There are no notes for this " + period + " to edit from.");
    }

    /**
     * Tells the user that the note has been successfully edited.
     *
     * @param usersNote the edited version of the note
     * @param period is either day, week or month
     */
    private void printEditSuccess(String usersNote, String period) {
        System.out.println("Got it. I've edited this note for that " + period +  ":");
        System.out.println(usersNote);
    }

    /** The main method that executes all the sub methods. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws IOException {
        //editNote day/week/month yyyy-MM-dd <note_num = index+1>
        //<the note they want to edit to become>
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

        ui.ReadCommand();
        String usersNote = ui.FullCommand;
        try {
            switch (command[1]) {
            case "day": editNoteInList(noteNum, NoteList.daily, userDate, usersNote, command[1]);
                break;
            case "week": editNoteInList(noteNum, NoteList.weekly, userDate, usersNote, command[1]);
                break;
            case "month": editNoteInList(noteNum, NoteList.monthly, userDate, usersNote, command[1]);
                break;
            default: System.out.println("Bug in EditNoteCommand");
                System.exit(1);
            }
            printEditSuccess(usersNote, command[1]);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
        }
    }
}
