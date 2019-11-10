//@@author e0323290

package gazeeebo.commands.specialization;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Allows users to mark a technical elective as completed.
 */
public class CompletedCommand {
    /**
     * Allows user to record the technical electives they have completed.
     *
     * @param ui            the object that deals with
     *                      printing things to the user.
     * @param storage       the object that deals with storing data,
     *                      in this case storing data in the expenses map
     * @param specMap       the map that map list of respective specializations
     * @param completedEMap the map that maps the completed electives to
     *                      their respective specializations
     * @throws DukeException throws a custom exception if
     *                       module index does not exist
     * @throws IOException   catch any error if read file fails
     */
    public CompletedCommand(final Ui ui,
                            final Storage storage,
                            final Map<String, ArrayList<ModuleCategory>>
                                    specMap,
                            final Map<String, ArrayList<String>> completedEMap)
            throws DukeException, IOException {
        final int commsAndNetworkingIndex = 1;
        final int embeddedComputingIndex = 2;
        final int intelligentSystemsIndex = 3;
        final int interactiveDigitalMediaIndex = 4;
        final int largeScaleComputingIndex = 5;
        final int sysDesignIndex = 6;
        try {
            ArrayList<String> completedElectiveList =
                    new ArrayList();
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

            ui.readCommand();
            String input = ui.fullCommand;
            int specNumber = Integer.parseInt(ui.fullCommand);
            if (specNumber < 0 || specNumber == 0
                    || specNumber > specList.size()) {
                throw new DukeException("Specialization index does not exist.");
            }

            String checkKey = "";
            if (specNumber == commsAndNetworkingIndex) {
                checkKey = specList.get(commsAndNetworkingIndex - 1);
            } else if (specNumber == embeddedComputingIndex) {
                checkKey = specList.get(embeddedComputingIndex - 1);
            } else if (specNumber == intelligentSystemsIndex) {
                checkKey = specList.get(intelligentSystemsIndex - 1);
            } else if (specNumber == interactiveDigitalMediaIndex) {
                checkKey = specList.get(interactiveDigitalMediaIndex - 1);
            } else if (specNumber == largeScaleComputingIndex) {
                checkKey = specList.get(largeScaleComputingIndex - 1);
            } else if (specNumber == sysDesignIndex) {
                checkKey = specList.get(sysDesignIndex - 1);
            }
            System.out.println("Which module have you completed?");
            for (int i = 0; i < specMap.get(checkKey).size(); i++) {
                System.out.println(i + 1 + ". "
                        + specMap.get(checkKey).get(i).code);
            }

            ui.readCommand();
            int moduleCodeIndex = Integer.parseInt(ui.fullCommand);
            if (moduleCodeIndex < 0 || moduleCodeIndex == 0 || moduleCodeIndex
                    > specMap.get(checkKey).size()) {
                throw new DukeException("The module index"
                        + " does not exist.");
            }
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
                storage.writeToCompletedElectivesFile(allCompletedE);
            }
        } catch (NumberFormatException e) {
            System.out.println("Please only key in the index.");
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }
}
