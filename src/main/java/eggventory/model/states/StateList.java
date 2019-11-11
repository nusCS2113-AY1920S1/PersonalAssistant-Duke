package eggventory.model.states;

import eggventory.model.LoanList;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.TemplateList;

import java.util.Stack;

//@@author patwaririshab

/**
 * The statelist class stores 4 stacks each storing states of the following data in string form.
 * The statelist is used in the StateInterface class for maintaining a futurelist and historylist
 * object used to maintain states to enable the undo and redo command.
 * 1) Stock
 * 2) StockType
 * 3) Loans
 * 4) Templates
 * 5) Persons
 */
public class StateList {
    public Stack<String> stockStates;
    private Stack<String> stockTypeStates;
    private Stack<String> loanListStates;
    private Stack<String> templateListStates;
    private Stack<String> personListStates;

    /**
     * Initialises stacks to store historical states of model components.
     */
    public StateList() {
        this.stockStates = new Stack<>();
        this.stockTypeStates = new Stack<>();
        this.loanListStates = new Stack<>();
        this.templateListStates = new Stack<>();
        this.personListStates = new Stack<>();
    }

    public boolean isEmpty() {
        return (stockStates.empty() || stockTypeStates.empty() || loanListStates.empty());
    }

    private void pushStockSave(String stockStateSave) {
        stockStates.push(stockStateSave);
    }

    private void pushStockTypeSave(String stockTypeSave) {
        stockTypeStates.push(stockTypeSave);
    }

    private void pushLoanListSave(String loanListSave) {
        loanListStates.push(loanListSave);
    }

    private void pushTemplateListSave(String templateListSave) {
        templateListStates.push(templateListSave);
    }

    private void pushPersonListSave(String personListSave) {
        personListStates.push(personListSave);
    }

    /**
     * Push all state components to statelist.
     */
    public void pushAllStatesSave(State currentState) {
        StockList stockList = currentState.getStockList();
        pushStockSave(stockList.saveDetailsString());
        pushStockTypeSave(stockList.saveStockTypesString());

        LoanList loanList = currentState.getLoanList();
        pushLoanListSave(loanList.saveLoanListString());

        PersonList personList = currentState.getPersonList();
        pushPersonListSave(personList.savePersonListString());

        TemplateList templateList = currentState.getTemplateList();
        pushTemplateListSave(templateList.saveTemplateListString());
    }


    public String popStockSave() {
        return stockStates.pop();
    }

    public String popStockTypeSave() {
        return stockTypeStates.pop();
    }

    public String popLoanListSave() {
        return loanListStates.pop();
    }

    public String popPersonListSave() {
        return personListStates.pop();
    }

    public String popTemplateListSave() {
        return templateListStates.pop();
    }
}
