package money;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Account {
    private ArrayList<Income> incomeListTotal;
    private ArrayList<Expenditure> expListTotal;
    private ArrayList<Income> incomeListCurrMonth;
    private ArrayList<Expenditure> expListCurrMonth;
    private ArrayList<Goal> shortTermGoals;
    private ArrayList<Installment> installments;
    private ArrayList<BankTracker> bankTrackerList;
    private float totalSavings;
    private float currMonthSavings;
    private float baseSavings;
    private float goalSavings;
    private boolean toInitialize;

    public Account() {
        incomeListTotal = new ArrayList<>();
        expListTotal = new ArrayList<>();
        incomeListCurrMonth = new ArrayList<>();
        expListCurrMonth = new ArrayList<>();
        shortTermGoals = new ArrayList<>();
        installments = new ArrayList<>();
        bankTrackerList = new ArrayList<>();
        toInitialize = true;
    }

    public void initialize(float userSavings, float avgExp) {
        Date nowDate = new Date();
        Income initialSavings;
        if (userSavings > avgExp * 6) {
            this.baseSavings = avgExp * 6;
            this.goalSavings = userSavings - baseSavings;
            initialSavings = new Income(userSavings, "InitialSavings", nowDate);
            incomeListTotal.add(initialSavings);
        } else {
            initialSavings = new Income(userSavings, "InitialSavings", nowDate);
            incomeListTotal.add(initialSavings);
            this.baseSavings = totalSavings;
            this.goalSavings = 0;
        }
        toInitialize = false;
    }

    public ArrayList<Income> getIncomeListTotal() {
        return incomeListTotal;
    }

    public ArrayList<Expenditure> getExpListTotal() {
        return expListTotal;
    }

    public ArrayList<Income> getIncomeListCurrMonth() {
        return incomeListCurrMonth;
    }

    public ArrayList<Expenditure> getExpListCurrMonth() {
        return expListCurrMonth;
    }

    public ArrayList<Goal> getShortTermGoals() {
        return shortTermGoals;
    }

    public ArrayList<Installment> getInstallments() {
        return installments;
    }

    public ArrayList<BankTracker> getBankTrackerList() {
        return bankTrackerList;
    }

    public float getTotalIncome() {
        float total = 0;
        for (Income i : incomeListTotal) {
            total += i.getPrice();
        }
        return total;
    }

    public float getTotalExp() {
        float total = 0;
        for (Expenditure i : expListTotal) {
            total += i.getPrice();
        }
        return total;
    }

    public float getCurrMonthIncome() {
        float total = 0;
        for (Income i : incomeListCurrMonth) {
            total += i.getPrice();
        }
        return total;
    }

    public float getCurrMonthExp() {
        float total = 0;
        for (Expenditure i : expListCurrMonth) {
            total += i.getPrice();
        }
        return total;
    }

    public float getTotalSavings() {
        totalSavings = getTotalIncome() - getTotalExp();
        return totalSavings;
    }

    public float getCurrMonthSavings() {
        currMonthSavings = getCurrMonthIncome() - getCurrMonthExp();
        return currMonthSavings;
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
        currMonthSavings = getCurrMonthIncome() - getCurrMonthExp();
        goalSavings = getTotalSavings() - getBaseSavings();
    }

    public boolean isToInitialize() {
        return toInitialize;
    }

    public void populateCurrentMonthLists() {
        Date currDate = new Date();
        Calendar dateNow = Calendar.getInstance();
        int currMonth = currDate.getMonth(); // there's an issue here of depreciation
        int currYear = currDate.getYear();
        for (Income i : incomeListTotal) {
            Calendar cal = Calendar.getInstance();
            if (i.getPayday().getMonth() == currMonth && i.getPayday().getYear() == currYear) {
                incomeListCurrMonth.add(i);
            }
        }
        for (Expenditure e : expListTotal) {
            if (e.getDateBoughtTime().getMonth() == currMonth && e.getDateBoughtTime().getYear() == currYear) {
                expListCurrMonth.add(e);
            }
        }
    }
}
