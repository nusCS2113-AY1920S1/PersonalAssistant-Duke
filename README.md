# Project Cube

[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://github.com/AY1920S1-CS2113T-F09-2/main/LICENSE)

Cube is a simple Book-keeping and Inventory Management System targeted for sellers looking to set-up a small online marketplace.

![Cube](docs/images/Ui.png)

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

# Launching Cube

Gradle has been integrated into Cube and can be easily used to run various functions.

**Launching from the Terminal or Command Prompt**

Simply run the command `gradlew {taskName}` in the terminal and Gradle will run the task!
* `gradlew run`: Launches Cube.
* `gradlew test`: Launches JUnit Tests for testing & debugging Cube.

**Launching from Gradle within IntelliJ**

1. Import the Cube project in IntelliJ as a Gradle project if you have not done so.
![Gradle Import](docs/images/GradleImportIntelliJ.png)
1. After importing, IntelliJ IDEA will identify your project as a Gradle project and you will gain access to the Gradle Toolbar.
1. Through the Gradle Toolbar, you will then be able create new run configuration and launch Cube within IntelliJ.
![Gradle Run](docs/images/GradleRunIntelliJ.png)

# Copyright Notice 

**Updated by the team repo creator, LL-Pengfei, on 2019/9/19 22:10PM**

This is the Group Component for the Module Project of the Module CS2113T, Software Engineering & Object-Oriented Programming, in Academic Year AY19/20, Semester 1 (AY19/20S1), conducted in School of Computing (SoC), National University of Singapore (NUS).

The authors and the copyright holder of this repository are the 5 AY19/20S1 CS2113T students in the Group CS2113T-F09-2. The authors have built and constantly updated this repository after forking the original repository materials from the NUS SoC CS2113T learning materials ([PersonalAssistant-Duke at NUSCS2113-AY1920S1](https://github.com/nusCS2113-AY1920S1/PersonalAssistant-Duke)).

**Plagiarism is strictly prohibited under NUS rules and regulations. No copying of the codes/any of the code segments for any purposes.** Please kindly refer to [the CS2113T Module Policy on plagiarism](https://nuscs2113-ay1920s1.github.io/website/admin/appendixB-policies.html#policy-on-plagiarism) if you attempt to copy the codes/code segments from this repository.
