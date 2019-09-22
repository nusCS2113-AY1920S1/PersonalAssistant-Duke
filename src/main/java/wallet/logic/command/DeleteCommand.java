package wallet.logic.command;

import wallet.model.Wallet;
import wallet.storage.Storage;
import wallet.model.task.Task;

public class DeleteCommand extends Command {
    public static final String COMMAND_WORD = "delete";
    public static final String MESSAGE_SUCCESS_DELETE_TASK = "Noted. I've removed this task:";

    private String object;
    private int index;

    public DeleteCommand(String object, int index) {
        this.object = object;
        this.index = index;
    }

    @Override
    public boolean execute(Wallet wallet, Storage storage) {
        switch (object) {
        case "task":
            Task task = wallet.getTaskList().getTask(index);
            wallet.getTaskList().deleteTask(index);
            storage.removeTask(wallet.getTaskList().getTaskList(), index);
            System.out.println(MESSAGE_SUCCESS_DELETE_TASK);
            System.out.println(task.toString());
            break;

        default:
            break;
        }
        return false;
    }
}
