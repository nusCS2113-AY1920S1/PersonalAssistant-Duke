package Enums;

public enum Color {
    RED("\u001b[31;1m"),
    BLUE("\u001b[38;5;117m"),
    YELLOW("\u001b[33;1m"),
    GREEN("\u001b[38;5;118m"),
    MAGNETA("\u001b[35;1m"),
    CYAN("\u001b[36;1m"),
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
