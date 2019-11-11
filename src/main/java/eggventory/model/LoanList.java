package eggventory.model;

import eggventory.model.loans.Loan;
import eggventory.ui.TableStruct;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

//@@author cyanoei

/**
 * The LoanList class stores:
 * 1) A list of Loan objects containing individual Loan details, and
 * 2) A HashMap mapping StockCode to quantity of Stock loaned out.
 * Adding, editing and deleting loans updates both these lists.
 * Loans can be printed as a complete list of all loans, or only by the Person or Stock involved.
 */
public final class LoanList {
    private static ArrayList<Loan> loansList = new ArrayList<Loan>();
    private static HashMap<String, Integer> stockLoaned = new HashMap<String, Integer>();

    /**
     * Overloaded version of addLoan which does not require Date parameters.
     * Adds a Loan object to both the LoanList and LoanPairs.
     * @param matricNo the matric number of the Person who is loaning.
     * @param stockCode the stockCode of the Stock loaned.
     * @param quantity the quantity loaned out.
     */
    public static void addLoan(String matricNo, String stockCode, int quantity) {
        Loan loan = new Loan(matricNo, stockCode, quantity);

        loansList.add(loan);
        updateStockLoaned(stockCode, quantity);
    }

    //@@author

    //@@author Deculsion
    /**
     * Add multiple loans using a template.
     * @param matricNo Matriculation number of student to assign loans to.
     * @param template Name of the template.
     * @return String of the loans added.
     */
    public static String addLoanByTemplate(String matricNo, String template) {
        Loan[] loans = TemplateList.getTemplateLoans(template);
        if (loans == null) {
            return null;
        }

        StringBuilder output = new StringBuilder(String.format(
                "The following loans have been added to %s:", matricNo));

        for (Loan loan : loans) {
            addLoan(matricNo, loan.getStockCode(), loan.getQuantity());
            output.append(String.format("%s: %d", loan.getStockCode(), loan.getQuantity()));
        }

        return output.toString();
    }

    //@@author cyanoei
    /**
     * Deletes a Loan object from the system. Removes both records of the loan.
     * @param stockCode the stockCode of the Stock.
     * @param matricNo the matric number of the Person.
     */
    public static boolean deleteLoan(String matricNo, String stockCode) {
        Loan loan = findLoan(matricNo, stockCode);

        if (loan == null) {
            return false;
        }

        updateStockLoaned(stockCode, loan.getQuantity() * -1);
        loansList.remove(loan);

        return true;
    }

    //@@author Deculsion

    /**
     * Returns the quantity of a certain Stock that a Person has loaned out.
     * @param stockCode the stockCode of the Stock involved.
     * @param matricNo the matric number of the Person involved.
     * @return the quantity loaned out by a person.
     */
    public static int getPersonLoanQuantity(String matricNo, String stockCode) {
        Loan loan = findLoan(matricNo, stockCode);
        if (loan == null) {
            return -1;
        }
        return loan.getQuantity();
    }

    //@@author cyanoei

    /**
     * Returns the total quantity of a certain Stock has been loaned out.
     * @param stockCode stockCode of the queried Stock.
     * @return The total quantity currently loaned out. Or 0 if the stock has not be loaned before.
     */
    public static int getStockLoanedQuantity(String stockCode) {
        if (stockLoaned.get(stockCode) == null) {
            return 0;
        }
        return stockLoaned.get(stockCode);
    }

    //@@author Deculsion

    /**
     * Returns the Loan of a particular Stock to a particular Person.
     * @param matricNo the matric number of the Person.
     * @param stockCode the stockCode of the Stock.
     * @return the Loan object of this particular loan pair.
     */
    private static Loan findLoan(String matricNo, String stockCode) {
        for (Loan loan : loansList) {
            if (loan.loanEquals(matricNo, stockCode)) {
                return loan;
            }
        }

        return null;
    }

