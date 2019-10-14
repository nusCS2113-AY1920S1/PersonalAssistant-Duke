package owlmoney.model.profile;

import owlmoney.logic.parser.exception.CardException;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.goals.Goals;
import owlmoney.model.goals.GoalsList;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.card.Card;
import owlmoney.model.card.CardList;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;


/**
 * The profile class that stores details of the user which includes bank accounts, cards, names.
 */

public class Profile {
    private String username;
    private BankList bankList;
    private GoalsList goalsList;
    private CardList cardList;


    /**
     * Constructor that creates a new instance of the user profile.
     *
     * @param newUserName The username that the user desires to use.
     */
    public Profile(String newUserName) {
        this.username = newUserName;
        this.bankList = new BankList();
        this.goalsList = new GoalsList();
        this.cardList = new CardList();
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the profile.
     */
    public String getUsername() {
        return username;
    }

    /**
     * Adds a new bank account into the BankList stored in this profile.
     *
     * @param newBank an instance of the new bank account.
     * @param ui      required for printing.
     * @throws BankException If duplicated bank name found.
     */
    public void addNewBank(Bank newBank, Ui ui) throws BankException {
        bankList.addBank(newBank, ui);
    }

    /**
     * Deletes a bank account from the BankList.
     *
     * @param bankName name of the Bank account.
     * @param ui       required for printing.
     * @throws BankException If bank account fails check criteria.
     */
    public void deleteBank(String bankName, String bankType, Ui ui) throws BankException {
        bankList.deleteBank(bankName, bankType, ui);
    }

    /**
     * Lists all the bank accounts in the BankList.
     *
     * @param ui required for printing.
     * @throws BankException If there are no bank account of specified type.
     */
    public void listBanks(String bankType, Ui ui) throws BankException {
        bankList.listBankAccount(bankType, ui);
    }

    /**
     * Adds a new expenditure tied to a specific bank account or credit card.
     *
     * @param accName The name of the bank account or credit card.
     * @param exp     An expenditure object.
     * @param ui      required for printing.
     * @param type    Represents type of expenditure to be added.
     * @throws BankException If bank amount becomes negative after adding expenditure.
     */
    public void addNewExpenditure(String accName, Transaction exp, Ui ui, String type)
            throws BankException, owlmoney.model.card.exception.CardException {
        if ("card".equals(type)) {
            cardList.addExpenditure(accName, exp, ui);
        } else {
            bankList.addExpenditure(accName, exp, ui);
        }
    }

    /**
     * Deletes an expenditure tied to a specific bank account.
     *
     * @param expIndex The index of the expenditure in the expenditureList tied to a specific bank account.
     * @param bankName The name of the bank account.
     * @param ui       required for printing.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If invalid transaction.
     */
    public void deleteExpenditure(int expIndex, String bankName, Ui ui,
                                  String type) throws BankException, TransactionException {
        if ("bank".equals(type)) {
            bankList.deleteExp(expIndex, bankName, ui);
        }
    }

    /**
     * Lists deposits from a specific bank account.
     *
     * @param listedBank Bank account to list from.
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If invalid transaction
     */
    public void listDeposit(String listedBank, Ui ui, int displayNum) throws BankException, TransactionException {
        bankList.listBankDeposit(listedBank, ui, displayNum);
    }

    /**
     * Lists expenditure from a specific a bank account or credit card.
     *
     * @param listedBankOrCard Bank account or credit card to list from.
     * @param ui               required for printing.
     * @param displayNum       Number of expenditure to list.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If no expenditure found.
     */
    public void listExpenditure(String listedBankOrCard, Ui ui, int displayNum, String type)
            throws BankException, TransactionException, owlmoney.model.card.exception.CardException {
        if ("card".equals(type)) {
            cardList.listCardExpenditure(listedBankOrCard, ui, displayNum);
        } else {
            bankList.listBankExpenditure(listedBankOrCard, ui, displayNum);
        }
    }

    /**
     * Edits a specific expenditure from a specific bank account.
     *
     * @param expNum       Transaction number of the expenditure.
     * @param editFromBank Bank account of expenditure.
     * @param desc         New description of expenditure.
     * @param amount       New amount of expenditure.
     * @param date         New date of expenditure.
     * @param category     New category of expenditure.
     * @param ui           required for printing.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If incorrect date format.
     */
    public void editExpenditure(int expNum, String editFromBank, String desc, String amount, String date,
                                String category, Ui ui, String type) throws BankException, TransactionException {
        if ("bank".equals(type)) {
            bankList.editExp(expNum, editFromBank, desc, amount, date, category, ui);
        }
    }

    /**
     * Edits a specific savings account.
     *
     * @param name    Name of savings account to be edited.
     * @param newName New name of the savings account.
     * @param amount  New amount of the savings account.
     * @param income  New income of the saving account.
     * @param ui      required for printing.
     * @throws BankException If duplicate bank account name is found.
     */
    public void editSavingsAccount(String name, String newName, String amount, String income, Ui ui)
            throws BankException {
        bankList.editSavings(name, newName, amount, income, ui);
    }

    /**
     * Edits a specific investment account.
     *
     * @param name    Name of investment account to be edited.
     * @param newName New name of the investment account.
     * @param amount  New amount of the investment account.
     * @param ui      required for printing.
     */
    public void editInvestmentAccount(String name, String newName, String amount, Ui ui) throws BankException {
        bankList.editInvestment(name, newName, amount, ui);
    }

    /**
     * Deletes a specific deposit from a specific bank account.
     *
     * @param depIndex Transaction number of the deposit.
     * @param bankName Bank name of the deposit.
     * @param ui       required for printing.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If transaction is not a deposit.
     */
    public void deleteDeposit(int depIndex, String bankName, Ui ui) throws BankException, TransactionException {
        bankList.deleteDeposit(bankName, depIndex, ui);
    }

    /**
     * Adds a new deposit to a specific bank account.
     *
     * @param accName Bank account name.
     * @param dep     Deposit to be added.
     * @param ui      required for printing.
     * @throws BankException If bank account does not exist.
     */
    public void addNewDeposit(String accName, Transaction dep, Ui ui) throws BankException {
        bankList.addDeposit(accName, dep, ui);
    }

    /**
     * Edits a specific deposit from a specific bank account.
     *
     * @param expNum       Transaction number of the deposit.
     * @param editFromBank Bank account of deposit.
     * @param desc         New description of deposit.
     * @param amount       New amount of deposit.
     * @param date         New date of deposit.
     * @param ui           required for deposit.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If incorrect date format.
     */
    public void editDeposit(int expNum, String editFromBank, String desc, String amount, String date, Ui ui)
            throws BankException, TransactionException {
        bankList.editDep(expNum, editFromBank, desc, amount, date, ui);
    }

    /**
     * Adds bond to a specific bank account.
     *
     * @param accName the name of the bank account.
     * @param newBond the bond object.
     * @param ui      required for printing.
     * @throws BankException If bank account does not exist.
     */
    public void addNewBond(String accName, Bond newBond, Ui ui) throws BankException {
        bankList.addBond(accName, newBond, ui);
    }

    /**
     * Checks if the bond exists before adding.
     *
     * @param accName the bank account name.
     * @param bond    the bond object.
     * @throws BankException If bank account does not exist.
     * @throws BondException If duplicate bond name is found.
     */
    public void isBondUnique(String accName, Bond bond) throws BankException, BondException {
        bankList.isBondExist(accName, bond);
    }

    /**
     * Adds a new credit card into the CardList stored in this profile.
     *
     * @param newCard an instance of the new credit card.
     * @param ui      required for printing.
     */
    public void addNewCard(Card newCard, Ui ui) {
        cardList.addCard(newCard, ui);
    }

    /**
     * Edits a card from the CardList.
     *
     * @param name    name of the credit card.
     * @param newName new name of the credit card if any.
     * @param limit   new limit of the credit card if any.
     * @param rebate  new rebate of the credit card if any.
     * @param ui      required for printing.
     */
    public void editCardDetails(String name, String newName, String limit, String rebate, Ui ui) throws CardException {
        cardList.editCard(name, newName, limit, rebate, ui);
    }

    /**
     * Deletes a card from the CardList.
     *
     * @param name name of the credit card.
     * @param ui   required for printing.
     */
    public void deleteCard(String name, Ui ui) throws CardException {
        cardList.deleteCard(name, ui);
    }

    /**
     * Lists all the cards in the CardList.
     *
     * @param ui required for printing.
     */
    public void listCards(Ui ui) throws CardException {
        cardList.listCards(ui);
    }

    /**
     * Lists all goals in GoalsList.
     *
     * @param ui required for printing.
     */
    public void listGoals(Ui ui) {
        goalsList.listGoals(ui);
    }

    /**
     * Adds a new financial goal.
     *
     * @param goals the goals object.
     * @param ui required for printing.
     * @throws GoalsException If invalid parameters / attempt to add the same goal name.
     */
    public void addGoals(Goals goals, Ui ui) throws GoalsException {
        goalsList.addToGoals(goals, ui);
    }

    /**
     * Deletes a goal from GoalsList.
     *
     * @param name name of the goal.
     * @param ui required for printing.
     * @throws GoalsException If goal does not exists.
     */
    public void deleteGoals(String name, Ui ui) throws GoalsException {
        goalsList.deleteFromGoalList(name, ui);
    }

    /**
     * Edit goals from GoalsList.
     *
     * @param goalName name of goal.
     * @param amount new target amount to reach for the goal.
     * @param date new targeted date to meet goal.
     * @param newName new name for the goal.
     * @param ui required for printing.
     * @throws GoalsException If goal does not exists.
     */
    public void editGoals(String goalName, String amount, String date, String newName, Ui ui) throws GoalsException {
        goalsList.editGoals(goalName, amount, date, newName, ui);
    }
}
