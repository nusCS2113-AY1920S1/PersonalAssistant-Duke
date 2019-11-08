package DIYeats.ui;

import DIYeats.model.user.User;

/** UserUi is a public class that facilitates the output of User info.
 */
public class UserUi {
    private static final String padding = "     ";
    private static final String boundary = "    ____________________________________________________________";

    public void showName() {
        System.out.println(boundary);
        System.out.println(padding + "Input name");
        System.out.println(boundary);
    }

    public void showAge() {
        System.out.println(boundary);
        System.out.println(padding + "Input age");
        System.out.println(boundary);
    }

    public void showWeight() {
        System.out.println(boundary);
        System.out.println(padding + "Input weight");
        System.out.println(boundary);
    }

    public void showHeight() {
        System.out.println(boundary);
        System.out.println(padding + "Input height");
        System.out.println(boundary);
    }

    public void showGender() {
        System.out.println(boundary);
        System.out.println(padding + "Input gender(Male/Female)");
        System.out.println(boundary);
    }

    public void showActivity() {
        System.out.println(boundary);
        System.out.println(padding + "Input Activity Level");
        System.out.println(padding + "1) Sedentary (Little or no exercise, desk job");
        System.out.println(padding + "2) Lightly active (Light exercise/ sports 1-3 days/week");
        System.out.println(padding + "3) Moderately active (Moderate exercise/ sports 6-7 days/week)");
        System.out.println(padding + "4) Very active (Hard exercise every day, or exercising 2 xs/day) ");
        System.out.println(padding + "5) Extra active (Hard exercise 2 or more times per day, or training for\n"
                + "marathon, or triathlon, etc. )");
        System.out.println(boundary);
    }

    public void showWelcome() {
        System.out.println(boundary);
        System.out.println(padding + "Hello! I'm DIYeats");
        System.out.println(padding + "What can I do for you?");
        System.out.println(boundary);
        System.out.println();
    }

    public void showWelcomeNew() {
        System.out.println(boundary);
        System.out.println(padding + "Welcome!");
        System.out.println(padding + "I see that you're new.");
        System.out.println(padding + "Please enter your particulars to get started!");
        System.out.println(boundary);
    }

    public void showWelcomeBack(User user) {
        System.out.println(boundary);
        System.out.println(padding + "Welcome back, "  + user.getName());
        System.out.println(boundary);
    }

    public void showUserSetupDone(User user) {
        System.out.println(boundary);
        System.out.println(padding + "Thanks, " + user.getName() + "!");
        System.out.println(padding + "We are done setting up!");
        System.out.println(boundary);
    }

    public void showMessage(String messageStr) {
        System.out.println(padding + messageStr);
    }
}

