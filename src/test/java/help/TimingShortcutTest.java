package help;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import moneycommands.AddIncomeCommand;
import moneycommands.MoneyCommand;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimingShortcutTest {
    private Ui ui;
    private Account account;
    private MoneyStorage storage;

    private DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");

    TimingShortcutTest() {
        Path currentDir = Paths.get("data/account-test.txt");
        String filePath = currentDir.toAbsolutePath().toString();
        storage = new MoneyStorage(filePath);
        account = new Account();
        ui = new Ui();
    }

    @Test
    void testNowTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on now";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now();
        String nowDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + nowDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testTomorrowTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on tmr";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusDays(1);
        String tmrDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + tmrDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testYesterdayTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on ytd";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusDays(-1);
        String ytdDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + ytdDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testLastWeekTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on lstwk";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusWeeks(-1);
        String lastWeekDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + lastWeekDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testNextWeekTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on nxtwk";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusWeeks(1);
        String nextWeekDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + nextWeekDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testLastMonthTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on lstmth";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusMonths(-1);
        String lastMonthDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + lastMonthDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testNextMonthTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on nxtmth";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusMonths(1);
        String nextMonthDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + nextMonthDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testLastYearTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on lstyr";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusYears(-1);
        String lastYearDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + lastYearDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }

    @Test
    void testNextYearTimingShortcuts() throws ParseException, DukeException {
        String nowInput = "add income TA /amt 530 /on nxtyr";
        MoneyCommand addNowCommand = new AddIncomeCommand(nowInput);
        ui.clearOutputString();
        addNowCommand.execute(account, ui, storage);
        LocalDate currDate = LocalDate.now().plusYears(1);
        String nextYearDate = dateTimeFormatter.format(currDate);
        assertEquals(" Got it. I've added this income source: \n"
                        + "     [I] TA (salary: $530.00) (Paid On: " + nextYearDate + ")\n",
                ui.getOutputString().split(" Now")[0]);
    }
}
