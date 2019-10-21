package oof.command;

import java.time.DayOfWeek;

import oof.Storage;
import oof.Ui;
import oof.exception.OofException;
import oof.model.module.Assessment;
import oof.model.module.Lesson;
import oof.model.module.Module;
import oof.model.module.Semester;
import oof.model.module.SemesterList;
import oof.model.task.Assignment;
import oof.model.task.TaskList;

/**
 * Represents a Command to open Semester Menu.
 */
public class SemesterCommand extends Command {

    private static final int RESPONSE_MENU_VIEW = 1;
    private static final int RESPONSE_MENU_ADD = 2;
    private static final int RESPONSE_MENU_EDIT = 3;
    private static final int RESPONSE_MENU_REMOVE = 4;
    private static final int RESPONSE_MENU_QUIT = 5;
    private static final int RESPONSE_MENU_BACK = 5;
    private static final int RESPONSE_EDIT_ACADEMIC_YEAR = 1;
    private static final int RESPONSE_EDIT_SEMESTER_NAME = 2;
    private static final int RESPONSE_EDIT_START_DATE = 3;
    private static final int RESPONSE_EDIT_END_DATE = 4;
    private static final int RESPONSE_EDIT_BACK = 5;
    private static final int RESPONSE_MODULE_ADD_LESSON = 1;
    private static final int RESPONSE_MODULE_ADD_ASSIGNMENT = 2;
    private static final int RESPONSE_MODULE_ADD_ASSESSMENT = 3;
    private static final int RESPONSE_MODULE_REMOVE_LESSON = 4;
    private static final int RESPONSE_MODULE_REMOVE_ASSIGNMENT = 5;
    private static final int RESPONSE_MODULE_REMOVE_ASSESSMENT = 6;
    private static final int RESPONSE_MODULE_BACK = 7;
    private static final int RESPONSE_MODULE_EDIT_CODE = 1;
    private static final int RESPONSE_MODULE_EDIT_NAME = 2;
    private static final int RESPONSE_MODULE_EDIT_BACK = 3;

    /**
     * Default Constructor for SemesterCommand.
     */
    public SemesterCommand() {
        super();
    }

    /**
     * Opens menu to view, add, edit or remove Semesters.
     *
     * @param semesterList Instance of SemesterList that stores Semester objects.
     * @param tasks        Instance of TaskList that stores Task objects.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @param storage      Instance of Storage that enables the reading and writing of Task
     *                     objects to hard disk.
     * @throws OofException if semesterList or moduleList is empty.
     */
    @Override
    public void execute(SemesterList semesterList, TaskList tasks, Ui ui, Storage storage) {
        boolean isExit = false;
        while (!isExit) {
            int semesterMenuOption = ui.scanSemesterMenuOption();
            switch (semesterMenuOption) {
            case RESPONSE_MENU_VIEW:
                try {
                    viewSemesterList(semesterList, ui);
                } catch (OofException e) {
                    ui.printOofException(e);
                }
                break;
            case RESPONSE_MENU_ADD:
                addSemester(semesterList, ui);
                break;
            case RESPONSE_MENU_EDIT:
                try {
                    editSemester(semesterList, ui);
                } catch (OofException e) {
                    ui.printOofException(e);
                }
                break;
            case RESPONSE_MENU_REMOVE:
                removeSemester(semesterList, ui);
                break;
            case RESPONSE_MENU_QUIT:
                isExit = true;
                break;
            default:
                break;
            }
        }
    }

    /**
     * Prints menu containing list of Semesters.
     *
     * @param semesterList Instance of SemesterList Object containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @throws OofException if semesterList is empty.
     */
    private void viewSemesterList(SemesterList semesterList, Ui ui) throws OofException {
        if (semesterList.getSemesterList().isEmpty()) {
            throw new OofException("Semester List is empty!");
        }
        boolean isExit = false;
        while (!isExit) {
            int viewSemesterOption = ui.scanSemesterOption(semesterList);
            if (viewSemesterOption != semesterList.getSize()) {
                viewModuleMenu(semesterList.getSemester(viewSemesterOption), ui);
            } else {
                isExit = true;
            }
        }
    }

    /**
     * Opens menu to view, add, edit or remove Modules.
     *
     * @param semester Instance of Semester containing list of Modules.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     */
    private void viewModuleMenu(Semester semester, Ui ui) {
        boolean isExit = false;
        while (!isExit) {
            int viewModuleMenuOption = ui.scanModuleMenuOption(semester);
            switch (viewModuleMenuOption) {
            case RESPONSE_MENU_VIEW:
                try {
                    viewModuleList(semester, ui);
                } catch (OofException e) {
                    ui.printOofException(e);
                }
                break;
            case RESPONSE_MENU_ADD:
                addModule(semester, ui);
                break;
            case RESPONSE_MENU_EDIT:
                try {
                    editModule(semester, ui);
                } catch (OofException e) {
                    ui.printOofException(e);
                }
                break;
            case RESPONSE_MENU_REMOVE:
                removeModule(semester, ui);
                break;
            case RESPONSE_MENU_BACK:
                isExit = true;
                break;
            default:
                break;
            }
        }
    }

