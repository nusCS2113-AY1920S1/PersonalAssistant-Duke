package duke.parser;

import duke.models.MyPlan;
import duke.models.MyTraining;
import duke.view.CliView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import duke.data.Storage;

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
     * @throws IOException IO
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
        final int inDelete = 4;
        final int back = 5;
        boolean inPlan = true;
        while (inPlan) {
            plan.clearPlanInList();
            cliView.trainingProgramHeading();
            int index = Integer.parseInt(sc.nextLine());
            switch (index) {
            case inView:
                boolean inList = true;
                while (inList) {
                    if (plan.keyList().isEmpty()) {
                        cliView.noPlanInMap();
                        break;
                    }
                    cliView.printLine();
                    cliView.planListHeading();
                    plan.showPlanList();
                    cliView.printLine();

                    int choosePlan = Integer.parseInt(sc.nextLine());
                    if (choosePlan <= plan.keyList().size()) {
                        String key = plan.keyList().get(choosePlan - 1);
                        plan.loadPlanToList(key);
                        cliView.printLine();
                        System.out.println(plan.viewPlan());
                        inList = false;
                    }
                }
                break;
            case inCreate:
                cliView.createPlanHeading();
                String i = sc.nextLine();
                cliView.showPlanCreating(i);
                plan.createPlan(i);
                break;
            case inEdit:
                inList = true;
                while (inList) {
                    if (plan.keyList().isEmpty()) {
                        cliView.noPlanInMap();
                        break;
                    }
                    cliView.printLine();
                    cliView.planListHeading();
                    plan.showPlanList();
                    cliView.printLine();

                    if (plan.keyList().size() != 0) {
                        int choosePlan = Integer.parseInt(sc.nextLine());
                        if (choosePlan <= plan.keyList().size()) {
                            String key = plan.keyList().get(choosePlan - 1);
                            plan.loadPlanToList(key);

                            String[] num = key.split("(?<=\\D)(?=\\d)");

                            try {
                                plan.editPlan(num[0], key);
                            } catch (IOException e) {
                                System.out.println("IO Problem");
                            }

                            inList = false;
                        }
                    } else {
                        cliView.noPlanInMap();
                        inList = false;
                    }
                }
                break;
            case inDelete:
                inList = true;
                while (inList) {
                    if (plan.keyList().isEmpty()) {
                        cliView.noPlanInMap();
                        break;
                    }
                    cliView.printLine();
                    cliView.planListHeading();
                    plan.showPlanList();
                    cliView.printLine();

                    if (plan.keyList().size() != 0) {
                        int choosePlan = Integer.parseInt(sc.nextLine());
                        if (choosePlan <= plan.keyList().size()) {
                            String key = plan.keyList().get(choosePlan - 1);
                            try {
                                plan.deletePlan(key);
                            } catch (IOException e) {
                                System.out.println("Failed to delete plan");
                            }
                            inList = false;
                        }
                    } else {
                        cliView.noPlanInMap();
                        inList = false;
                    }
                }
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
