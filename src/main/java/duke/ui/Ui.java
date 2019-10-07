package duke.ui;

import duke.tasks.Meal;
import duke.user.User;

import java.util.ArrayList;
import java.util.Scanner;

public class Ui {
    private static final String padding = "     ";
    private static final String logo = padding + " ____        _        \n"
            + padding + "|  _ \\ _   _| | _____ \n"
            + padding + "| | | | | | | |/ / _ \\\n"
            + padding + "| |_| | |_| |   <  __/\n"
            + padding + "|____/ \\__,_|_|\\_\\___|\n";
    private static final String boundary = "    ____________________________________________________________";
    public Scanner in = new Scanner(System.in);

    public void showWelcome() {
        System.out.println(padding + "Hello from\n" + logo);
        System.out.println(boundary);
        System.out.println(padding + "Hello! I'm Duke");
        System.out.println(padding + "What can I do for you?");
        System.out.println(boundary);
        System.out.println();
    }

    public void showLine() {
        System.out.println(boundary);
    }

    void showPadding() {
        System.out.print(padding);
    }

    public void showBye() {
        System.out.println("     " + "Bye. Hope to see you again soon!");
    }

    public void showList(ArrayList<Meal> meals)  {
        showPadding();
        System.out.println("Here are the tasks in your list: ");
        for (int i = 1; i <= meals.size(); i++) {
            Meal currentMeal = meals.get(i - 1);
            showPadding();
            System.out.println(i + ". " + currentMeal);
        }
    }

    public void showDone(ArrayList<Meal> meals) {
        showPadding();
        System.out.println("Nice! I've marked these tasks as done:");
        for (int i = 1; i <= meals.size(); i++) {
            Meal currentMeal = meals.get(i - 1);
            showPadding();
            System.out.println(i + ". " + currentMeal);
        }
    }

    public void showAdded(Meal currentMeal, ArrayList<Meal> meals, User user, String date) {
        System.out.println(padding + "Got it. I've added this meal:");
        System.out.println(padding + currentMeal);
        int totalConsume = 0;
        for (int i = 0; i < meals.size(); i += 1) {
            totalConsume += meals.get(i).getNutritionalValue().get("calorie");
        }
        System.out.println(padding + "Now you have " + (user.getDailyCalorie()
                - totalConsume) + " calories left on " + date);
    }

    public void showDeleted(Meal currentMeal, ArrayList<Meal> meals) {
        System.out.println(padding + "Noted. I've removed this task:");
        System.out.println(padding + currentMeal);
        System.out.println(padding + "Now you have " + meals.size()  + " tasks in the list.");
    }

    public String readCommand(Scanner in) {
        String input = in.nextLine();
        return input;
    }

    public void showError(String message) {
        System.out.println(padding + message);
    }

    public void showLoadingError() {
        System.out.println(padding + "Failed to load file.");
    }

    public void showUserLoadingError() {
        System.out.println(padding + "Unable to load user file.");
    }

    public void showWelcomeNew() {
        System.out.println(padding + "Welcome!");
        System.out.println(padding + "I see that you're new.");
        System.out.println(padding + "Please enter your particulars to get started!");
    }

    public void showWelcomeBack(User user) {
        System.out.println(padding + "Welcome back, "  + user.getName());
    }

    public void showUserSetupDone(User user) {
        System.out.println(padding + "Thanks, " + user.getName() + "!");
        System.out.println(padding + "We are done setting up!");
    }

    public void showCalorie(User user) {
        System.out.println(padding + "This is your daily limit");
        System.out.println(padding + user.getDailyCalorie());
    }

    public void showRemainingCalorie(ArrayList<Meal> mealsOfDay, User user) {
        System.out.println(padding + "You can consume this many calorie today");
        int limit = user.getDailyCalorie();
        int consumeTotal = 0;
        for (int i = 0; i < mealsOfDay.size(); i += 1) {
            consumeTotal += mealsOfDay.get(i).getNutritionalValue().get("calorie");
        }
        System.out.println(padding + Integer.toString(limit - consumeTotal));
    }
}