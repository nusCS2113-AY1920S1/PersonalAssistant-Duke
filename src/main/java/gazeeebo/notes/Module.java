//@@author yueyuu

package gazeeebo.notes;

import gazeeebo.exception.DukeException;

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
            System.out.println((i + 1) + ". " + assessments.get(i).toString());
        }
        System.out.print("\n");
        System.out.println("Miscellaneous:");
        for (int i = 0; i < miscellaneousInfo.size(); i++) {
            System.out.println((i + 1) + ". " + miscellaneousInfo.get(i));
        }
    }

    /**
     * Edits the name of the module to that specified by the user if the name is not already used.
     * The name of the module can be changed to the name that is currently being used for the module.
     *
     * @param newName the new name the user wants to edit the existing name of the module to
     * @throws DukeException if the command's description is empty
     */
    public void editName(String newName) throws DukeException {
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
     * @param assmt contains the name and weightage of the assessment
     * @throws DukeException if the command's description is empty
     */
    public void addAssessment(String assmt) throws DukeException {
        if (assmt.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] assmtDetails = assmt.split(" /a", 2);
        try {
            if (assmtDetails[1].trim().isEmpty()) {
                throw new DukeException("Please input a weightage.");
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'add assmt /n NAME /a WEIGHTAGE\'.");
        }
        int percentage;
        try {
            percentage = Integer.parseInt(assmtDetails[1].trim());
            if (percentage < 0) {
                throw new DukeException("Please input a positive number for the weightage.");
            }
        } catch (NumberFormatException n) {
            throw new DukeException("Please input a number for the weightage.");
        }
        Assessment newAssessment = new Assessment(assmtDetails[0], percentage);
        assessments.add(newAssessment);
        System.out.println("Okay we have successfully added this assessment:");
        System.out.println(newAssessment.toString());
    }

    /**
     * Edits the name of an existing assessment.
     *
     * @param assmtDetails contains the index and the new assessment name
     * @throws DukeException if the command's description is empty or the index is invalid
     */
    public void editAssessmentName(String assmtDetails) throws DukeException {
        if (assmtDetails.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] details = assmtDetails.split(" /a", 2);
        try {
            if (details[1].trim().isEmpty()) {
                throw new DukeException(EMPTY_DESCRIPTION);
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'edit assmt /n INDEX /a NEW_NAME\'.");
        }
        String[] indexAndOldName = checkIfValidIndexAssmt(details[0].trim());
        int assmtNum = Integer.parseInt(indexAndOldName[0]);
        String oldName = indexAndOldName[1];
        assert oldName != null : "Bug in notes.Module: editAssessment: oldName";
        assert assmtNum != -1 : "Bug in notes.Module: editAssessment: assmtNum";
        assessments.get(assmtNum).name = details[1].trim();
        System.out.println("Okay we have successfully changed the name of \"" + oldName + "\" to:");
        System.out.println(details[1].trim());
    }

    /**
     * Checks if the index specified is in the assessments list.
     *
     * @param index the index to be checked
     * @return String[] which contains the valid index and the name of the assessment that corresponds to that index
     * @throws DukeException if the index is invalid
     */
    private String[] checkIfValidIndexAssmt(String index) throws DukeException {
        int assmtNum;
        String assmtName;
        try {
            assmtNum = Integer.parseInt(index) - 1;
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
     * @param assmtDetails contains the index and the new weightage
     * @throws DukeException if the command's description is empty or if the index is invalid
     */
    public void editAssessmentWeightage(String assmtDetails) throws DukeException {
        if (assmtDetails.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] details = assmtDetails.split(" /a", 2);
        try {
            if (details[1].trim().isEmpty()) {
                throw new DukeException("Please input a weightage.");
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format"
                    + " \'edit weight /n INDEX /a NEW_WEIGHTAGE\'.");
        }
        String[] indexAndOldName = checkIfValidIndexAssmt(details[0].trim());
        int assmtNum = Integer.parseInt(indexAndOldName[0]);
        String oldName = indexAndOldName[1];
        assert oldName != null : "Bug in notes.Module: editAssessment: oldName";
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
     * @param index the index of the assessment to be deleted
     * @throws DukeException if the command's description is empty or the index is invalid
     */
    public void deleteAssessment(String index) throws DukeException {
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
     * @param msc the miscellaneous information to be added
     * @throws DukeException if the command's description is empty
     */
    public void addMiscellaneous(String msc) throws DukeException {
        if (msc.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        miscellaneousInfo.add(msc);
        System.out.println("Okay we have successfully added this miscellaneous information:");
        System.out.println(msc);
    }

    /**
     * Checks if the index specified is a valid index in the miscellaneous information list.
     *
     * @param index the index to be checked
     * @return String[] which contains the valid index and the name of the miscellaneous information
     *     that corresponds to that index
     * @throws DukeException if the index is invalid
     */
    private String[] checkIfValidIndexMsc(String index) throws DukeException {
        int mscNum;
        String mscName;
        try {
            mscNum = Integer.parseInt(index) - 1;
            try {
                mscName = miscellaneousInfo.get(mscNum);
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
     * @param mscDetails contains the index and updated miscellaneous information
     * @throws DukeException if the command's description is empty or the index is invalid
     */
    public void editMiscellaneous(String mscDetails) throws DukeException {
        if (mscDetails.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] details = mscDetails.split(" /a", 2);
        try {
            if (details[1].trim().isEmpty()) {
                throw new DukeException("Please input a new miscellaneous information.");
            }
        } catch (IndexOutOfBoundsException i) {
            throw new DukeException("Please input the command in the format \'edit msc /n INDEX /a NEW_DESCRIPTION\'.");
        }
        String[] indexAndMscToEdit = checkIfValidIndexMsc(details[0].trim());
        int mscNum = Integer.parseInt(indexAndMscToEdit[0]);
        String mscToEdit = indexAndMscToEdit[1];
        assert mscToEdit != null : "Bug in notes.Module: editMiscellaneous: mscToEdit";
        assert mscNum != -1 : "Bug in notes.Module: editMiscellaneous: mscNum";
        miscellaneousInfo.set(mscNum, details[1].trim());
        System.out.println("Okay we have successfully changed \"" + mscToEdit + "\" to:");
        System.out.println(details[1].trim());
    }

    /**
     * Deletes a miscellaneous information corresponding to the index specified by the user.
     *
     * @param index the index of the miscellaneous information to be deleted
     * @throws DukeException if the command's description is empty or the index is invalid
     */
    public void deleteMiscellaneous(String index) throws DukeException {
        if (index.isEmpty()) {
            throw new DukeException(EMPTY_DESCRIPTION);
        }
        String[] indexAndMscToDelete = checkIfValidIndexMsc(index);
        int mscNum = Integer.parseInt(indexAndMscToDelete[0]);
        String mscToDelete = indexAndMscToDelete[1];
        assert mscToDelete != null : "Bug in notes.Module: deleteMiscellaneous: mscToDelete";
        assert mscNum != -1 : "Bug in notes.Module: deleteMiscellaneous: mscNum";
        miscellaneousInfo.remove(mscNum);
        System.out.println("Okay we have successfully deleted this miscellaneous information:");
        System.out.println(mscToDelete);
    }

}