    /**
     * Opens menu to add or remove Lessons, Assignments or Assessments for current Module.
     *
     * @param module Instance of Module that contains list of Lessons, Assignments and Assessments.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void viewModule(Module module, Ui ui) {
        boolean isExit = false;
        while (!isExit) {
            int viewModuleOption = ui.scanViewModuleOption(module);
            switch (viewModuleOption) {
            case RESPONSE_MODULE_ADD_LESSON:
                addLesson(module, ui);
                break;
            case RESPONSE_MODULE_ADD_ASSIGNMENT:
                addAssignment(module, ui);
                break;
            case RESPONSE_MODULE_ADD_ASSESSMENT:
                addAssessment(module, ui);
                break;
            case RESPONSE_MODULE_REMOVE_LESSON:
                removeLesson(module, ui);
                break;
            case RESPONSE_MODULE_REMOVE_ASSIGNMENT:
                removeAssignment(module, ui);
                break;
            case RESPONSE_MODULE_REMOVE_ASSESSMENT:
                removeAssessment(module, ui);
                break;
            case RESPONSE_MODULE_BACK:
                isExit = true;
                break;
            default:
                break;
            }
        }
    }

    /**
     * Prints menu containing list of Modules.
     *
     * @param semester Instance of Semester Object containing list of Modules.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @throws OofException if module List is empty.
     */
    private void viewModuleList(Semester semester, Ui ui) throws OofException {
        if (semester.getModules().isEmpty()) {
            throw new OofException("Module List is empty!");
        }
        int viewModuleOption = ui.scanModuleOption(semester);
        if (viewModuleOption == semester.getModules().size() + 1) {
            return;
        }
        viewModule(semester.getModules().get(viewModuleOption), ui);
    }

    /**
     * Adds a Lesson object to a Module.
     *
     * @param module Instance of Module object containing list of Lessons.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void addLesson(Module module, Ui ui) {
        String lessonName = ui.scanLessonName();
        String location = ui.scanLocation();
        DayOfWeek day = ui.scanDay();
        String startTime = ui.scanStartTime();
        String endTime = ui.scanEndTime();
        Lesson lesson = new Lesson(lessonName, location, day, startTime, endTime);
        module.getLessons().add(lesson);
        ui.printLessonAddedMessage(module.getModuleCode(), lesson);
    }

    /**
     * Adds a Assignment object to a Module.
     *
     * @param module Instance of Module object containing list of Assignments.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void addAssignment(Module module, Ui ui) {
        String assignmentName = ui.scanAssignmentName();
        String deadline = ui.scanAssignmentDeadline();
        Assignment assignment = new Assignment(module.getModuleCode(), assignmentName, deadline);
        module.getAssignments().add(assignment);
        ui.printAssignmentAddedMessage(assignment);
    }

    /**
     * Adds a Assessment object to a Module.
     *
     * @param module Instance of Module object containing list of Assessments.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void addAssessment(Module module, Ui ui) {
        String assessmentName = ui.scanAssessmentName();
        String date = ui.scanAssessmentDate();
        String startTime = ui.scanStartTime();
        String endTime = ui.scanEndTime();
        Assessment assessment = new Assessment(module.getModuleCode(), assessmentName, date, startTime, endTime);
        module.getAssessments().add(assessment);
        ui.printAssessmentAddedMessage(assessment);
    }

    /**
     * Adds a Semester object to a SemesterList.
     *
     * @param semesterList Instance of SemesterList object containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     */
    public void addSemester(SemesterList semesterList, Ui ui) {
        String academicYear = ui.scanAcademicYear();
        String semesterName = ui.scanSemesterName();
        String startDate = ui.scanSemesterStartDate();
        String endDate = ui.scanSemesterEndDate();
        Semester semester = new Semester(academicYear, semesterName, startDate, endDate);
        semesterList.addSemester(semester);
        ui.printSemesterAddedMessage(semester);
    }

    /**
     * Adds a Module object to a Semester.
     *
     * @param semester Instance of Semester object containing list of Modules.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     */
    private void addModule(Semester semester, Ui ui) {
        String moduleCode = ui.scanModuleCode();
        String moduleName = ui.scanModuleName();
        Module module = new Module(moduleCode, moduleName);
        semester.addModule(module);
        ui.printModuleAddedMessage(module);
    }

