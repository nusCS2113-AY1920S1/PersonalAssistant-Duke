package duchess.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonGetter;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSetter;

import java.util.ArrayList;
import java.util.List;

public class Module {
    private String name;
    private String code;
    private List<Grade> grades;

    /**
     * Creates a new Module.
     *
     * @param code the code of the module
     * @param name the mame of the module
     */
    @JsonCreator
    public Module(@JsonProperty("code") String code, @JsonProperty("name") String name) {
        this.code = code;
        this.name = name;
        grades = new ArrayList<>();
    }

    public String toString() {
        return this.code + " " + this.name;
    }

    public boolean isOfCode(String code) {
        return this.code.equalsIgnoreCase(code);
    }

    public boolean equals(Module that) {
        return this.code.equalsIgnoreCase(that.code);
    }

    @JsonGetter("name")
    public String getName() {
        return name;
    }

    @JsonGetter("code")
    public String getCode() {
        return code;
    }

    @JsonSetter("grades")
    public void setGrades(List<Grade> grades) {
        this.grades = grades;
    }

    @JsonGetter("grades")
    public List<Grade> getGrades() {
        return grades;
    }

    public void addGrade(Grade grade) {
        this.grades.add(grade);
    }
}
