package gazeeebo.commands.note;

import Storage.NoteStorage;

import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.Exception.DukeException;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Deletes a note for a particular day, week or month.
 */
public class DeleteNoteCommand extends EditNoteCommand {
    /**
     * Deletes the note specified if the note exists. Else it throws an exception.
     *
     * @param noteNumber the index of the note to delete
     * @param listToEdit the list of Notes that contains the note to delete depending on if its a day, week or month
     * @param dateToEdit the start date of the period of the note to delete
     * @param period is either day, week or month
     * @return the note that was deleted
     * @throws DukeException if the note to delete does not exist
     */
    private String deleteNoteInList(int noteNumber, ArrayList<Note> listToEdit, LocalDate dateToEdit,
                                String period, String fileName) throws DukeException{
        for (Note n: listToEdit) {
            if (n.noteDate.equals(dateToEdit)) {
                try {
                    String deletedNote = n.notes.get(noteNumber-1);
                    n.notes.remove(noteNumber-1);
                    if (n.notes.isEmpty()) {
                        listToEdit.remove(n);
                    }
                    NoteStorage.writeToFile(fileName, listToEdit);
                    return deletedNote;
                } catch (IndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! That note number does not exist.");
                } catch (IOException f) {
                    throw new DukeException("The " + fileName + " file cannot be opened.");
                }
            }
        }
        throw new DukeException("OOPS!!! There are no gazeeebo.notes for this " + period + " to delete.");
    }

    /**
     * Tells the user that the note has been successfully deleted.
     *
     * @param usersNote the note that was deleted
     * @param period is either day, week or month
     */
    private void printDeleteSuccess(String usersNote, String period) {
        System.out.println("Got it. I've deleted this note for that " + period +  ":");
        System.out.println(usersNote);
    }

    /** The main method that executes all the sub methods. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        //deleteNote day/week/month yyyy-MM-dd <note_num>
        String[] command = ui.FullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "deleteNote");
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

        String noteToBeDeleted;
        try {
            switch (command[1]) {
            case "day":
                noteToBeDeleted = deleteNoteInList(noteNum, NoteList.daily, userDate, command[1], "NoteDaily.txt");
                break;
            case "week" :
                noteToBeDeleted = deleteNoteInList(noteNum, NoteList.weekly, userDate, command[1], "NoteWeekly.txt");
                break;
            case "month":
                noteToBeDeleted = deleteNoteInList(noteNum, NoteList.monthly, userDate, command[1], "NoteMonthly.txt");
                break;
            default: noteToBeDeleted = null;
                break;
            }

            assert noteToBeDeleted != null : "there is a bug in DeleteNoteCommand";
            printDeleteSuccess(noteToBeDeleted, command[1]);
        } catch (DukeException d) {
            ui.showErrorMessage(d);
        }
    }
}
