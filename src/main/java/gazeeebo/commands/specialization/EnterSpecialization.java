package gazeeebo.commands.specialization;
import gazeeebo.UI.Ui;
import gazeeebo.storage.Storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class EnterSpecialization {
    final static int COMMS_NETWORKING_INDEX = 1;
    final static int EMBEDDED_COMPUTING_INDEX = 2;
    final static int INTELLIGENT_SYSTES_INDEX = 3;
    final static int INTERACTIVE_DIGITAL_MEDIA_INDEX = 4;
    final static int LARGE_SCALE_COMPUTING_INDEX = 5;
    final static int SYS_DESIGN_INDEX = 6;

    public EnterSpecialization(Ui ui, Storage storage,  HashMap<String, ArrayList<moduleCategories>> specMap) throws IOException {
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
        System.out.println("\n Input in this format: SPECIALIZATION_NUMBER");
        ui.readCommand();
        String specNumber = ui.fullCommand;
        int specChosen = Integer.parseInt(specNumber);

        System.out.println("You have chosen " + specList.get(specChosen - 1) + ".");

        HashMap<String, ArrayList<moduleCategories>> collectionOfSpecAndMods = new HashMap<String, ArrayList<moduleCategories>>();
        //moduleCategories contain code, bool status, d and b
        ArrayList<moduleCategories> modAndBool = new ArrayList<>();

        if (specChosen == COMMS_NETWORKING_INDEX) {
            String CS1231 = "CS1231";
            moduleCategories mC = new moduleCategories(CS1231);
            mC.breadth = true;
            modAndBool.add(mC);
            String CS4334 = "CS4334";
            moduleCategories mC2 = new moduleCategories(CS4334);
            mC2.depth = true;
            modAndBool.add(mC2);

            collectionOfSpecAndMods.put(specList.get(specChosen - 1), modAndBool);

            System.out.println("Breadth:");
            for (int i = 0; i < modAndBool.size(); i++) {
                if (modAndBool.get(i).breadth) {
                    System.out.println((i + 1) + ". " + modAndBool.get(i).getStatusIcon() + " " + modAndBool.get(i).code);
                } else {
                    System.out.println("Depth:");
                    for (int j = 0; j < modAndBool.size(); j++) {

                        if (modAndBool.get(j).depth) {
                            System.out.println((j + 1) + ". " + modAndBool.get(j).getStatusIcon() + " " + modAndBool.get(j).code);
                        }
                    }
                }

            }
        } else if (specChosen == EMBEDDED_COMPUTING_INDEX) {
            //   breadth = storage.readfromindex1file();
            String CS1231 = "CS1231";
            moduleCategories mC3 = new moduleCategories(CS1231);
            mC3.breadth = true;
            modAndBool.add(mC3);
            String CS4324 = "CS4324";
            moduleCategories mC4 = new moduleCategories(CS4324);
            mC4.depth = true;
            modAndBool.add(mC4);

            collectionOfSpecAndMods.put(specList.get(specChosen - 1), modAndBool);
            System.out.println("Breadth:");
            for (int i = 0; i < modAndBool.size(); i++) {
                if (modAndBool.get(i).breadth) {
                    System.out.println((i + 1) + ". " + modAndBool.get(i).getStatusIcon() + " " + modAndBool.get(i).code);
                } else {
                    System.out.println("Depth:");
                    for (int j = 0; j < modAndBool.size(); j++) {

                        if (modAndBool.get(j).depth) {
                            System.out.println((j + 1) + ". " + modAndBool.get(j).getStatusIcon() + " " + modAndBool.get(j).code);
                        }
                    }
                }

            }
        }

        String toStoreCommsB = "";
        String toStoreCommsD = "";
        String toStoreEmbB = "";
        String toStoreEmbD = "";

        for (String key : collectionOfSpecAndMods.keySet()) {

            switch (key) {
                case "Communications & Networking":
                    for (int i = 0; i < modAndBool.size(); i++) {
                        if (collectionOfSpecAndMods.get(key).get(i).breadth) {
                            toStoreCommsB = toStoreCommsB.concat("commsB|" + key + "|" + collectionOfSpecAndMods.get(key).get(i).code + "|"
                                    + collectionOfSpecAndMods.get(key).get(i).getStatusIcon() + "|b:" + collectionOfSpecAndMods.get(key).get(i).breadth + "|d:"
                                    + collectionOfSpecAndMods.get(key).get(i).depth);

                        } else {
                            toStoreCommsD = toStoreCommsD.concat("commsD|" + key + "|" + collectionOfSpecAndMods.get(key).get(i).code + "|"
                                    + collectionOfSpecAndMods.get(key).get(i).getStatusIcon() + "|b:" + collectionOfSpecAndMods.get(key).get(i).breadth + "|d:"
                                    + collectionOfSpecAndMods.get(key).get(i).depth);
                        }
                    }

                case "Embedded Computing":
                    for (int i = 0; i < modAndBool.size(); i++) {
                        if (collectionOfSpecAndMods.get(key).get(i).breadth) {
                            toStoreEmbB = toStoreEmbB.concat("embB|" + key + "|" + collectionOfSpecAndMods.get(key).get(i).code + "|"
                                    + collectionOfSpecAndMods.get(key).get(i).getStatusIcon() + "|b:" + collectionOfSpecAndMods.get(key).get(i).breadth + "|d:"
                                    + collectionOfSpecAndMods.get(key).get(i).depth);

                        } else {
                            toStoreEmbD = toStoreEmbD.concat("embD|" + key + "|" + collectionOfSpecAndMods.get(key).get(i).code + "|"
                                    + collectionOfSpecAndMods.get(key).get(i).getStatusIcon() + "|b:" + collectionOfSpecAndMods.get(key).get(i).breadth + "|d:"
                                    + collectionOfSpecAndMods.get(key).get(i).depth);
                        }
                    }

            }
        }
        String totalStoringOfSpec = toStoreCommsB + "\n" + toStoreCommsD + "\n" + toStoreEmbB + "\n" + toStoreEmbD;
        storage.specializationStorage(totalStoringOfSpec);

    }

}
