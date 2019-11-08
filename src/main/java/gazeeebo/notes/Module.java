//@@author yueyuu
package gazeeebo.notes;

import gazeeebo.UI.Ui;
import gazeeebo.exception.DukeException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Deals with all the commands related to the module notes.
 */
public class Module {
    public String name;
    public ArrayList<Assessment> assessments;
    public ArrayList<String> miscellaneousInfo;
    private static final String EMPTY_DESCRIPTION = "The description of the command cannot be empty.";

    /**
     * Constructor that creates a module given the module name.
     *
     * @param name of the module being created
     */
    public Module(String name) {
        this.name = name;
        this.assessments = new ArrayList<>();
        this.miscellaneousInfo = new ArrayList<>();
    }

    /**
     * Prints out the module's notes: name + assessments + miscellaneous information.
     */
    public void viewModule() {
        System.out.println("[ " + name + " ]");
        System.out.println("Assessments:");
        for (int i = 0; i < assessments.size(); i++) {
            System.out.println((i+1) + ". " + assessments.get(i).toString());
        }
        System.out.print("\n");
        System.out.println("Miscellaneous:");
        for (int i = 0; i < miscellaneousInfo.size(); i++) {
            System.out.println((i+1) + ". " + miscellaneousInfo.get(i));
        }
    }

    /**
     * Edits the name of the module to that specified by the user if the name is not already used.
     * The name of the module can be changed to the name that is currently being used for the module.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void editName(String newName) throws DukeException {
        //System.out.println("What do you want to edit the name to?");
        //ui.readCommand();
        if (newName.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        for (Module m: GeneralNotePage.modules) {
            if (m.name.equals(newName) && !newName.equals(this.name)) {
                System.out.println("You already have a module with the same name. Please use a different name.");
                return;
            }
        }
        name = newName;
        System.out.println("Okay we have successfully updated the module name to:");
        System.out.println(name);
    }

    //ASSESSMENT FEATURES-------------------------------------------------------------------------

    /**
     * Adds an assessment to the module being viewed/edited.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void addAssessment(String assmt) throws DukeException {
        //System.out.println("What assessment do you want to add?");
        //ui.readCommand();
        //String name = ui.fullCommand;
        //System.out.println("What is the weightage of the assessment?");
        if (assmt.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] assmtDetails = assmt.split(" /a", 2);
        try {
            if (assmtDetails[1].trim().isEmpty()) {
                throw new DukeException("Please input a weightage.");
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'add assmt /n ASSESSMENT_NAME" +
                    " /a ASSESSMENT_WEIGHTAGE\'.");
        }
        //boolean isInt = false;
        int percentage = -1;
        //do {
            //ui.readCommand();
            try {
                percentage = Integer.parseInt(assmtDetails[1].trim());
                if (percentage < 0) {
                    throw new DukeException("Please input a positive number.");
                }
            } catch (NumberFormatException n) {
                throw new DukeException("Please input a number.");
            }
        //} while (!isInt);
        Assessment newAssessment = new Assessment(assmtDetails[0], percentage);
        assessments.add(newAssessment);
        System.out.println("Okay we have successfully added this assessment:");
        System.out.println(newAssessment.toString());
    }

    /**
     * Edits the name of an existing assessment.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void editAssessmentName(String assmtDetails) throws DukeException {
        //System.out.println("Which assessment do you want to edit?");
        if (assmtDetails.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] details = assmtDetails.split(" /a", 2);
        try {
            if (details[1].trim().isEmpty()) {
                throw new DukeException(EMPTY_DESCRIPTION);
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'edit assmt /n ASSESSMENT_INDEX" +
                    " /a NEW_ASSESSMENT_NAME\'.");
        }
        String[] indexAndOldName = checkIfValidIndexAssmt(details[0].trim());
        int assmtNum = Integer.parseInt(indexAndOldName[0]);
        String oldName = indexAndOldName[1];
        assert oldName != null: "Bug in notes.Module: editAssessment: oldName";
        assert assmtNum != -1 : "Bug in notes.Module: editAssessment: assmtNum";
        //System.out.println("What do you want to change the name to?");
        //ui.readCommand();
        assessments.get(assmtNum).name = details[1].trim();
        System.out.println("Okay we have successfully changed the name of \"" + oldName + "\" to:");
        System.out.println(details[1].trim());
    }

    /**
     * Makes the user input a valid index in the assessments list.
     *
     * @param
     * @return String[] which contains the valid index and the name of the assessment that corresponds to that index
     * @throws IOException if the command input by the user cannot be read
     */
    private String[] checkIfValidIndexAssmt(String index) throws DukeException {
        int assmtNum;
        String assmtName;
        try {
            assmtNum = Integer.parseInt(index)-1;
            try {
                assmtName = assessments.get(assmtNum).toString();

            } catch (IndexOutOfBoundsException e) {
                throw new DukeException("Sorry there is no such index.");
            }
        } catch (NumberFormatException n) {
            throw new DukeException("Please input a number for the index.");
        }
        return new String[]{Integer.toString(assmtNum), assmtName};
    }

