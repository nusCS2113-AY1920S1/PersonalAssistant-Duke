package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Goal;

import java.text.ParseException;

public class FindCommand extends MoneyCommand{

    private String inputString;

    public FindCommand(String cmd) {
        inputString = cmd;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if(inputString.split(" ").length == 1){
            throw new DukeException("The description of a find cannot be empty.");
        }else{
            String find = inputString.split("find ")[1];
            StringBuilder descSearch = new StringBuilder();
            int findCounter = 0;
            for(Goal goal: account.getShortTermGoals()){
                if(goal.getDescription().contains(find)){
                    findCounter++;
                    descSearch.append(findCounter).append(".").append(goal.toString()).append("\n");
                }
            }
            String descSearchOutput = descSearch.toString();
            ui.appendToGraphContainer(descSearchOutput);

        }


        //ui.appendToOutput("Got it, Search Results will be printed in the other pane!\n");
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
