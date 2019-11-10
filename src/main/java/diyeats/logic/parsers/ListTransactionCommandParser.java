package diyeats.logic.parsers;

import diyeats.commons.exceptions.ProgramException;
import diyeats.logic.commands.ListTransactionsCommand;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.HashMap;

public class ListTransactionCommandParser implements ParserInterface<ListTransactionsCommand> {

    @Override
    public ListTransactionsCommand parse(String userInputStr) throws ProgramException {
        HashMap<String, String> argumentInfoMap;
        LocalDate localDate = LocalDate.now();
        String sortArgStr = "";
        int numberofEntry = 10;
        if (userInputStr.isBlank()) {
            return new ListTransactionsCommand();
        }
        argumentInfoMap = ArgumentSplitter.splitForwardSlashArguments(userInputStr);
        for (String details : argumentInfoMap.keySet()) {
            if (details.equals("date")) {
                String dateArgStr = "";
                try {
                    dateArgStr = argumentInfoMap.get(details);
                    localDate = LocalDate.parse(dateArgStr, dateFormat);
                } catch (DateTimeParseException e) {
                    return new ListTransactionsCommand(false, "Unable to parse \"" + dateArgStr
                                                        + "\" as a date." );
                }
            }
            if (details.equals("sort")) {
                sortArgStr = argumentInfoMap.get(details).trim();
                //TODO: add sorting
            }
            if (details.equals("entry")) {
                try {
                    numberofEntry = Integer.parseInt(argumentInfoMap.get(details));
                } catch (NumberFormatException e) {
                    return new ListTransactionsCommand(false, "Unable to parse number of entry as int");
                }
            }
        }
        return new ListTransactionsCommand(localDate, sortArgStr);
    }
}
