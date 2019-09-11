import java.util.ArrayList;

public class FindStringCommand extends Command {

    protected String inputLine;
    protected String commandName;

    public FindStringCommand(String inputLine) {
        this.inputLine = inputLine;
        this.commandName = "find";
    }

    @Override
    public void execute(TaskList tasks) {

        String searchStr = "";
        ArrayList<String> msg = new ArrayList<String>();

        try {
            searchStr = inputLine.substring(commandName.length()+1);
        } catch (IndexOutOfBoundsException e) {
            msg.add("Please use the format 'find <string>'");
            Ui.printMsg(msg);
            return;
        }

        // Find tasks that match the searchStr and add into itemsFound
        ArrayList<String> itemsFound = new ArrayList<String>();
        for (Task currTask : tasks.list) {
            String taskStr = currTask.getTask();
            if (taskStr.indexOf(searchStr) != -1) {
                itemsFound.add(taskStr);
            }
        }


        if (itemsFound.isEmpty()) {
            msg.add("There are no matching tasks in your list :(");
        } else {
            msg.add("Here are the matching tasks in your list:");
            for (int i = 0; i < itemsFound.size(); i++) {
                msg.add( (i+1) + "."  + itemsFound.get(i) );
            }
        }

        Ui.printMsg(msg);
    }

}
