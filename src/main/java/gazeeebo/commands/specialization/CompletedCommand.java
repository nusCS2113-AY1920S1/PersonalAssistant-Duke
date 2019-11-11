//@@author e0323290

package gazeeebo.commands.specialization;

import gazeeebo.ui.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.CompletedElectivesStorage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Allows users to mark a technical elective as completed.
 */
public class CompletedCommand {
    /**
     * Index of Communications and Networking on the list.
     */
    private static final int COMMS_AND_NETWORKING_INDEX = 1;
    /**
     * Index of Embedded Computing on the list.
     */
    private static final int EMBEDDED_COMPUTING_INDEX = 2;
    /**
     * Index of Intelligent Systems on the list.
     */
    private static final int INTELLIGENT_SYSTEMS_INDEX = 3;
    /**
     * Index of Interactive Digital Media on the list.
     */
    private static final int INTERACTIVE_DIGITAL_MEDIA_INDEX = 4;
    /**
     * Index of Large-Scale Computing on the list.
     */
    private static final int LARGE_SCALE_COMPUTING_INDEX = 5;
    /**
     * Index of System On A Chip Design on the list.
     */
    private static final int SYS_ON_A_CHIP_DESIGN_INDEX = 6;

    /**
     * Allows user to record the technical electives they have completed.
     *
     * @param ui            the object that deals with
     *                      printing things to the user.
     * @param specMap       the map that map list of respective specializations
     * @param completedEMap the map that maps the completed electives to
     *                      their respective specializations
     * @throws IOException catch any error if read file fails
     */
    public CompletedCommand(final Ui ui,
                            final Map<String, ArrayList<ModuleCategory>>
                                    specMap,
                            final Map<String, ArrayList<String>> completedEMap)
            throws IOException {
        CompletedElectivesStorage completedElectivesStorage = new CompletedElectivesStorage();

        ArrayList<String> completedElectiveList = new ArrayList();
        System.out.println("Which specialization number "
                + "is your module under?");
        ArrayList<String> specList = new ArrayList<String>();
        specList.add("Communications & Networking"); //index 0
        specList.add("Embedded Computing"); //index 1
        specList.add("Intelligent Systems"); //index 2
        specList.add("Interactive Digital Media"); //index 3
        specList.add("Large-Scale Computing"); //index 4
        specList.add("System-On-A-Chip Design"); //index 5
        for (int i = 0; i < specList.size(); i++) {
            System.out.println(i + 1 + ". " + specList.get(i));
        }
        try {
            ui.readCommand();
            int specNumber = Integer.parseInt(ui.fullCommand);
            if (specNumber < 0 || specNumber == 0
                    || specNumber > specList.size()) {
                throw new DukeException("Specialization index does not exist.");
            }

            String checkKey = "";
            if (specNumber == COMMS_AND_NETWORKING_INDEX) {
                checkKey = specList.get(COMMS_AND_NETWORKING_INDEX - 1);
            } else if (specNumber == EMBEDDED_COMPUTING_INDEX) {
                checkKey = specList.get(EMBEDDED_COMPUTING_INDEX - 1);
            } else if (specNumber == INTELLIGENT_SYSTEMS_INDEX) {
                checkKey = specList.get(INTELLIGENT_SYSTEMS_INDEX - 1);
            } else if (specNumber == INTERACTIVE_DIGITAL_MEDIA_INDEX) {
                checkKey = specList.get(INTERACTIVE_DIGITAL_MEDIA_INDEX - 1);
            } else if (specNumber == LARGE_SCALE_COMPUTING_INDEX) {
                checkKey = specList.get(LARGE_SCALE_COMPUTING_INDEX - 1);
            } else if (specNumber == SYS_ON_A_CHIP_DESIGN_INDEX) {
                checkKey = specList.get(SYS_ON_A_CHIP_DESIGN_INDEX - 1);
            }
            System.out.println("Which module have you completed?");
            for (int i = 0; i < specMap.get(checkKey).size(); i++) {
                System.out.println(i + 1 + ". "
                        + specMap.get(checkKey).get(i).code);
            }

            ui.readCommand();
            int moduleCodeIndex = Integer.parseInt(ui.fullCommand);

            String moduleCode
                    = specMap.get(checkKey).get(moduleCodeIndex - 1).code;
            boolean isEqual = false;
            for (String key : completedEMap.keySet()) {
                if (checkKey.equals(key)) {
                    completedEMap.get(key).add(moduleCode);
                    isEqual = true;
                }
            }
            if (!isEqual) {
                completedElectiveList.add(moduleCode);
                completedEMap.put(checkKey, completedElectiveList);
            }
            System.out.println("You have completed " + moduleCode + ".");

            String toStoreCN = "";
            String toStoreEC = "";
            String toStoreIS = "";
            String toStoreID = "";
            String toStoreLS = "";
            String toStoreSC = "";
            for (String key : completedEMap.keySet()) {
                if (key.equals("Communications & Networking")) {
                    for (int i = 0; i < completedEMap.get("Communications "
                            + "& Networking").size(); i++) {
                        toStoreCN = toStoreCN.concat("Communications "
                                + "& Networking"
                                + "|"
                                + completedEMap.get("Communications "
                                + "& Networking").get(i));
                    }
                } else if (key.equals("Embedded Computing")) {
                    for (int i = 0; i < completedEMap.get("Embedded"
                            + " Computing").size(); i++) {
                        toStoreEC = toStoreEC.concat("Embedded Computing"
                                + "|"
                                + completedEMap.get("Embedded"
                                + " Computing").get(i));
                    }
                } else if (key.equals("Intelligent"
                        + " Systems")) {
                    for (int i = 0; i < completedEMap.get("Intelligent"
                            + " Systems").size(); i++) {
                        toStoreIS = toStoreIS.concat("Intelligent"
                                + " Systems"
                                + "|"
                                + completedEMap.get("Intelligent"
                                + " Systems").get(i));
                    }
                } else if (key.equals("Interactive"
                        + " Digital Media")) {
                    for (int i = 0; i < completedEMap.get("Interactive"
                            + " Digital Media").size(); i++) {
                        toStoreIS = toStoreIS.concat("Interactive"
                                + " Digital Media"
                                + "|"
                                + completedEMap.get("Interactive"
                                + " Digital Media").get(i));
                    }
                } else if (key.equals("Large-Scale"
                        + " Computing")) {
                    for (int i = 0; i < completedEMap.get("Large-Scale"
                            + " Computing").size(); i++) {
                        toStoreIS = toStoreIS.concat("Large-Scale"
                                + " Computing"
                                + "|"
                                + completedEMap.get("Large-Scale"
                                + " Computing").get(i));
                    }
                } else if (key.equals("System-On-A-Chip"
                        + " Design")) {
                    for (int i = 0; i < completedEMap.get("System-On-A-Chip"
                            + " Design").size(); i++) {
                        toStoreIS = toStoreIS.concat("System-On-A-Chip"
                                + " Design"
                                + "|"
                                + completedEMap.get("System-On-A-Chip"
                                + " Design").get(i));
                    }
                }
                String allCompletedE = toStoreCN
                        + "\n" + toStoreEC + "\n"
                        + toStoreIS + "\n" + toStoreID + "\n"
                        + toStoreLS + "\n" + toStoreSC;
                completedElectivesStorage.writeToCompletedElectivesFile(allCompletedE);
            }
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        } catch (NumberFormatException e) {
            System.out.println("Please key in numbers only.");
        } catch (IndexOutOfBoundsException e) {
            System.out.print("Technical Elective index does not exist.\n");
        }
    }
}
