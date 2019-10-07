package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;
import money.Instalment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class AddInstalmentCommand extends MoneyCommand{
    private String inputString;
    private SimpleDateFormat simpleDateFormat;

    public AddInstalmentCommand(String command) {
        inputString = command.replaceFirst("add instalment ", "");
        simpleDateFormat = new SimpleDateFormat("d/M/yyyy");
    }

    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("d/M/yyyy");
        String[] splitStr = inputString.split("/amt ", 2);
        String description = splitStr[0];
        String[] furSplit = splitStr[1].split("/within ", 2);
        float cost = Float.parseFloat(furSplit[0]);
        String[] morSplit = furSplit[1].split(" months /from ", 2);
        int numOfPayments = Integer.parseInt(morSplit[0]);
        String[] evenMorSplit = morSplit[1].split(" @");
        LocalDate boughtDate = LocalDate.parse(evenMorSplit[0], dateTimeFormatter);
        String[] lastSplit = evenMorSplit[1].split("%");
        float AIR = Float.parseFloat(lastSplit[0]);

        String category = "instalments";

        Instalment instalment = new Instalment(cost, description, category, boughtDate, numOfPayments, AIR);
        account.getInstalments().add(instalment);
        storage.writeToFile(account);

        ui.appendToOutput(" Got it. I've added this to your instalments: \n");
        ui.appendToOutput("     ");
        ui.appendToOutput(account.getInstalments().get(account.getInstalments().size() - 1).toString() + "\n");
        ui.appendToOutput(" Now you have " + account.getInstalments().size() + " instalments listed\n");
        ui.appendToOutput(" You are paying " + instalment.EqualMonthlyInstalment() + " per month\n");
        ui.appendToOutput(" For " + instalment.getNumOfPayments() + " months\n");
        ui.appendToOutput(" Until " + instalment.getDateEndDate() + "\n");
        ui.appendToOutput(" The total amount you will pay is " + instalment.totalAmount() + "\n");
    }
}
