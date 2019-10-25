package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;
import money.Instalment;
import money.Item;

import java.text.ParseException;

public class DeleteInstalmentCommand extends MoneyCommand {
    private String inputString;
    private int serialNo;

    //@@author ChenChao19
    public DeleteInstalmentCommand(String command) {
        inputString = command;
        String temp = inputString.replaceAll("[^0-9]", "");
        serialNo = Integer.parseInt(temp);
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if (serialNo > account.getInstalments().size()) {
            throw new DukeException("The serial number of the Instalments is Out Of Bounds!");
        }
        Instalment deletedEntryINS = account.getInstalments().get(serialNo - 1);
        ui.appendToOutput(" Noted. I've removed this Instalment:\n");
        ui.appendToOutput("  " + deletedEntryINS.toString() + "\n");
        ui.appendToOutput(" Now you have " + (account.getInstalments().size() - 1) + " instalments in the list.\n");

        account.getInstalments().remove(serialNo - 1);
        storage.addDeletedEntry(deletedEntryINS);
        storage.writeToFile(account);
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        Item deletedEntry = storage.getDeletedEntry();
        if (deletedEntry instanceof Instalment) {
            account.getInstalments().add(serialNo - 1, (Instalment)deletedEntry);
            storage.writeToFile(account);
            ui.appendToOutput(" Last command undone: \n");
            ui.appendToOutput(account.getInstalments().get(serialNo - 1).toString() + "\n");
            ui.appendToOutput(" Now you have " + account.getInstalments().size() + " instalments listed\n");
        } else {
            throw new DukeException("u messed up (INS)");
        }
    }
}