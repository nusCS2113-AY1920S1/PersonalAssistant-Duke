package duke.Parser;

import duke.sports.MyPlan;

import java.io.FileNotFoundException;

public class ParserTrainingPlan implements IParser {
    /**
     * To parse training plan command.
     * @param input command.
     * @throws FileNotFoundException
     */
    @Override
    public void parseCommand(final String input) throws FileNotFoundException {
        MyPlan plan = new MyPlan();
        String[] word = input.split(" ");
        String cmd = word[0];
        switch (cmd) {
            case "plan":
                if (word[1].equals("view")) {
                    //int num = 2;
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
