# Project Portfolio - Ho Si Shi Annette
## 1. Project Overview
This portfolio describes the contributions that I made to my school project, [WalletCLi](https://github.com/AY1920S1-CS2113T-W17-2/main).  As a team with 3 other CS2113T Software Engineering students, our group implemented a Command Line Interface application, called **WalletCLi**. 

**WalletCLi** primarily caters to NUS students and staff who prefer to use a desktop application for managing their monthly expenditure. Users can store their records of their expenses and loans with WalletCLi. In addition, **WalletCLi** provide features for users to keep track of their monthly budget and analyse their expenditure through statistics. On top of these features, users can export and import data to and from **WalletCLi**. The application is written in Java and compiled into jar files for users to run and use.
### 1.1 About this Portfolio
## 2. Summary of Contributions
This section shows a summary of contributions which I made for **WalletCLi**.
### 2.1 Code Contributed
A collation of my code files and lines of codes I written can be viewed from this link:
[Code Contribution Report](https://nuscs2113-ay1920s1.github.io/dashboard/#=undefined&search=Xdecosee)
### 2.2 Features Implemented
#### Contact Management
* _Purpose_: Users will be required to tag each loan record with the contacts details of the person whom they borrow or lend money to. 
* _How it Works_: Users can manage their contact list by adding, editing or deleting contacts. Each contact will require the name of the contact and optional user input which is the contact's phone number and other details.
#### Import Data
* _Purpose_: Allow users to transfer records of their loans or expenses from CSV files to **WalletCLi**. 
* _Justification_: For users who are not comfortable with Command Line interface, they can still utilise our statistics feature to have a quick analysis their monthly expenditure by importing their data into **WalletCLi**.
* _How it Works_: User will have to let **WalletCLi** know what is the name of the file which they want to import expenses or loan records from. The file has to be formatted correctly and placed within the same directory as the running application for **WalletCLi** to detect.
* _Resource Credit_: [OpenCSV](http://opencsv.sourceforge.net/)
#### Export Data
* _Purpose_: Allow users to transfer records of their loans or monthly expenses from **WalletCLi** to CSV files.
* _Justification_: Aside from personal expenses, users can utilize **WalletCLi** to track group expenses that they are in charge of. Group expenses can be household expenses, one-off holiday expenses or event and project budgeting. In view of this, this feature will be useful when users want to send a copy of the data with other people.
* _How it Works_: When users request to export loans or expenses from a particular month, records will be retrieved and written into CSV files and saved into the same directory as the running application.
* _Resource Credit_: [OpenCSV](http://opencsv.sourceforge.net/)
#### Help Section
* _Purpose_: Users can easily refer to the different command syntaxes when running the application.
* _How it Works_:  **WalletCLi** groups sets of commands into their own help section and user can select a section to read. **WalletCLi** will provide the command format, command description and usage examples.
### 2.3 Other Contributions
#### Tools
* _Integrated [OpenCSV library](http://opencsv.sourceforge.net/) to project (for export/import feature)_: [#97](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/97)
* _Integrated [Gradle Shadow](https://github.com/johnrengelman/shadow) to compile project code into jar_: [#42](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/42)
* _Added License for project code_: [#64](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/64)
#### Community
* _Reviewed Github Pull Requests_ : [#44](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/44), [#51](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/51), [#52](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/52), [#58](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/58), [#71](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/71), [#78](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/78), [#80](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/80), [#84](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/84), [#103](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/103), [#106](https://github.com/AY1920S1-CS2113T-W17-2/main/pull/106)
## 3. Documentation Contribution
### 3.1 User Guide
### 3.2 Developer Guide