    /**
     * Opens menu to edit a Semester.
     *
     * @param semesterList Instance of SemesterList containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     * @throws OofException if semesterList is empty.
     */
    public void editSemester(SemesterList semesterList, Ui ui) throws OofException {
        if (semesterList.getSize() == 0) {
            throw new OofException("Semester List is empty!");
        }
        boolean isExit = false;
        while (!isExit) {
            int semesterOption = ui.scanSemesterOption(semesterList);
            if (semesterOption == semesterList.getSize()) {
                break;
            }
            int semesterEditOption = ui.scanSemesterEditOption(semesterList.getSemester(semesterOption));
            switch (semesterEditOption) {
            case RESPONSE_EDIT_ACADEMIC_YEAR:
                String academicYear = ui.scanAcademicYear();
                semesterList.getSemester(semesterOption).setAcademicYear(academicYear);
                break;
            case RESPONSE_EDIT_SEMESTER_NAME:
                String semesterName = ui.scanSemesterName();
                semesterList.getSemester(semesterOption).setSemesterName(semesterName);
                break;
            case RESPONSE_EDIT_START_DATE:
                String startDate = ui.scanSemesterStartDate();
                semesterList.getSemester(semesterOption).setStartDate(startDate);
                break;
            case RESPONSE_EDIT_END_DATE:
                String endDate = ui.scanSemesterEndDate();
                semesterList.getSemester(semesterOption).setEndDate(endDate);
                break;
            case RESPONSE_EDIT_BACK:
                isExit = true;
                break;
            default:
                break;
            }
        }
    }

    /**
     * Opens menu to edit a Module.
     *
     * @param semester Instance of Semester containing list of Modules.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     * @throws OofException if semesterList is empty.
     */
    private void editModule(Semester semester, Ui ui) throws OofException {
        if (semester.getModules().isEmpty()) {
            throw new OofException("Module List is empty!");
        }
        boolean isExit = false;
        while (!isExit) {
            int moduleOption = ui.scanModuleOption(semester);
            if (moduleOption == semester.getModules().size()) {
                return;
            }
            int moduleEditOption = ui.scanModuleEditOption(semester.getModules().get(moduleOption));
            switch (moduleEditOption) {
            case RESPONSE_MODULE_EDIT_CODE:
                String moduleCode = ui.scanModuleCode();
                semester.getModules().get(moduleOption).setModuleCode(moduleCode);
                break;
            case RESPONSE_MODULE_EDIT_NAME:
                String moduleName = ui.scanModuleName();
                semester.getModules().get(moduleOption).setModuleName(moduleName);
                break;
            case RESPONSE_MODULE_EDIT_BACK:
                isExit = true;
                break;
            default:
                break;
            }
        }
    }

    /**
     * Removes a Semester object from a SemesterList.
     *
     * @param semesterList Instance of SemesterList containing list of Semesters.
     * @param ui           Instance of Ui that is responsible for visual feedback.
     */
    private void removeSemester(SemesterList semesterList, Ui ui) {
        int index = ui.scanSemesterOption(semesterList);
        if (index == semesterList.getSize()) {
            return;
        }
        ui.printSemesterRemovalMessage(semesterList.getSemester(index));
        semesterList.getSemesterList().remove(index);
    }

    /**
     * Removes a Module object from a semester.
     *
     * @param semester Instance of Semester containing list of Modules.
     * @param ui       Instance of Ui that is responsible for visual feedback.
     */
    private void removeModule(Semester semester, Ui ui) {
        int index = ui.scanModuleOption(semester);
        if (index == semester.getModules().size()) {
            return;
        }
        ui.printModuleRemovalMessage(semester.getModules().get(index));
        semester.getModules().remove(index);
    }

    /**
     * Removes a Lesson object from a Module.
     *
     * @param module Instance of Module containing list of Lessons.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void removeLesson(Module module, Ui ui) {
        int index = ui.scanLessonOption(module);
        if (index == module.getLessons().size()) {
            return;
        }
        ui.printLessonRemovalMessage(module.getModuleCode(), module.getLessons().get(index));
        module.getLessons().remove(index);
    }

    /**
     * Removes a Assignment object from a Module.
     *
     * @param module Instance of Module containing list of Assignment.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void removeAssignment(Module module, Ui ui) {
        int index = ui.scanAssignmentOption(module);
        if (index == module.getAssignments().size()) {
            return;
        }
        ui.printAssignmentRemovalMessage(module.getAssignments().get(index));
        module.getAssignments().remove(index);
    }

    /**
     * Removes a Assessment object from a Module.
     *
     * @param module Instance of Module containing list of Assessment.
     * @param ui     Instance of Ui that is responsible for visual feedback.
     */
    private void removeAssessment(Module module, Ui ui) {
        int index = ui.scanAssessmentOption(module);
        if (index == module.getAssessments().size()) {
            return;
        }
        ui.printAssessmentRemovalMessage(module.getAssessments().get(index));
        module.getAssessments().remove(index);
    }

    @Override
    public boolean isExit() {
        return false;
    }
}
