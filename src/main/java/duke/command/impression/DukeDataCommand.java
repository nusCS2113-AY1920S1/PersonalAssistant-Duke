package duke.command.impression;

import duke.command.ArgCommand;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class DukeDataCommand extends ArgCommand {

    // TODO: change map to allowed switches, and code accordingly
    private static final Map<String, List<String>> forbiddenSwitchesMap = Map.ofEntries(
            Map.entry("medicine", Arrays.asList("summary", "subjective", "objective")),
            Map.entry("plan", Arrays.asList("dose", "duration", "date", "subjective", "objective")),
            Map.entry("investigation", Arrays.asList("dose", "duration", "date", "subjective", "objective")),
            Map.entry("result", Arrays.asList("dose", "duration", "date", "status", "subjective", "objective")),
            Map.entry("observation", Arrays.asList("dose", "duration", "date", "status")));

    /**
     * Checks if the type of data to add was uniquely specified.
     * @return The type of data specified amongst the switches for this Command, or null if none of them were given.
     * @throws DukeException If multiple data type switches were specified.
     */
    protected String uniqueDataType() throws DukeException {
        String addType = null;
        String[] typeArr = {"medicine", "investigation", "result", "observation", "plan"};
        for (String type : typeArr) {
            if (isSwitchSet(type)) {
                if (addType != null) {
                    throw new DukeHelpException("Multiple types of data specified: '" + addType
                            + "' and '" + type + "'!", this);
                }
                addType = type;
            }
        }
        return addType;
    }

    protected void checkTypeSwitches(String addType) throws DukeException {
        // check if required switches are in place
        if ("medicine".equals(addType)) {
            if (getSwitchVal("dose") == null) {
                throw new DukeException("I need to know the dose of this course of medicine!");
            }
            if (getSwitchVal("duration") == null) {
                throw new DukeException("I need to know how long this medicine will be given for!");
            }
        }

        List<String> forbiddenSwitches = forbiddenSwitchesMap.get(addType);
        for (String switchName : forbiddenSwitches) {
            if (isSwitchSet(switchName)) {
                throw new DukeException("Illegal switch '" + switchName + "' for data type '" + addType + "'!");
            }
        }
    }
}
