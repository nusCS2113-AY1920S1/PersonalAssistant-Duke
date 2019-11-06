package duke.command;

// TODO: Write different commands for different contexts?

import duke.command.home.HomeDischargeSpec;
import duke.command.home.HomeFindSpec;
import duke.command.home.HomeHistorySpec;
import duke.command.home.HomeNewSpec;
import duke.command.home.HomeOpenSpec;
import duke.command.home.HomeReportSpec;
import duke.command.impression.ImpressionDeleteSpec;
import duke.command.impression.ImpressionEditSpec;
import duke.command.impression.ImpressionFindSpec;
import duke.command.impression.ImpressionMoveSpec;
import duke.command.impression.ImpressionNewSpec;
import duke.command.impression.ImpressionPrioritySpec;
import duke.command.impression.ImpressionResultSpec;
import duke.command.impression.ImpressionStatusSpec;
import duke.command.patient.PatientDeleteCommand;
import duke.command.patient.PatientDischargeCommand;
import duke.command.patient.PatientEditCommand;
import duke.command.patient.PatientFindCommand;
import duke.command.patient.PatientHistoryCommand;
import duke.command.patient.PatientNewCommand;
import duke.command.patient.PatientOpenCommand;
import duke.command.patient.PatientPrimaryCommand;
import duke.command.patient.PatientReportCommand;
import duke.ui.context.Context;

/**
 * Maintains the associations between command keywords and commands (e.g. "list" -> ListCommand). For use in parsing
 * user input.
 */
public class Commands {
    /**
     * Constructs and returns the command corresponding to a name provided by the user.
     *
     * @param cmdStr The user-provided name.
     * @return The newly constructed command without any parameters loaded.
     */
    public Command getCommand(String cmdStr, Context context) {
        // check context-independent switches first
        switch (cmdStr) {
        case "bye":
            return new ByeCommand();
        case "back":
            return new BackCommand();
        case "up":
            return new UpCommand();
        default:
            break; //not one of these; continue
        }

        switch (context) {
        case HOME:
            switch (cmdStr) {
            case "find":
                return new ArgCommand(HomeFindSpec.getSpec());
            case "new":
                return new ArgCommand(HomeNewSpec.getSpec());
            case "open":
                return new ArgCommand(HomeOpenSpec.getSpec());
            case "report":
                return new ArgCommand(HomeReportSpec.getSpec());
            case "discharge":
                return new ArgCommand(HomeDischargeSpec.getSpec());
            case "history":
                return new ArgCommand(HomeHistorySpec.getSpec());
            default:
                return null;
            }
        case PATIENT:
            switch (cmdStr) {
            case "new":
                return new PatientNewCommand();
            case "find":
                return new PatientFindCommand();
            case "report":
                return new PatientReportCommand();
            case "discharge":
                return new PatientDischargeCommand();
            case "delete":
                return new PatientDeleteCommand();
            case "primary":
                return new PatientPrimaryCommand();
            case "edit":
                return new PatientEditCommand();
            case "history":
                return new PatientHistoryCommand();
            case "open":
                return new PatientOpenCommand();
            default:
                return null;
            }
        case IMPRESSION:
            switch (cmdStr) {
            case "new":
                return new ArgCommand(ImpressionNewSpec.getSpec());
            case "edit":
                return new ArgCommand(ImpressionEditSpec.getSpec());
            case "find":
                return new ArgCommand(ImpressionFindSpec.getSpec());
            case "move":
                return new ArgCommand(ImpressionMoveSpec.getSpec());
            case "delete":
                return new ArgCommand(ImpressionDeleteSpec.getSpec());
            case "result":
                return new ArgCommand(ImpressionResultSpec.getSpec());
            case "priority":
                return new ArgCommand(ImpressionPrioritySpec.getSpec());
            case "status":
                return new ArgCommand(ImpressionStatusSpec.getSpec());
            case "primary":
                return new ArgCommand(ImpressionPrimarySpec.getSpec());
            default:
                return null;
            }
        case TREATMENT:
            if ("status".equals(cmdStr)) {
                //return new TreatmentStatusCommand();
                break;
            }
            //fallthrough

        case INVESTIGATION:
            if ("result".equals(cmdStr)) {
                //return new InvxResultCommand();
                break;
            }
            //fallthrough

        case EVIDENCE:

        default:
            break;
        }
        return null;
    }
}
