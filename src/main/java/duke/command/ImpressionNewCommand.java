package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Medicine;
import duke.exception.DukeException;
import duke.ui.Context;

public class ImpressionNewCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addType = uniqueDataType();
        checkTypeSwitches(addType);
        Impression impression = getImpression(core);
        DukeData newData;

        //extract parameters and data type
        Integer priority = switchToInt(getSwitchVal("priority"));
        switch(addType) {
        case "medicine":
            Integer status = processStatus(getSwitchVal("status"), Medicine.getStatusArr());
            Medicine medicine = new Medicine(getSwitchVal("name"), impression.getName(), priority, status,
                    )
            impression.addNewTreatment()
            break;
        case "plan": //fallthrough
        case "investigation":
            break;
        case "result":
            break;
        case "observation":
            break;
        default:
            throw new DukeException("Invalid data type!");
        }

        if (isSwitchSet("go")) {
            switch(addType) {
            case "plan": //fallthrough
            case "medicine":
                core.uiContext.setContext(Context.TREATMENT, newData);
                break;
            case "investigation":
                core.uiContext.setContext(Context.INVESTIGATION, newData);
                break;
            case "result": //fallthrough
            case "observation":
                core.uiContext.setContext(Context.EVIDENCE, newData);
            default:
                throw new DukeException("Invalid data type!");
            }
        }
    }
}
