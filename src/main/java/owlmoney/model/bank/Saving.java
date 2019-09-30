package owlmoney.model.bank;

public class Saving extends Bank{

    private String type;
    private double income;
    private static final String SAVING = "saving";

    public Saving(String name, double currentAmount, double income) {
        super(name,currentAmount);
        this.income = income;
        type = SAVING;
    }

    @Override
    public void getDescription() {
        super.getDescription();
        System.out.println(income);
        System.out.println(type);
    }
}
