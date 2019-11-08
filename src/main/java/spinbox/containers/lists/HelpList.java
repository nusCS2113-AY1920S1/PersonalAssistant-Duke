package spinbox.containers.lists;

public class HelpList {
    private String helpOutput = "";
    private final String horizontalLine = "____________________________________________________________";

    public final String helpOnly = helpOutput.concat(horizontalLine + "\n" + "Welcome to the help page. Below is the "
            + "full list of help commands.\n" + "You may view individual help commands by providing the specific "
            + "command after the 'help' keyword.\n" + "Example:\n" + "\tTo view the help page for the 'remove' "
            + "command: help / remove\n"
            + "Full list of help commands:\n"
            + "\t1. help / view : To navigate and change view from the current page to the specified page\n"
            + "\t2. help / add : To add a module/task/grade/file/note component\n"
            + "\t3. help / remove : To remove a module/task/grade/file/note component\n"
            + "\t4. help / remove-* : To remove multiple task/grade/file/note components\n"
            + "\t5. help / set-date : To set deadline/event/exam/lab/lecture/tutorial date to a new date\n"
            + "\t6. help / set-name : To set task/grade/file/note name to a new name\n"
            + "\t7. help / update : To update a task/grade/file component to a value\n"
            + "\t8. help / update-* : To update multiple task/grade/file components to a value\n"
            + "\t9. help / export : To export a current snapshot of a module's tasks/grades/files\n"
            + horizontalLine);

    public final String view = helpOutput.concat(horizontalLine + "\n" + "Command: view\n"
            + "Function: To navigate and change view from the current page to the specified page\n"
            + "Format: view / <page>\n"
            + "Example:\n"
            + "\t1. View the main page: view / main\n"
            + "\t2. View the calendar page: view / calendar\n"
            + "\t3. View the modules page: view / modules\n\n"
            + "\t* Note: omit 'modules' and module code if current page is the specific module *\n"
            + "\t4. View the list of files under module CG1111: view / modules cg1111 files\n"
            + "\t5. View the list of grade components under module CG1111: view / modules cg1111 grades\n"
            + "\t6. View the list of notes under module CG1111: view / modules cg1111 notes\n"
            + "\t7. View the list of tasks under module CG1111: view / modules cg1111 tasks\n" + horizontalLine);

    public final String add = helpOutput.concat(horizontalLine + "\n" + "Command: add\n"
            + "Function: To add a module/task/grade/file/note component\n"
            + "Format:\n" + "\t1. Add a new module: add / module <module code> <module name>\n"
            + "\t2. Add items for a specific module: add <module code> / <item type> <item description>\n"
            + "Example:\n"
            + "\t* Adding a new module *\n"
            + "\t1. Add a new module CG1111, module name EPP1: add / module CG1111 EPP1\n\n"
            + "\t* Adding an item for a specific module (omit module code if current page is the specific module "
            + "page) *\n"
            + "\t2. Add a new file under module CG1111: add CG1111 / file quiz 2 2018\n"
            + "\t3. Add a new grade component under CG1111: add CG1111 / grade Report weightage: 12.5%\n"
            + "\t4. Add a new note under CG1111: add CG1111 / note bring textbook\n"
            + "\t5. Add a new todo task under module CG1111: add CG1111 / todo finish assignment\n"
            + "\t   -List of task type includes:\n"
            + "\t\ta. todo -- Format: add <module code> / todo <todo description>\n"
            + "\t\tb. deadline -- Format: add <module code> / deadline <deadline description> by: <MM/DD/YYYY HH:MM>\n"
            + "\t\tc. event/exam/lab/lecture/tutorial -- Format: add <module code> / <event/exam/lab/lecture/"
            + "tutorial>\n"
            + "\t\t <event/exam/lab/lecture/tutorial description> at: <start as MM/DD/YYYY HH:MM>"
            + " to <end as MM/DD/YYYY HH:MM>\n" + horizontalLine);

    public final String remove = helpOutput.concat(horizontalLine + "\n" + "Command: remove\n"
            + "Function: To remove a module/task/grade/file/note component\n"
            + "Format:\n" + "\t1. Remove a module: remove / module <module code> <module name>\n"
            + "\t2. Remove items for a specific module: remove <module code> / <item type> <item index>\n"
            + "Example:\n"
            + "\t* Removing a module *\n"
            + "\t1. Remove a module CG1111: remove / module CG1111 EPP1\n\n"
            + "\t* Removing an item for a specific module (omit module code if current page is the specific module "
            + "page) *\n"
            + "\t2. Remove the first file under module CG1111: remove CG1111 / file 1\n"
            + "\t3. Remove the first grade component under CG1111: remove CG1111 / grade 1\n"
            + "\t4. Remove the first note under CG1111: remove CG1111 / note 1\n"
            + "\t5. Remove the first task under module CG1111: remove CG1111 / task 1\n" + horizontalLine);

