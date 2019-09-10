package Task;

public class ToDo extends item {

    public ToDo(String info, Boolean status) {
        super(info, status);
        super.setType("T");
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }

}
