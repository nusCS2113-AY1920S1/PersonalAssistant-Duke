package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
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

public class ImpressionNewSpec extends DukeDataSpec {

    private static final ImpressionNewSpec spec = new ImpressionNewSpec();

    public static ImpressionNewSpec getSpec() {
        return spec;
    }

    private ImpressionNewSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("medicine", null, true, ArgLevel.NONE, "m"),
                new Switch("investigation", null, true, ArgLevel.NONE, "i", "invx"),
                new Switch("plan", null, true, ArgLevel.NONE, "p"),
                new Switch("observation", null, true, ArgLevel.NONE, "o"),
                new Switch("result", null, true, ArgLevel.NONE, "r"),
                new Switch("go", null, true, ArgLevel.NONE, "g"),
                new Switch("name", String.class, false, ArgLevel.REQUIRED, "n"),
                new Switch("status", String.class, true, ArgLevel.REQUIRED, "sta"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum"),
                new Switch("dose", String.class, true, ArgLevel.REQUIRED, "d"),
                new Switch("date", String.class, true, ArgLevel.REQUIRED, "da"),
                new Switch("duration", String.class, true, ArgLevel.REQUIRED, "du"),
                new Switch("subjective", null, true, ArgLevel.NONE, "sub"),
                new Switch("objective", null, true, ArgLevel.NONE, "obj")
        );
    }

    // TODO refactor into 5 different methods for each type
    @Override
    protected void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String addType = uniqueDataType(cmd);
        if (addType == null) {
            throw new DukeHelpException("No data type specified!", cmd);
        }
        checkTypeSwitches(addType, cmd);
        Impression impression = ImpressionUtils.getImpression(core);
        DukeData newData;
        String newStr;

        //extract parameters and data type
        int priority = cmd.switchToInt("priority");
        if (priority == -1) {
            priority = 0;
        }
        cmd.nullToEmptyString(); //set optional string parameters to ""
        int status;
        switch (addType) { //isn't polymorphism fun?
        case "medicine":
            //TODO proper date parsing
            String startDate = cmd.getSwitchVal("date");
            if ("".equals(startDate)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd MMM yyyy");
                startDate = LocalDate.now().format(formatter);
            }
            Medicine medicine = new Medicine(cmd.getSwitchVal("name"), impression, priority,
                    cmd.getSwitchVal("status"), cmd.getSwitchVal("dose"), startDate,
                    cmd.getSwitchVal("duration"));
            impression.addNewTreatment(medicine);
            newData = medicine;
            newStr = "New medicine course added:\n" + medicine.toString();
            break;

        case "plan":
            Plan plan = new Plan(cmd.getSwitchVal("name"), impression, priority, cmd.getSwitchVal("status"),
                    cmd.getSwitchVal("summary"));
            impression.addNewTreatment(plan);
            newData = plan;
            newStr = "New treatment plan item added:\n" + plan.toString();
            break;

        case "investigation":
            Investigation invx = new Investigation(cmd.getSwitchVal("name"), impression, priority,
                    cmd.getSwitchVal("status"), cmd.getSwitchVal("summary"));
            impression.addNewTreatment(invx);
            newData = invx;
            newStr = "New investigation being tracked:\n" + invx.toString();
            break;

        case "result":
            Result result = new Result(cmd.getSwitchVal("name"), impression, priority,
                    cmd.getSwitchVal("summary"));
            impression.addNewEvidence(result);
            newData = result;
            newStr = "New result entered:\n" + result.toString();
            break;

        case "observation":
            boolean isObjective = !cmd.isSwitchSet("subjective"); //default to objective
            Observation obsv = new Observation(cmd.getSwitchVal("name"), impression, priority,
                    cmd.getSwitchVal("summary"), isObjective);
            impression.addNewEvidence(obsv);
            newData = obsv;
            newStr = "New observation logged:\n" + obsv.toString();
            break;

        default:
            throw new DukeException("Invalid data type!");
        }

        core.updateUi(newStr);

        if (cmd.isSwitchSet("go")) {
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
