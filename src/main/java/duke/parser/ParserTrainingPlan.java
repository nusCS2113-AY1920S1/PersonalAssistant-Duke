package duke.parser;

import duke.models.MyPlan;
import duke.view.CliView;

import java.io.IOException;
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
    private Scanner sc;
    /**
     * MyPlan object.
     */
    private MyPlan plan;

    /**
     * Constructor for ParserTrainingPlan.
     * @throws FileNotFoundException File does not exist
     */
    public ParserTrainingPlan() throws IOException {
        cliView = new CliView();
        plan = new MyPlan(new Storage(
                ".\\src\\main\\java\\duke\\data\\plan.txt").loadPlans());
        sc = new Scanner(System.in);
    }

    /**
     * To parse training plan command.
     * @param input command.
     */
    @Override
    public void parseCommand(final String input) {
        final int inView = 1;
        final int inCreate = 2;
        final int inEdit = 3;
        final int back = 4;
        boolean inPlan = true;
        while (inPlan) {
            cliView.trainingProgramHeading();
            int index = Integer.parseInt(sc.nextLine());
            switch (index) {
            case inView:
                cliView.planListHeading();
                int choosePlan = Integer.parseInt(sc.nextLine());
                if (choosePlan <= plan.keyList().size()) {
                    String key = plan.keyList().get(choosePlan);
                    String[] details = key.split("(?<=\\D)(?=\\d)");

                    plan.loadPlanToList(details[0], Integer.parseInt(details[1]));
                    System.out.println(plan.viewPlan());
                } else {
                    cliView.planNotFound();
                }
                break;
            case inCreate:
                cliView.createPlanHeading();
                String i = sc.nextLine();
                cliView.showPlanCreating(i);
                plan.createPlan(i);
                break;
            case inEdit:
                System.out.println("To be created...");
                break;
            case back:
                inPlan = false;
                break;
            default:
                cliView.showCorrectPlanHeading();
            }
        }
    }
}
