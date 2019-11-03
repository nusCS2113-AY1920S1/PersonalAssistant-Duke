package javacake.commands;

import javacake.Logic;
import javacake.exceptions.CakeException;
import javacake.notes.Note;
import javacake.notes.NoteList;
import javacake.storage.StorageManager;
import javacake.ui.Ui;

import java.util.ArrayList;

public class ListNoteCommand extends Command {

    public ListNoteCommand() {

    }

    /**
     * Constructor for ListCommand.
     * Checks that no parameters are included.
     * @param inputCommand List command from user.
     * @throws CakeException If other parameter is appended to command.
     */
    public ListNoteCommand(String inputCommand) throws CakeException {
        checksParam(inputCommand);
        type = CmdType.LISTNOTE;
    }

    /**
     * Prints out the names of all the notes.
     * Notes can be stored beforehand or created when program is running using CreateNoteCommand.
     * @param logic TaskList containing current tasks
     * @param ui the Ui responsible for outputting messages
     * @param storageManager storage container
     * @return String of the file names of the notes.
     * @throws CakeException If file does not exist.
     */
    public String execute(Logic logic, Ui ui, StorageManager storageManager) throws CakeException {
        StringBuilder sb = new StringBuilder();
        ArrayList<Note> notesArchive = new NoteList().compileNotes();
        sb.append("You have ").append(notesArchive.size()).append(" note(s) available!").append("\n");
        int index = 1;
        for (Note n : notesArchive) {
            sb.append(index).append(". ");
            sb.append(n.getName()).append("\n");
            index++;
        }
        return sb.toString();
    }
}
