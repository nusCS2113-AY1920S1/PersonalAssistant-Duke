package duke.command;

public class Switch {

   public String name;
   public Class type;
   public boolean isOptional;
   public ArgLevel argLevel;

   public Switch(String name, Class type, boolean isOptional, ArgLevel argLevel) {
      this.name = name;
      this.type = type;
      this.isOptional = isOptional;
      this.argLevel = argLevel;
   }
}
