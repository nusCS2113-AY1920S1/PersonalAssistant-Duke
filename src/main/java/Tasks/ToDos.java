package Tasks;

public class ToDos extends Task{

    public ToDos(String description) {
        super(description);
        super.type = "T";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
