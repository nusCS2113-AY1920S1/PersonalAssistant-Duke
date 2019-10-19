package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;

import java.util.ArrayList;

public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_LIST_HISTORY = "Command History:";
    public static final String MESSAGE_USAGE = "Error in format for command.";

    private ArrayList<String> commandHistory;

    public HistoryCommand(){
        commandHistory = LogicManager.getCommandHistory();
    }

    @Override
    public boolean execute(Wallet wallet) {

        System.out.println("Showing command history from earliest to latest:");
        for(int i = 1; i < commandHistory.size();  i++ ){
            System.out.println(i + ". " + commandHistory.get(i));
        }

        return false;
    }
}

