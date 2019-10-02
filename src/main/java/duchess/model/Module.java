package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Module {
    private final String name;

    @JsonCreator
    public Module(@JsonProperty("name") String name) {
        this.name = name.toLowerCase();
    }

    @JsonGetter("name")
    public String toString() {
        return this.name;
    }

    public boolean equals(Module that) {
        return this.name.equals(that.name);
    }
}
