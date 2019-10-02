# Duke - Developer Guide

1. Setting Up

2. Design 

3. Implementation

4. Documentation

5. Testing

6. Dev Ops

   Appendix A: Product Scope

   Appendix B: User Stories

   Appendix C: Use Cases

   Appendix D: Non Functional Requirements 

   Appendix E: Glossary 

   Appendix F: Product Survey 

   Appendix G: Instructions for Manual



### 1. Setting Up

- see the Guide [Here]() 

### 2. Design 

#### 2.1  Architecture

![architecture](https://github.com/9hafidz6/main/blob/master/docs/images/architecture.png)

`main` has 1 class called `Duke`. It is responsible for,

- at app launch: Loads all the data in storage into the application, initialize the  components, reads the commands and executes them correctly.
- at shut down: shuts down all component and exits the application

The Application consist of 6 other components 

- `command`: executes the command that is read from the user

- `exception`: handle error messages 
- `parser`: determine the next course of action from the user command
- `storage`: Reads, writes data from and to the hard disk
- `task`: stores a list of deadline/event/todo that needs to be done
- `ui`: The UI of the application

#### 2.2 UI


API: `Ui.java`

The Ui class consists of methods that outputs messages to the user as a response when the user enters a certain command

The Ui component,

- reads and return s user input using `scanner.nextLine()`
- outputs messages to the user as a response such as `showAddCommand`, `showRemoveCommand`, etc

#### 2.3 Command Component

API: `Command.java`

The Command class is used as an abstract class for other classes, its method `execute` is also declared as an abstract method that is used by the following classes 

- DoneCommand
- ExitCommand
- FindCommand
- ListCommand
- RemindCommand
- Snooze
- ViewCommand

each of the above class has its own implementation of the `execute` method

#### 2.4 Parser Component

API: `Parser.java`



#### 2.5 Storage Component

only stores tasks in a certain format, eg,

- T|1|read book
- D|0|return book|Monday
- E|0|meeting|U-Town
- P|0|lecture|1600|1800

first column is denotes the type of task, T for todo, D for deadline, etc. 

the program can `load` or `generate` task from the storage and also `changeContent` and `addCommandInFile`



#### 2.6 Task Component

API: `Task.java`



#### 2.7 Exception Component

API: `DukeException.java`



### 3. Implementation



### 4. Documentation



### 5. Testing



### 6. Dev Ops 



### Appendix A: Product Scope

Target user profile: Restaurant Chef

- needs to manage all the ingredients for his dishes  
- prefers a desktop application with GUI
- prefers to keep everything neat in terms of viewing information
- can also use CLI if needed
### Appendix B: User Stories

​	Priorities: High (must have), Medium (nice to have), Low (may not have)

*the user stories might change as our user profile may become kitchen chef instead of restaurant manager* 

| Priority | As a ...           | I want to ...                                 | So that ...                                 |
| -------- | ------------------ | --------------------------------------------- | ------------------------------------------- |
| High     |                    |                                               |                                             |
| High     |                    |                                               |                                             |
| High     |                    |                                               |                                             |
| Medium   |                    |                                               |                                             |
| Medium   |                    |                                               |                                             |
| Low      | restaurant manager | note down customer feedbacks regarding dishes | i can tell if y recipe is doing well or not |
| Low      |                    |                                               |                                             |
|          |                    |                                               |                                             |
|          |                    |                                               |                                             |

### Appendix C: Use Case



### Appendix D: Non Functional Requirement



### Appendix E: Glossary 



### Appendix F: Product Survey



### Appendix G: Instruction for Manual Testing 





 

