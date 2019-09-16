//package command;
//import exception.DukeException;
//import task.Task;
//import storage.Storage;
//import task.Deadline;
//import task.Event;
//import task.TaskList;
//import task.Todo;
//import ui.Ui;
//
//import java.util.ArrayList;
//
///**
// * The RecurCommand class is used when the user has intends to set a task as of recurring type.
// *
// * @author Sai Ganesh Suresh
// * @version v1.0
// */
//public class ViewCommand extends Command{
//    private Integer recurType;
//    private Integer indexOfTask;
//
//        /**
//         * This RecurCommand constructor is used to assign the different parameters required when adding a task.
//         *
//         * @param recurType this Integer holds the type of recurring of the task.
//         */
//        public ViewCommand(Integer indexOfTask,Integer recurType) {
//            this.recurType = recurType;
//            this.indexOfTask = indexOfTask;
//        }
//
//        /**
//         * This execute function is used to add the respective tasks to the TaskList and save to persistent storage.
//         *
//         * @param tasks this string holds command type determinant to decide how to process the user input.
//         * @param ui this string holds the description of the task provided by the user.
//         * @param storage this parameter provides the execute function the storage to allow the saving of the file.
//         *
//         */
//        public void execute(TaskList tasks, Ui ui, Storage storage) throws DukeException {
//            ArrayList<Task> holdRecurringTask =  tasks.getTasks();
//            Task tasktorecur = holdRecurringTask.get(indexOfTask);
//
//
//            switch (recurType) {
//                case 1:
//                    AddCommand(tasktorecur.toString() + );
//                    break;
//                case 2:
//                    AddCommand(tasktorecur.toString() + );
//                    break;
//                case 3:
//                    AddCommand(tasktorecur.toString() + );
//                    break;
//                default:
//                    throw new DukeException(DukeException.UNKNOWN_USER_COMMAND());
//            }
//
//            tasks.add(task);
//            storage.saveFile(tasks.getTasks());
//            Ui.printOutput("Got it! I've added this task:" + "\n  " + task.toString() +"\nNow you have " +
//                    tasks.getSize() + " task(s) in the list.");
//        }
//    }
