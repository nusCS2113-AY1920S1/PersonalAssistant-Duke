package spinbox.commands;

import spinbox.Ui;
import spinbox.containers.ModuleContainer;
import spinbox.containers.lists.FileList;
import spinbox.containers.lists.GradeList;
import spinbox.containers.lists.TaskList;
import spinbox.entities.Module;
import spinbox.exceptions.InputException;
import spinbox.exceptions.SpinBoxException;
import spinbox.exporter.Exporter;

import java.util.ArrayDeque;

public class ExportCommand extends Command {
    private static final String EXPORT_LOCATION = "SpinBoxData/exports/";
    private static final String FILE_HEADER = "These are your files for ";
    private static final String GRADED_COMPONENTS_HEADER = "These are your graded components for ";
    private static final String TASKS_HEADER = "These are your tasks for ";
    private static final String FILENAME_MODIFIER_FILES = "_files.txt";
    private static final String FILENAME_MODIFIER_GRADES = "_grades.txt";
    private static final String FILENAME_MODIFIER_TASKS = "_tasks.txt";
    private static final String ACKNOWLEDGEMENT = "Your selected items have been exported to SpinBoxData/exports";
    private static final String NON_EXISTENT_MODULE = "Sorry, that module does not exist.";
    private static final String NON_EXISTENT_CATEGORY = "Sorry, that category does not exist. Please choose one of"
            + " [tasks, files, grades]";

    private String type;
    private String moduleCode;

    /**
     * Constructor for exporting items belonging to a module.
     * @param pageDataComponents The current page context.
     * @param content A string containing the content of the processed user input.
     */
    public ExportCommand(String[] pageDataComponents, String content) {
        if (pageDataComponents.length > 1) {
            this.moduleCode = pageDataComponents[1];
        }
        this.type = content.split(" ")[0].toLowerCase();
    }

    @Override
    public String execute(ModuleContainer moduleContainer, ArrayDeque<String> pageTrace, Ui ui, boolean guiMode) throws
            SpinBoxException {

        if (!moduleContainer.checkModuleExists(moduleCode)) {
            throw new InputException(NON_EXISTENT_MODULE);
        }
        Module module = moduleContainer.getModule(moduleCode);
        Exporter exporter;

        switch (type) {
        case "files":
            exporter = new Exporter(EXPORT_LOCATION + moduleCode + FILENAME_MODIFIER_FILES,
                    FILE_HEADER + moduleCode);
            FileList fileList = module.getFiles();
            exporter.writeData(fileList.getList());
            break;

        case "grades":
            exporter = new Exporter(EXPORT_LOCATION + moduleCode + FILENAME_MODIFIER_GRADES,
                    GRADED_COMPONENTS_HEADER + moduleCode);
            GradeList gradeList = module.getGrades();
            exporter.writeData(gradeList.getList());
            break;

        case "tasks":
            exporter = new Exporter(EXPORT_LOCATION + moduleCode + FILENAME_MODIFIER_TASKS,
                    TASKS_HEADER + moduleCode);
            TaskList taskList = module.getTasks();
            exporter.writeData(taskList.getList());
            break;

        default:
            throw new InputException(NON_EXISTENT_CATEGORY);
        }
        return ACKNOWLEDGEMENT;
    }
}
