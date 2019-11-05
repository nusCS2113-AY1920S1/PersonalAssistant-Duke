package Commands;

import Commons.Storage;
import Commons.UserInteraction;
import Tasks.TaskList;

/**
 * Abstract class Command with methods representing all the Command subclasses to be
 * carried out when an input is entered by the user.
 */
public abstract class Command {
    public abstract String execute(TaskList events, TaskList deadlines, UserInteraction ui, Storage storage) throws Exception;
}