    public final String removeMultiple = helpOutput.concat(horizontalLine + "\n" + "Command: remove-*\n"
            + "Function: To remove multiple task/grade/file/note components\n"
            + "Format: remove-* <module code> / <item type> <item indexes>\n"
            + "Example:\n"
            + "\t* Note: omit module code if current page is the specific module *\n"
            + "\t1. Remove the first 2 files under module CG1111: remove-* CG1111 / file 1,2\n"
            + "\t2. Remove the first 2 grade components under CG1111: remove-* CG1111 / grade 1,2\n"
            + "\t3. Remove the first 2 notes under CG1111: remove-* CG1111 / note 1,2\n"
            + "\t4. Remove the first 2 tasks under module CG1111: remove-* CG1111 / task 1,2\n" + horizontalLine);

    public final String setDate = helpOutput.concat(horizontalLine + "\n" + "Command: set-date\n"
            + "Function: To set deadline/event/exam/lab/lecture/tutorial date to a new date\n"
            + "Format: set-date <module code> / task <item index> to: <new date and time details>\n"
            + "Example:\n"
            + "\t* Note: omit module code if current page is the specific module *\n"
            + "\t1. Set the date of the first task (deadline) under module CG1111 : set-date CG1111 / task 1 to: "
            + "01/01/2019 23:59\n"
            + "\t2. Set the date of the second task (event) under module CG1111 : set-date CG1111 / task 2 to: "
            + "02/01/2019 10:00 to 02/01/2019 15:00\n"
            + horizontalLine);

    public final String setName = helpOutput.concat(horizontalLine + "\n" + "Command: set-name\n"
            + "Function: To set task/grade/file/note name to a new name\n"
            + "Format: set-name <module code> / <item type> <item index> to: <new name>\n"
            + "Example:\n"
            + "\t* Note: omit module code if current page is the specific module *\n"
            + "\t1. Set the first file under module CG1111 to 'lecture note': set-name CG1111 / file 1 to: lecture"
            + " note\n"
            + "\t2. Set the first grade component under CG1111: TBC\n"
            + "\t3. Set the first note under module CG1111 to 'bring textbook': set-name CG1111 / note 1 to: bring"
            + " textbook\n"
            + "\t4. Set the first task under module CG1111 to 'return book': set-name CG1111 / task 1 to: "
            + "return book\n"
            + horizontalLine);

    public final String update = helpOutput.concat(horizontalLine + "\n" + "Command: update\n"
            + "Function: To update a task/grade/file component to a boolean value\n"
            + "Format: update <module code> / <item type> <item index> <booleanValue>\n"
            + "Example:\n"
            + "\t* Note: omit module code if current page is the specific module *\n"
            + "\t1. Update a file to downloaded under module CG1111: update CG1111 / file 1 true\n"
            + "\t2. Update a grade component under CG1111: update CG1111 / grade 1 false\n"
            + "\t3. Update a task to done under module CG1111: update CG1111 / task 1 true\n" + horizontalLine);

    public final String updateMultiple = helpOutput.concat(horizontalLine + "\n" + "Command: update-*\n"
            + "Function: To update multiple task/grade/file components to a value\n"
            + "Format: update-* <module code> / <item type> <item indexes> <item values>\n"
            + "Example:\n"
            + "\t* Note: omit module code if current page is the specific module *\n"
            + "\t1. Update the first 2 files under module CG1111: update-* CG1111 / file 1,2 true\n"
            + "\t2. Update the first 2 grade components under CG1111: TBC\n"
            + "\t3. Update the first 2 tasks under module CG1111: update-* CG1111 / task 1,2 true\n"
            + horizontalLine);

    public final String export = helpOutput.concat(horizontalLine + "\n" + "Command: export\n"
            + "Function: To export a snapshot of the current tasks/files/grades within a module\n"
            + "Format: export <module code> / <item type>\n"
            + "Example:\n"
            + "\t* Note: omit module code if current page is the specific module *\n"
            + "\t1. Export the files under module CG1111: export CG1111 / files\n"
            + "\t2. Export the tasks under module CG1111, while viewing CG1111:  export / tasks\n"
            + horizontalLine);
}