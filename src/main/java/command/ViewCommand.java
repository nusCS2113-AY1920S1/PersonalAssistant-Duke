package command;

import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import main.Duke;
import statistics.CohortSizeDisplay;
import statistics.GraduateEmployment;
import statistics.GraduateEmploymentDisplay;
import storage.Storage;
import task.TaskList;
import ui.UI;

public class ViewCommand extends Command{
    private String arguments;
    private String command;

    public ViewCommand(String command, String arguments) {
        this.command = command;
        this.arguments = arguments;
    }

    @Override
    public void execute(TaskList tasks, UI ui, Storage storage, DegreeList lists, DegreeManager degreesManager) throws DukeException {
        switch (this.command) {
        case "view_employment":
            GraduateEmploymentDisplay ged = new GraduateEmploymentDisplay();
            if(this.arguments.matches("bme|che|cive|ee|enve|me|mse|ise|ceg")) {
                ged.print(this.arguments);
            }
            else {
                throw new DukeException("Enter degree in the correct format");
            }
            break;
        case "cohort_size":
            CohortSizeDisplay csd = new CohortSizeDisplay();
            if(this.arguments.matches("bme|che|cive|ee|enve|me|mse|ise|ceg")) {
                csd.print(this.arguments);
            }
            else {
                throw new DukeException("Enter degree in the correct format");
            }
            break;
        default:
            throw new DukeException("Invalid ViewCommand");
        }
    }
}
