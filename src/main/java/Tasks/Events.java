package Tasks;

public class Events extends Task{

    protected String at;

    public Events(String description, String at) {
        super(description);
        this.at = at;
        super.type = "E";
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + "(at: " + at + ")";
    }

    public String getAt(){
        return at;
    }

}