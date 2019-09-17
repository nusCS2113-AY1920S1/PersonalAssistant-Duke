import java.util.Scanner;

public class Ui{
    String line = "   ___________________________________________________\n";

    String format(String output){
        return line + output + line;
    }
    private boolean is_exit;

    public Ui(){
        this.is_exit = false;
    }

    public boolean exit(){
        return is_exit;
    }

    public void showWelcome(){
        System.out.print(format("   Hello! I'm Duke\n   What can I do for you?\n"));
    }

    public String readCommand(){
        Scanner sc = new Scanner(System.in);
        return sc.nextLine();
    }

    public void showGoodbye(){
        is_exit = true;
        System.out.print(format("   Bye. Hope to see you again soon! \n"));
    }

    public void showAction(String action){
        System.out.print(format(action));
    }
}
