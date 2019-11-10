//@@author yueyuu

package gazeeebo.storage;

import gazeeebo.logger.LogCenter;
import gazeeebo.notes.Assessment;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class NotePageStorage {

    private static final Logger logger = Logger.getLogger(NotePageStorage.class.getName());

    private static final String FILE_GOAL = "goal.txt";
    private static final String FILE_MODULES = "modules.txt";

    /**
     * Write data to the goal file.
     */
    public static void writeToGoalFile() {
        LogCenter.setUpLogger(logger);
        try {
            FileWriter file = new FileWriter(FILE_GOAL);
            file.write(GeneralNotePage.goal);
            file.flush();
            file.close();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't write to goal file. Creating a new one...", e);
            File f = new File(FILE_GOAL);
            try {
                f.createNewFile();
            } catch (IOException a) {
                logger.log(Level.SEVERE, "Can't create a new goal file.", a);
            }
        }
    }

    /**
     * Reads data from the goal file.
     */
    public static void readFromGoalFile() {
        LogCenter.setUpLogger(logger);
        File f = new File(FILE_GOAL);
        try {
            Scanner txtFile = new Scanner(f);
            if (txtFile.hasNextLine()) {
                GeneralNotePage.goal = txtFile.nextLine();
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read from goal file. Creating a new one...", e);
            try {
                f.createNewFile();
            } catch (IOException a) {
                logger.log(Level.SEVERE, "Can't create a new goal file.", a);
            }
        }
    }

    /**
     * Writes data to the modules file.
     */
    public static void writeToModulesFile() {
        LogCenter.setUpLogger(logger);
        try {
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
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't write to modules file. Creating a new one...", e);
            File f = new File(FILE_GOAL);
            try {
                f.createNewFile();
            } catch (IOException a) {
                logger.log(Level.SEVERE, "Can't create a new modules file.", a);
            }
        }
    }

    /**
     * Reads data from the modules file.
     */
    public static void readFromModulesFile() {
        LogCenter.setUpLogger(logger);
        File f = new File(FILE_MODULES);
        try {
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
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Can't read from modules file. Creating a new one...", e);
            try {
                f.createNewFile();
            } catch (IOException a) {
                logger.log(Level.SEVERE, "Can't create a new modules file.", a);
            }
        }
    }

}
