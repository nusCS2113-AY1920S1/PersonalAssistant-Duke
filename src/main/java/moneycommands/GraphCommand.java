package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Parser;
import controlpanel.Ui;
import money.Account;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class GraphCommand extends MoneyCommand {

    private String cmd;

    //@@author cctt1014
    public GraphCommand(String inputString) {
        cmd = inputString;
    }

    @Override
    public boolean isExit() {
        return false;
    }

    @Override
    public void execute(Account account, Ui ui, MoneyStorage storage) throws DukeException, ParseException {
        if (cmd.equals("change icon")) {
            ui.appendToOutput("Done.\n");
        } else if (cmd.startsWith("graph monthly report") || cmd.startsWith("graph income trend")
                || cmd.startsWith("graph expenditure trend") || cmd.startsWith("graph finance status /until ")) {
            try {
                if (cmd.startsWith("graph finance status /until ")) {
                    String dateString = cmd.split(" /until ")[1];
                    LocalDate date = Parser.shortcutTime(dateString);
                }
            } catch (DateTimeParseException e) {
                throw new DukeException("Invalid date! Please enter date in the format: d/m/yyyy\n");
            } finally {
                ui.appendToOutput("Got it, graph will be printed in the other pane!\n");
            }
        } else {
            throw new DukeException("Check your command. This input graph command is invalid.");
        }
    }

    @Override
    //@@author Chianhaoplanks
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
