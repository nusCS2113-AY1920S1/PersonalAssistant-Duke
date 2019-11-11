package eggventory.model.states;

import eggventory.model.LoanList;
import eggventory.model.PersonList;
import eggventory.model.StockList;
import eggventory.model.TemplateList;
import eggventory.storage.Storage;
import eggventory.storage.StorageLoadStub;

//@@author patwaririshab
public class State {
    private StockList stockList;
    private LoanList loanList;
    private PersonList personList;
    private TemplateList templateList;

    /**
     * Initializes referrences to static model components.
     */
    public State(StockList stockList, LoanList loanList, PersonList personList, TemplateList templateList) {
        this.stockList = stockList;
        this.loanList = loanList;
        this.personList = personList;
        this.templateList = templateList;
    }

    public StockList getStockList() {
        return stockList;
    }

    public LoanList getLoanList() {
        return loanList;
    }

    public PersonList getPersonList() {
        return personList;
    }

    public TemplateList getTemplateList() {
        return templateList;
    }

    /**
     * Clears current state components and updates with new component value.
     */
    public void setAllStates(StateList stateList) {
        clearAllStates();
        StockList updatedStockList = new StorageLoadStub().loadStockList(stateList.popStockSave(),
                stateList.popStockTypeSave());
        this.stockList = updatedStockList;

        LoanList updatedLoanList = new StorageLoadStub().loadLoanList(stateList.popLoanListSave());
        this.loanList = updatedLoanList;

        String personString = stateList.popPersonListSave();
        PersonList updatedPersonList = new StorageLoadStub().loadPersonList(personString);
        this.personList = updatedPersonList;

        TemplateList updatedTemplateList = new StorageLoadStub().loadTemplateList(stateList.popTemplateListSave());
        this.templateList = updatedTemplateList;
    }

    /**
     * Clears all statelists.
     */
    public void clearAllStates() {
        stockList.getList().clear();
        loanList.getLoansList().clear();
        personList.getPersonList().clear();
        templateList.getTemplates().clear();
    }
}