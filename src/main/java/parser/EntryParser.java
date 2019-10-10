package parser;

import dolla.Ui;
import dolla.command.Command;
import dolla.command.ShowListCommand;

public class EntryParser extends Parser {

    public EntryParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("list")) {
            return new ShowListCommand(mode);
        } else {
            return invalidCommand();
        }
    }
}