    /**
     * Updates the quantity of Stock loaned out. Used when loans are added or deleted/returned.
     * @param stockCode the Stock whose loaned quantity is changed.
     * @param quantity the amount the quantity is changing by.
     */
    private static void updateStockLoaned(String stockCode, int quantity) {
        if (stockLoaned.containsKey(stockCode)) {
            quantity += stockLoaned.get(stockCode);
        }
        stockLoaned.put(stockCode, quantity);
    }

    /**
     * Returns a list of Loans made by a single Person.
     * @param matricNo the matric number of the Person.
     * @return the list.
     */
    private static ArrayList<Loan> getPersonLoans(String matricNo) {
        ArrayList<Loan> loans = new ArrayList<Loan>();
        for (Loan loan : loansList) {
            if (loan.getMatricNo().equals(matricNo)) {
                loans.add(loan);
            }
        }

        return loans;
    }

    //@@author cyanoei

    /**
     * Means of obtaining all the Loans of a single type of Stock.
     * @param stockCode the unique identifier of the Stock.
     * @return the list of Loans involving that Stock.
     */
    private static ArrayList<Loan> getStockLoans(String stockCode) {
        ArrayList<Loan> loans = new ArrayList<Loan>();
        for (Loan loan : loansList) {
            if (loan.getStockCode().equals(stockCode)) {
                loans.add(loan);
            }
        }

        return loans;

    }

    /**
     * Returns a string containing all the loan entries with their details.
     * @return the print string.
     */
    public static String printLoans() {
        String output = "Here are all the Loans: \n";

        for (Loan loan : loansList) {
            output += loan.toString() + "\n";
        }

        return output;
    }

    /**
     * Returns a string containing all the Loans made by a single Person (identified by matric number).
     * @param matricNo the string that identifies the Person making the Loan.
     * @return the print string.
     */
    public static String printPersonLoans(String matricNo) {
        String output = "Here are all Loans made by " + matricNo + ": \n";

        ArrayList<Loan> loans = getPersonLoans(matricNo);
        for (Loan loan : loans) {
            output += loan.toString() + "\n";
        }

        return output;
    }

    /**
     * Returns a string containing all the Loans of a single stock (identified by stockCode).
     * @param stockCode the string that identifies the Stock loaned.
     * @return the print string.
     */
    public static String printStockLoans(String stockCode) {
        String output = "Here are all Loans of " + stockCode + ": \n";

        ArrayList<Loan> loans = getStockLoans(stockCode);
        for (Loan loan : loans) {
            output += loan.toString() + "\n";
        }

        return output;
    }

    /**
     * Returns all loan data as a TableStruct.
     * @return The TableStruct of all data.
     */
    public static TableStruct getAllLoansStruct() {
        TableStruct dataTable = new TableStruct("Loan list");
        dataTable.setTableColumns("Matric No.", "Name", "StockCode", "Quantity");

        ArrayList<ArrayList<String>> dataArray = new ArrayList<>();
        for (Loan loan : loansList) {
            dataArray.add(loan.getDataAsArray());
        }

        dataTable.setTableData(dataArray);

        return dataTable;

    }

    /**
     * Returns all of a person's loans as a TableStruct.
     * @param matricNo Matriculation number of person to output.
     */
    public static TableStruct getPersonLoansStruct(String matricNo) {
        TableStruct dataTable = new TableStruct("Loans of " + matricNo);
        dataTable.setTableColumns("Stock Code", "Quantity Loaned");

        ArrayList<Loan> loans = getPersonLoans(matricNo);
        ArrayList<ArrayList<String>> dataList = new ArrayList<>();

        for (Loan loan : loans) {
            dataList.add(loan.getStockDataAsArray());
        }

        dataTable.setTableData(dataList);

        return dataTable;
    }


    //@@author patwaririshab
    /**
     * Saves the stocktypes into a String.
     * @return The String will be directly saved into a saved_stocktypes file.
     */
    public String saveLoanListString() {
        StringBuilder stockTypesString = new StringBuilder();

        for (Loan loan : loansList) {
            stockTypesString.append(loan.savedLoanString()).append("\n");
        }

        return stockTypesString.toString();
    }

    public static ArrayList<Loan> getLoansList() {
        return loansList;
    }
    //@@author

}
