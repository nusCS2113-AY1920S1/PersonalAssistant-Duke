package dolla.parser;

import dolla.Ui;
import dolla.command.AddEntryCommand;
import dolla.command.Command;
import dolla.command.ErrorCommand;
import dolla.command.ShowListCommand;

public class EntryParser extends Parser { public EntryParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("entries")) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("add")) {
            if (verifyAddCommand() == true) {
                return new AddEntryCommand(inputArray[1], stringToDouble(inputArray[2]), inputArray[3], date);
            } else {
                return new ErrorCommand();
            }
        } else {
            return invalidCommand();
        }
    }


}
