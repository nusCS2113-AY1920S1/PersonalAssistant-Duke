package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.exception.DukeException;

// TODO update JavaDocs

public class DataCommand extends ArgCommand {

    private final DataSpec spec;

    /**
     * Creates a new command that takes arguments, with switches and functionality specified in the supplied ArgSpec
     * object.
     * @param spec The ArgSpec object that specifies this command object's switches and functionality.
     */
    public DataCommand(DataSpec spec) {
        super(spec);
        this.spec = spec;
    }

    /**
     * Creates a new command that takes arguments, with switches and functionality specified in the supplied ArgSpec
     * object, with the option to specify the argument and switch values, if allowed.
     * @param spec The DataSpec object that specifies this command object's switches and functionality.
     * @param arg The argument to load for this command.
     * @param switchNames The names of the switches to set.
     * @param switchVals The values of the switches to set.
     * @throws DukeException If illegal values are supplied for the switches or arguments, or if the number of switch
     *      values is different from the number of switch names.
     */
    public DataCommand(DataSpec spec, String arg, String[] switchNames, String[] switchVals) throws DukeException {
        super(spec, arg, switchNames, switchVals);
        this.spec = spec;
    }

    /**
     * Executes this command, with the behaviour being defined by its DataSpec object, operating on the data specified.
     * @param core The DukeCore object for this command to operate on.
     * @param data The specific DukeData object for this command to operate on, typically returned by search.
     * @throws DukeException If an exceptional condition is encountered during execution of the command.
     */
    public void execute(DukeCore core, DukeData data) throws DukeException {
        spec.execute(core, this, data);
    }
}
