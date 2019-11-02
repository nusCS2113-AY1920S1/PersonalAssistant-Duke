package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.*;

import java.text.ParseException;
import java.util.ArrayList;

public class FindCommand extends MoneyCommand{

    private String inputString;
    private String find;

    //@@author therealnickcheong
    public FindCommand(String cmd) {
        inputString = cmd;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    public String searchGoal(ArrayList<Goal> itemList, StringBuilder result, String keyword){
        result.setLength(0);
        int findCounter = 0;
            for(Goal goal: itemList){
                if(goal.getDescription().contains(keyword)){
                    findCounter++;
                    result.append(findCounter).append(".").append(goal.toString()).append("\n");
                }
            }
            if(result.length() <= 0) {
                result.append("No Goals Match Your Search!\n");
                return result.toString();
            } else {
                return result.toString();
            }

    }

    public String searchIncome(ArrayList<Income> itemList, StringBuilder result, String keyword){
        result.setLength(0);
        int findCounter = 0;
        for(Income income: itemList){
            if(income.getDescription().contains(keyword)){
                findCounter++;
                result.append(findCounter).append(".").append(income.toString()).append("\n");
            }
        }
        if(result.length() <= 0) {
            result.append("No Income Items Match Your Search!\n");
            return result.toString();
        } else {
            return result.toString();
        }
    }

    public String searchExpenditure(ArrayList<Expenditure> itemList, StringBuilder result, String keyword){
        result.setLength(0);
        int findCounter = 0;
        for(Expenditure expenditure: itemList){
            if(expenditure.getDescription().contains(keyword)){
                findCounter++;
                result.append(findCounter).append(".").append(expenditure.toString()).append("\n");
            }
        }
        if(result.length() <= 0) {
            result.append("No Expenditure Items Match Your Search!\n");
            return result.toString();
        } else {
            return result.toString();
        }
    }

    public String searchLoan(ArrayList<Loan> itemList, StringBuilder result, String keyword){
        result.setLength(0);
        int findCounter = 0;
        for(Loan loan: itemList){
            if(loan.getDescription().contains(keyword)){
                findCounter++;
                result.append(findCounter).append(".").append(loan.toString()).append("\n");
            }
        }
        if(result.length() <= 0) {
            result.append("No Loans Match Your Search!\n");
            return result.toString();
        } else {
            return result.toString();
        }
    }

    public String searchInstalment(ArrayList<Instalment> itemList, StringBuilder result, String keyword){
        result.setLength(0);
        int findCounter = 0;
        for(Instalment instalment: itemList){
            if(instalment.getDescription().contains(keyword)){
                findCounter++;
                result.append(findCounter).append(".").append(instalment.toString()).append("\n");
            }
        }
        if(result.length() <= 0) {
            result.append("No Instalments Match Your Search!\n");
            return result.toString();
        } else {
            return result.toString();
        }
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if(inputString.split(" ").length == 1){
            throw new DukeException("The description of a find cannot be empty.");
        }else{
            try{
                find = inputString.split("find# ")[1];
            }catch(ArrayIndexOutOfBoundsException e){
                throw new DukeException("Only Alphanumeric Search Inputs are Supported");
            }

            StringBuilder descSearch = new StringBuilder();
            String descSearchOutput = searchGoal(account.getShortTermGoals(), descSearch, find);
            ui.appendToGraphContainer(("Goals Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            descSearchOutput = searchIncome(account.getIncomeListTotal(), descSearch, find);
            ui.appendToGraphContainer(("Income Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            descSearchOutput = searchExpenditure(account.getExpListTotal(), descSearch, find);
            ui.appendToGraphContainer(("Expenditure Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            descSearchOutput = searchLoan(account.getLoans(), descSearch, find);
            ui.appendToGraphContainer(("Loan Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");

            descSearchOutput = searchInstalment(account.getInstalments(), descSearch, find);
            ui.appendToGraphContainer(("Instalment Items Found:\n"));
            ui.appendToGraphContainer(descSearchOutput + "\n");
        }

    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
