package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.*;

import java.text.ParseException;

public class FindCommand extends MoneyCommand{

    private String inputString;

    //@@author {therealnickcheong}
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
            ui.appendToGraphContainer(("Goals Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            findCounter = 0;
            descSearch.setLength(0);
            for(Income income: account.getIncomeListTotal()){
                if(income.getDescription().contains(find)){
                    findCounter++;
                    descSearch.append(findCounter).append(".").append(income.toString()).append("\n");
                }
            }
            descSearchOutput = descSearch.toString();
            ui.appendToGraphContainer(("Income Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            findCounter = 0;
            descSearch.setLength(0);
            for(Expenditure expenditure: account.getExpListTotal()){
                if(expenditure.getDescription().contains(find)){
                    findCounter++;
                    descSearch.append(findCounter).append(".").append(expenditure.toString()).append("\n");
                }
            }
            descSearchOutput = descSearch.toString();
            ui.appendToGraphContainer(("Expenditure Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            findCounter = 0;
            descSearch.setLength(0);
            for(Loan loan: account.getLoans()){
                if(loan.getDescription().contains(find)){
                    findCounter++;
                    descSearch.append(findCounter).append(".").append(loan.toString()).append("\n");
                }
            }
            descSearchOutput = descSearch.toString();
            ui.appendToGraphContainer(("Loan Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            findCounter = 0;
            descSearch.setLength(0);
            for(Instalment instalment: account.getInstalments()){
                if(instalment.getDescription().contains(find)){
                    findCounter++;
                    descSearch.append(findCounter).append(".").append(instalment.toString()).append("\n");
                }
            }
            descSearchOutput = descSearch.toString();
            ui.appendToGraphContainer(("Instalment Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");
        }


        //ui.appendToOutput("Got it, Search Results will be printed in the other pane!\n");
    }

    @Override
    //@@author {Chianhaoplanks}
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
