package gazeeebo.storage;

import gazeeebo.notes.Assessment;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class NotePageStorage {
    public static void writeToGoalFile() throws IOException {
        FileWriter file = new FileWriter("/goal.txt");
        file.write(GeneralNotePage.goal);
        file.close();
    }

    public static void readFromGoalFile() {
        InputStream inputStream = NoteStorage.class.getResourceAsStream("/goal.txt");
        Scanner txtFile = new Scanner(inputStream);
        if (txtFile.hasNextLine()) {
            GeneralNotePage.goal = txtFile.nextLine();
        }
    }

    public static void writeToModulesFile() throws IOException {
        FileWriter file = new FileWriter("/modules.txt");
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
        file.close();
    }

    public static void readFromModulesFile() {
        InputStream inputStream = NoteStorage.class.getResourceAsStream("/modules.txt");
        Scanner txtFile = new Scanner(inputStream);
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
