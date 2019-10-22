package owlmoney.model.bank;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import owlmoney.model.bank.exception.BankException;
import owlmoney.model.transaction.Expenditure;
import owlmoney.model.transaction.RecurringExpenditureList;
import owlmoney.model.transaction.Transaction;
import owlmoney.model.transaction.TransactionList;
import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

/**
 * Savings account class that extends a normal bank account.
 */
public class Saving extends Bank {

    private double income;
    private static final String SAVING = "saving";
    private static final String ACCOUNT_TYPE = "bank";
    private Date nextIncomeDate;
    private RecurringExpenditureList recurringExpenditures;

    /**
     * Creates an instance of a savings account.
     *
     * @param name          The name of the bank account.
     * @param currentAmount The current amount of money in  the bank account.
     * @param income        The amount of money that is credited monthly into the account.
     */
    public Saving(String name, double currentAmount, double income) {
        super(name, currentAmount);
        this.income = income;
        this.type = SAVING;
        this.transactions = new TransactionList();
        this.recurringExpenditures = new RecurringExpenditureList();
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(new Date());
        calendar.set(Calendar.DATE, 1);
        calendar.add(Calendar.MONTH, 1);
        nextIncomeDate = calendar.getTime();
    }

    /**
     * Updates the next income date and current amount if an income has been earned.
     *
     * @return If there is an update to the income.
     */
    private boolean earnedIncome() {
        if (income > 0 && new Date().compareTo(nextIncomeDate) >= 0) {
            addToAmount(income);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nextIncomeDate);
            calendar.add(Calendar.MONTH, 1);
            nextIncomeDate = calendar.getTime();
            return true;
        }
        return false;
    }

    /**
     * Gets the income of the bank accounts.
     *
     * @return the income of the bank account which includes income and type.
     */
    @Override
    public double getIncome() {
        return income;
    }

    /**
     * Gets the description of the bank accounts.
     *
     * @return the description of the bank account which includes income and type.
     */
    @Override
    public String getDescription() {
        return super.getDescription() + "\nIncome: $" + new DecimalFormat("0.00").format(getIncome());
    }

    /**
     * Adds an expenditure tied to this instance of the bank account.
     *
     * @param exp      an instance of expenditure.
     * @param ui       required for printing.
     * @param bankType Type of bank to add expenditure into.
     * @throws BankException If bank account becomes negative after adding expenditure.
     */
    @Override
    public void addInExpenditure(Transaction exp, Ui ui, String bankType) throws BankException {
        if (!"bank".equals(bankType) && !"savings transfer".equals(bankType)) {
            throw new BankException("Bonds cannot be added to this account");
        }
        if (exp.getAmount() > this.getCurrentAmount()) {
            throw new BankException("Bank account cannot have a negative amount");
        } else {
            transactions.addExpenditureToList(exp, ui, bankType);
            deductFromAmount(exp.getAmount());
        }
    }

    /**
     * Lists the deposits in the current bank account.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     */
    @Override
    void listAllDeposit(Ui ui, int displayNum) throws TransactionException {
        transactions.listDeposit(ui, displayNum);
    }

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui         Ui of OwlMoney.
     * @param displayNum Number of expenditure to list.
     * @throws TransactionException If no expenditure is found.
     */
    @Override
    void listAllExpenditure(Ui ui, int displayNum) throws TransactionException {
        transactions.listExpenditure(ui, displayNum);
    }

    /**
     * Deletes an expenditure tied to this bank account.
     *
     * @param exId The id of the expenditure in ExpenditureList.
     * @param ui   required for printing.
     * @throws TransactionException If invalid transaction.
     */
    @Override
    public void deleteExpenditure(int exId, Ui ui) throws TransactionException {
        addToAmount(transactions.deleteExpenditureFromList(exId, ui));
    }

    /**
     * Sets a new income of the current bank account.
     *
     * @param newIncome Income to set.
     */
    @Override
    void setIncome(double newIncome) {
        this.income = newIncome;
    }

    /**
     * Edits the expenditure details from the current bank account.
     *
     * @param expNum   Transaction number.
     * @param desc     New description.
     * @param amount   New amount.
     * @param date     New date.
     * @param category New category.
     * @param ui       Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If amount is negative after editing expenditure.
     */
    @Override
    void editExpenditureDetails(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException, BankException {
        if (!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + transactions.getExpenditureAmount(expNum) < Double.parseDouble(amount)) {
            throw new BankException("Bank account cannot have a negative amount");
        }
        double oldAmount = transactions.getExpenditureAmount(expNum);
        double newAmount = transactions.editExpenditure(expNum, desc, amount, date, category, ui);
        this.addToAmount(oldAmount);
        this.deductFromAmount(newAmount);
    }

    /**
     * Edits the deposit details from the current bank account.
     *
     * @param expNum Transaction number.
     * @param desc   New description.
     * @param amount New amount.
     * @param date   New date.
     * @param ui     Ui of OwlMoney.
     * @throws TransactionException If incorrect date format.
     * @throws BankException        If amount becomes negative after editing deposit.
     */
    @Override
    void editDepositDetails(int expNum, String desc, String amount, String date, Ui ui)
            throws TransactionException, BankException {
        if (!(amount.isEmpty() || amount.isBlank()) && this.getCurrentAmount()
                + Double.parseDouble(amount) < transactions.getDepositValue(expNum)) {
            throw new BankException("Bank account cannot have a negative amount");
        }
        double oldAmount = transactions.getDepositValue(expNum);
        double newAmount = transactions.editDeposit(expNum, desc, amount, date, ui);
        this.addToAmount(newAmount);
        this.deductFromAmount(oldAmount);
    }

    /**
     * Adds a new deposit to the current bank account.
     *
     * @param dep      Deposit to add.
     * @param ui       Ui of OwlMoney.
     * @param bankType Type of bank to add deposit into.
     */
    @Override
    void addDepositTransaction(Transaction dep, Ui ui, String bankType) throws BankException {
        if (!"bank".equals(bankType) && !"savings transfer".equals(bankType)) {
            throw new BankException("This account does not support investment account deposits");
        }
        transactions.addDepositToList(dep, ui, bankType);
        addToAmount(dep.getAmount());
    }

    /**
     * Deletes a deposit from the current bank account.
     *
     * @param index Transaction number.
     * @param ui    Ui of OwlMoney.
     * @throws TransactionException If transaction is not a deposit.
     * @throws BankException        If amount becomes negative after editing deposit.
     */
    @Override
    void deleteDepositTransaction(int index, Ui ui) throws TransactionException, BankException {
        double depositValue = transactions.getDepositValue(index);
        if (this.getCurrentAmount() < depositValue) {
            throw new BankException("Bank account cannot have a negative amount");
        } else {
            this.deductFromAmount(transactions.deleteDepositFromList(index, ui));
        }
    }

    /**
     * Updates the recurring expenditure to the net date and add an expenditure to expenditure list if overdue.
     *
     * @param recurringExpenditure The recurring expenditure to check.
     * @param outdatedState The state of the recurring expenditure if it is outdated.
     * @param ui Used for printing.
     * @return Outdated state of the expenditure.
     * @throws BankException If bank amount becomes negative.
     */
    private boolean savingUpdateRecurringExpenditure(Transaction recurringExpenditure, boolean outdatedState, Ui ui)
            throws BankException {
        DateFormat dateOutputFormat = new SimpleDateFormat("dd MMMM yyyy");
        Date expenditureDate = null;
        boolean currentState = outdatedState;
        try {
            expenditureDate = dateOutputFormat.parse(recurringExpenditure.getDate());
        } catch (ParseException e) {
            //check is already done when adding expenditure
        }
        if (new Date().compareTo(expenditureDate) >= 0) {
            Transaction newExpenditure = new Expenditure(
                    recurringExpenditure.getDescription(), recurringExpenditure.getAmount(),
                    expenditureDate, recurringExpenditure.getCategory());
            addInExpenditure(newExpenditure, ui, ACCOUNT_TYPE);
            Calendar calendar = Calendar.getInstance();
            calendar.clear();
            calendar.setTime(expenditureDate);
            calendar.add(Calendar.MONTH, 1);
            recurringExpenditure.setDate(calendar.getTime());
            currentState = true;
        }
        return currentState;
    }

    /**
     * Updates all recurring expenditures in the bank.
     * @param ui Used for printing.
     */
    @Override
    void updateRecurringTransactions(Ui ui) {
        boolean outdatedIncome;
        boolean outdatedExpenditure;
        do {
            outdatedExpenditure = false;
            outdatedIncome = earnedIncome();
            for (int i = 0; i < recurringExpenditures.getListSize(); i++) {
                try {
                    outdatedExpenditure = savingUpdateRecurringExpenditure(
                            recurringExpenditures.getRecurringExpenditure(i), outdatedExpenditure, ui);
                } catch (BankException errorMessage) {
                    ui.printError("There is not enough money in the bank for: "
                            + recurringExpenditures.getRecurringExpenditure(i).getDescription());
                }
            }
        } while (outdatedIncome || outdatedExpenditure);
    }

    /**
     * Adds a new recurring expenditure to the bank.
     *
     * @param newExpenditure New recurring expenditure to be added.
     * @param ui Used for printing.
     * @throws TransactionException If the recurring expenditure list is full.
     */
    void savingAddRecurringExpenditure(Transaction newExpenditure, Ui ui) throws TransactionException {
        recurringExpenditures.addRecurringExpenditure(newExpenditure, ui);
    }

    /**
     * Deletes a recurring expenditure from the bank.
     *
     * @param index Index of the recurring expenditure.
     * @param ui Used for printing.
     * @throws TransactionException If there are 0 recurring expenditures or index is out of range.
     */
    void savingDeleteRecurringExpenditure(int index, Ui ui) throws TransactionException {
        recurringExpenditures.deleteRecurringExpenditure(index, ui);
    }

    /**
     * Edits a recurring transaction from the bank.
     *
     * @param index Index of the recurring expenditure.
     * @param description New description of the recurring expenditure.
     * @param amount New amount of the recurring expenditure.
     * @param category New category of the recurring expenditure.
     * @param ui Used for printing.
     * @throws TransactionException If there are 0 recurring expenditures or index is out of range.
     */
    void savingEditRecurringExpenditure(int index, String description, String amount, String category, Ui ui)
            throws TransactionException {
        recurringExpenditures.editRecurringExpenditure(index, description, amount, category, ui);
    }

    /**
     * Lists all recurring expenditures in the bank.
     *
     * @param ui Used for printing.
     * @throws TransactionException If there are 0 recurring expenditures.
     */
    void savingListRecurringExpenditure(Ui ui) throws TransactionException {
        recurringExpenditures.listRecurringExpenditure(ui);
    }
}
