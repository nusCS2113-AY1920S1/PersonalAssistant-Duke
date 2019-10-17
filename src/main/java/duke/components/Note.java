package duke.components;

public class Note {

    private Pitch pitch;
    private String duration; // 1, 2*, 2, 4*, 4, 8
    private boolean isStart;

    /**
     * Constructor for the Note object, taking in a String representation of the note.
     *
     * @param description A String representation of the Note object to be created of the form [DURATION]_[PITCH]
     *                    E.g. 4_UA represents an A note from the upper octave with the duration of a 1/4 note.
     */
    public Note(String description) {
        String[] characteristics = description.split("_", 2);
        this.duration = characteristics[0];
        this.isStart = true;
        switch (characteristics[1]) {
        case "LC":
            this.pitch = Pitch.LOWER_C;
            break;
        case "LD":
            this.pitch = Pitch.LOWER_D;
            break;
        case "LE":
            this.pitch = Pitch.LOWER_E;
            break;
        case "LF":
            this.pitch = Pitch.LOWER_F;
            break;
        case "LG":
            this.pitch = Pitch.LOWER_G;
            break;
        case "LA":
            this.pitch = Pitch.LOWER_A;
            break;
        case "LB":
            this.pitch = Pitch.LOWER_B;
            break;
        case "MC":
            this.pitch = Pitch.MIDDLE_C;
            break;
        case "UD":
            this.pitch = Pitch.UPPER_D;
            break;
        case "UE":
            this.pitch = Pitch.UPPER_E;
            break;
        case "UF":
            this.pitch = Pitch.UPPER_F;
            break;
        case "UG":
            this.pitch = Pitch.UPPER_G;
            break;
        case "UA":
            this.pitch = Pitch.UPPER_A;
            break;
        case "UB":
            this.pitch = Pitch.UPPER_B;
            break;
        case "UC":
            this.pitch = Pitch.UPPER_C;
            break;
        default:
            this.pitch = Pitch.REST;
        }
    }

    public Note(String duration, Pitch pitch) {
        this.duration = duration;
        this.pitch = pitch;
        this.isStart = true;
    }

    public void setStart(boolean val) {
        this.isStart = val;
    }

    public boolean isStart() {
        return isStart;
    }

    public Pitch getPitch() {
        return pitch;
    }

    public String getDuration() {
        return duration;
    }

    public Note getUnitNote() {
        return new Note("8", this.pitch);
    }

    /**
     * Returns the relative duration of the note in the form of a float. Asterisks (*) increase the relative duration
     * by 50%, just like the dotted notes in sheet music (notes that are followed by a ·)
     *
     * @return the relative duration of the note in the form of a float.
     */
    public float getNumericalDuration() {
        float result = (float) (1.0 / Character.getNumericValue(this.duration.charAt(0)));
        if (this.duration.length() == 2) {
            result *= 1.5;
        }
        return result;
    }

    /**
     * Returns an integer that is equivalent its duration relative to an 1/8 note.
     *
     * @return an integer representing how many times of an 1/8 note the duration is
     */
    public int getRelativeUnitDuration() {
        switch (this.duration) {
        case "1": return 8;
        case "2*": return 6;
        case "2": return 4;
        case "4*": return 3;
        case "4": return 2;
        case "8": return 1;
        default: return -1;
        }
    }

}
