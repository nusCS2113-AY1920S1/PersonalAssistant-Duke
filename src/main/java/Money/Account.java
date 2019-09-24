package Money;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    private ArrayList<Income> IncomeListTotal;
    private ArrayList<Expenditure> ExpListTotal;
    private ArrayList<Income> IncomeListCurrMonth;
    private ArrayList<Expenditure> ExpListCurrMonth;
    private ArrayList<Goal> ShortTermGoals;
    private ArrayList<Installment> Installments;
    private float TotalSavings;
    private float CurrMonthSavings;
    private float BaseSavings;
    private float GoalSavings;
    private boolean toInitialize;

    public Account() {
        IncomeListTotal = new ArrayList<>();
        ExpListTotal = new ArrayList<>();
        IncomeListCurrMonth = new ArrayList<>();
        ExpListCurrMonth = new ArrayList<>();
        ShortTermGoals = new ArrayList<>();
        Installments = new ArrayList<>();
        toInitialize = true;
    }

    public void Initialize(float UserSavings, float AvgExp) {
        Date nowDate = new Date();
        Income InitialSavings;
        if(UserSavings > AvgExp * 6) {
            this.BaseSavings = AvgExp * 6;
            this.GoalSavings = UserSavings - BaseSavings;
            InitialSavings = new Income(UserSavings, "InitialSavings", nowDate);
            IncomeListTotal.add(InitialSavings);
        } else {
            InitialSavings = new Income(UserSavings, "InitialSavings", nowDate);
            IncomeListTotal.add(InitialSavings);
            this.BaseSavings = TotalSavings;
            this.GoalSavings = 0;
        }toInitialize = false;
    }

    public ArrayList<Income> getIncomeListTotal() {
        return IncomeListTotal;
    }

    public ArrayList<Expenditure> getExpListTotal() {
        return ExpListTotal;
    }

    public ArrayList<Income> getIncomeListCurrMonth() {
        return IncomeListCurrMonth;
    }

    public ArrayList<Expenditure> getExpListCurrMonth() {
        return ExpListCurrMonth;
    }

    public ArrayList<Goal> getShortTermGoals() {
        return ShortTermGoals;
    }

    public ArrayList<Installment> getInstallments() {
        return Installments;
    }

    public float getTotalIncome() {
        float total = 0;
        for(Income i : IncomeListTotal) total += i.getPrice();
        return total;
    }

    public float getTotalExp() {
        float total = 0;
        for(Expenditure i : ExpListTotal) total += i.getPrice();
        return total;
    }

    public float getCurrMonthIncome() {
        float total = 0;
        for(Income i : IncomeListCurrMonth) total += i.getPrice();
        return total;
    }

    public float getCurrMonthExp() {
        float total = 0;
        for(Expenditure i : ExpListCurrMonth) total += i.getPrice();
        return total;
    }

    public float getTotalSavings() {
        TotalSavings = getTotalIncome() - getTotalExp();
        return TotalSavings;
    }

    public float getCurrMonthSavings() {
        CurrMonthSavings = getCurrMonthIncome() - getCurrMonthExp();
        return CurrMonthSavings;
    }

    public float getBaseSavings() {
        return BaseSavings;
    }

    public float getGoalSavings() {
        GoalSavings = getTotalSavings() - getBaseSavings();
        return GoalSavings;
    }

    public void UpdateSavings() {
        TotalSavings = getTotalIncome() - getTotalExp();
        CurrMonthSavings = getCurrMonthIncome() - getCurrMonthExp();
        GoalSavings = getTotalSavings() - getBaseSavings();
    }

    public boolean isToInitialize() {
        return toInitialize;
    }
}
