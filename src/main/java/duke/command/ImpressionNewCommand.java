package duke.command;

import duke.DukeCore;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Plan;
import duke.data.Result;
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
        Integer status;
        switch (addType) { //isn't polymorphism fun?
        case "medicine":
            status = processStatus(getSwitchVal("status"), Medicine.getStatusArr());
            Medicine medicine = new Medicine(getSwitchVal("name"), impression.getName(), priority, status,
                    getSwitchVal("dose"), getSwitchVal("date"), getSwitchVal("duration"));
            impression.addNewTreatment(medicine);
            newData = medicine;
            core.ui.print("New medicine course added:\n" + medicine.toString());
            break;

        case "plan":
            status = processStatus(getSwitchVal("status"), Plan.getStatusArr());
            Plan plan = new Plan(getSwitchVal("name"), impression.getName(), priority, status,
                    getSwitchVal("summary"));
            impression.addNewTreatment(plan);
            core.ui.print("New treatment plan item added:\n" + plan.toString());
            newData = plan;
            break;

        case "investigation":
            status = processStatus(getSwitchVal("status"), Investigation.getStatusArr());
            Investigation invx = new Investigation(getSwitchVal("name"), impression.getName(), priority, status,
                    getSwitchVal("summary"));
            impression.addNewTreatment(invx);
            core.ui.print("New investigation being tracked:\n" + invx.toString());
            newData = invx;
            break;

        case "result":
            Result result = new Result(getSwitchVal("name"), impression.getName(), priority,
                    getSwitchVal("summary"));
            impression.addNewEvidence(result);
            core.ui.print("New result entered:\n" + result.toString());
            newData = result;
            break;

        case "observation":
            boolean isObjective = !isSwitchSet("subjective"); //default to objective
            Observation obsv = new Observation(getSwitchVal("name"), impression.getName(), priority,
                    getSwitchVal("summary"), isObjective);
            impression.addNewEvidence(obsv);
            newData = obsv;
            core.ui.print("New observation logged:\n" + obsv.toString());
            break;

        default:
            throw new DukeException("Invalid data type!");
        }

        if (isSwitchSet("go")) {
            switch (addType) {
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
                break;
            default:
                throw new DukeException("Invalid data type!");
            }
        }
        core.writeJsonFile();
    }


}
