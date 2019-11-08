package duke.command.impression;

import duke.DukeCore;
import duke.command.ArgLevel;
import duke.command.Switch;
import duke.data.DukeData;
import duke.data.Impression;
import duke.data.Investigation;
import duke.data.Medicine;
import duke.data.Observation;
import duke.data.Patient;
import duke.data.Plan;
import duke.data.SearchResults;
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;
import duke.exception.DukeUtilException;

import java.util.List;
import java.util.Map;

public class ImpressionEditSpec extends ImpressionObjSpec {

    private static final ImpressionEditSpec spec = new ImpressionEditSpec();

    public static ImpressionEditSpec getSpec() {
        return spec;
    }

    private ImpressionEditSpec() {
        cmdArgLevel = ArgLevel.NONE;
        initSwitches(
                new Switch("append", null, true, ArgLevel.NONE, "a"),
                new Switch("evidence", String.class, true, ArgLevel.REQUIRED, "e"),
                new Switch("treatment", String.class, true, ArgLevel.REQUIRED, "t"),
                new Switch("name", String.class, true, ArgLevel.REQUIRED, "n"),
                new Switch("status", String.class, true, ArgLevel.REQUIRED, "sta"),
                new Switch("priority", Integer.class, true, ArgLevel.REQUIRED, "pri"),
                new Switch("summary", String.class, true, ArgLevel.REQUIRED, "sum"),
                new Switch("dose", String.class, true, ArgLevel.REQUIRED, "d"),
                new Switch("date", String.class, true, ArgLevel.REQUIRED, "da"),
                new Switch("duration", String.class, true, ArgLevel.REQUIRED, "du"),
                new Switch("subjective", null, true, ArgLevel.NONE, "sub"),
                new Switch("objective", null, true, ArgLevel.NONE, "obj"),
                new Switch("description", String.class, true, ArgLevel.REQUIRED, "desc")
        );
    }

    // TODO: split method to call one method per editType
    @Override
    protected void execute(DukeCore core) throws DukeException {
        boolean isAppending = false;
        if (cmd.isSwitchSet("append")) {
            isAppending = true;
        }

        if (cmd.getSwitchVal("evidence") == null
                && cmd.getSwitchVal("treatment") == null) { // edit impression
            editImpression(ImpressionUtils.getImpression(core), isAppending);
            core.writeJsonFile();
            core.updateUi("Updated details of this Impression!");
        } else {
            if (cmd.isSwitchSet("description")) {
                throw new DukeHelpException("Descriptions are only for impressions!", cmd);
            }
            super.execute(core); // find specified DukeData
        }

            // find DukeData of editType
            // TODO: proper search with disambiguation

            // check if these are indices
            String editDataName = cmd.getSwitchVal(editType);

            SearchResults results = ImpressionUtils.getImpression(core).findByName(editDataName);


            DukeData editData;
            if (editData == null) {
                throw new DukeHelpException("I can't find a " + editType + " item named '" + editDataName + "'!", cmd);
            }

            // process status
            switch (editType) {
            case "plan":
                updateStatus(editData, Plan.getStatusArr());
                break;
            case "medicine":
                updateStatus(editData, Medicine.getStatusArr());
                break;
            case "investigation":
                updateStatus(editData, Investigation.getStatusArr());
                break;
            default:
                break;
            }

            // process remaining switches entered
            for (Map.Entry<String, String> entry : cmd.getSwitchVals().entrySet()) {
                String switchName = entry.getKey();
                String entryStr = entry.getValue();
                int entryInt = 0;
                if (Integer.class.equals(getSwitchMap().get(switchName).type)) {
                    entryInt = cmd.switchToInt(switchName);
                }

                // ignore switches that don't need processing
                if (switchName.equals(editType)) {
                    continue;
                }

                // TODO: deal with null
                switch (switchName) {
                case "name":
                    //TODO check for allergies for medicine
                    editData.setName((isAppending) ? editData.getName() + entryStr : entryStr);
                    break;
                case "priority":
                    if (entryInt == -1) {
                        break;
                    }
                    ImpressionUtils.checkPriority(entryInt);
                    editData.setPriority(entryInt);
                    break;
                case "summary":
                    editData.setSummary((isAppending) ? editData.getSummary() + entryStr : entryStr);
                    break;
                default:
                    switch (editType) {
                    case "medicine":
                        Medicine med = (Medicine) editData;
                        switch (switchName) {
                        case "dose":
                            med.setDose((isAppending) ? med.getDose() + entryStr : entryStr);
                            break;
                        case "date":
                            med.setStartDate((isAppending) ? med.getStartDate() + entryStr : entryStr);
                            break;
                        case "duration":
                            med.setDuration((isAppending) ? med.getDuration() + entryStr : entryStr);
                            break;
                        default:
                            throw new DukeHelpException("Medicine plans do not have the property: '"
                                    + entryStr + "'", cmd);
                        }
                        break;
                    case "observation":
                        Observation obsv = (Observation) editData;
                        if (cmd.isSwitchSet("objective") && cmd.isSwitchSet("subjective")) {
                            throw new DukeHelpException("I don't know if you want the observation to be objective"
                                    + "or subjective!", cmd);
                        } else if (cmd.isSwitchSet("objective")) {
                            obsv.setObjective(true);
                        } else if (cmd.isSwitchSet("subjective")) {
                            obsv.setObjective(false);
                        }
                        break;
                    case "plan": //fallthrough
                    case "result": //fallthrough
                    case "investigation": //all switches should already be handled
                        break;
                    default:
                        throw new DukeException("Invalid data type found when making edits!");
                    }
                    break;
                }

            }
        }

        core.writeJsonFile();
        core.updateUi("Details updated");
    }

    private void editImpression(Impression impression, boolean isAppending) {
        Patient patient = (Patient) impression.getParent();

        String newName = cmd.getSwitchVal("name");
        if (newName != null) {
            impression.setName((isAppending) ? impression.getName() + newName : newName);
        }

        String newDesc = cmd.getSwitchVal("description");
        if (newDesc != null) {
            impression.setDescription((isAppending) ? impression.getDescription() + newDesc : newDesc);
        }
    }

    // TODO proper searching
    // simply gets the first search result with a matching class
    // this is a terrible method, kill it and replace it with proper disambiguation as soon as possible
    private DukeData findDataOfClass(List<DukeData> resultList, Class dataClass) {
        for (DukeData result : resultList) {
            if (result.getClass() == dataClass) {
                return result;
            }
        }
        return null;
    }

    private void updateStatus(DukeData editData, List<String> statusList) throws DukeUtilException {
        String statusStr = cmd.getSwitchVal("status");
        if (statusStr != null) {
            Treatment treatment = (Treatment) editData;
            treatment.setStatusIdx(ImpressionUtils.processStatus(statusStr, statusList));
        }
    }
}
