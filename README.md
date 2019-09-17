# AY1920S1-CS2113T-W17-G1

**Team Members:**

1. JAEDON KEE SHAOWEI
2. LIU PEIZE
3. MUHAMMAD SHOLIHIN BIN KAMARUDIN
4. TAN KAI LI CATHERINE
5. YUE JUN YI

# Changelog V1.1
- viewing of tasks on a specific date
  * displays the tasks for that date
  * usage: view <dd/mm/yyyy> 
- reminders
  * ComPAL shows reminders of tasks due within a week and tasks with reminders set
  * NOTE: setting reminders is not yet implemented
- new task type added: doaftertask
  * task that can be done only after a certain date
  * usage: doaftertask < descriptive name> /after <dd/mm/yyyy hhmm>
- new task type added: fixeddurationtask
  * task that have a fixed duration
  * usage: fixeddurationtask < descriptive name> /on <dd/mm/yyyy hhmm> /for < number of hours> hours < number of minutes> minutes






# Setting up

**Prerequisites**

* JDK 11
* Recommended: IntelliJ IDE
* Fork this repo to your GitHub account and clone the fork to your computer

**Importing the project into IntelliJ**

1. Open IntelliJ (if you are not in the welcome screen, click `File` > `Close Project` to close the existing project dialog first).
1. Set up the correct JDK version.
   * Click `Configure` > `Structure for new Projects` (in older versions of Intellij:`Configure` > `Project Defaults` > `Project Structure`).
   * If JDK 11 is listed in the drop down, select it. If it is not, click `New...` and select the directory where you installed JDK 11.
   * Click `OK`.
1. Click `Import Project`.
1. Locate the project directory and click `OK`.
1. Select `Create project from existing sources` and click `Next`.
1. Rename the project if you want. Click `Next`.
1. Ensure that your src folder is checked. Keep clicking `Next`.
1. Click `Finish`.

# Tutorials 

Duke Increment | Tutorial
---------------|---------------
`A-Gradle` | [Gradle Tutorial](tutorials/gradleTutorial.md)
`A-TextUiTesting` | [Text UI Testing Tutorial](tutorials/textUiTestingTutorial.md)
`Level-10` | JavaFX tutorials:<br>→ [Part 1: Introduction to JavaFX][fx1]<br>→ [Part 2: Creating a GUI for Duke][fx2]<br>→ [Part 3: Interacting with the user][fx3]<br>→ [Part 4: Introduction to FXML][fx4]

[fx1]: <tutorials/javaFxTutorialPart1.md>
[fx2]: <tutorials/javaFxTutorialPart2.md>
[fx3]: <tutorials/javaFxTutorialPart3.md>
[fx4]: <tutorials/javaFxTutorialPart4.md>

# Feedback, Bug Reports

* If you have feedback or bug reports, please post in [se-edu/duke issue tracker](https://github.com/se-edu/duke/issues).
* We welcome pull requests too.
