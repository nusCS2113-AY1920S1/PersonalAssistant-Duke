package parser;

public enum Flag {
    BY("/by"),
    AT("/at"),
    BETWEEN("/between"), FOR("/for");

    private String flag;

    private Flag(String flag) {
        this.flag = flag;
    }
}



