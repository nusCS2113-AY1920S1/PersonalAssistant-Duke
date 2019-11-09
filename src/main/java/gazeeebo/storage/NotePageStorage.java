//@@author yueyuu

package gazeeebo.storage;

import gazeeebo.notes.Assessment;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NotePageStorage {

    private static final String FILE_GOAL = "goal.txt";
    private static final String FILE_MODULES = "modules.txt";

    /**
     * Write data to the goal file.
     * @throws IOException if the file cannot be written to
     */
    public static void writeToGoalFile() throws IOException {
        FileWriter file = new FileWriter(FILE_GOAL);
        file.write(GeneralNotePage.goal);
        file.flush();
        file.close();
    }

    /**
     * Reads data from the goal file.
     * @throws IOException if the file cannot be read
     */
    public static void readFromGoalFile() throws IOException {
        File f = new File(FILE_GOAL);
        Scanner txtFile = new Scanner(f);
        if (txtFile.hasNextLine()) {
            GeneralNotePage.goal = txtFile.nextLine();
        }
    }

    /**
     * Writes data to the modules file.
     * @throws IOException if the file cannot be written to
     */
    public static void writeToModulesFile() throws IOException {
        FileWriter file = new FileWriter(FILE_MODULES);
        for (Module m : GeneralNotePage.modules) {
            file.write(m.name + "\n");
            file.write(m.assessments.size() + "\n");
            for (Assessment a : m.assessments) {
                file.write(a.name + "\n");
                file.write(a.weightage + "\n");
            }
            file.write(m.miscellaneousInfo.size() + "\n");
            for (String s : m.miscellaneousInfo) {
                file.write(s + "\n");
            }
        }
        file.flush();
        file.close();
    }

    /**
     * Reads data from the modules file.
     * @throws IOException if the file cannot be read
     */
    public static void readFromModulesFile() throws IOException {
        File f = new File(FILE_MODULES);
        Scanner txtFile = new Scanner(f);
        while (txtFile.hasNextLine()) {
            Module m = new Module(txtFile.nextLine()); //read in module name
            int numOfAssmt = Integer.parseInt(txtFile.nextLine());
            for (int i = 0; i < numOfAssmt; i++) {
                m.assessments.add(new Assessment(txtFile.nextLine(), Integer.parseInt(txtFile.nextLine())));
            }
            int numOfMsc = Integer.parseInt(txtFile.nextLine());
            for (int j = 0; j < numOfMsc; j++) {
                m.miscellaneousInfo.add(txtFile.nextLine());
            }
            GeneralNotePage.modules.add(m);
        }
    }
}
