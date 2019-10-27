package duke.model.user;

import duke.commons.exceptions.DukeException;
import duke.model.Goal;

import java.math.BigDecimal;
import java.util.Scanner;

public class UserSetup {

    /**
     * This is a contructor to update an empty user profile with all the info.
     * Used during startup.
     */
    public static User setup() throws DukeException {
        int age = 0;
        Goal goal;
        Scanner in = new Scanner(System.in);
        String name;
        int weight = 0;
        int height = 0;
        System.out.println("     Input name");
        name = in.nextLine();
        try {
            System.out.println("     Input age");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input weight");
            weight = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        try {
            System.out.println("     Input height");
            height = Integer.parseInt(in.nextLine());
        } catch (NumberFormatException e) {
            throw new DukeException(e.getMessage());
        }
        System.out.println("     Input gender(Male/Female)");
        String sex = in.nextLine();
        Gender gender;
        if (sex.charAt(0) == 'M') {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        int activityLevel = 5;
        while (activityLevel > 4 || activityLevel < 0) {
            System.out.println("     Input Activity Level");
            System.out.println("     1) Sedentary (Little or no exercise, desk job");
            System.out.println("     2) Lightly active (Light exercise/ sports 1-3 days/week");
            System.out.println("     3) Moderately active (Moderate exercise/ sports 6-7 days/week)");
            System.out.println("     4) Very active (Hard exercise every day, or exercising 2 xs/day) ");
            System.out.println("     5) Extra active (Hard exercise 2 or more times per day, or training for\n"
                    + "        marathon, or triathlon, etc. )");
            try {
                activityLevel = Integer.parseInt(in.nextLine()) - 1;
            } catch (NumberFormatException e) {
                throw new DukeException(e.getMessage());
            }
        }
        System.out.println("      What is your initial account balance? (in SGD)");
        BigDecimal accountBalance = new BigDecimal(in.nextLine());
        return new User(name, age, height, gender, activityLevel, weight, accountBalance);
    }
}
