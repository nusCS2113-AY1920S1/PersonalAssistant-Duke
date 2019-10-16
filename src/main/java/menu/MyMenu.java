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


    public MyMenu(String myCategory, String menu) {
        this.myCategory = myCategory;
        this.menu = menu;
    }


    public String getMyCategory() {
        return myCategory;
    }

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
