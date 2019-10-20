package menu;

/*
* To add al the main menu list
* 1. Manage Students
* 2. Training Schedule
* 3. Training Circuits
*
 */
public class MyMenu {
    private String myCategory;
    private String menu;


    /**
     * A constructor for the menu list.
     * @param myCategory Category of the menu.
     * @param menu respective sections of the category.
     */
    public MyMenu(final String myCategory, final String menu) {
        this.myCategory = myCategory;
        this.menu = menu;
    }

    /**
     * Method to get the menu.
     * @return list of the sections in the menu.
     */
    public String getMenu() {
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
