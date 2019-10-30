package duke.Parser;


import duke.data.Storage;
import duke.sports.MyPlan;
import duke.Ui;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ParserTrainingPlan implements IParser {

    /**
     * Ui class.
     */
    private Ui ui;
    /**
     * A scanner to handle user input.
     */
    private Scanner sc;
    /**
     * MyPlan object.
     */
    private MyPlan plan;

    /**
     * Constructor for ParserTrainingPlan.
     * @throws FileNotFoundException File does not exist
     */
    public ParserTrainingPlan() throws FileNotFoundException {
        ui = new Ui();
        plan = new MyPlan(new Storage(
                ".\\src\\main\\java\\duke\\data\\plan.txt").loadPlans());
        sc = new Scanner(System.in);
    }

    /**
     * To parse training plan command.
     * @param input command.
     * @throws FileNotFoundException
     */
    @Override
    public void parseCommand(final String input) throws FileNotFoundException {
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
