package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.Instalment;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * This command adds an Instalment to the Instalments List.
 */
public class AddInstalmentCommand extends MoneyCommand {

    private String inputString;
    private DateTimeFormatter dateTimeFormatter;
    String desc;
    float amount;
    int numOfPaymentsReq;
    LocalDate boughtDate;
    float annualIR;
    String category;

    //@@author ChenChao19
    /**
     * Constructor of the command which initialises the add instalment command.
     * with the instalment data within the user input.
     * @param cmd add command inputted from user.
     */
    public AddInstalmentCommand(String cmd) {
        inputString = cmd;
        dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
    }

    public boolean isExit() {
        return false;
    }

    /**
     * This method executes the add instalment command. Takes input from user
     * and adds an Instalment to the Instalment List
     * @param account Account object containing all financial info of user saved on the programme
     * @param ui Handles interaction with the user
     * @param storage Saves and loads data into/from the local disk
     * @throws ParseException If invalid date is parsed
     * @throws DukeException When the command is invalid
     */
    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        try {
            desc = inputString.split(" /amt ")[0].replaceFirst("add instalment ", "");
            amount = Float.parseFloat(inputString.split(" /amt ")[1].split(" /within ")[0]);
            numOfPaymentsReq = Integer.parseInt(inputString.split(" /within ")[1].split(" months /from ")[0]);
            boughtDate = Parser.shortcutTime(inputString.split(" months /from ")[1].split(" @")[0]);
            annualIR = Float.parseFloat(inputString.split(" @")[1].split("%")[0]);
            category = "INS";
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            throw new DukeException("Please enter in the format: "
                    + "add instalment <desc> /amt <amount> /within <number of months of payment> months "
                    + "/from <date> @<annual interest rate>%\n");
        } catch (DateTimeParseException e) {
            throw new DukeException("Invalid date! Please enter date in the format: d/m/yyyy\n");
        }

        Instalment ins = new Instalment(amount, desc, category, boughtDate, numOfPaymentsReq, annualIR);
        account.getInstalments().add(ins);
        storage.writeToFile(account);

        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);

        ui.appendToOutput(" Got it. I've added this to your instalments: \n");
        ui.appendToOutput(account.getInstalments().get(account.getInstalments().size() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getInstalments().size() + " instalments listed\n");
        ui.appendToOutput(" You are paying $" + df.format(ins.equalMonthlyInstalment()) + " per month\n");
        ui.appendToOutput(" For " + ins.getNumOfPayments() + " months\n");
        ui.appendToOutput(" Until " + ins.getDateEndDate() + "\n");
        ui.appendToOutput(" The total amount you will pay is $" + ins.totalAmount() + "\n");

        MoneyCommand update = new AutoUpdateInstalmentCommand();
        MoneyCommand list = new ListInstalmentCommand();
        update.execute(account, ui, storage);
        list.execute(account,ui,storage);
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) {
        int lastIndex = account.getInstalments().size() - 1;
        Instalment ins = account.getInstalments().get(lastIndex);
        account.getInstalments().remove(ins);
        storage.writeToFile(account);

        ui.appendToOutput(" Last command undone: \n");
        ui.appendToOutput(ins.toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getInstalments().size() + " instalments listed\n");
    }
}