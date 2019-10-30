package duke.parser;

import duke.logic.commands.ExportLockerCommand;

public class ExportLockerCommandParser {

    /**
     * This function is used to parse the user input for exporting the details as a CSV file.
     */

    public ExportLockerCommand parse()  {
        return new ExportLockerCommand();
    }
}
