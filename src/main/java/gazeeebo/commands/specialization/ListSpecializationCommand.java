package gazeeebo.commands.specialization;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class ListSpecializationCommand {
    final static int COMMS_NETWORKING_INDEX = 1;
    final static int EMBEDDED_COMPUTING_INDEX = 2;
    final static int INTELLIGENT_SYSTEM_INDEX = 3;
    final static int INTERACTIVE_DIGITAL_MEDIA_INDEX = 4;
    final static int LARGE_SCALE_COMPUTING_INDEX = 5;
    final static int SYS_DESIGN_INDEX = 6;

    public ListSpecializationCommand(Ui ui, Storage storage, Map<String, ArrayList<ModuleCategory>> specMap, Map<String, ArrayList<String>> completedEMap) throws DukeException, IOException {
        try {
        new ListOfSpecializationAndModules(ui, storage, specMap);
        ArrayList<String> specList = new ArrayList<String>();
        specList.add("Communications & Networking"); //index 0
        specList.add("Embedded Computing"); //index 1
        specList.add("Intelligent Systems"); //index 2
        specList.add("Interactive Digital Media"); //index 3
        specList.add("Large-Scale Computing"); //index 4
        specList.add("System-On-A-Chip Design"); //index 5

        System.out.println("Choose a specialization:");
        for (int i = 0; i < specList.size(); i++) {
            System.out.println(i + 1 + ". " + specList.get(i));
        }
        System.out.println("\n");
        System.out.println("Input in this format: SPECIALIZATION_NUMBER");
        ui.readCommand();
        String specNumber = ui.fullCommand;
        int specChosen = Integer.parseInt(specNumber);

            if (specChosen < 0 || specChosen > specList.size()) {
                throw new DukeException("Specialization index does not exist.");
            }
            System.out.println("You have chosen " + specList.get(specChosen - 1) + ".");
            System.out.println("\n");

            if (specChosen == COMMS_NETWORKING_INDEX) {
                System.out.println("Breadth:");
                for (int i = 0; i < specMap.get("Communications & Networking").size(); i++) {
                    if (specMap.get("Communications & Networking").get(i).breadth) {
                        System.out.println((i + 1) + ". " + specMap.get("Communications & Networking").get(i).code);
                    }
                }
                System.out.println("Depth:");
                for (int j = 0; j < specMap.get("Communications & Networking").size(); j++) {
                    if (specMap.get("Communications & Networking").get(j).depth) {
                        System.out.println((j + 1) + ". " + specMap.get("Communications & Networking").get(j).code);
                    }
                }

                System.out.println("\n");
                System.out.println("You have completed:");
                if (completedEMap.isEmpty()) {
                    System.out.println("NIL");
                } else {
                    for (int i = 0; i < completedEMap.get(specList.get(specChosen - 1)).size(); i++) {
                        System.out.println(i + 1 + ". " + completedEMap.get(specList.get(specChosen - 1)).get(i));
                    }
                }
                System.out.println("\n");
                System.out.println("MCs completed:");
                if (completedEMap.isEmpty()) { //programme stops running when completed is empty, need to solve!
                    System.out.println("NIL");
                } else {
                    System.out.println(completedEMap.get(specList.get(specChosen - 1)).size() * 4 + "/20");
                }
            } else if (specChosen == EMBEDDED_COMPUTING_INDEX) {
                System.out.println("Breadth:");
                for (int i = 0; i < specMap.get("Embedded Computing").size(); i++) {
                    if (specMap.get("Embedded Computing").get(i).breadth) {
                        System.out.println((i + 1) + ". " + specMap.get("Embedded Computing").get(i).code);
                    }
                }

                System.out.println("Depth:");
                for (int j = 0; j < specMap.get("Embedded Computing").size(); j++) {
                    if (specMap.get("Embedded Computing").get(j).depth) {
                        System.out.println((j + 1) + ". " + specMap.get("Embedded Computing").get(j).code);
                    }
                }

                System.out.println("\n");
                System.out.println("You have completed:");
                if (completedEMap.isEmpty()) {
                    System.out.println("NIL");
                } else {
                    for (int i = 0; i < completedEMap.get(specList.get(specChosen - 1)).size(); i++) {
                        System.out.println(i + 1 + ". " + completedEMap.get(specList.get(specChosen - 1)).get(i));
                    }
                }
                System.out.println("\n");
                System.out.println("MCs completed:");
                if (completedEMap.isEmpty()) { //programme stops running when completed is empty, need to solve!
                    System.out.println("NIL");
                } else {
                    System.out.println(completedEMap.get(specList.get(specChosen - 1)).size() * 4 + "/20");
                }
            } else if (specChosen == INTELLIGENT_SYSTEM_INDEX) {
                System.out.println("Breadth:");
                for (int i = 0; i < specMap.get("Intelligent Systems").size(); i++) {
                    if (specMap.get("Intelligent Systems").get(i).breadth) {
                        System.out.println((i + 1) + ". " + specMap.get("Intelligent Systems").get(i).code);
                    }
                }

                System.out.println("Depth:");
                for (int j = 0; j < specMap.get("Intelligent Systems").size(); j++) {
                    if (specMap.get("Intelligent Systems").get(j).depth) {
                        System.out.println((j + 1) + ". " + specMap.get("Intelligent Systems").get(j).code);
                    }
                }

                System.out.println("\n");
                System.out.println("You have completed:");
                if (completedEMap.isEmpty()) {
                    System.out.println("NIL");
                } else {
                    for (int i = 0; i < completedEMap.get(specList.get(specChosen - 1)).size(); i++) {
                        System.out.println(i + 1 + ". " + completedEMap.get(specList.get(specChosen - 1)).get(i));
                    }
                }
                System.out.println("\n");
                System.out.println("MCs completed:");
                if (completedEMap.isEmpty()) { //programme stops running when completed is empty, need to solve!
                    System.out.println("NIL");
                } else {
                    System.out.println(completedEMap.get(specList.get(specChosen - 1)).size() * 4 + "/20");
                }
            } else if (specChosen == INTERACTIVE_DIGITAL_MEDIA_INDEX) {
                System.out.println("Breadth:");
                for (int i = 0; i < specMap.get("Interactive Digital Media").size(); i++) {
                    if (specMap.get("Interactive Digital Media").get(i).breadth) {
                        System.out.println((i + 1) + ". " + specMap.get("Interactive Digital Media").get(i).code);
                    }
                }

                System.out.println("Depth:");
                for (int j = 0; j < specMap.get("Interactive Digital Media").size(); j++) {
                    if (specMap.get("Interactive Digital Media").get(j).depth) {
                        System.out.println((j + 1) + ". " + specMap.get("Interactive Digital Media").get(j).code);
                    }
                }

                System.out.println("\n");
                System.out.println("You have completed:");
                if (completedEMap.isEmpty()) {
                    System.out.println("NIL");
                } else {
                    for (int i = 0; i < completedEMap.get(specList.get(specChosen - 1)).size(); i++) {
                        System.out.println(i + 1 + ". " + completedEMap.get(specList.get(specChosen - 1)).get(i));
                    }
                }
                System.out.println("\n");
                System.out.println("MCs completed:");
                if (completedEMap.isEmpty()) { //programme stops running when completed is empty, need to solve!
                    System.out.println("NIL");
                } else {
                    System.out.println(completedEMap.get(specList.get(specChosen - 1)).size() * 4 + "/20");
                }
            } else if (specChosen == LARGE_SCALE_COMPUTING_INDEX) {
                System.out.println("Breadth:");
                for (int i = 0; i < specMap.get("Large-Scale Computing").size(); i++) {
                    if (specMap.get("Large-Scale Computing").get(i).breadth) {
                        System.out.println((i + 1) + ". " + specMap.get("Large-Scale Computing").get(i).code);
                    }
                }

                System.out.println("Depth:");
                for (int j = 0; j < specMap.get("Large-Scale Computing").size(); j++) {
                    if (specMap.get("Large-Scale Computing").get(j).depth) {
                        System.out.println((j + 1) + ". " + specMap.get("Large-Scale Computing").get(j).code);
                    }
                }

                System.out.println("\n");
                System.out.println("You have completed:");
                if (completedEMap.isEmpty()) {
                    System.out.println("NIL");
                } else {
                    for (int i = 0; i < completedEMap.get(specList.get(specChosen - 1)).size(); i++) {
                        System.out.println(i + 1 + ". " + completedEMap.get(specList.get(specChosen - 1)).get(i));
                    }
                }
                System.out.println("\n");
                System.out.println("MCs completed:");
                if (completedEMap.isEmpty()) { //programme stops running when completed is empty, need to solve!
                    System.out.println("NIL");
                } else {
                    System.out.println(completedEMap.get(specList.get(specChosen - 1)).size() * 4 + "/20");
                }
            } else if (specChosen == SYS_DESIGN_INDEX) {
                System.out.println("Breadth:");
                for (int i = 0; i < specMap.get("System-On-A-Chip Design").size(); i++) {
                    if (specMap.get("System-On-A-Chip Design").get(i).breadth) {
                        System.out.println((i + 1) + ". " + specMap.get("System-On-A-Chip Design").get(i).code);
                    }
                }

                System.out.println("Depth:");
                for (int j = 0; j < specMap.get("System-On-A-Chip Design").size(); j++) {
                    if (specMap.get("System-On-A-Chip Design").get(j).depth) {
                        System.out.println((j + 1) + ". " + specMap.get("System-On-A-Chip Design").get(j).code);
                    }
                }

                System.out.println("\n");
                System.out.println("You have completed:");
                if (completedEMap.isEmpty()) {
                    System.out.println("NIL");
                } else {
                    for (int i = 0; i < completedEMap.get(specList.get(specChosen - 1)).size(); i++) {
                        System.out.println(i + 1 + ". " + completedEMap.get(specList.get(specChosen - 1)).get(i));
                    }
                }
                System.out.println("\n");
                System.out.println("MCs completed:");
                if (completedEMap.isEmpty()) { //programme stops running when completed is empty, need to solve!
                    System.out.println("NIL");
                } else {
                    System.out.println(completedEMap.get(specList.get(specChosen - 1)).size() * 4 + "/20");
                }
            }

            /** Storage */
            String toStoreCommsB = "";
            String toStoreCommsD = "";
            String toStoreEmbB = "";
            String toStoreEmbD = "";
            String toStoreIsB = "";
            String toStoreIsD = "";
            String toStoreIdmB = "";
            String toStoreIdmD = "";
            String toStorelsB = "";
            String toStorelsD = "";
            String toStoreSysChipB = "";
            String toStoreSysChipD = "";

            for (String key : specMap.keySet()) {

                if (key.equals("Communications & Networking")) {
                    for (int i = 0; i < specMap.get("Communications & Networking").size(); i++) {
                        if (specMap.get(key).get(i).breadth) {
                            toStoreCommsB = toStoreCommsB.concat("commsB|" + key + "|" + specMap.get(key).get(i).code +
                                    "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");

                        } else {
                            toStoreCommsD = toStoreCommsD.concat("commsD|" + key + "|" + specMap.get(key).get(i).code +
                                    "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");
                        }
                    }
                } else if (key.equals("Embedded Computing")) {
                    for (int i = 0; i < specMap.get("Embedded Computing").size(); i++) {
                        if (specMap.get(key).get(i).breadth) {
                            toStoreEmbB = toStoreEmbB.concat("embB|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");

                        } else {
                            toStoreEmbD = toStoreEmbD.concat("embD|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");
                        }
                    }

                } else if (key.equals("Intelligent Systems")) {
                    for (int i = 0; i < specMap.get("Intelligent Systems").size(); i++) {
                        if (specMap.get(key).get(i).breadth) {
                            toStoreIsB = toStoreIsB.concat("isB|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");

                        } else {
                            toStoreIsD = toStoreIsD.concat("isD|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");
                        }
                    }

                } else if (key.equals("Interactive Digital Media")) {
                    for (int i = 0; i < specMap.get("Interactive Digital Media").size(); i++) {
                        if (specMap.get(key).get(i).breadth) {
                            toStoreIdmB = toStoreIdmB.concat("idmB|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");

                        } else {
                            toStoreIdmD = toStoreIdmD.concat("idmD|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");
                        }
                    }

                } else if (key.equals("Large-Scale Computing")) {
                    for (int i = 0; i < specMap.get("Large-Scale Computing").size(); i++) {
                        if (specMap.get(key).get(i).breadth) {
                            toStorelsB = toStorelsB.concat("lsB|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");

                        } else {
                            toStorelsD = toStorelsD.concat("lsD|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");
                        }
                    }
                } else if (key.equals("System-On-A-Chip Design")) {
                    for (int i = 0; i < specMap.get("System-On-A-Chip Design").size(); i++) {
                        if (specMap.get(key).get(i).breadth) {
                            toStoreSysChipB = toStoreSysChipB.concat("sysChipB|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");

                        } else {
                            toStoreSysChipD = toStoreSysChipD.concat("sysChipD|" + key + "|" + specMap.get(key).get(i).code
                                    + "|b:" + specMap.get(key).get(i).breadth + "|d:"
                                    + specMap.get(key).get(i).depth + " ");
                        }
                    }

                }

            }
            String totalStoringOfSpec = toStoreCommsB + "\n" + toStoreCommsD + "\n" + toStoreEmbB + "\n" + toStoreEmbD + "\n"
                    + toStoreIsB + "\n" + toStoreIsD + "\n" + toStoreIdmB + "\n" + toStoreIdmD + toStorelsB + "\n" + toStorelsD + "\n" + toStoreSysChipB + "\n" + toStoreSysChipD;
            storage.specializationStorage(totalStoringOfSpec);
        } catch (DukeException e) {
            System.out.println(e.getMessage());
        }
    }

}
