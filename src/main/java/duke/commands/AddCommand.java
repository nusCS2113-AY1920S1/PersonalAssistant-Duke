package duke.commands;

import duke.TaskList;
import duke.Ui;
import duke.DukeException;
import duke.Storage;
import duke.tasks.ToDo;
import duke.tasks.Deadline;
import duke.tasks.Event;

/**
 * A class representing the command to add tasks to the task list.
 */
public class AddCommand extends Command {

    /**
     * Constructor for the command to add a task to the task list.
     *
     * @param message the input message that resulted in the creation of the duke.Commands.Command
     */
    public AddCommand(String message) {
        this.message = message;
    }

    /**
     * Modifies the task list in use and returns the messages intended to be displayed.
     *
     * @param taskList the duke.TaskList object that contains the task list
     * @param ui the Ui object that determines the displayed output of duke.Duke
     * @param storage the storage
     * @return the string to be displayed in duke.Duke
     * @throws DukeException if an exception occurs in the parsing of the message or in IO
     */
    public String execute(TaskList taskList, Ui ui, Storage storage) throws DukeException {
        String identifier;
        try {
            identifier = message.substring(0, 4);
        } catch (Exception e) {
            throw new DukeException("","other");
        }
        switch (identifier) {
        case "todo": {
            ToDo todo;
            if (message.length() < 5 || !message.substring(4,5).equals(" ")) {
                throw new DukeException(message);
            }
            try {
                todo = new ToDo(message.trim().substring(5));
                taskList.add(todo);
                storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), todo);
            } catch (Exception e) {
                throw new DukeException(message, "todo"); //empty to-do
            }
        }
        case "dead": {
            if (message.length() < 9 || !message.substring(4,9).equals("line ")) { //exception if not fully spelt
                throw new DukeException(message);
            }
            Deadline deadline;
            try {
                String[] sections = message.substring(9).split(" /by ");
                deadline = new Deadline(sections[0], sections[1]);
                taskList.add(deadline);
                storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), deadline);
            } catch (Exception e) {
                throw new DukeException(message,"deadline");
            }
        }
        case "even": {
            if (message.length() < 6 || !message.substring(4,6).equals("t ")) { //exception if not fully spelt
                throw new DukeException(message);
            }
            Event event;
            try {
                String[] sections = message.substring(6).split(" /at ");
                event = new Event(sections[0], sections[1]);
                taskList.add(event);
                storage.updateFile(taskList);
                return ui.formatAdd(taskList.getTaskList(), event);
            } catch (Exception e) {
                throw new DukeException(message, "event");
            }
        }
        default: {
            throw new DukeException(message);
        }
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
