package Enums;

public enum Color {
    RED("\u001b[31;1m"),
    BLUE("\u001b[38;5;117m"),
    YELLOW("\u001b[33;1m"),
    BRIGHTYELLOW("\u001b[38;5;11m"),
    ORANGE("\u001b[38;5;202m"),
    BRIGHTRED("\u001b[38;5;196m"),
    GREEN("\u001b[38;5;118m"),
    RESET("\u001b[0m");

    private String code;

    Color(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return code;
    }
}
