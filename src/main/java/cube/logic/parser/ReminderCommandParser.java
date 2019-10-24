package cube.logic.parser;

import cube.logic.command.ReminderCommand;
import cube.logic.command.SoldCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

public class ReminderCommandParser implements ParserPrototype<ReminderCommand> {

    public ReminderCommand parse(String[] args) throws ParserException {
        int daysToExpiryIndex = -1;
        int stockIndex = -1;
        for (int i = 1; i < args.length; i ++) {
            if (args[i].equals("-d")) {
                daysToExpiryIndex = i;
            }
            if (args[i].equals("-s")) {
                stockIndex = i;
            }
        }

        if(daysToExpiryIndex == -1 && stockIndex != -1) {
            return new ReminderCommand(7, Integer.parseInt(args[stockIndex+1]));
        }else if (stockIndex == -1 && daysToExpiryIndex != -1) {
            return new ReminderCommand(Integer.parseInt(args[daysToExpiryIndex+1]), 5);
        } else if (daysToExpiryIndex == -1 && stockIndex == -1) {
            return new ReminderCommand(7,5);
        }

        return new ReminderCommand(Integer.parseInt(args[daysToExpiryIndex+1]), Integer.parseInt(args[stockIndex+1]));
    }
}