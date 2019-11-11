package gazeeebo.storage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;
import java.util.stream.Collectors;

public class StudyAssistPageStorage {
    private String[] relativePathStudyPlanner
            = {"Study_Plan.txt", "/Study_Plan.txt"};
    private String relativePathStudyPlannerResource
            = "Study_Plan.txt";
    private String relativePathPrerequisiteResource
            = "Prerequisite.txt";

    /**
     * This method reads from Study_Plan.txt, get users' current module plan
     *
     * @return double ArrayList storing the table.
     * @throws IOException if reading went wrong.
     */
    public ArrayList<ArrayList<String>> readFromStudyPlanFile() throws IOException {
        ArrayList<ArrayList<String>> studyPlan = new ArrayList<ArrayList<String>>();
        File file = new File(relativePathStudyPlannerResource);
        Scanner sc = new Scanner(file);
        for (int i = 0; i < 8; i++) {
            if (sc.hasNext()) {
                String[] split = sc.nextLine().split(" ");
                ArrayList<String> temp = Arrays.stream(split).collect(Collectors.toCollection(ArrayList::new));
                studyPlan.add(temp);
            } else {
                ArrayList<String> temp = new ArrayList<String>();
                studyPlan.add(temp);
            }
        }
        // }
        return studyPlan;
    }

    /**
     * This method writes to Study_Plan.txt, updates changes in module plan.
     * @param fileContent String of content to be saved.
     * @throws IOException if the saving process goes wrong.
     */
    public void writeToStudyPlanFile(String fileContent) throws IOException {
        BufferedWriter fileWriter = new BufferedWriter(new FileWriter(relativePathStudyPlannerResource));
        fileWriter.write(fileContent);
        fileWriter.flush();
        fileWriter.close();
    }

    /**
     * This method reads from prerequisite txt file, gets information about courses' prerequisites.
     * @return a data structure containing information of prerequisites.
     * @throws IOException if reading went wrong.
     */
    public HashMap<String, ArrayList<String>> readFromPrerequisiteFile() throws IOException {
        HashMap<String, ArrayList<String>> prerequisiteList = new HashMap<String, ArrayList<String>>();
        File file = new File(relativePathPrerequisiteResource);
        Scanner sc = new Scanner(file);
        while (sc.hasNext()) {
            String wholeSentence = sc.nextLine();
            String head = wholeSentence.split(" ")[0];
            ArrayList<String> prerequisites = new ArrayList<String>();
            for (int i = 1; i < wholeSentence.split(" ").length; i++) {
                prerequisites.add(wholeSentence.split(" ")[i]);
            }
            prerequisiteList.put(head, prerequisites);
        }
        return prerequisiteList;
    }
}
