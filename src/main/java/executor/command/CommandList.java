package executor.command;

import storage.StorageManager;

public class CommandList extends Command {

    /**
     * Constructor for CommandList subCommand Class.
     * @param userInput The user input from the CLI
     */
    public CommandList(String userInput) {
        super();
        this.userInput = userInput;
        this.description = "Lists all tasks and receipts added by user"
                + "FORMAT : LIST \n";
        this.commandType = CommandType.LIST;
    }

    @Override
    public void execute(StorageManager storageManager) {
        String outputStr = "You have ("
                    + storageManager.getTaskListSize()
                    + ") Tasks in your list!\n";
        outputStr += storageManager.getPrintableTasks();
        outputStr += "\n";
        outputStr += "You have ("
                + storageManager.getReceiptTrackerSize()
                + ") receipts!\n";
        outputStr += storageManager.getPrintableReceipts();
        this.infoCapsule.setCodeCli();
        this.infoCapsule.setOutputStr(outputStr);
    }
}
