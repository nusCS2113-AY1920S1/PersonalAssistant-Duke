package duke.command;

import duke.exception.DukeException;

import java.util.Arrays;
import java.util.List;

public abstract class DukeDataCommand extends ArgCommand {

    protected String uniqueDataType() throws DukeException {
        //check if the type of data to add was uniquely specified
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
        List<String> forbiddenSwitches;

        // TODO: make static
        switch(addType) {
        case "medicine":
            if (getSwitchVal("dose") == null) {
                throw new DukeException("I need to know the dose of this course of medicine!");
            }

            if (getSwitchVal("duration") == null) {
                throw new DukeException("I need to know how long this medicine will be given for!");
            }
            forbiddenSwitches = Arrays.asList("summary", "subjective", "objective");
            break;
        case "plan": //fallthrough
        case "investigation":
            forbiddenSwitches = Arrays.asList("dose", "duration", "date", "subjective", "objective");
            break;
        case "result":
            forbiddenSwitches = Arrays.asList("dose", "duration", "date", "status", "subjective", "objective");
            break;
        case "observation":
            forbiddenSwitches = Arrays.asList("dose", "duration", "date", "status");
            break;
        default:
            throw new DukeException("Invalid data type!");
        }

        for (String switchName : forbiddenSwitches) {
            if (isSwitchSet(switchName)) {
                throw new DukeException("Illegal switch '" + switchName + "' for data type '" + addType + "'!");
            }
        }
    }
}
