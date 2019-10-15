package gazeeebo.commands;
import gazeeebo.Exception.DukeException;
import gazeeebo.Storage.Storage;
import gazeeebo.Tasks.Task;
import gazeeebo.UI.Ui;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Stack;

public class UndoCommand extends Command {
    @Override
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask) throws DukeException, ParseException, IOException, NullPointerException {
        if (!commandStack.empty() && commandStack.peek().contains("done")) {
            new DoneCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("delete")) {
            new DeleteCommand().undo(list,storage,deletedTask);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("deadline")) {
            new DeadlineCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("/after")) {
            new DoAfterCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("event")) {
            new EventCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("todo")) {
            new TodoCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("/between")) {
            new TimeboundCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("/require")) {
            new FixDurationCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("tentative")) {
            new TentativeEventCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("confirm")) {
            new ConfirmTentativeCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else if (!commandStack.empty() && commandStack.peek().contains("undone")) {
            new UndoneCommand().undo(commandStack.peek(),list,storage);
            System.out.println("I've undo your previous command");
            commandStack.pop();
        } else {
            System.out.println("The previous command cannot be undo");
            for (String command:commandStack) {
                System.out.println(command);
            }
        }
    }

    @Override
    public boolean isExit() {
        return false;
    }
}