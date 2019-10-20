package owlmoney.model.profile;

import java.util.Date;

import owlmoney.model.card.exception.CardException;
import owlmoney.model.bank.Bank;
import owlmoney.model.bank.BankList;
import owlmoney.model.bank.exception.BankException;
import owlmoney.model.bond.Bond;
import owlmoney.model.bond.exception.BondException;
import owlmoney.model.card.Card;
import owlmoney.model.card.CardList;
import owlmoney.model.goals.Goals;
import owlmoney.model.goals.GoalsList;
import owlmoney.model.goals.exception.GoalsException;
import owlmoney.model.transaction.Deposit;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * The profile class that stores details of the user which includes bank accounts, cards, names.
 */

public class Profile {
    private String username;
    private BankList bankList;
    private CardList cardList;
    private GoalsList goalsList;

    private static final String SAVING = "saving";
    private static final String ISBANK = "savings transfer";
    private static final String ISINVESTMENT = "investment transfer";

    /**
     * Creates a new instance of the user profile.
     *
     * @param newUserName The username that the user desires to use.
     */
    public Profile(String newUserName) {
        this.username = newUserName;
        this.bankList = new BankList();
        this.cardList = new CardList();
        this.goalsList = new GoalsList();
    }

    /**
     * Gets the username of the user.
     *
     * @return The username of the profile.
     */
    public String profileGetUsername() {
        return this.username;
    }

    /**
     * Adds a new bank account into the BankList stored in this profile.
     *
     * @param newBank an instance of the new bank account.
     * @param ui      required for printing.
     * @throws BankException If duplicated bank name found.
     */
    public void profileAddNewBank(Bank newBank, Ui ui) throws BankException {
        bankList.bankListAddBank(newBank, ui);
    }

    /**
     * Deletes a bank account from the BankList.
     *
     * @param bankName name of the Bank account.
     * @param ui       required for printing.
     * @throws BankException If bank account fails check criteria.
     */
    public void profileDeleteBank(String bankName, String bankType, Ui ui) throws BankException {
        bankList.bankListDeleteBank(bankName, bankType, ui);
    }

