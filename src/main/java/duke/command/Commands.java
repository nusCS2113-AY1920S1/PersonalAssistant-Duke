package duke.command;

// TODO: Write different commands for different contexts?

import duke.command.home.HomeDischargeCommand;
import duke.command.home.HomeFindCommand;
import duke.command.home.HomeHistoryCommand;
import duke.command.home.HomeNewCommand;
import duke.command.home.HomeOpenCommand;
import duke.command.home.HomeReportCommand;
import duke.command.impression.ImpressionDeleteCommand;
import duke.command.impression.ImpressionEditCommand;
import duke.command.impression.ImpressionFindCommand;
import duke.command.impression.ImpressionMoveCommand;
import duke.command.impression.ImpressionNewCommand;
import duke.command.impression.ImpressionPrimaryCommand;
import duke.command.impression.ImpressionPriorityCommand;
import duke.command.impression.ImpressionResultCommand;
import duke.command.impression.ImpressionStatusCommand;
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
                return new HomeFindCommand();
            case "new":
                return new HomeNewCommand();
            case "open":
                return new HomeOpenCommand();
            case "report":
                return new HomeReportCommand();
            case "discharge":
                return new HomeDischargeCommand();
            case "history":
                return new HomeHistoryCommand();
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
                return new ImpressionNewCommand();
            case "edit":
                return new ImpressionEditCommand();
            case "find":
                return new ImpressionFindCommand();
            case "move":
                return new ImpressionMoveCommand();
            case "delete":
                return new ImpressionDeleteCommand();
            case "result":
                return new ImpressionResultCommand();
            case "priority":
                return new ImpressionPriorityCommand();
            case "status":
                return new ImpressionStatusCommand();
            case "primary":
                return new ImpressionPrimaryCommand();
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
