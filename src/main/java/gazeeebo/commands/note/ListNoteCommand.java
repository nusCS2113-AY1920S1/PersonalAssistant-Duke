package gazeeebo.commands.note;

import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;
import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.notes.Note;
import gazeeebo.notes.NoteList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Lists out the notes for a particular day, week or month.
 */
public class ListNoteCommand extends AddNoteCommand {
    //listNote day/week/month yyyy-MM-dd

    /**
     * Prints out the notes with indexes from a given list of notes.
     *
     * @param listOFNotes the list of notes for the specified period
     */
    private void printOutNoteList(ArrayList<String> listOFNotes) {
        assert !listOFNotes.isEmpty() : "there is an empty note section of a date (bug in DeleteNoteCommand)";
        for (int i = 0; i < listOFNotes.size(); i++) {
            System.out.println((i+1) + ". " + listOFNotes.get(i));
        }
    }

    /**
     * Finds the list of notes for the specified period and prints them out.
     *
     * @param periodList the list of Notes for the specified period depending on if its a day, week or month
     * @param dateToList the date specified by the user
     * @param period is either day, week or month
     * @throws DukeException if there are no notes for the specified period
     */
    private void printNoteList(ArrayList<Note> periodList, LocalDate dateToList, String period) throws DukeException {
        for (Note n: periodList) {
            if (n.noteDate.equals(dateToList)) {
                System.out.println("Here are your gazeeebo.notes for that " + period + ":");
                printOutNoteList(n.notes);
                return;
            }
        }
        throw new DukeException("There are no gazeeebo.notes for that " + period + ".");
    }

    /** The main method that executes all the sub methods. */
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws IOException {
        String[] command = ui.fullCommand.split(" ");
        LocalDate userDate;
        try {
            userDate = processCommand(command, "listNote");
        } catch (DukeException e) {
            ui.showErrorMessage(e);
            return;
        }
        try {
            switch (command[1]) {
            case "day": printNoteList(NoteList.daily, userDate, command[1]);
                break;
            case "week": printNoteList(NoteList.weekly, userDate, command[1]);
                break;
            case "month": printNoteList(NoteList.monthly, userDate, command[1]);
                break;
            default: System.out.println("Bug in ListNoteCommand");
                System.exit(1);
            }
        } catch (DukeException d) {
            ui.showErrorMessage(d);
        }
    }
}
