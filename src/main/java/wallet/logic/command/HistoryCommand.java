//@@author A0171206R

package wallet.logic.command;

import wallet.logic.LogicManager;
import wallet.model.Wallet;

import java.util.ArrayList;

public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String MESSAGE_LIST_HISTORY = "Command History from earliest to latest:";
    public static final String MESSAGE_USAGE = "Error in syntax for history command.";

    private ArrayList<String> commandHistory;

    public HistoryCommand() {
        commandHistory = LogicManager.getCommandHistory();
    }

    @Override
    public boolean execute(Wallet wallet) {

        System.out.println(MESSAGE_LIST_HISTORY);
        for (int i = 0; i < commandHistory.size(); i++) {
            System.out.println(commandHistory.get(i));
        }
        return false;
    }
}

