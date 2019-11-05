package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgSpec;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Plan;
import duke.data.Result;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;
import duke.ui.context.Context;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ImpressionNewCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionNewSpec.getSpec();
    }

    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addType = uniqueDataType();
        if (addType == null) {
            throw new DukeHelpException("No data type specified!", this);
        }
        checkTypeSwitches(addType);
        Impression impression = ImpressionUtils.getImpression(core);
        DukeData newData;
        String newStr;

        //extract parameters and data type
        int priority = switchToInt("priority");
        if (priority == -1) {
            priority = 0;
        }
        ImpressionUtils.checkPriority(priority);
        nullToEmptyString(); //set optional string parameters to ""
        int status;
        switch (addType) { //isn't polymorphism fun?
        case "medicine":
            //TODO check for allergies
            status = ImpressionUtils.processStatus(getSwitchVal("status"), Medicine.getStatusArr());
            //TODO proper date parsing
            if ("".equals(getSwitchVal("date"))) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                setSwitchVal("date", LocalDate.now().format(formatter));
            }
            Medicine medicine = new Medicine(getSwitchVal("name"), impression, priority, status,
                    getSwitchVal("dose"), getSwitchVal("date"), getSwitchVal("duration"));
            impression.addNewTreatment(medicine);
            newData = medicine;
            newStr = "New medicine course added:\n" + medicine.toString();
            break;

        case "plan":
            status = ImpressionUtils.processStatus(getSwitchVal("status"), Plan.getStatusArr());
            Plan plan = new Plan(getSwitchVal("name"), impression, priority, status,
                    getSwitchVal("summary"));
            impression.addNewTreatment(plan);
            newData = plan;
            newStr = "New treatment plan item added:\n" + plan.toString();
            break;

        case "investigation":
            status = ImpressionUtils.processStatus(getSwitchVal("status"), Investigation.getStatusArr());
            Investigation invx = new Investigation(getSwitchVal("name"), impression, priority, status,
                    getSwitchVal("summary"));
            impression.addNewTreatment(invx);
            newData = invx;
            newStr = "New investigation being tracked:\n" + invx.toString();
            break;

        case "result":
            Result result = new Result(getSwitchVal("name"), impression, priority,
                    getSwitchVal("summary"));
            impression.addNewEvidence(result);
            newData = result;
            newStr = "New result entered:\n" + result.toString();
            break;

        case "observation":
            boolean isObjective = !isSwitchSet("subjective"); //default to objective
            Observation obsv = new Observation(getSwitchVal("name"), impression, priority,
                    getSwitchVal("summary"), isObjective);
            impression.addNewEvidence(obsv);
            newData = obsv;
            newStr = "New observation logged:\n" + obsv.toString();
            break;

        default:
            throw new DukeException("Invalid data type!");
        }

        core.updateUi(newStr);

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
