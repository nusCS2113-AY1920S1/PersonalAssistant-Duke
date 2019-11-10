
package gazeeebo.storage;


import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.BufferedWriter;


import gazeeebo.commands.specialization.ModuleCategory;
import gazeeebo.tasks.Deadline;
import gazeeebo.tasks.DoAfter;
import gazeeebo.tasks.Event;
import gazeeebo.tasks.FixedDuration;
import gazeeebo.tasks.Task;

import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Storage {

    private String[] relativePath
            = {"Save.txt", "/Save.txt"};
    private String[] relativePathPassword
            = {"Password.txt", "/Password.txt"};
    private String[] relativePathContact
            = {"Contact.txt", "/Contact.txt"};
    private String[] relativePathExpenses
            = {"Expenses.txt", "/Expenses.txt"};
    private String[] relativePathPlaces
            = {"Places.txt", "/Places.txt"};
    private String[] relativePathTrivia
            = {"Trivia.txt", "/Trivia.txt"};
    private String[] relativePathCAP
            = {"CAP.txt", "/CAP.txt"};
    private String[] relativePathSpecialization
            = {"Specialization.txt", "/Specialization.txt"};
    private String[] relativePathStudyPlanner
            = {"Study_Plan.txt", "/Study_Plan.txt"};
    private String[] relativePathCompletedElectives
            = {"CompletedElectives.txt", "/CompletedElectives.txt"};
    private String[] relativePathPrerequisite
            = {"Prerequisite.txt", "/Prerequisite.txt"};
    private String[] getrelativePathGoal
            = {"goal.txt", "/goal.txt"};
    private String[] getrelativePathModule
            = {"modules.txt", "/modules.txt"};
    private String[] getrelativeNoteDaily
            = {"NoteDaily.txt", "/NoteDaily.txt"};
    private String[] getrelativeNoteWeekly
            = {"NoteMonthly.txt", "/NoteMonthly.txt"};
    private String[] getrelativeNoteMonthly
            = {"NoteWeekly.txt", "/NoteWeekly.txt"};

    private String relativePathPlacesResource
            = "Places.txt";

    private String relativePathTriviaResource
            = "Trivia.txt";
    private String relativePathStudyPlannerResource
            = "Study_Plan.txt";
    private String relativePathPrerequisiteResource
            = "Prerequisite.txt";
    //@@author jessteoxizhi

    /**
     * Check if there are save txt file in the directory, if there is not, create a new txt file and copy
     * preloaded data into the new txt file
     *
     * @throws IOException exception when there is an error read the txt file
     */
    public void startUp() throws IOException {
        ArrayList<String[]> resourcelist = new ArrayList<>();
        resourcelist.add(relativePath);
        resourcelist.add(relativePathPassword);
        resourcelist.add(relativePathContact);
        resourcelist.add(relativePathExpenses);
        resourcelist.add(relativePathExpenses);
        resourcelist.add(relativePathPlaces);
        resourcelist.add(relativePathTrivia);
        resourcelist.add(relativePathCAP);
        resourcelist.add(relativePathSpecialization);
        resourcelist.add(relativePathStudyPlanner);
        resourcelist.add(relativePathCompletedElectives);
        resourcelist.add(relativePathPrerequisite);
        resourcelist.add(getrelativePathGoal);
        resourcelist.add(getrelativeNoteDaily);
        resourcelist.add(getrelativeNoteWeekly);
        resourcelist.add(getrelativeNoteMonthly);
        resourcelist.add(getrelativePathModule);
        for (String[] path : resourcelist) {
            File tmpDir = new File(path[0]);
            boolean exists = tmpDir.exists();
            if (!exists) {
                InputStream inputStream
                        = Storage.class.getResourceAsStream(path[1]);
                Scanner sc = new Scanner(inputStream);
                FileWriter fw = new FileWriter(path[0], true);
                String s;
                while (sc.hasNext()) {
                    s = sc.nextLine() + "\n"; // read a line
                    fw.write(s); // write to output file
                    fw.flush();
                }
                sc.close();
                fw.close();
            }
        }
    }
}