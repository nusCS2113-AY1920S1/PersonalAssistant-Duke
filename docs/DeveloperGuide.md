

# COMPal - Develepor Guide

Welcome to **COMpal** Developer Guide! This Developer guide is still being work on for now!

**Appendix A**
 - [User Stories](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#user-stories)
 - [Use Case](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#use-case)
 - [Non-Functional Requirements](https://github.com/AY1920S1-CS2113T-W17-1/main/blob/master/docs/DeveloperGuide.md#non-functional-requirements)


# User Stories
Priorities: High (must have) - `* * *`, Medium (nice to have) - `* *`, Low (unlikely to have) - `*`

| As a ...|      I want to...     |  So that i can ... | Priority |
|:----------|:-------------:|:------:|:----------|
| user | login the application with a password | be the only one to have access to the application  |
| computing student | to use command based interface | to get used to it  |
| busy student | keep everything academic related in a single app |  easy access to all school related activities stored |
| student | store different type of school-related task based on priority ranking | better manage my school schedule |
| student | store school contacts based on module,discipline & courses tag | better manage my school contact |
| student | store my various username & passwords securely | access school related system with stored username and password |
| student | store my own timetable | view my school schedule |
| student | store my daily school expenses | track my expenses |
| student | store the timetables of my friends  |  sync our schedules and organise study sessions together |
| student | store the result/grade of module assignment,attendance,midterm results | estimate received grade.  |
| student | store my received module grades for each semsester | |
| student | store school-related notes for each module | note down things that are being taght in lecture |
| student | store module exam dates,midterms and test | keep track of the dates given |
| student  | view the timetable of my friends or group mates |  organise team meetings that do not clash with our schedules   |
| new user | view the user guide easily | learn how to use the app  |
| student  | view my individual module component results for that semester | gauge my expected results |
| student  | view my ongoing school-related task | keep track of what of my progress is |
| student  | mark my ongoing school-related task as completed based by task & subtask |  |
| forgetful student | track my assignment progress | know what is needed to be done |
| student           | track my Cumlative GPA | work towards the GPA I aim for  |
| forgetful student | be notified of my classes to attend |  better manage my schedule |
| forgetful student | be notified of school-related task due |  better manage my schedule |
| forgetful student | be notified of upcoming midterms & exam |  better manage my schedule |
| student | sort my contact stored based by modules,group,course & disciple taken |  |
| student | sort my tasks according to the deadlines & importance set | be more organised |
| student | find specified things in the application using a keyword | find related thing |
| student | send emails of school contact in my contact list  |  |
| student  | share my contact information (School E-mail,Phone number) with school peers easily |  | 
| student  | plan my future module to be taken | |
| student  | prioritise more important modules then other |  I do not lag too far behind in the one im weaker in  |

# Use Case

System: NUSPal

**Actors: Student**
**Persons that can play this role** : undergraduate student, graduate student, a staff member doing a part-time course, exchange student

**Use case 1:** Track a task

 1. User enters a task into the command prompt
 2. NUSPal prompts user for more details about the task
    	
     - List item
     -  Task Type
     -  Importance
     - Deadline date
    - Description
     - Module Code
 3.   User enters the relevant information
    
 4.  NUSPal provides an acknowledgement that the task has been added

**Use case 2: Store Timetable**

1.  User enters command to store timetable    
2.  NUSPal prompts you for the timetable details
    - Number of mods
    - Session type(enum lab lecture tutorial sectional)
    - Day, Period and Duration
    - Priority
3.  User enters in time table manually
    
4.  NUSPal displays the timetable


**Use case 3: Store contacts**

1.  User enters command to store contacts.
    
2.  NUSPal prompts user for name and email address.
    
3.  NUSPal prompt category to store
	- Prof
	- Students
	- TA
	- Tags(Optional)

**Use case 4: Store Notes for modules**

1.  User enter command to store notes
    
2.  NUSPal prompt user for title & desc/module code
    
3.  User enters in title & desc/module code
    
4.  NUSPal prompt user for note content
    
5.  User enters content 


**Use case 5: Store Passwords Securely**  

1.  User enter commands to store password  
      
2.  NUSPal prompts for master password
    
3.  User enters master password
    
4.  User enter Description & Password
    
5.  NUSPal prompt for re-password for confirmation  


**Use case 6: Access Passwords**

1.  User enter commands to access password list
    
2.  NUSPal request master password
    
3.  User search by description

# Non-Functional Requirements

- Should work on any mainstream OS as long as it has Java 9 or higher installed.

- Should be able to hold up to 1000 prints without a noticeable sluggishness in performance for typical usage.

- A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.
