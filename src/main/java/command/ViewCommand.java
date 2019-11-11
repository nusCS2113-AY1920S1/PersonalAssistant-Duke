package command;

import degree.DegreeManager;
import exception.DukeException;
import list.DegreeList;
import main.Duke;
import statistics.CohortSize;
import statistics.GraduateEmployment;
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
            GraduateEmployment ged = new GraduateEmployment();
            if(this.arguments.matches("BME|ChE|CivE|EE|ENVE|ME|MSE|ISE|ComE")) {
                ged.print(this.arguments);
            }
            else {
                throw new DukeException("Enter degree in the correct format");
            }
            break;
        case "cohort_size":
            CohortSize csd = new CohortSize();
            if(this.arguments.matches("BME|ChE|CivE|EE|ENVE|ME|MSE|ISE|ComE")) {
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
