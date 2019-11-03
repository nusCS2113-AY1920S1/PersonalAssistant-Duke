package cube.logic.parser;

import cube.logic.command.ReminderCommand;
import cube.logic.parser.exception.ParserErrorMessage;
import cube.logic.parser.exception.ParserException;

public class ReminderCommandParser implements ParserPrototype<ReminderCommand> {

    public ReminderCommand parse(String[] args) throws ParserException {
        String[] params = new String[]{"-s","-d"};

        if(ParserUtil.hasInvalidParameters(args,params)){
            throw new ParserException(ParserErrorMessage.INVALID_PARAMETER);
        }
        if(ParserUtil.hasRepetitiveParameters(args)){
            throw new ParserException(ParserErrorMessage.REPETITIVE_PARAMETER);
        }

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
            if (!ParserUtil.hasField(args,stockIndex+1)) {
                throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
            }
            if(!ParserUtil.isValidNumber(args[stockIndex + 1])){
                throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
            }
            return new ReminderCommand(7, Integer.parseInt(args[stockIndex+1]));
        }else if (stockIndex == -1 && daysToExpiryIndex != -1) {
            if (!ParserUtil.hasField(args,daysToExpiryIndex+1)) {
                throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
            }
            if(!ParserUtil.isValidNumber(args[daysToExpiryIndex + 1])){
                throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
            }
            return new ReminderCommand(Integer.parseInt(args[daysToExpiryIndex+1]), 5);
        } else if (daysToExpiryIndex == -1 && stockIndex == -1) {
            return new ReminderCommand(7,5);
        }

        if(!ParserUtil.isValidNumber(args[stockIndex + 1])){
            throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
        }
        if(!ParserUtil.isValidNumber(args[daysToExpiryIndex + 1])){
            throw new ParserException(ParserErrorMessage.INVALID_NUMBER);
        }
        if (!ParserUtil.hasField(args,stockIndex+1)) {
            throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
        }
        if (!ParserUtil.hasField(args,daysToExpiryIndex+1)) {
            throw new ParserException(ParserErrorMessage.EMPTY_FIELD);
        }
        return new ReminderCommand(Integer.parseInt(args[daysToExpiryIndex+1]), Integer.parseInt(args[stockIndex+1]));
    }
}