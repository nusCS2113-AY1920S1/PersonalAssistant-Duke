package owlmoney.model.goals;

import owlmoney.model.bank.Saving;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class GoalsStub extends Goals {

    GoalsStub() throws ParseException {
        super("TEST GOALS", 500, new SimpleDateFormat("dd/MM/yyyy").parse("20/10/2020"));
    }

    GoalsStub(String savingAcc) throws ParseException {
        super("TEST SAVING GOALS", 5000,
                new SimpleDateFormat("dd/MM/yyyy").parse("20/10/2020"),
                new Saving("Jun Saving", 10, 100));
    }

    @Override
    public double getGoalsAmount() {
        return 500;
    }

    @Override
    public String getGoalsDate() {
        return "20 October 2020";
    }

    @Override
    public String getRemainingAmount() {
        return Integer.toString(5000 - 10);
    }

    @Override
    public String getSavingAcc() {
        return "Jun Saving";
    }

    @Override
    public String getStatus() {
        return "✘";
    }
}
