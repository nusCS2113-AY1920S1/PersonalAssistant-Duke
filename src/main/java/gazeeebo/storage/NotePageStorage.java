package gazeeebo.storage;

import gazeeebo.notes.Assessment;
import gazeeebo.notes.GeneralNotePage;
import gazeeebo.notes.Module;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class NotePageStorage {
    public static void writeToGoalFile() throws IOException {
        FileWriter file = new FileWriter("data/note/note-page/goal.txt");
        file.write(GeneralNotePage.goal);
        file.close();
    }

    public static void readFromGoalFile() throws IOException {
        File data = new File("data/note/note-page/goal.txt");
        if (data.createNewFile()) {
            System.out.println("Existing goal.txt file does not exist.");
            System.out.println("I have created a new text file for you.");
        }
        assert !data.createNewFile() : "There is no goal.txt file to read from (NotePageStorage)";
        Scanner txtFile = new Scanner(data);
        if (txtFile.hasNextLine()) {
            GeneralNotePage.goal = txtFile.nextLine();
        }
    }

    public static void writeToModulesFile() throws IOException {
        FileWriter file = new FileWriter("data/note/note-page/modules.txt");
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

    public static void readFromModulesFile() throws IOException {
        File data = new File("data/note/note-page/modules.txt");
        if (data.createNewFile()) {
            System.out.println("Existing modules.txt file does not exist.");
            System.out.println("I have created a new text file for you.");
        }
        assert !data.createNewFile() : "There is no modules.txt file to read from (NotePageStorage)";
        Scanner txtFile = new Scanner(data);
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
