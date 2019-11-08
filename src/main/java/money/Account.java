package money;

import controlpanel.DukeException;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * This class is created to store the information of the user's financial information.
 */
public class Account {
    private ArrayList<Income> incomeListTotal;
    private ArrayList<Expenditure> expListTotal;
    private ArrayList<Goal> shortTermGoals;
    private ArrayList<Instalment> instalments;
    private ArrayList<BankTracker> bankTrackerList;
    private ArrayList<Loan> loans;
    private float totalSavings;
    private float baseSavings;
    private float goalSavings;
    private boolean toInitialize;

    //@@author ChenChao19
    /**
     * Constructor of the Account Object to record financial information of the user.
     * This constructor is used when initializing a new account.
     */
    public Account() {
        incomeListTotal = new ArrayList<>();
        expListTotal = new ArrayList<>();
        shortTermGoals = new ArrayList<>();
        instalments = new ArrayList<>();
        bankTrackerList = new ArrayList<>();
        loans = new ArrayList<>();
        toInitialize = true;
    }

    /**
     * Constructor of the Account Object to record financial information of the user.
     * This constructor is used when a previous account is parsed in as a parameter.
     * @param account A previously existing account that is parsed in.
     */
    public Account(Account account) {
        incomeListTotal = account.getIncomeListTotal();
        expListTotal = account.getExpListTotal();
        shortTermGoals = account.getShortTermGoals();
        instalments = account.getInstalments();
        bankTrackerList = account.getBankTrackerList();
        loans = account.getLoans();
        toInitialize = account.isToInitialize();
        baseSavings = account.getBaseSavings();
        updateSavings();
        //if (account.isInitialised()) {
        //    toInitialize = false;
        //} else { toInitialize = true; }
    }

    //@@author therealnickcheong
    /**
     * This method is to initialize the user account when he uses Financial Ghost for the first time.
     * @param userSavings The initial savings the user have in his account
     * @param avgExp The average Expenditure per month of the user
     */
    public void initialize(float userSavings, float avgExp) {
        LocalDate nowDate = LocalDate.now();
        Income initialSavings;
        if (userSavings > avgExp * 6) {
            this.baseSavings = avgExp * 6;
            this.goalSavings = userSavings - baseSavings;
            initialSavings = new Income(userSavings, "Initial Savings", nowDate);
            incomeListTotal.add(initialSavings);
        } else {
            initialSavings = new Income(userSavings, "Initial Savings", nowDate);
            incomeListTotal.add(initialSavings);
            this.baseSavings = totalSavings;
            this.goalSavings = 0;
        }
        toInitialize = false;
    }

    //@@author ChenChao19
    public ArrayList<Income> getIncomeListTotal() {
        return incomeListTotal;
    }

    public ArrayList<Expenditure> getExpListTotal() {
        return expListTotal;
    }

    public ArrayList<Instalment> getInstalments() {
        return instalments;
    }

    public ArrayList<Goal> getShortTermGoals() {
        return shortTermGoals;
    }

    //@@author chengweixuan
    public ArrayList<Loan> getLoans() {
        return loans;
    }

    /**
     * This method iterates through all the loans and sum them up.
     * @return The total amount of all the loans
     */
    public float getLoansTotal() {
        float total = 0;
        for (Loan l : loans) {
            total += l.getPrice();
        }
        return total;
    }

    /**
     * This method iterate through all the loans and select the outgoing loans and put them in a list.
     * @return The list of outgoing loans.
     */
    public ArrayList<Loan> getOutgoingLoans() {
        ArrayList<Loan> outgoingLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.type == Loan.Type.OUTGOING) {
                outgoingLoans.add(l);
            }
        }
        return outgoingLoans;
    }

    /**
     * This method iterate through all the loans and select the incoming loans and put them in a list.
     * @return The list of incoming loans.
     */
    public ArrayList<Loan> getIncomingLoans() {
        ArrayList<Loan> incomingLoans = new ArrayList<>();
        for (Loan l : loans) {
            if (l.type == Loan.Type.INCOMING) {
                incomingLoans.add(l);
            }
        }
        return incomingLoans;
    }

    //@@author therealnickcheong
    /**
     * This method sorts the short term goal with a custom comparator function.
     * @param shortTermGoals The lists of short term goals
     */
    public void sortShortTermGoals(ArrayList<Goal> shortTermGoals) {
        Collections.sort(shortTermGoals, new Comparator<Goal>() {
            @Override
            public int compare(Goal g1, Goal g2) {
                if (g1.getPriority() == g2.getPriority()) {
                    return g1.getDescription().compareTo(g2.getDescription());
                } else {
                    return g1.getPriority().compareTo(g2.getPriority());
                }
            }
        });
    }

    //@@author cctt1014
    public ArrayList<BankTracker> getBankTrackerList() {
        return bankTrackerList;
    }

    //@@author ChenChao19
    public void setBaseSavings(float baseSavings) {
        this.baseSavings = baseSavings;
    }

    /**
     * This method iterate through the list of the income and
     * sum them up.
     * @return The sum of all the income
     */
    public float getTotalIncome() {
        float total = 0;
        for (Income i : incomeListTotal) {
            total += i.getPrice();
        }
        return total;
    }

    /**
     * This method iterate through the list of the expenditure and
     * sum them up.
     * @return The sum of all the expenditure
     */
    public float getTotalExp() {
        float total = 0;
        for (Expenditure i : expListTotal) {
            total += i.getPrice();
        }
        return total;
    }

    public float getTotalSavings() {
        totalSavings = getTotalIncome() - getTotalExp();
        return totalSavings;
    }

    public float getBaseSavings() {
        return baseSavings;
    }

    public float getGoalSavings() {
        goalSavings = getTotalSavings() - getBaseSavings();
        return goalSavings;
    }

    public void updateSavings() {
        totalSavings = getTotalIncome() - getTotalExp();
        goalSavings = getTotalSavings() - getBaseSavings();
    }

    //@@author therealnickcheong
    public boolean isToInitialize() {
        return toInitialize;
    }

    public void setToInitialize(boolean initStatus) {
        this.toInitialize = initStatus;
    }

    //@@author cctt1014
    /**
     * This method helps to find the corresponding bank account tracker by given description(name).
     * @param name The given description
     * @return The corresponding tracker
     * @throws DukeException Handle the case when there is no such account
     */
    public BankTracker findTrackerByName(String name) throws DukeException {
        BankTracker bankTracker = null;
        for (BankTracker b : bankTrackerList) {
            if (b.getDescription().equals(name)) {
                bankTracker = b;
                break;
            }
        }
        if (bankTracker == null) {
            throw new DukeException("Sorry, FG does not find this account...");
        }
        return bankTracker;
    }
}
