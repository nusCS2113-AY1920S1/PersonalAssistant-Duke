package duke.commands;

import duke.DukeException;
import duke.Storage;
import duke.TaskList;
import duke.Ui;
import duke.components.Bar;
import duke.components.Note;
import duke.tasks.Task;

/**
 * A class representing the command to add a new bar of notes to the current song.
 */
public class AddBarCommand extends Command {

    private int songIndex;

    /**
     * Constructor for the command to add a new bar to the current song.
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AddBarCommand(String message) {
        this.message = message;
    }

    /**
     * Modifies the song in the song list and returns the messages intended to be displayed.
     *
     * @param taskList the duke.TaskList object that contains the task list
     * @param ui the Ui object responsible for the reading of user input and the display of
     *           the responses
     * @param storage the Storage object used to read and manipulate the .txt file
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        Note note1;
        Note note2;
        Note note3;
        Note note4;
        int barNo;
        if (message.length() < 7 || !message.substring(0, 7).equals("addbar ")) { //exception if not fully spelt
            throw new DukeException(message);
        }
        try {
            String[] sections = message.substring(7).split(" ");
            barNo = Integer.parseInt(sections[4].substring(4));
            Bar newBar = new Bar(barNo, sections[0] + sections[1] + sections[2] + sections[3]);
            Task song = taskList.getTaskIndex(songIndex);
            //add the bar to the song in the songlist
            //storage.updateFile(taskList);
            return ui.formatAddBar(taskList.getTaskList(), song);
        } catch (Exception e) {
            throw new DukeException(message, "addbar");
        }
    }

    /**
     * Returns a boolean value representing whether the program will terminate or not, used in
     * duke.Duke to reassign a boolean variable checked at each iteration of a while loop.
     *
     * @return a boolean value that represents whether the program will terminate after the command
     */
    @Override
    public boolean isExit() {
        return false;
    }
}
