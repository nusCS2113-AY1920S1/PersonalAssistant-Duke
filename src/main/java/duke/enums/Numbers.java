package duke.enums;

public enum Numbers {
    ZERO(0),
    ONE(1),
    MINUS_ONE(-1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5),
    SIX(6),
    TWENTY_ONE(21),
    TWENTY_TWO(22),
    TWENTY_THREE(23),
    THIRTY_ONE(31);

    public final int value;
    Numbers(int value) {
        this.value = value;
    }
}
