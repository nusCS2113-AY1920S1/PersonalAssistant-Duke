package duke.Task;

/*
* To add al the main menu list
* 1. Manage Students
* 2. Training Schedule
* 3. Training Circuits
*
 */
public class MyMenu {
    private String subMenuDescription;
    private String menu;


    public MyMenu(String subMenuDescription, String menu) {
        this.subMenuDescription = subMenuDescription;
        this.menu = menu;
    }

    public String getSubMenuDescription() {
        return subMenuDescription;
    }

    public String getMenu() {
        return menu;
    }
}