    /**
     * Lists all the bank accounts in the BankList.
     *
     * @param ui required for printing.
     * @throws BankException If there are no bank account of specified type.
     */
    public void profileListBanks(String bankType, Ui ui) throws BankException {
        bankList.bankListListBankAccount(bankType, ui);
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
    public void profileAddNewExpenditure(String accName, Transaction exp, Ui ui, String type)
            throws BankException, CardException {
        if ("card".equals(type)) {
            cardList.cardListAddExpenditure(accName, exp, ui, type);
        } else if ("bank".equals(type) || "bond".equals(type)) {
            bankList.bankListAddExpenditure(accName, exp, ui, type);
        }
    }

    /**
     * Deletes an expenditure tied to a specific bank account.
     *
     * @param expIndex    The index of the expenditure in the expenditureList tied to a specific bank account.
     * @param accountName The name of the card or bank account.
     * @param ui          required for printing.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If invalid transaction.
     * @throws CardException        If card does not exist.
     */
    public void profileDeleteExpenditure(int expIndex, String accountName, Ui ui,
            String type) throws BankException, TransactionException, CardException {
        if ("bank".equals(type)) {
            bankList.bankListDeleteExpenditure(expIndex, accountName, ui);
        } else if ("card".equals(type)) {
            cardList.cardListDeleteExpenditure(expIndex, accountName, ui);
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
    public void profileListDeposit(String listedBank, Ui ui, int displayNum)
            throws BankException, TransactionException {
        bankList.bankListListBankDeposit(listedBank, ui, displayNum);
    }

    /**
     * Lists expenditure from a specific a bank account or credit card.
     *
     * @param listedBankOrCard Bank account or credit card to list from.
     * @param ui               required for printing.
     * @param displayNum       Number of expenditure to list.
     * @param type             Type of account to add expenditure into.
     * @throws BankException        If bank account does not exist.
     * @throws TransactionException If no expenditure found or no expenditure is in the list..
     * @throws CardException        If the credit card name cannot be found.
     */
    public void profileListExpenditure(String listedBankOrCard, Ui ui, int displayNum, String type)
            throws BankException, TransactionException, CardException {
        if ("card".equals(type)) {
            cardList.cardListListCardExpenditure(listedBankOrCard, ui, displayNum);
        } else if ("bank".equals(type)) {
            bankList.bankListListBankExpenditure(listedBankOrCard, ui, displayNum);
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
    public void profileEditExpenditure(int expNum, String editFromBank, String desc, String amount,
            String date, String category, Ui ui, String type)
            throws BankException, TransactionException, CardException {
        if ("card".equals(type)) {
            cardList.cardListEditExpenditure(expNum, editFromBank, desc, amount, date, category, ui);
        } else if ("bank".equals(type)) {
            bankList.bankListEditExpenditure(expNum, editFromBank, desc, amount, date, category, ui);
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
    public void profileEditSavingsAccount(String name, String newName, String amount, String income, Ui ui)
            throws BankException {
        bankList.bankListEditSavings(name, newName, amount, income, ui);
    }

    /**
     * Edits a specific investment account.
     *
     * @param name    Name of investment account to be edited.
     * @param newName New name of the investment account.
     * @param amount  New amount of the investment account.
     * @param ui      required for printing.
     * @throws BankException If duplicate bank account name is found.
     */
    public void profileEditInvestmentAccount(String name, String newName, String amount, Ui ui)
            throws BankException {
        bankList.bankListEditInvestment(name, newName, amount, ui);
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
    public void profileDeleteDeposit(int depIndex, String bankName, Ui ui)
            throws BankException, TransactionException {
        bankList.bankListDeleteDeposit(bankName, depIndex, ui);
    }

    /**
     * Adds a new deposit to a specific bank account.
     *
     * @param accName  Bank account name.
     * @param dep      Deposit to be added.
     * @param ui       required for printing.
     * @param bankType Type of bank to add deposit into
     * @throws BankException If bank account does not exist.
     */
    public void profileAddNewDeposit(String accName, Transaction dep, Ui ui, String bankType)
            throws BankException {
        bankList.bankListAddDeposit(accName, dep, ui, bankType);
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
    public void profileEditDeposit(int expNum, String editFromBank, String desc, String amount,
            String date, Ui ui) throws BankException, TransactionException {
        bankList.bankListEditDeposit(expNum, editFromBank, desc, amount, date, ui);
    }

    /**
     * Adds a new credit card into the CardList stored in this profile.
     *
     * @param newCard an instance of the new credit card.
     * @param ui      required for printing.
     */
    public void profileAddNewCard(Card newCard, Ui ui) throws CardException {
        cardList.cardListAddCard(newCard, ui);
    }

    /**
     * Edits a card from the CardList.
     *
     * @param name    name of the credit card.
     * @param newName new name of the credit card if any.
     * @param limit   new limit of the credit card if any.
     * @param rebate  new rebate of the credit card if any.
     * @param ui      required for printing.
     * @throws CardException If card cannot be found.
     */
    public void profileEditCardDetails(String name, String newName, String limit, String rebate, Ui ui)
            throws CardException {
        cardList.cardListEditCard(name, newName, limit, rebate, ui);
    }

    /**
     * Deletes a card from the CardList.
     *
     * @param name name of the credit card.
     * @param ui   required for printing.
     * @throws CardException If card does not exist.
     */
    public void profileDeleteCard(String name, Ui ui) throws CardException {
        cardList.cardListDeleteCard(name, ui);
    }

    /**
     * Lists all the cards in the CardList.
     *
     * @param ui required for printing.
     * @throws CardException If CardList is empty.
     */
    public void profileListCards(Ui ui) throws CardException {
        cardList.cardListListCards(ui);
    }

    /**
     * Deletes bond from the bondList in the specified investment account.
     *
     * @param bankName the name of the bank account.
     * @param bondName the name of the bond to delete.
     * @param ui       required for printing.
     * @throws BankException if the bank is not found.
     */
    public void profileDeleteBond(String bankName, String bondName, Ui ui)
            throws BankException, BondException {
        bankList.bankListDeleteBond(bankName, bondName, ui);
    }

    /**
     * Gets the bond object from the investment account if it exists.
     *
     * @param bankName the name of the bank account.
     * @param bondName the name of the bond to retrieve.
     * @return the bond object.
     * @throws BankException if the bank does not exist.
     * @throws BondException if the bond does not exist.
     */
    public Bond profileGetBond(String bankName, String bondName) throws BankException, BondException {
        return bankList.bankListGetBond(bankName, bondName);
    }

    /**
     * Adds bond to a specific bank account.
     *
     * @param bankName the name of the bank account.
     * @param newBond  the bond object.
     * @param ui       required for printing.
     * @throws BankException If bank account does not exist.
     */
    public void profileAddNewBond(String bankName, Bond newBond, Ui ui) throws BankException {
        bankList.bankListAddBond(bankName, newBond, ui);
    }

    /**
     * Checks if the bond exists before adding.
     *
     * @param bankName the bank account name.
     * @param bond     the bond object.
     * @throws BankException If bank account does not exist.
     * @throws BondException If duplicate bond name is found.
     */
    public void profileIsBondUnique(String bankName, Bond bond) throws BankException, BondException {
        bankList.bankListIsBondExist(bankName, bond);
    }

    /**
     * Edits the bond in the bank account.
     *
     * @param bankName the name of the bank.
     * @param bondName the name of the bond to edit.
     * @param year     the new year of the bond.
     * @param rate     the new rate
     * @param ui       required for printing.
     * @throws BankException If bank account does not exist.
     * @throws BondException If there are no bonds.
     */
    public void profileEditBond(String bankName, String bondName, String year, String rate, Ui ui)
            throws BankException, BondException {
        bankList.bankListEditBond(bankName, bondName, year, rate, ui);
    }

    /**
     * Lists the bonds in the bank specified bank account.
     *
     * @param bankName   the name of the bank account.
     * @param ui         required for printing.
     * @param displayNum the number of bonds to display.
     * @throws BankException If bank account does not exist.
     * @throws BondException If there are no bonds.
     */
    public void profileListBonds(String bankName, Ui ui, int displayNum)
            throws BankException, BondException {
        bankList.bankListListBond(bankName, ui, displayNum);
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
     * @param ui    required for printing.
     * @throws GoalsException If invalid parameters / attempt to add the same goal name.
     */
    public void addGoals(Goals goals, Ui ui) throws GoalsException {
        goalsList.addToGoals(goals, ui);
    }

    /**
     * Deletes a goal from GoalsList.
     *
     * @param name name of the goal.
     * @param ui   required for printing.
     * @throws GoalsException If goal does not exists.
     */
    public void deleteGoals(String name, Ui ui) throws GoalsException {
        goalsList.deleteFromGoalList(name, ui);
    }

    /**
     * Edit goals from GoalsList.
     *
     * @param goalName name of goal.
     * @param amount   new target amount to reach for the goal.
     * @param date     new targeted date to meet goal.
     * @param newName  new name for the goal.
     * @param ui       required for printing.
     * @throws GoalsException If goal does not exists.
     */
    public void editGoals(String goalName, String amount, String date, String newName, Ui ui) throws GoalsException {
        goalsList.editGoals(goalName, amount, date, newName, ui);
    }

    /**
     * Transfers fund from one bank account to another bank account from GoalsList.
     *
     * @param from   The account name for transferring the fund.
     * @param to     The account name to receive the fund.
     * @param amount The amount to be transferred.
     * @param date   The date that the fund was transferred.
     * @param ui     Required for printing.
     * @throws GoalsException If any of the bank does not exist or insufficient fund to transfer.
     */
    public void transferFund(String from, String to, double amount, Date date,
            Ui ui) throws BankException {
        String fromType = bankList.bankListIsAccountExistToTransfer(from, amount);
        String toType = bankList.bankListIsAccountExistToReceive(to);
        String descriptionTo = "Fund Transfer to " + to;
        String category = "Transfer Fund";
        Transaction newExpenditure = new Expenditure(descriptionTo, amount, date, category);
        bankList.bankListAddExpenditure(from, newExpenditure, ui, checkBankType(fromType));
        String descriptionFrom = " Fund Received from " + from;
        Transaction newDeposit = new Deposit(descriptionFrom, amount, date, "deposit");
        bankList.bankListAddDeposit(to, newDeposit, ui, checkBankType(toType));
    }

    /**
     * Checks whether the bank account is a savings or investment account.
     *
     * @param type type of bank account.
     * @return the result whether it is to be transfer or deposit to a savings or investment account.
     */
    private String checkBankType(String type) {
        if (SAVING.equals(type)) {
            return ISBANK;
        } else {
            return ISINVESTMENT;
        }
    }
}
