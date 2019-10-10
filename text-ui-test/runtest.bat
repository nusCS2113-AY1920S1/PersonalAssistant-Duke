@ECHO OFF

REM create bin directory if it doesn't exist
if not exist ..\bin mkdir ..\bin

REM delete output from previous run
del ACTUAL.TXT

REM compile the code into the bin folder
REM create a new file listing all your Java sources
type nul > all.javas
REM find all Java source files
for /F "tokens=*" %%f in ('dir ..\src\main\java /B/A:-D/S "*.java" 2^>nul') do (
    echo %%f >> all.javas
)

javac  -cp ..\bin\production -Xlint:none -d ..\bin\production @all.javas
IF ERRORLEVEL 1 (
    echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ..\bin\production Duke < input.txt > ACTUAL.TXT

REM compare the output to the expected output
FC ACTUAL.TXT EXPECTED.TXT