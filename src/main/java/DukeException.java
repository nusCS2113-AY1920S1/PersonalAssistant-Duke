public class DukeException extends Exception {
    enum ExceptionType{
        INVALID_COMMAND,
        INVALID_TODO,
        INVALID_DEADLINE,
        INVALID_EVENT,
        DEADLINE_TIME,
        EVENT_TIME,
        OUT_OF_RANGE,
        INVALID_DONE,
        UNKNOWN,
        INVALID_ARGUMENT
    }

    private ExceptionType type;

    public DukeException(String message, ExceptionType type){
        super(message);
        this.type = type;
    }

    public void PrintExceptionMessage(){
        if(this.type.equals( ExceptionType.INVALID_COMMAND)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! I'm sorry, but I don't know what that means :-(");
            Ui.print_line();
        }else if(this.type.equals(ExceptionType.INVALID_TODO)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! The description of a todo cannot be empty.");
            Ui.print_line();
        }else if (this.type.equals(ExceptionType.INVALID_DEADLINE)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! The description of a deadline cannot be empty.");
            Ui.print_line();
        }else if(this.type.equals(ExceptionType.INVALID_EVENT)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! The description of a event cannot be empty.");
            Ui.print_line();
        }else if(this.type.equals(ExceptionType.EVENT_TIME)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! The time of a event cannot be empty.");
            Ui.print_line();
        }else if(this.type.equals(ExceptionType.DEADLINE_TIME)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! The time of a deadline cannot be empty.");
            Ui.print_line();
        }else if (this.type.equals(ExceptionType.OUT_OF_RANGE)){
            Ui.print_line();
            System.out.println("☹ OOPS!!! THe item you have selected is out of range.");
            Ui.print_line();
        }else if(this.type.equals(ExceptionType.UNKNOWN)){
            System.out.println("☹ OOPS!!! WTF SOMETHING WEIRED HAPPENED");
        }else if(this.type.equals(ExceptionType.INVALID_DONE)) {
            Ui.print_line();
            System.out.println("☹ OOPS!!! The index of mark as done cannot be empty.");
            Ui.print_line();
        }else if(this.type.equals(ExceptionType.INVALID_ARGUMENT)) {
            Ui.print_line();
            System.out.println("☹ OOPS!!! The argument for find cannot be empty!");
            Ui.print_line();
        }
    }
}
