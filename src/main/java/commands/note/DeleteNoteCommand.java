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

public class DeleteNoteCommand extends EditNoteCommand {
    private String deleteNoteInList(int noteNumber, ArrayList<Note> listToEdit, LocalDate dateToEdit,
                                String period) throws DukeException{
        for (Note n: listToEdit) {
            if (n.noteDate.equals(dateToEdit)) {
                try {
                    String deletedNote = n.notes.get(noteNumber-1);
                    n.notes.remove(noteNumber-1);
                    if (n.notes.isEmpty()) {
                        listToEdit.remove(n);
                    }
                    //WRITE TO FILE!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                    return deletedNote;
                } catch (IndexOutOfBoundsException e) {
                    throw new DukeException("OOPS!!! That note number does not exist.");
                }
            }
        }
        throw new DukeException("OOPS!!! There are no notes for this " + period + " to delete.");
    }

    private void printDeleteSuccess(String usersNote, String period) {
        System.out.println("Got it. I've deleted this note for that " + period +  ":");
        System.out.println(usersNote);
    }

    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage) throws IOException {
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
                noteToBeDeleted = deleteNoteInList(noteNum, NoteList.daily, userDate, command[1]);
                break;
            case "week" :
                noteToBeDeleted = deleteNoteInList(noteNum, NoteList.weekly, userDate, command[1]);
                break;
            case "month":
                noteToBeDeleted = deleteNoteInList(noteNum, NoteList.monthly, userDate, command[1]);
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
