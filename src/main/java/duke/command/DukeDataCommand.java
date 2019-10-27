package duke.command;

import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public abstract class DukeDataCommand extends ImpressionCommand {

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
                    throw new DukeException("Multiple types of data specified: '" + addType
                            + "' and '" + type + "'!");
                }
                addType = type;
            }
        }
        return addType;
    }

    protected void checkTypeSwitches(String addType) throws DukeException {
        // check if required switches are in place
        if (addType.equals("medicine")) {
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

    /**
     * Checks if a status is a string or an integer, and returns the appropriate integer if it is a string.
     * @param status The String supplied as an argument to the status switch.
     * @param statusList The status descriptions that the numeric value of the status represent. The numeric value of
     *                  the status is the index of the corresponding description in the array.
     * @return The Integer that the string represents, or 0 if it is null.
     * @throws NumberFormatException If the string is not a valid representation of an integer.
     */
    protected Integer processStatus(String status, List<String> statusList) throws DukeHelpException {
        if (status.equals("")) {
            return 0;
        } else {
            try {
                int convertedStatus = Integer.parseInt(status);
                if (convertedStatus < 0 || convertedStatus >= statusList.size()) {
                    throw new DukeHelpException(status + "is not a valid numeric value for the status!", this);
                }
                return convertedStatus;
            } catch (NumberFormatException excp) { // not numeric
                // TODO: parse with autocorrect?
                for (int i = 0; i < statusList.size(); ++i) {
                    if (statusList.get(i).equalsIgnoreCase(status)) {
                        return i;
                    }
                }
                throw new DukeHelpException("'" + status + "' is not a valid status name!", this);
            }
        }
    }

    protected List<String> getForbiddenSwitches(String dataType) {
        return forbiddenSwitchesMap.get(dataType);
    }
}
