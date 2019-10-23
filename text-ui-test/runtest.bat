@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
REM create a new file listing all your Java sources
type nul > all.javas
REM find all Java source files


javac  -cp ..\src -Xlint:none -d ..\bin ..\src\main\java\duke.Duke.java ..\src\main\java\exceptions\*.java ..\src\main\java\storage\*.java ..\src\main\java\duke.task\*.java ..\src\main\java\parser\*.java ..\src\main\java\duke.ui\*.java ..\src\main\java\wrapper\*.java

IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin duke.Duke < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT

