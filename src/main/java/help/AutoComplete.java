package help;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This class is created to parse out the list of all the commands available to
 * the userInput TextField.
 */
public class AutoComplete {
    private final String[] list = {
        "add income",
        "add instalment",
        "bank-account",
        "borrowed",
        "bye",
        "change icon",
        "check-balance",
        "check expenditure",
        "check income",
        "delete expenditure",
        "delete goal",
        "delete instalment",
        "deposit",
        "done goal",
        "goal",
        "graph curr finance",
        "graph expenditure trend",
        "graph income trend",
        "graph monthly report",
        "init",
        "lent",
        "list all income",
        "list all expenditure",
        "list all instalments",
        "list all loans",
        "list bank trackers",
        "list incoming loans",
        "list goals",
        "list month income",
        "list month expenditure",
        "list outgoing loans",
        "paid",
        "received",
        "spent",
        "start",
        "split",
        "settle",
        "withdraw"
    };
    private List<String> commandList = Arrays.asList(list);

    //@@author ChenChao19
    /**
     * This is the constructor for the AutoComplete class.
     * It automatically sorts the list of all commands when it instantiates.
     */
    public AutoComplete() {
        Collections.sort(commandList);
    }

    /**
     * This method is the getter for the list of commands.
     * @return The list of the sorted commands.
     */
    public List<String> getCommandList() {
        return commandList;
    }
}