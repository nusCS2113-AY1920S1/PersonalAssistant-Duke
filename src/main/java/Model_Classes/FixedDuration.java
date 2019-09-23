package Model_Classes;

public class FixedDuration extends Task {
    private String duration;


    public FixedDuration(String name, String duration) {
        super(name);
        this.duration = duration;
    }


    @Override
    public String toString() {
        return "[TT] " + super.toString() + " (done in: " + duration + " seconds )";
    }
}
