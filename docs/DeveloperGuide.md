
# Compal - Developer Guide

Welcome to the **Compal** Developer Guide! This Developer Guide is still being worked on for now!

# Table of Contents
1. [Introduction](https://github.com/AY1920S1-CS2113T-W17->1/main/blob/master/docs/DeveloperGuide.md#user-stories)
 2. Setting Up
 3. Design
 4. Implementation
 5. Documentation
 6. Testing
 7. DevOps

 	* [**Appendix A: User Profile**]( https://github.com/jaedonkey/main/blob/master/docs/DeveloperGuide.md#appendix-a-user-profile)
 	* [**Appendix C: User Stories**](https://github.com/jaedonkey/main/blob/master/docs/DeveloperGuide.md#appendix-c-user-stories)
	* [**Appendix D: Use Cases**](https://github.com/AY1920S1-CS2113T-W17->1/main/blob/master/docs/DeveloperGuide.md#use-case)
	* [**Appendix E: Non-Functional Requirements**](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#non-functional-requirements)
	* [**Appendix F: Glossary**](https://github.com/AY1920S1-CS2113T-W17->1/main/blob/master/docs/DeveloperGuide.md#glossary)



# Introduction
ComPal is a desktop application specifically designed with busy, disorganized students in mind. It is catered to student-users who prefer to use and are adept at using a Command-Line Interface (CLI), while still having a clean Graphical User Interface (GUI) to properly visualize schedules and organize tasks better.


## Appendix A: User Profile

**System**: ComPal

**Target User Profile**: Students who
-   want to better organize their time not just according to deadlines but by perceived priorities
-   prefer interacting with a CLI
-   prefers typing over mouse input

**Persons that can play this role** : undergraduate student, graduate student, a staff member doing a part-time course, exchange student

**Value Proposition**: Students wanting to be more organized without going through too much of a hassle can now better manage their schedules and tasks with Compal’s clean and intuitive user-interface and user-defined priority-based organization.


## Appendix C: User Stories




Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| As a ...|      I want to...     |  So that I can ... | Priority |
|:----------|:-------------:|:------:|:----------|
|Student| Add the due dates of tasks that I have| Neatly organize my schedule|***
|Student| Add my academic timetable| Store my academic schedule|***
|Student| Add meeting schedules| Easily remember about scheduled meetings|***
|Student| Add examination dates and times| View and track upcoming assessments|***
|Student| Add a description to a task that I have| Record necessary information about the task|***
|Student| Edit due dates of tasks that I have| Update the description and deadlines of the tasks|***
|Student| Edit my academic timetable| Update my academic schedule|***
|Student| Edit meeting schedules| Update my appointment timings|***
|Student| Edit examination dates and times| Update assessment dates|***
|Student| View the application in a graphical user interface| View things in an organised and quick manner|***
|Student| View the tasks that are soon to be overdue| Keep track of the things to do|***
|Student| View the timetable in a daily view| See the overview of the whole day|***
|Student| View my ongoing school-related task| Keep track of my progress|***
|Student| Be notified of my classes to attend| Better manage my schedule|***
|Student| Be notified of the tasks due| Better manage my schedule|***
|Student| Be notified of upcoming examinations| Better manage my schedule|***
|Student| Be notified of upcoming meetings| Better manage my schedule|***
|Student| Sort my tasks according to the deadlines and importance| Know which task needs to be focused on|***
|Student| Find specific things in the application using a keyword| Find related things|***
|Student| Remove a scheduled slot| Delete cancelled meetings/classes|***
|Student| Remove tasks| Delete irrelevant tasks|***
|Student| Priortise more important timetable slots based on personal ranking| rearrange my schedule in the event that there is a timetable clash|***
|Student| View the timetable in a monthly view| See the overview of the whole month|**
|Student| View the timetable in a weekly view| See the overview of the whole week|**
|Student| Mark my ongoing school-related task as completed by task and subtask| Keep track of the progress of individual task and subtasks|**
|Student| Track my assignment progress| Know what needs to be done|**
|Student| Add the result/grade of module assignment, attendance, midterm results|Store module's component grades|*
|Student| Add my received module grades for each semester| Store the semester's grades|*
|Student| Edit the result/grade of module assignment, attendance, midterm results| Estimate the grade that I will receive|*
|Student| Track my cumulative GPA| Work towards the GPA I aim for|*




## Appendix D: Use Cases



**Use case 1: Store Academic Timetable**

1.  User enters command to store timetable
    
2.  ComPal prompts you for the timetable details

		    
		1.  Number of Modules
		2.  Session type(enum lab lecture tutorial sectional)
		3.  Day, Period and Duration   
		4.  Priority
    

4.  User enters in timetable manually
    
5.  ComPal displays the timetable
    

  

**Use case 2: Store a Schedule/Tasks**

1.  User enters command to store a schedule
    
2.  compal prompts for the task type.
    

1.  Assignment(DEADLINE)
    
2.  Meeting (Event)
    
4.  User enters task type.
    
5.  compal prompts for description.
    
6.  User enters description.
    
7.  compal ask for importance level of schedule
    
8.  User enter (Enum high,med,low)
    
9.  If the task is of low priority, compal prompts user whether to allow task to increase in priority.
    

  

**Use Case 3: Edit Task**

*Prerequisite: User is aware of the TaskID.*

1.  User inputs command to edit task/schedule.
    
2.  compal prompts for task ID.
    
3.  User enters task ID.
    
4.  compal returns task
    
5.  User edits task parameter.
    
6.  compal displays edited task.
    

  
  
  

**Use Case 4: Edit Academic Schedule**

1.  User inputs command to edit academic schedule.
    
2.  compal prompts for academic schedule name.
    

  

**Use Case 5: Change the Views (Daily/Monthly/Weekly)**

1.  User enters command to change view
    
2.  compal displays the selected view on GUI.
    

**Use Case 6: Mark Task as Done**

*Prerequisite: User is aware of the TaskID.*

1.  User enters command to mark task as done
    
2.  compal reflects task status changes
    

  

**Use Case 7: Search for Tasks**

1.  User enter search command
    
2.  compal prompts for keyword
    
3.  User input keyword
    
4.  compal reflects search results

## Appendix E: Non-Functional Requirements
1.  ComPal stores the academic calendar of NUS for up to 10 years, provided that NUS does not update its calendar.
    
2.  ComPal prompts for academic time table at the beginning of every semester.
    
3.  Compal can store up to 1,000,000 tasks in a clear text file.
    
4.  Compal must respond fast, within 2 seconds so that the user does not have to wait too long.
    
5.  Compal system application does not take up much space on the local machine.
    
6.  ComPal’s GUI must be intuitive and pleasant to the eyes
    
7.  Compal consistently performs specified function without failure
    
8.  The user’s OS must provide the correct time and date for Compal.



## Appendix F: Glossary
**Task**:  A generic term used to refer to any instance of an object in the user's schedule
**View**: The layout in which the schedule is displayed to the user (Daily/Weekly/Monthly)
