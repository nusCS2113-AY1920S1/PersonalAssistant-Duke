package duke.parser;

import duke.logic.commands.ExportLockerSelectCommand;

import duke.exceptions.DukeException;

import static java.util.Objects.requireNonNull;

public class ExportLockerSelectCommandParser {

    /**
     * This function is used to parse the user input for exporting the details as a CSV file.
     * @param args this is the user input string for the tags that the user wants
     * @return reference to the class ExportLockerSelectCommand
     * @throws DukeException when the user input is invalid
     */

    public ExportLockerSelectCommand parse(String args) throws DukeException {

        requireNonNull(args);
        args = args.toLowerCase();

        int checkLocker = 0;
        int checkStatus = 0;

        String[] newArgs = args.split(" ");

        if (args.trim().length() == 0) {
            throw new DukeException(ExportLockerSelectCommand.INVALID_FORMAT);
        } else {
            for (String str : newArgs) {
                if (str.equals("locker")) {
                    checkLocker = 1;
                }
            }
            if (checkLocker == 0) {
                throw new DukeException(ExportLockerSelectCommand.MISSINGLOCKER_FORMAT);
            }

            for (String str : newArgs) {
                if (str.equals("status")) {
                    checkStatus = 1;
                }
            }
            if (checkStatus == 0) {
                for (String str : newArgs) {
                    if (str.equals("name")) {
                        throw new DukeException(ExportLockerSelectCommand.MISSINGSTATUS_FORMAT);
                    }
                    if (str.equals("matrixid")) {
                        throw new DukeException(ExportLockerSelectCommand.MISSINGSTATUS_FORMAT);
                    }
                    if (str.equals("course")) {
                        throw new DukeException(ExportLockerSelectCommand.MISSINGSTATUS_FORMAT);
                    }
                    if (str.equals("email")) {
                        throw new DukeException(ExportLockerSelectCommand.MISSINGSTATUS_FORMAT);
                    }
                    if (str.equals("startdate")) {
                        throw new DukeException(ExportLockerSelectCommand.MISSINGSTATUS_FORMAT);
                    }
                    if (str.equals("enddate")) {
                        throw new DukeException(ExportLockerSelectCommand.MISSINGSTATUS_FORMAT);
                    }
                }
            }
            return new ExportLockerSelectCommand(args);
        }
    }
}