    /**
     * Edits the weightage of the assessment corresponding to the index specified by the user.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void editAssessmentWeightage(String assmtDetails) throws IOException, DukeException {
        if (assmtDetails.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] details = assmtDetails.split(" /a", 2);
        try {
            if (details[1].trim().isEmpty()) {
                throw new DukeException("Please input a weightage.");
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'edit weightage /n ASSESSMENT_INDEX" +
                    " /a NEW_ASSESSMENT_WEIGHTAGE\'.");
        }
        String[] indexAndOldName = checkIfValidIndexAssmt(details[0].trim());
        int assmtNum = Integer.parseInt(indexAndOldName[0]);
        String oldName = indexAndOldName[1];
        assert oldName != null: "Bug in notes.Module: editAssessment: oldName";
        assert assmtNum != -1 : "Bug in notes.Module: editAssessment: assmtNum";
        try {
            assessments.get(assmtNum).weightage = Integer.parseInt(details[1].trim());
            if (assessments.get(assmtNum).weightage < 0) {
                throw new DukeException("Please input a positive number for the weightage.");
            }
        } catch (NumberFormatException n) {
            throw new DukeException("Please input a number for the weightage.");
        }
        System.out.println("Okay we have successfully changed the weightage to:");
        System.out.println(details[1].trim());
    }

    /**
     * Deletes an assessment corresponding to the index specified by the user.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void deleteAssessment(String index) throws IOException, DukeException {
        if (index.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] indexAndAssmtToDelete = checkIfValidIndexAssmt(index);
        int assmtNum = Integer.parseInt(indexAndAssmtToDelete[0]);
        assert assmtNum != -1 : "Bug in notes.Module: deleteAssessment: assmtNum";
        assessments.remove(assmtNum);
        System.out.println("Okay we have successfully deleted this assessment:");
        System.out.println(indexAndAssmtToDelete[1]);
    }

    //MISCELLANEOUS INFORMATION FEATURES-----------------------------------------------------------------

    /**
     * Adds a miscellaneous information to the module being edited/viewed.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void addMiscellaneous(String msc) throws DukeException {
        //System.out.println("What miscellaneous information do you want to add?");
        //ui.readCommand();
        if (msc.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        miscellaneousInfo.add(msc);
        System.out.println("Okay we have successfully added this miscellaneous information:");
        System.out.println(msc);
    }

    /**
     * Makes the user input a valid index in the miscellaneous information list.
     *
     * @param
     * @return String[] which contains the valid index and the name of the miscellaneous information that corresponds to that index
     * @throws IOException if the command input by the user cannot be read
     */
    private String[] checkIfValidIndexMsc(String index) throws DukeException {
        int mscNum;
        String mscName;
        //boolean isValidIndex = false;
        //do {
            //ui.readCommand();
            try {
                mscNum = Integer.parseInt(index)-1;
                try {
                    mscName = miscellaneousInfo.get(mscNum);
                    //isValidIndex = true;
                } catch (IndexOutOfBoundsException e) {
                    throw new DukeException("Sorry there is no such index.");
                }
            } catch (NumberFormatException n) {
                throw new DukeException("Please input a number for the index.");
            }
        return new String[]{Integer.toString(mscNum), mscName};
    }

    /**
     * Edits a miscellaneous information corresponding to the index specified by the user.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void editMiscellaneous(String mscDetails) throws IOException, DukeException {
        //System.out.println("Which miscellaneous information do you want to edit?");
        //ui.readCommand();
        if (mscDetails.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] details = mscDetails.split(" /a", 2);
        try {
            if (details[1].trim().isEmpty()) {
                throw new DukeException("Please input a new miscellaneous information.");
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'edit msc /n MISCELLANEOUS_INDEX" +
                    " /a NEW_DESCRIPTION\'.");
        }
        String[] indexAndMscToEdit = checkIfValidIndexMsc(details[0].trim());
        int mscNum = Integer.parseInt(indexAndMscToEdit[0]);
        String mscToEdit = indexAndMscToEdit[1];
        assert mscToEdit != null: "Bug in notes.Module: editMiscellaneous: mscToEdit";
        assert mscNum != -1 : "Bug in notes.Module: editMiscellaneous: mscNum";
        //System.out.println("What do you want to change the miscellaneous information to?");
        //ui.readCommand();
        miscellaneousInfo.set(mscNum, details[1].trim());
        System.out.println("Okay we have successfully changed \"" + mscToEdit + "\" to:");
        System.out.println(details[1].trim());
    }

    /**
     * Deletes a miscellaneous information corresponding to the index specified by the user.
     *
     * @param
     * @throws IOException if the command input by the user cannot be read
     */
    public void deleteMiscellaneous(String index) throws DukeException {
        if (index.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] indexAndMscToDelete = checkIfValidIndexMsc(index);
        int mscNum = Integer.parseInt(indexAndMscToDelete[0]);
        String mscToDelete = indexAndMscToDelete[1];
        assert mscToDelete != null: "Bug in notes.Module: deleteMiscellaneous: mscToDelete";
        assert mscNum != -1 : "Bug in notes.Module: deleteMiscellaneous: mscNum";
        miscellaneousInfo.remove(mscNum);
        System.out.println("Okay we have successfully deleted this miscellaneous information:");
        System.out.println(mscToDelete);
    }

}
