package seedu.hustler.ui;

import seedu.hustler.Hustler;
import seedu.hustler.game.achievement.Achievements;
import seedu.hustler.game.avatar.Avatar;
import seedu.hustler.game.shop.items.ShopItem;
import seedu.hustler.task.Task;

import java.util.ArrayList;

import static seedu.hustler.game.achievement.Achievements.totalPoints;

/**
 * A class dedicated to performing interactions with the user.
 * Takes inputs and contains methods that output error messages.
 */
public class Ui {

    public static final String LINE = "\t_____________________________________";

    /**
     * Print with formatting.
     */
    public void showMessage(String message) {
        System.out.println(LINE);
        System.out.println("\t" + message);
        System.out.println(LINE);
    }

    /**
     * Prints an output message if list history was not saved.
     */
    public void showSaveError() {
        System.out.println(LINE);
        System.out.println("\tCouldn't saveAchievements file.");
        System.out.println(LINE);
    }

    /**
     * Prints a bye message if user enters bye.
     */
    public void showByeMessage() {
        System.out.println(LINE);
        System.out.println("\tBye. Hope to see you again soon!");
        System.out.println(LINE);
    }

    /**
     * Prints an error message if user does not enter a valid command.
     */
    public void correctCommandError() {
        System.out.println(LINE);
        System.out.println("\tPlease enter a valid command: /add, /list, /bye, /find, /delete.\n"
                + "\tRefer to User Guide for more info.");
        System.out.println(LINE);
    }

    /**
     * Prints error message if an empty list is asked to be displayed.
     */
    public void showEmptyListError() {
        System.out.println(LINE);
        System.out.println("\tList is empty. Please type another command apart from list.");
        System.out.println(LINE);
    }

    /**
     * Prints the new name of the avatar and lets the user know that it has been changed.
     */
    public void showNameChangeSuccess() {
        System.out.println(LINE);
        System.out.println("\tName has been successfully changed!");
        System.out.println("\tYour new name is: " + Hustler.avatar.getName());
        System.out.println(LINE);
    }

    /**
     * Prints the statistics of the avatar.
     */
    public void showAvatarStatistics() {
        System.out.println(LINE);
        System.out.println("\tHere are the information on your Avatar:");
        System.out.println("\t" + Hustler.avatar.toString());
        System.out.println(LINE);
    }

    /**
     * Prints a message if list is empty.
     */
    public void showListEmpty() {
        System.out.println(LINE);
        System.out.println("\tNothing to be cleared! Task list is already empty!");
        System.out.println(LINE);
    }

    /**
     * Prints a message when list is cleared.
     */
    public void showListCleared() {
        System.out.println(LINE);
        System.out.println("\tAll tasks in the task list has been cleared! List is now empty!");
        System.out.println(LINE);
    }

    /**
     * Prints a message when completed tasks are cleared.
     */
    public void showCompletedCleared(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tAll completed tasks in the task list has been cleared!");
        showTaskList(list);
    }

    /**
     * Prints a message when a task has been snoozed.
     * @param taskDescription description of the snoozed task.
     */
    public void showTaskSnoozed(String taskDescription) {
        System.out.println(LINE);
        System.out.println("\tGot it. You have snoozed the task.");
        System.out.println("\t" + taskDescription);
        System.out.println(LINE);
    }

