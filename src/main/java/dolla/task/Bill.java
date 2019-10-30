package dolla.task;

//@@author tatayu
public class Bill extends Record {

    protected String type;
    protected int people;
    protected double amount;

    public Bill(String type, int people, double amount) {
        this.type = type;
        this.people = people;
        this.amount = amount;
    }

    @Override
    public String getRecordDetail() {
        return "[" + type + "] "
                + "[" + people + "] "
                + "[" + amountToMoney() + "] ";
    }

    //////////
    @Override
    public String formatSave() {
       return null;
    }

    @Override
    public String amountToMoney() {
        return "$" + amount;
    }
}
