package help;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class AutoComplete {
    private List<String> PopUpList;
    private List<String> MatchList;
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
            "goal-short",
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

    public AutoComplete()  {
        Collections.sort(CommandList);
        this.PopUpList = new ArrayList<String>();
        this.MatchList = new ArrayList<String>();
    }

    public List<String> Populate(String prefix) {
        populatePopup(prefix);
        return PopUpList;
    }

    public void populatePopup(String prefix) {
        int maxEntries = 5;
        allMatches(prefix);
        int count = Math.min(MatchList.size(), maxEntries);
        for (int i = 0; i < count; i++) {
            PopUpList.add(MatchList.get(i));
        }
    }

    public void allMatches(String prefix) {
        int firstIndex = -1;
        for (int i = 0; i < CommandList.size(); i++) {
            if (CommandList.get(i).startsWith(prefix)) {
                firstIndex = i;
                break;
            }
        }

        if (firstIndex == -1) {
            return;
        }

        int lastIndex = firstIndex;
        for (int i = firstIndex + 1; i < CommandList.size(); i++) {
            if (!CommandList.get(i).startsWith(prefix)) {
                lastIndex = i - 1;
                break;
            }
        }

        for(int i = firstIndex; i <= lastIndex; i++) {
            MatchList.add(CommandList.get(i));
        }
    }
}