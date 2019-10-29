package owlmoney.model.goals;

import owlmoney.model.bank.Saving;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class GoalsStub extends Goals {

    private Date date = new SimpleDateFormat("dd/MM/yyyy").parse("20/10/2020");
    private Date testDate = new SimpleDateFormat("dd/MM/yyyy").parse("29/10/2019");
    private String name;
    private double amount;
    private Date goalsDate;
    private double income = 100;

    GoalsStub() throws ParseException {
        super("TEST GOALS", 500, new SimpleDateFormat("dd/MM/yyyy").parse("20/10/2020"));
    }

    GoalsStub(String name) throws ParseException {
        super("TEST SAVING GOALS", 5000,
                new SimpleDateFormat("dd/MM/yyyy").parse("20/10/2020"),
                new Saving(name, 10, 100));
    }

    @Override
    public double getGoalsAmount() {
        return super.getGoalsAmount();
    }

    @Override
    public String getGoalsDate() {
        return "20 October 2020";
    }

    @Override
    public String getRemainingAmount() {
        return super.getRemainingAmount();
    }

    @Override
    public String getSavingAccount() {
        return super.getSavingAccount();
    }

    @Override
    public String getStatus() {
        return super.getStatus();
    }

    @Override
    public String getGoalsName() {
        return super.getGoalsName();
    }

    @Override
    public int convertDateToDays() {
        long diffInMillies = Math.abs(date.getTime() - testDate.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        return (int) diff;
    }

    @Override
    public boolean getRawStatus() {
        return false;
    }

    @Override
    void setGoalsName(String newName) {
        this.name = newName;
    }

    public String getNewName() {
        return this.name;
    }

    @Override
    void setGoalsAmount(double newAmount) {
        this.amount = newAmount;
    }

    public double getNewAmount() {
        return this.amount;
    }

    @Override
    void setGoalsDate(Date newDate) {
        this.goalsDate = newDate;
    }

    public Date getNewDate() {
        return this.goalsDate;
    }

    void setSavingAccount(Saving newSavingAcc) {
        this.savingAcc = newSavingAcc;
    }

    public double getNewRemainAmount() {
        return this.amount - this.income;
    }
}
