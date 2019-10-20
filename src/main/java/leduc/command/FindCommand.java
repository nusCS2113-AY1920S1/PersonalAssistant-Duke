package leduc.command;

import leduc.exception.EmptyArgumentException;
import leduc.storage.Storage;
import leduc.Ui;
import leduc.task.TaskList;
import java.lang.*;
import java.util.ArrayList;

/**
 * Represents a Find Command.
 * Allow to find a specific task from the task list.
 */
public class FindCommand extends Command {
    /**
     * static variable used for shortcut
     */
    private static String findShortcut = "find";
    /**
     * Constructor of FindCommand.
     * @param user String which represent the input string of the user.
     */
    public FindCommand(String user){
        super(user);
    }
    /**
     * Finds the index of the maximum value in the arraylist
     * @param scores ArrayList<Double> containing relevance scores for all tasks in the list
     * @return returns index of the index of the maximum score in the list.
     */
    public int findMaxIndex(ArrayList<Double> scores){
        //index of scores correspond to tasks in TaskList. To preserve index, processed scores are assigned a null Value
        double nullDouble = -99.0;
        int nullInt = -99;
        double max = 0.0;
        int max_index = nullInt;
        for (int j = 0; j < scores.size(); j++) {
            if(scores.get(j) == 0.0){//tasks with no common characters will be flagged as null
                scores.set(j, nullDouble);
            }
            if (scores.get(j) > max) {//update max
                max = scores.get(j);
                max_index = j;
            }
        }
        return max_index;
    }
    /**
     * Compares each task description with the user query, generates a score from 0-1 based on how close the match is.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param find String that contains the user's query
     * @return returns an ArrayList containing relevance scores for each task description
     */
    public ArrayList<Double> generateRelevanceScores(String find, TaskList tasks){
        ArrayList<Double> scores = new ArrayList<Double>();
        double relevanceScore = 0.0;
        for (int i = 0; i < tasks.size(); i++) {
            double numMatches = 0;
            double shortestStringLength = Math.min(tasks.get(i).getTask().length(), find.length());
            double longestStringLength = Math.max(tasks.get(i).getTask().length(), find.length());
            //use nested for loop to compare query and task description elementwise
            for (int j = 0; j < shortestStringLength; j++) {
                for (int k = 0; k < tasks.get(i).getTask().length(); k++) {
                    //compare characters, if they match, increment nummatches then break
                    if (find.charAt(j) == tasks.get(i).getTask().charAt(k)) {
                        numMatches += 1.0;
                        break;
                    }

                }
            }
            relevanceScore = numMatches / longestStringLength;
            scores.add(relevanceScore);
        }
        return scores;
    }
    /**
     * Allow to find top relevant tasks from the task list by utilizing fuzzy matching algorithm.
     * @param tasks leduc.task.TaskList which is the list of task.
     * @param ui leduc.Ui which deals with the interactions with the user.
     * @param storage leduc.storage.Storage which deals with loading tasks from the file and saving tasks in the file.
     * @throws EmptyArgumentException Exception caught when there is no argument
     */

    public void execute(TaskList tasks, Ui ui, Storage storage) throws EmptyArgumentException {
        String userSubstring;
        if(callByShortcut){
            userSubstring = user.substring(FindCommand.findShortcut.length());
        }
        else {
            userSubstring = user.substring(4);
        }
        if(userSubstring.isBlank()){
            throw new EmptyArgumentException();
        }
        String find = user.substring(FindCommand.findShortcut.length()+1);
        ArrayList<Double> scores;
        //populate list of relevance scores
        scores = generateRelevanceScores(find, tasks);

        String result = "";
        //Add tasks to "String result" in the order of relevance.
        for(int i = 0; i < scores.size(); i++) {
            double nullDouble = -99.0;
            //find the index of the task that is most similar to the user query
            int max_index = findMaxIndex(scores);
            if(max_index > nullDouble) {
                result += tasks.displayOneElementList(max_index);
                //To preserve indices, previously sorted scores are replaced with a null value
                scores.set(max_index, nullDouble);
            }
            else{//all tasks are sorted
                break;
            }

        }
        if (result.isEmpty()) {
            ui.display("\t There is no matching tasks in your list");
        } else {
            ui.display("\t Here are the matching tasks in your list:\n" + result);
        }
    }
    /**
     * getter because the shortcut is private
     * @return the shortcut name
     */
    public static String getFindShortcut() {
        return findShortcut;
    }

    /**
     * used when the user want to change the shortcut
     * @param findShortcut the new shortcut
     */
    public static void setFindShortcut(String findShortcut) {
        FindCommand.findShortcut = findShortcut;
    }
}
