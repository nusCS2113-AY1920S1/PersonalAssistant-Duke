package diyeats.ui;

import diyeats.model.user.User;

//@@author koushireo

/** UserUi is a public class that facilitates the output of User info.
 */
public class UserUi {
    private static final String padding = "     ";
    private static final String boundary = "    ____________________________________________________________";

    public void showLine() {
        System.out.println(boundary);
    }

    public void showMessage(String messageStr) {
        System.out.println(padding + messageStr);
    }

    public void showWelcome() {
        showLine();
        showMessage("Hello! I'm DIYeats");
        showMessage("What can I do for you?");
        showLine();
    }

    public void showWelcomeNew() {
        showLine();
        showMessage("Welcome!");
        showMessage("I see that you're new.");
        showMessage("Please enter your particulars to get started!");
        showLine();
    }

    public void showActivityLevel() {
        showMessage("Input Activity Level");
        showMessage("1) Sedentary (Little or no exercise, desk job");
        showMessage("2) Lightly active (Light exercise/ sports 1-3 days/week");
        showMessage("3) Moderately active (Moderate exercise/ sports 6-7 days/week)");
        showMessage("4) Very active (Hard exercise every day, or exercising 2 xs/day) ");
        showMessage("5) Extra active (Hard exercise 2 or more times per day, or training for\n"
                + padding + "   marathon, or triathlon, etc. )");
    }

    public void showWelcomeBack(User user) {
        showLine();
        showMessage("Welcome back, "  + user.getName());
        showLine();
    }

    public void showUserSetupDone(User user) {
        showLine();
        showMessage("Thanks, " + user.getName() + "!");
        showMessage("We are done setting up!");
        showLine();
    }

    public void showSetupMessage() {
        showLine();
        showMessage("Please enter the user info.");
        showLine();
    }

    public void showLackAge() {
        showLine();
        showMessage("Please enter an age using /age.");
        showLine();
    }

    public void showLackName() {
        showLine();
        showMessage("Please enter a name using /name.");
        showLine();
    }

    public void showLackWeight() {
        showLine();
        showMessage("Please enter a weight(cannot be less than 2kg)");
        showMessage("using /weight/.");
        showLine();
    }

    public void showLackHeight() {
        showLine();
        showMessage("Please enter a height(cannot be less than 54cm");
        showMessage("or more than 272cm) using /height.");
        showLine();
    }

    public void showLackActivity() {
        showLine();
        showMessage("Please enter an activity level using /activity.");
        showActivityLevel();
        showLine();
    }

    public void showLackGender() {
        showLine();
        showMessage("Please enter a gender(M/F) using /gender.");
        showLine();
    }

    public void showWrongGenderInfo() {
        showLine();
        showMessage("Please enter either M/F for gender.");
        showActivityLevel();
        showLine();
    }

}

