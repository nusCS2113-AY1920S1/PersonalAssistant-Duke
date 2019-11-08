import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.*;
import moneycommands.*;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

public class FindTest {
    private Ui ui;
    private Account account;
    private MoneyStorage moneyStorage;
    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    private LocalDate testDate = LocalDate.parse("9/10/2015", dateTimeFormatter);
    private LocalDate testDate2 = LocalDate.parse("9/10/2050", dateTimeFormatter);

    FindTest() throws IOException {
        ui = new Ui();
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        moneyStorage = new MoneyStorage(filePath);
        account = new Account(moneyStorage.load());
    }

    @Test
    public void testFindCommand()throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        account.getInstalments().clear();
        account.getLoans().clear();

        Income i1 = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i1);
        Income i2 = new Income(1000, "crypto", testDate);
        account.getIncomeListTotal().add(i2);

        Expenditure e1 = new Expenditure(500, "straw", "E", testDate);
        account.getExpListTotal().add(e1);
        Expenditure e2 = new Expenditure(1000, "cup", "E", testDate);
        account.getExpListTotal().add(e2);

        Goal g1 = new Goal(1000, "watch", "GS", testDate2, "HIGH");
        account.getShortTermGoals().add(g1);
        Goal g2 = new Goal(2000, "car", "GS", testDate2, "MEDIUM");
        account.getShortTermGoals().add(g2);

        Loan outgoingLoan = new Loan(500, "my bros", testDate, Loan.Type.OUTGOING);
        Loan incomingLoan = new Loan(1000, "my daddy", testDate, Loan.Type.INCOMING);
        account.getLoans().add(outgoingLoan);
        account.getLoans().add(incomingLoan);

        Instalment instalment = new Instalment(5000, "car", "instalments", testDate, 120, 3);
        Instalment instalment1 = new Instalment(100000, "mortgage", "instalments", testDate, 180, 4);
        account.getInstalments().add(instalment);
        account.getInstalments().add(instalment1);

        String testInput = "find# a";
        MoneyCommand FindGoalCommand =  new FindCommand(testInput);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        FindGoalCommand.execute(account, ui, moneyStorage);
        assertEquals( "Goals Found:\n" +
                        "1.[GS] watch(target: $1000.00)\n" +
                        " (to achieve by: 9/10/2050) HIGH\n" +
                        "2.[GS] car(target: $2000.00)\n" +
                        " (to achieve by: 9/10/2050) MEDIUM\n" +
                        "\n" +
                        "Income Items Found:\n" +
                        "1.[I] TA Pay(salary: $2000.00) (Paid On: 9/10/2015)\n" +
                        "\n" +
                        "Expenditure Items Found:\n" +
                        "1.[E]$500.00 straw(on: 9/10/2015)\n" +
                        "\n" +
                        "Loan Items Found:\n" +
                        "1.[Outstanding] [I] my daddy(loan: $1000.00) (Lent On: 9/10/2015) Outstanding Amount: $1000.00\n" +
                        "\n" +
                        "Instalment Items Found:\n" +
                        "1.[INS]$5000.00 car(on: 9/10/2015)\n" +
                        "2.[INS]$100000.00 mortgage(on: 9/10/2015)\n" +
                        "\n"
                , ui.getGraphContainerString().split(" Now")[0]);


        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        account.getInstalments().clear();
        account.getLoans().clear();
        MoneyCommand exitCommand =  new ExitMoneyCommand();
        exitCommand.execute(account, ui, moneyStorage);
    }

    @Test
    void testBlankFind() throws ParseException, DukeException {
        account.getShortTermGoals().clear();
        account.getIncomeListTotal().clear();
        account.getExpListTotal().clear();
        account.getInstalments().clear();
        account.getLoans().clear();

        Income i1 = new Income(2000, "TA Pay", testDate);
        account.getIncomeListTotal().add(i1);

        Expenditure e1 = new Expenditure(500, "straw", "E", testDate);
        account.getExpListTotal().add(e1);

        Goal g1 = new Goal(1000, "watch", "GS", testDate2, "HIGH");
        account.getShortTermGoals().add(g1);

        Loan outgoingLoan = new Loan(500, "my bros", testDate, Loan.Type.OUTGOING);
        account.getLoans().add(outgoingLoan);

        Instalment instalment = new Instalment(5000, "car", "instalments", testDate, 120, 3);
        account.getInstalments().add(instalment);

        String testInput = "find ";
        MoneyCommand FindGoalCommand =  new FindCommand(testInput);
        ui.clearOutputString();
        ui.clearGraphContainerString();
        try {
            FindGoalCommand.execute(account, ui, moneyStorage);
            fail();
        } catch (DukeException e) {
            assertThat(e.getMessage(), is("The description of a find cannot be empty."));
        }

    }
}
