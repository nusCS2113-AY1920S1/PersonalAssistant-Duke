package duke.command;

import duke.DukeCore;
import duke.data.DukeObject;
import duke.exception.DukeException;

// TODO update JavaDocs

public class ObjCommand extends ArgCommand {

    private final ObjSpec spec;

    /**
     * Creates a new command that takes arguments, with switches and functionality specified in the supplied ArgSpec
     * object.
     * @param spec The ArgSpec object that specifies this command object's switches and functionality.
     */
    public ObjCommand(ObjSpec spec) {
        super(spec);
        this.spec = spec;
    }

    /**
     * Creates a new command that takes arguments, with switches and functionality specified in the supplied ArgSpec
     * object, with the option to specify the argument and switch values, if allowed.
     * @param spec The ObjSpec object that specifies this command object's switches and functionality.
     * @param arg The argument to load for this command.
     * @param switchNames The names of the switches to set.
     * @param switchVals The values of the switches to set.
     * @throws DukeException If illegal values are supplied for the switches or arguments, or if the number of switch
     *      values is different from the number of switch names.
     */
    public ObjCommand(ObjSpec spec, String arg, String[] switchNames, String[] switchVals) throws DukeException {
        super(spec, arg, switchNames, switchVals);
        this.spec = spec;
    }

    /**
     * Executes this command, with the behaviour being defined by its ObjSpec object, operating on the obj specified.
     * @param core The DukeCore object for this command to operate on.
     * @param obj The specific DukeObject object for this command to operate on, typically returned by search.
     * @throws DukeException If an exceptional condition is encountered during execution of the command.
     */
    public void execute(DukeCore core, DukeObject obj) throws DukeException {
        spec.execute(core, this, obj);
    }
}
