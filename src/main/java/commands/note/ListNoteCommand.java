package commands.note;

import Storage.Storage;
import Tasks.Task;
import UI.Ui;
import Exception.DukeException;
import notes.Note;
import notes.NoteList;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Stack;

/**
 * Lists out the notes for a particular day, week or month.
 */
public class ListNoteCommand extends AddNoteCommand {
    //listNote day/week/month yyyy-MM-dd

    /***
     *
     * @param listOFNotes
     */
    private void printOutNoteList(ArrayList<String> listOFNotes) {
        assert !listOFNotes.isEmpty() : "there is an empty note section of a date (bug in DeleteNoteCommand)";
        for (int i = 0; i < listOFNotes.size(); i++) {
            System.out.println((i+1) + ". " + listOFNotes.get(i));
        }
    }

    private void printNoteList(ArrayList<Note> periodList, LocalDate dateToList, String period) throws DukeException {
        for (Note n: periodList) {
            if (n.noteDate.equals(dateToList)) {
                System.out.println("Here are your notes for that " + period + ":");
                printOutNoteList(n.notes);
                return;
            }
        }
        throw new DukeException("There are no notes for that " + period + ".");
    }

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws IOException {
        String[] command = ui.FullCommand.split(" ");
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
