package gazeeebo.commands.gpacalculator;

import gazeeebo.TriviaManager.TriviaManager;
import gazeeebo.UI.Ui;
import gazeeebo.commands.Command;
import gazeeebo.exception.DukeException;
import gazeeebo.storage.Storage;
import gazeeebo.tasks.Task;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class GpaCalculatorCommand extends Command {
    public int moduleCredit;
    public double score;

    public GpaCalculatorCommand(int moduleCredit, double score) {
        this.moduleCredit = moduleCredit;
        this.score = score;
    }
    public void execute(ArrayList<Task> list, Ui ui, Storage storage, Stack<String> commandStack, ArrayList<Task> deletedTask, TriviaManager triviaManager) throws DukeException, ParseException, IOException, NullPointerException {
        System.out.println("Welcome to your GPA Calculator page! What would you like to do?\n\n");
        HashMap<String, GpaCalculatorCommand> map = new HashMap<>(); //Read the file
        Map<String, GpaCalculatorCommand> calculator = new TreeMap<>(map);




        GpaCalculatorCommand current_gpa = new GpaCalculatorCommand(4, 4.5);
        calculator.put("CS2101", current_gpa);

        int cap;
        int sum_GPA_MCS = 0;
        int sum_MCS = 0;

        for(String key: calculator.keySet()) {
            sum_GPA_MCS += calculator.get(key).moduleCredit * calculator.get(key).score;
            sum_MCS += calculator.get(key).moduleCredit;
        }

        cap = sum_GPA_MCS/sum_MCS;
    }
    @Override
    public boolean isExit() {
        return false;
    }
}
