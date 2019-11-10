package eggventory.model;

import eggventory.commons.exceptions.BadInputException;
import eggventory.model.states.State;
import eggventory.model.states.StateList;

//@@author patwaririshab
public class StateInterface {
    private StateList historyList;
    private StateList futureList;
    private static State currentState;

    /**
     * Initializes the static currentState which contains Model containers and historylist and futurelists.
     */
    public StateInterface(StockList stockList, LoanList loanList, PersonList personList, TemplateList templateList) {
        this.historyList = new StateList();
        this.futureList = new StateList();
        this.currentState = new State(stockList, loanList, personList, templateList);
    }

    /**
     * Pushes state of model components converted into String to historylist.
     */
    public void pushStateHistoryList() {
        //Push current state into historyList
        historyList.pushAllStatesSave(currentState); //No problem
    }

    /**
     * Pushes state of model components converted into String to futurelist.
     */
    public void pushStateFutureList() {
        //Push current state into futurelist
        futureList.pushAllStatesSave(currentState); //No problem
    }


    public StockList getStockList() {
        return currentState.getStockList();
    }

    public LoanList getLoanList() {
        return currentState.getLoanList();
    }

    public PersonList getPersonList() {
        return currentState.getPersonList();
    }

    public TemplateList getTemplateList() {
        return currentState.getTemplateList();
    }

    /**
     * Pushes current state into futurelist and loads last state from historylist.
     * @throws BadInputException when previous state contains bad input.
     */
    public void executeUndoCommand() throws BadInputException {
        if (historyList.isEmpty()) {
            throw new BadInputException("The UNDO command could not be executed.\n");
        }

        //Push current state information into futurelist
        pushStateFutureList();

        //Update currentstate infromation with new state information
        currentState.setAllStates(historyList);
    }

    /**
     * Pushes current state into historylist and loads last state from futurelist.
     * @throws BadInputException when future state contains bad input.
     */
    public void executeRedoCommand() throws BadInputException {
        if (futureList.isEmpty()) {
            throw new BadInputException("The REDO command could not be executed.\n");
        }

        //Push current state into history list
        pushStateHistoryList();

        //Update currentstate infromation with new stateinformation
        currentState.setAllStates(futureList);
    }

    /**
     * Pushes current state into historylist and reinitialises a clean futurelist.
     */
    public void updateStateHistory() {
        futureList = new StateList();
        pushStateHistoryList();
    }

    public StateList getHistoryList() {
        return historyList;
    }

    public StateList getFutureList() {
        return futureList;
    }
}
//@@author
