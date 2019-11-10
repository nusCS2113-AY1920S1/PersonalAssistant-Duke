package chronologer.parser;

public enum Flag {
    BY("/by"),
    AT("/at"),
    BETWEEN("/between"), FOR("/for"),
    IN("/in"),
    DEADLINE("-d"),
    EVENT("-e"),
    TODO("-t"),
    RAW("-r");

    private String flag;

    Flag(String flag) {
        this.flag = flag;
    }

    public String getFlag() {
        return flag;
    }
}



