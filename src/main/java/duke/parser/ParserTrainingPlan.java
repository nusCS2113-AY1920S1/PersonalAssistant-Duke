package duke.parser;

import duke.models.MyPlan;
import duke.view.CliView;
import java.util.Scanner;
import duke.data.Storage;
import java.io.FileNotFoundException;

public class ParserTrainingPlan implements IParser {

    /**
     * Ui class.
     */
    private CliView cliView;

    /**
     * A scanner to handle user input.
     */
    private Scanner sc = new Scanner(System.in);
    /**
     * MyPlan object.
     */
    private MyPlan plan;

    /**
     * Constructor for ParserTrainingPlan.
     * @throws FileNotFoundException File does not exist
     */
    public ParserTrainingPlan() throws FileNotFoundException {
        cliView = new CliView();
        plan = new MyPlan(new Storage(
                ".\\src\\main\\java\\duke\\data\\plan.txt").loadPlans());
        sc = new Scanner(System.in);
    }

    /**
     * To parse training plan command.
     * @param input command.
     * @throws FileNotFoundException File not found
     */
    @Override
    public void parseCommand() throws FileNotFoundException {
        String[] word = input.split(" ");
        String cmd = word[0];

        switch (cmd) {
        case "plan":
            if (word[1].equals("view")) {
                //plan.loadPlan(word[num].toLowerCase(), word[++num]);
                System.out.println(plan.viewPlan());
            } else if (word[1].equals("new")) {
                plan.createPlan(word[2].toLowerCase());
            } else if (word[1].equals("edit")) {
                System.out.println("To be created...");
            }
            break;
        case "training":
            switch (word[1]) {
            case "view":
                System.out.println("TBC");
                System.out.println(plan.viewPlan());
                break;
            case "add-plan":
                //pass
                break;
            case "add-activity":
                int num = 2;
                System.out.println(plan.addActivity(word[num],
                        Integer.parseInt(word[++num]),
                        Integer.parseInt(word[++num])));
                break;
            case "delete":
                System.out.println("To be added.");
                break;
            case "delete-all":
                System.out.println("To be added");
                break;
            default:
                break;
            }
            break;
        default:
            System.out.println("Incorrect Command.");
        }
    }
}
