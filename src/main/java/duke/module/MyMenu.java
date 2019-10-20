package duke.module;

/*
* To add all the main menu list
* 1. Manage Students
* 2. Training Schedule
* 3. Training Circuits
*
 */
public class MyMenu {
    /**
     * The string representing the category of the menu.
     */
    private String myCategory;

    /**
     * The string representing the name of the menu.
     */
    private String menu;

    /**
     * Constructor for the menu object.
     * @param category The string representing the category of the menu.
     * @param myMenu The string representing the name of the menu.
     */
    public MyMenu(final String category, final String myMenu) {
        this.myCategory = category;
        this.menu = myMenu;
    }

    /**
     * Getter function to obtain the category of the menu.
     * @return The string representing the category of the menu.
     */
    public final String getMyCategory() {
        return myCategory;
    }

    /**
     * Getter function to obtain the name of the menu.
     * @return The string representing the name of the menu.
     */
    public final String getMenu() {
        return menu;
    }

//    public void trainingScheduleHeading() {
//        System.out.flush();
//        System.out.println("TRAINING SCHEDULE: \n");
//    }
//    public void manageStudentsHeading() {
//        System.out.flush();
//        System.out.println("MANAGE STUDENTS: \n");
//    }
//    public void trainingProgramHeading() {
//        System.out.flush();
//        System.out.println("TRAINING PROGRAM: \n");
//    }
}
