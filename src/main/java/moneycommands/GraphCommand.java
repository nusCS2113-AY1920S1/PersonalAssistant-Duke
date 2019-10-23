package moneycommands;

import controlpanel.DukeException;
import controlpanel.MoneyStorage;
import controlpanel.Ui;
import money.Account;

import java.text.ParseException;

public class GraphCommand extends MoneyCommand{

    private String cmd;

    //@@ cctt1014
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
        } else {
            ui.appendToOutput("Got it, graph will be printed in the other pane!\n");
        }
    }

    @Override
    public void undo(Account account, Ui ui, MoneyStorage storage) throws DukeException {
        throw new DukeException("Command can't be undone!\n");
    }
}
