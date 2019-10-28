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
import duke.data.Treatment;
import duke.exception.DukeException;
import duke.exception.DukeHelpException;

import java.util.List;
import java.util.Map;

import static duke.command.impression.ImpressionHelpers.getImpression;

public class ImpressionEditCommand extends DukeDataCommand {

    @Override
    protected ArgSpec getSpec() {
        return ImpressionEditSpec.getSpec();
    }

    // TODO: refactor, this method is a behemoth
    @Override
    public void execute(DukeCore core) throws DukeException {
        super.execute(core);
        String editType = uniqueDataType();
        DukeData editData;
        boolean isAppending = false;
        if (isSwitchSet("append")) {
            isAppending = true;
        }

        if (editType == null) { // edit impression
            editImpression(getImpression(core), isAppending);
        } else {
            if (isSwitchSet("description")) {
                throw new DukeHelpException("Descriptions are only for impressions!", this);
            }

            // find DukeData of editType
            // TODO: proper search with disambiguation
            // TODO: select by index

            String editDataName = getSwitchVal(editType);
            List<DukeData> searchResults = getImpression(core).find(editDataName);
            switch (editType) {
            case "plan":
                editData = findDataOfClass(searchResults, Plan.class);
                break;
            case "medicine":
                editData = findDataOfClass(searchResults, Medicine.class);
                break;
            case "investigation":
                editData = findDataOfClass(searchResults, Investigation.class);
                break;
            case "result":
                editData = findDataOfClass(searchResults, Result.class);
                break;
            case "observation":
                editData = findDataOfClass(searchResults, Observation.class);
                break;
            default:
                throw new DukeException("Invalid data type!");
            }

            if (editData == null) {
                throw new DukeHelpException("I can't find a " + editType + " item named '" + editDataName + "'!", this);
            }

            // process status
            switch (editType) {
            case "plan":
                editStatus(editData, Plan.getStatusArr());
                break;
            case "medicine":
                editStatus(editData, Medicine.getStatusArr());
                break;
            case "investigation":
                editStatus(editData, Investigation.getStatusArr());
                break;
            default:
                break;
            }

            // process remaining switches entered
            for (Map.Entry<String, String> entry : getSwitchVals().entrySet()) {
                String switchName = entry.getKey();
                String entryStr = entry.getValue();
                int entryInt = 0;
                if (Integer.class.equals(getSwitchMap().get(switchName).type)) {
                    entryInt = switchToInt(switchName);
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
                    ImpressionHelpers.checkPriority(entryInt, this);
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
                            throw new DukeHelpException("Medicine plans do not have this property: '"
                                    + entryStr + "'", this);
                        }
                        break;
                    case "observation":
                        Observation obsv = (Observation) editData;
                        if (isSwitchSet("objective") && isSwitchSet("subjective")) {
                            throw new DukeHelpException("I don't know if you want this observation to be objective"
                                    + "or subjective!", this);
                        } else if (isSwitchSet("objective")) {
                            obsv.setObjective(true);
                        } else if (isSwitchSet("subjective")) {
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
    }

    private void editStatus(DukeData editData, List<String> statusList) throws DukeHelpException {
        assert (editData instanceof Treatment);
        String statusStr = getSwitchVal("status");
        if (statusStr != null) {
            Treatment treatment = (Treatment) editData;
            treatment.setStatusIdx(processStatus(statusStr, statusList));
        }
    }

    private void editImpression(Impression impression, boolean isAppending) {
        String newName = getSwitchVal("name");
        if (newName != null) {
            impression.setName((isAppending) ? impression.getName() + newName : newName);
        }
        String newDesc = getSwitchVal("description");
        if (newDesc != null) {
            impression.setDescription((isAppending) ? impression.getDescription() + newDesc : newDesc);
        }
    }

    // TODO proper searching
    // simply gets the first search result with a matching class
    // this is a terrible method, kill it and replace it with proper disambiguation as soon as possible
    private DukeData findDataOfClass(List<DukeData> searchResults, Class dataClass) {
        for (DukeData result : searchResults) {
            if (result.getClass() == dataClass) {
                return result;
            }
        }
        return null;
    }
}
