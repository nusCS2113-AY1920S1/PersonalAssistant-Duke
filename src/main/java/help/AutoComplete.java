package help;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AutoComplete {
    private final String[] List = {
            "add income",
            "add instalment",
            "bank-account",
            "borrowed",
            "change icon",
            "check-balance",
            "check expenditure",
            "check income",
            "delete expenditure",
            "delete goal",
            "delete instalments",
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
    private List<String> CommandList = Arrays.asList(List);

    //@@ ChenChao19
    public AutoComplete() {
        Collections.sort(CommandList);
    }

    public List<String> getCommandList() {
        return CommandList;
    }
}