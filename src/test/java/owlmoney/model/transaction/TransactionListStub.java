package owlmoney.model.transaction;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import owlmoney.model.transaction.exception.TransactionException;
import owlmoney.ui.Ui;

public class TransactionListStub extends TransactionList {

    private ArrayList<Transaction> expLists = new ArrayList<>();
    private static final int ONE_INDEX = 1;

    /**
     * Lists the expenditures in the current bank account.
     *
     * @param ui         required for printing.
     * @param displayNum Number of expenditures to list.
     * @throws TransactionException If no expenditure is found or no expenditure is in the list.
     */
    @Override
    public void listExpenditure(Ui ui, int displayNum) throws TransactionException {
        if (this.expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = displayNum;
            boolean expenditureExist = false;
            for (int i = this.expLists.size() - ONE_INDEX; i >= 0; i--) {
                if (this.expLists.get(i).getSpent()) {
                    System.out.print("Expenditure exists");
                    expenditureExist = true;
                    counter--;
                }
                if (counter <= 0) {
                    break;
                }
            }
            if (!expenditureExist) {
                throw new TransactionException("No expenditures found");
            }
        }
    }

    /**
     * Adds an expenditure to the TransactionList.
     *
     * @param exp an instance of an expenditure.
     * @param ui  required for printing.
     */
    @Override
    public void addExpenditureToList(Transaction exp, Ui ui, String type) {
        this.expLists.add(exp);
    }

    /**
     * Adds a deposit to the TransactionList.
     *
     * @param dep an instance of an deposit.
     * @param ui  required for printing.
     */
    @Override
    public void addDepositToList(Transaction dep, Ui ui, String type) {
        this.expLists.add(dep);
    }

    /**
     * Lists the deposits in the current bank account.
     *
     * @param ui         required for printing.
     * @param displayNum Number of deposits to list.
     * @throws TransactionException If no deposit is found.
     */
    @Override
    public void listDeposit(Ui ui, int displayNum) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        } else {
            int counter = displayNum;
            boolean depositExist = false;
            for (int i = expLists.size() - ONE_INDEX; i >= 0; i--) {
                if (!this.expLists.get(i).getSpent()) {
                    System.out.print("Deposit exists");
                    depositExist = true;
                    counter--;
                }
                if (counter <= 0) {
                    break;
                }
            }
            if (!depositExist) {
                throw new TransactionException("No deposits found");
            }
        }
    }

    /**
     * Deletes an expenditure to the TransactionList.
     *
     * @param index index of the expenditure in the TransactionList.
     * @param ui    required for printing.
     * @throws TransactionException If invalid transaction.
     */
    @Override
    public double deleteExpenditureFromList(int index, Ui ui) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (!expLists.get(index - 1).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                Transaction temp = expLists.get(index - ONE_INDEX);
                expLists.remove(index - ONE_INDEX);
                System.out.print("Expenditure removed");
                return temp.getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Edits the specific expenditure in the list.
     *
     * @param expNum   Transaction number of the expenditure.
     * @param desc     New description of the expenditure.
     * @param amount   New amount of the expenditure.
     * @param date     New date of the expenditure.
     * @param category New category of the expenditure.
     * @param ui       required for printing.
     * @return New amount of the expenditure.
     * @throws TransactionException If incorrect date format.
     */
    @Override
    public double editEx(int expNum, String desc, String amount, String date, String category, Ui ui)
            throws TransactionException {
        if (!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setDescription(desc);
            System.out.print("New description: " + expLists.get(expNum - ONE_INDEX).getDescription());
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setAmount(Double.parseDouble(amount));
            System.out.print("New amount: " + expLists.get(expNum - ONE_INDEX).getAmount());
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                expLists.get(expNum - ONE_INDEX).setDate(temp.parse(date));
                System.out.print("New date: " + expLists.get(expNum - ONE_INDEX).getDate());
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
                throw new TransactionException(e.toString());
            }
        }
        return expLists.get(expNum - ONE_INDEX).getAmount();
    }

    /**
     * Edits the specific deposit in the list.
     *
     * @param expNum Transaction number of the deposit.
     * @param desc   New description of the deposit.
     * @param amount New amount of the deposit.
     * @param date   New date of the deposit.
     * @param ui     required for printing.
     * @return New amount of the deposit.
     * @throws TransactionException If incorrect date format.
     */
    @Override
    public double editDep(int expNum, String desc, String amount, String date, Ui ui) throws TransactionException {
        if (!(desc.isBlank() || desc.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setDescription(desc);
            System.out.print("New description: " + expLists.get(expNum - ONE_INDEX).getDescription());
        }
        if (!(amount.isBlank() || amount.isEmpty())) {
            expLists.get(expNum - ONE_INDEX).setAmount(Double.parseDouble(amount));
            System.out.print("New amount: " + expLists.get(expNum - ONE_INDEX).getAmount());
        }
        if (!(date.isBlank() || date.isEmpty())) {
            DateFormat temp = new SimpleDateFormat("dd/MM/yyyy");
            try {
                expLists.get(expNum - ONE_INDEX).setDate(temp.parse(date));
                System.out.print("New date: " + expLists.get(expNum - ONE_INDEX).getDate());
            } catch (ParseException e) {
                //check handled in ParseEditExpenditure
                throw new TransactionException(e.toString());
            }
        }
        return expLists.get(expNum - ONE_INDEX).getAmount();
    }

    /**
     * Gets the specific expenditure amount.
     *
     * @param index Transaction number of the expenditure.
     * @return Amount of the expenditure.
     * @throws TransactionException If transaction is not an expenditure.
     */
    @Override
    public double getExpenditureAmount(int index) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (!expLists.get(index - ONE_INDEX).getSpent()) {
                throw new TransactionException("The transaction is a deposit");
            } else {
                return expLists.get(index - ONE_INDEX).getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }

    /**
     * Deletes the specific deposit from the current bank account.
     *
     * @param index Transaction number of the deposit.
     * @param ui    required for printing.
     * @return Amount of the deleted deposit.
     */
    @Override
    public double deleteDepositFromList(int index, Ui ui) {
        Transaction temp = expLists.get(index - ONE_INDEX);
        expLists.remove(index - ONE_INDEX);
        System.out.println("Deleted deposit");
        return temp.getAmount();
    }

    /**
     * Gets the amount of the deposit specified.
     *
     * @param index Transaction number of the deposit.
     * @return Amount of the deposit
     * @throws TransactionException If transaction is not a deposit.
     */
    @Override
    public double getDepositValue(int index) throws TransactionException {
        if (expLists.size() <= 0) {
            throw new TransactionException("There are no transactions in this bank account");
        }
        if ((index - ONE_INDEX) >= 0 && (index - ONE_INDEX) < expLists.size()) {
            if (expLists.get(index - ONE_INDEX).getSpent()) {
                throw new TransactionException("The transaction is not a deposit");
            } else {
                return expLists.get(index - ONE_INDEX).getAmount();
            }
        } else {
            throw new TransactionException("Index is out of transaction list range");
        }
    }
}
