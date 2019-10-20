package gazeeebo.notes;

import gazeeebo.UI.Ui;

import java.io.IOException;
import java.util.ArrayList;

public class Module {
    public String name;
    public ArrayList<Assessment> assessments;
    public ArrayList<String> miscellaneousInfo;

    public Module(String name) {
        this.name = name;
        this.assessments = new ArrayList<>();
        this.miscellaneousInfo = new ArrayList<>();
    }

    public void viewModule() {
        System.out.println("[ " + name + " ]");
        System.out.println("Assessments:");
        for (int i = 0; i < assessments.size(); i++) {
            System.out.println((i+1) + ". " + assessments.get(i).toString());
        }
        System.out.println("\n");
        System.out.println("Miscellaneous:");
        for (int i = 0; i < miscellaneousInfo.size(); i++) {
            System.out.println((i+1) + ". " + miscellaneousInfo.get(i));
        }
    }
    public void editName(Ui ui) throws IOException {
        System.out.println("What do you want to edit the name to?");
        ui.readCommand();
        name = ui.fullCommand;
        System.out.println("Okay we have successfully updated the module name to:");
        System.out.println(name);
    }

    public void addAssessment(Ui ui) throws IOException {
        System.out.println("What assessment do you want to add?");
        ui.readCommand();
        String name = ui.fullCommand;
        System.out.println("What is the weightage of the assessment?");
        boolean isInt = false;
        int percentage = -1;
        do {
            ui.readCommand();
            try {
                percentage = Integer.parseInt(ui.fullCommand);
                if (percentage >= 0) {
                    isInt = true;
                } else {
                    System.out.println("Please input a positive number.");
                }
            } catch (NumberFormatException n) {
                System.out.println("Please input a number.");
            }
        } while (!isInt);
        Assessment newAssessment = new Assessment(name, percentage);
        assessments.add(newAssessment);
        System.out.println("Okay we have successfully added this assessment:");
        System.out.println(newAssessment.toString());
    }

    public void editAssessment(Ui ui) throws IOException {
        System.out.println("Which assessment do you want to edit?");
        ui.readCommand();

        System.out.println("What is the new assessment name?");
        ui.readCommand();

    }

}
