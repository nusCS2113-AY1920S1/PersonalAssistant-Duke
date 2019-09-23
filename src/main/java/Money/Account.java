package Money;

import java.util.ArrayList;
import java.util.Date;

public class Account {
    private ArrayList<Income> IncomeListTotal;
    private ArrayList<Expenditure> ExpListTotal;
    private ArrayList<Income> IncomeListCurrMonth;
    private ArrayList<Expenditure> ExpListCurrMonth;
    private float TotalSavings;
    private float CurrMonthSavings;
    private float BaseSavings;
    private float GoalSavings;
    private ArrayList<Goal> ShortTermGoals;
    private ArrayList<Installment> Installments;

    Account(float User_savings, float AvgExp) {
        Date nowDate = new Date();
        Income InitialSavings;
        if(User_savings > AvgExp * 6) {
            this.BaseSavings = AvgExp * 6;
            this.GoalSavings = User_savings - BaseSavings;
            InitialSavings = new Income(User_savings, "InitialSavings", nowDate);
            IncomeListTotal.add(InitialSavings);
        } else {
            InitialSavings = new Income(User_savings, "InitialSavings", nowDate);
            IncomeListTotal.add(InitialSavings);
            this.BaseSavings = TotalSavings;
            this.GoalSavings = 0;
        }
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

    public void getTotalSavings() {
        TotalSavings = getTotalIncome() - getTotalExp();
    }
}