    /**
     * Prints the entire collection of tasks in task list.
     * @param list collection of tasks.
     */
    public void showTaskList(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tHere are the tasks in your list:");
        for (int i = 0; i < list.size(); i++) {
            System.out.println("\t" + (i + 1) + ". " + list.get(i).toString());
        }
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been added.
     * @param list collection of tasks.
     */
    public void showTaskAdded(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tGot it. I've added this task:");
        System.out.println("\t  " + list.get(list.size() - 1).toString());
        System.out.println("\tNow you have " + list.size() + " tasks in the list.");
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task clashes with another task in the list.
     */
    public void showTaskClash() {
        System.out.println(LINE);
        System.out.println("\tTask clashes with another existing task in the list!");
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been completed.
     * @param taskDescription description of the completed task.
     */
    public void showTaskDone(String taskDescription) {
        System.out.println(LINE);
        System.out.println("\tNice! I've marked this task as done:");
        System.out.println("\t  " + taskDescription);
        System.out.println(LINE);
    }

    /**
     * Prints a message when a task has been removed.
     * @param list collection of tasks.
     * @param taskDescription description of the removed task.
     */
    public void showTaskRemoved(ArrayList<Task> list, String taskDescription) {
        System.out.println(LINE);
        System.out.println("\tNoted. I have removed this task:");
        System.out.println("\t  " + taskDescription);
        System.out.println("\tNow there are " + list.size() + " tasks left.");
        System.out.println(LINE);
    }

    /**
     * Prints the tasks that contains the searched keyword.
     * @param matchingTasks the ArrayList of matching tasks.
     * @param taskDescription the queried description.
     */
    public void showMatchingTasks(ArrayList<Task> matchingTasks, String taskDescription) {
        System.out.println(LINE);
        if (matchingTasks.isEmpty()) {
            System.out.println("\tNo task found given the query \"" + taskDescription + "\".");
        } else {
            System.out.println("\tFound " + matchingTasks.size() + " task(s) given the query \""
                    + taskDescription + "\". Here you go.");
            for (int i = 0; i < matchingTasks.size(); i++) {
                System.out.println("\t" + (i + 1) + ". " + matchingTasks.get(i).toString());
            }
        }
        System.out.println(LINE);
    }

    /**
     * Prints a message when the task list has been sorted.
     * @param list collection of tasks.
     */
    public void showListSorted(ArrayList<Task> list) {
        System.out.println(LINE);
        System.out.println("\tTask list has been successfully sorted!");
        showTaskList(list);
    }

    /**
     * Displays on the screen the congratulatory message to indicate that the User
     * has leveled up.
     */
    public void showCongrats(Avatar avatar) {
        System.out.println("\t_____________________________________");
        System.out.println("\tCongratulations, you've leveled up! Your avatar has gotten stronger:");
        System.out.println(avatar.toString());
        System.out.println("\t_____________________________________\n\n");
    }

    /**
     * Displays the item that the Avatar recently equipped.
     */
    public void showEquipped(ShopItem item) {
        System.out.println(LINE);
        System.out.println("\tYou are equipped with " + item);
        System.out.println(LINE);
    }

    /**
     * Displays the list of all items in the inventory.
     */
    public void listInventory(ArrayList<ShopItem> items) {
        System.out.println("********** You currently have these items: **********");
        if (items.size() == 0) {
            System.out.println();
            System.out.println("You have no items in your inventory.");
            System.out.println();
        } else {
            for (int i = 0; i < items.size(); i++) {
                System.out.println(i + 1 + ". " + items.get(i).toString());
            }
        }
        System.out.println("******************************************************");
    }

    /**
     * Shows the purchased text when purchase is successful.
     */
    public void showPurchasedSuccess() {
        System.out.println(LINE);
        System.out.println("\t Item has been purchased!");
        System.out.println("\tYour leftover points are: " + Achievements.totalPoints);
        System.out.println(LINE);
    }

    /**
     * Shows the failed text when purcahse is unsuccessful.
     */
    public void showPurchasedFailure() {
        System.out.println(LINE);
        System.out.println("\tNot enough points. Please accumulate more points!");
        System.out.println(LINE);
    }

    /**
     * Shows the text to inform the user that item was already purchased before.
     */
    public void showAlreadyPurchased() {
        System.out.println(LINE);
        System.out.println("\tItem has already been purchased! Please check your inventory.");
        System.out.println(LINE);
    }

    /**
     * Shows the list of items in the shop for purchase.
     * @param shopList the ArrayList of the items in the shop.
     */
    public void showShopList(ArrayList<ShopItem> shopList) {
        System.out.println("******************** Here are the items in the shop ********************");
        for (int i = 0; i < shopList.size(); i++) {
            /**
             * Divides the list to format the printing of different classes.
             */
            if (i == 0 || !(shopList.get(i).isEquals(shopList.get(i - 1)))) {
                System.out.println("\n\t\t\t\t======" + shopList.get(i).getType() + "=====");
            }
            System.out.print((i + 1) + ". ");
            System.out.print(shopList.get(i).toString());
            System.out.println(shopList.get(i).isPurchased() ? " [Purchased]" :
                    " [" + shopList.get(i).getCost() + " points to purchase]");
        }
        System.out.println();
        System.out.println("\t\t\t\tYou currently have: " + Achievements.totalPoints + " points.");
        System.out.println("*************************************************************************");
    }

    /**
     * Prints a message to show the newly unlocked achievement.
     *
     * @param achievement newly unlocked achievement
     */
    public void showAchievementUnlocked(Achievements achievement) {
        System.out.println("Congratulations, you have unlocked this achievement!\n" + achievement.toString());
    }

    /**
     * Prints out the all the unlocked and locked achievements.
     *
     * @param achievementList list of achievements
     */
    public void showAchievementList(ArrayList<Achievements> achievementList) {
        System.out.println("****************** Here is the list of your achievements ******************");
        System.out.println("\n\t\t\t====== UNLOCKED ACHIEVEMENTS ======");
        if (!achievementList.isEmpty()) {
            int l = 0;
            for (int i = 0; i < achievementList.size(); i++) {
                if (!achievementList.get(i).checkLock()) {
                    l++;
                    System.out.print(l + ". ");
                    System.out.println(achievementList.get(i));
                }
            }
            System.out.println("\n\t\t\t======= LOCKED ACHIEVEMENTS =======");
            int j = 0;
            for (int i = 0; i < achievementList.size(); i++) {
                if (achievementList.get(i).checkLock()) {
                    j++;
                    System.out.print(j + ". ");
                    System.out.println(achievementList.get(i));
                }
            }
            System.out.println("\n\t\t\t\tYou currently have: " + totalPoints + " points.");
            System.out.println("*************************************************************************");
        }
    }
}
