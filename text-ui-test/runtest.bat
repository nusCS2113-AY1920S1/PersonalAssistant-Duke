@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
javac  -cp ..\src\main\java\DIYeats -Xlint:none -d ..\bin ..\src\main\java\DIYeats\*.java ..\src\main\java\DIYeats\commands\*.java ..\src\main\java\DIYeats\exceptions\*.java ..\src\main\java\DIYeats\parsers\*.java ..\src\main\java\DIYeats\storage\*.java ..\src\main\java\DIYeats\ui\*.java ..\src\main\java\DIYeats\foods\*.java
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin DIYeats.Main < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT