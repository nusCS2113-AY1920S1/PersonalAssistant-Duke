


# Compal - Developer Guide

Welcome to the **COMPal** Developer Guide! This Developer Guide is still being worked on for now!

**Appendix A**
 - [User Stories](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#user-stories)
 - [Use Case](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#use-case)
 - [Non-Functional Requirements](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#non-functional-requirements)


# User Stories
Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| As a ...|      I want to...     |  So that I can ... | Priority |
|:----------|:-------------:|:------:|:----------|
|Student| Add the due dates of tasks that I have| Neatly organize my schedule|***
|Student| Add my academic timetable| Store my academic schedule|***
|Student| Add meeting schedules| Easily remember about scheduled meetings|***
|Student| Add examination dates and times| View and track upcoming assessments|***
|Student| Add the result/grade of module assignment,attendance,midterm results|Store module's component grades|*
|Student| Add my received module grades for each semester|Store the semester's grades|*
|Student| Add a description to a task that I have | Record necessary information about the task|***
# Use Cases

System: NUSPal

**Actors:** Student
**Persons that can play this role** : undergraduate student, graduate student, a staff member doing a part-time course, exchange student


Use case 1: Store Academic Timetable

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
    
2.  COMPal prompts for the task type.
    

1.  Assignment(DEADLINE)
    
2.  Meeting (Event)
    
4.  User enters task type.
    
5.  COMPal prompts for description.
    
6.  User enters description.
    
7.  COMPal ask for importance level of schedule
    
8.  User enter (Enum high,med,low)
    
9.  If the task is of low priority, COMPal prompts user whether to allow task to increase in priority.
    

  

**Use Case 3: Edit Task**

*Prerequisite: User is aware of the TaskID.*

1.  User inputs command to edit task/schedule.
    
2.  COMPal prompts for task ID.
    
3.  User enters task ID.
    
4.  COMPal returns task
    
5.  User edits task parameter.
    
6.  COMPal displays edited task.
    

  
  
  

**Use Case 4: Edit Academic Schedule**

1.  User inputs command to edit academic schedule.
    
2.  COMPal prompts for academic schedule name.
    

  

**Use Case 5: Change the Views (Daily/Monthly/Weekly)**

1.  User enters command to change view
    
2.  COMPal displays the selected view on GUI.
    

**Use Case 6: Mark Task as Done**

*Prerequisite: User is aware of the TaskID.*

1.  User enters command to mark task as done
    
2.  COMPal reflects task status changes
    

  

**Use Case 7: Search for Tasks**

1.  User enter search command
    
2.  COMPal prompts for keyword
    
3.  User input keyword
    
4.  COMPal reflects search results

# Non-Functional Requirements
1.  ComPal stores the academic calendar of NUS for up to 10 years, provided that NUS does not update its calendar.
    
2.  ComPal prompts for academic time table at the beginning of every semester.
    
3.  COMPal can store up to 1,000,000 tasks in a clear text file.
    
4.  COMPal must respond fast, within 2 seconds so that the user does not have to wait too long.
    
5.  COMPal system application does not take up much space on the local machine.
    
6.  ComPal’s GUI must be intuitive and pleasant to the eyes
    
7.  COMPal consistently performs specified function without failure
    
8.  The user’s OS must provide the correct time and date for COMPal.



# Glossary
**Task**: 
**View**: The layout in which the schedule is displayed to the user (Daily/Weekly/Monthly)
