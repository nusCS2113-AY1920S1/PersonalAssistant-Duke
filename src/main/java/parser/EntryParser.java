package parser;

import dolla.Ui;
import dolla.command.*;

public class EntryParser extends Parser { public EntryParser(String inputLine) {
        super(inputLine);
    }

    @Override
    public Command handleInput(String mode, String inputLine) {

        if (commandToRun.equals("entries")) { //show entry list
            return new ShowListCommand(mode);
        } else if (commandToRun.equals("add")) {
            String entryType = null;
            double amount = 0.0;
            try {
                entryType = DollaParser.verifyType(inputArray[1]);
                amount = stringToDouble(inputArray[2]);

                String[] data = inputLine.split(" /on ");
                String[] desc = data[0].split(inputArray[2] + " ");
                description = desc[1];

                splitDescTime();
            } catch (IndexOutOfBoundsException e) {
                Ui.printInvalidEntryFormatError();
                return new ErrorCommand();
            } catch (Exception e) {
                return new ErrorCommand(); // If error occurs, stop the method!
            }
            return new AddEntryCommand(entryType, amount, description, date);

        } else if (commandToRun.equals("search")) {
            String content = inputArray[1];
            return new SearchCommand(mode, content);
        } else {
            return invalidCommand();
        }
    }
}
