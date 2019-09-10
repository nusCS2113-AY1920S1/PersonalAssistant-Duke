public class ToDos extends Task {

    protected boolean isToDo;

    public ToDos(String description) {
        super(description);
        this.isToDo = true;
    }

    @Override
    public String toString() {
        return "[T]" + super.printStatus();
    }

    public String txtFormat() {
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }


    public String writeTxt(){
        return "T | " + (this.isDone ? "1" : "0") + " | " + this.description;
    }

}