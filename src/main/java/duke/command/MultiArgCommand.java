package duke.command;

import duke.exception.DukeException;

/**
 * Abstract class for commands with multiple arguments split by a common delimiter.
 */
public abstract class MultiArgCommand extends ArgCommand {
    //TODO: figure out the proper Javadoc for these parameters
    protected String[] argv; //array to store multiple arguments
    protected int argc; //minimum number of arguments expected
    protected String delim; //delimiter to split arguments by, may be a regex
    protected String invalidArgMsg; //message to display if insufficient arguments are supplied

    /**
     * Splits the input string into the argv array based on the common delimiter delim, stripping the strings in argv.
     *
     * @throws DukeException If, after being split the number of arguments is less than argc, the required number.
     */
    public void parse(String inputStr) throws DukeException {

        //remove excess whitespace between commands
        //primarily to prevent space-delimited commands from throwing errors if there are two spaces
        argv = arg.split("\\s*" + delim + "\\s*");
        if (argv.length < argc) {
            argv = null;
            throw new DukeException(invalidArgMsg);
        }
        for (int i = 0; i < argv.length; ++i) {
            argv[i] = argv[i].strip();
        }
    }
}
