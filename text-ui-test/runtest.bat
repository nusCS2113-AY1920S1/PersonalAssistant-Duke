@ECHO OFF

REM create bin and data directories if needed
if not exist ..\bin mkdir ..\bin
if not exist ..\data mkdir ..\data

REM delete output from previous run
if exist "../data/tasks.tsv" del "../data/tasks.tsv"

REM find all Java files
REM NOTE: this will fail if there are any other files with "java" in their name
REM in the source directory
REM TODO figure out a way to check for end of line

type nul > all.javas
for /F "tokens=*" %%f in ('dir ..\src\main\java /B/A:-D/S "*.java" 2^>nul') do (
    echo %%f >> all.javas
)

REM compile the code into the bin folder
javac -cp ..\bin\production -Xlint:none -d ..\bin\production @all.javas
IF ERRORLEVEL 1 (
   echo ********** BUILD FAILURE **********
    exit /b 1
)
REM no error here, errorlevel == 0

REM change to project root so we can open data file
cd ..

REM run the program, feed commands from input.txt file and redirect the output
REM to test.out
java -classpath bin\production duke/Duke < text-ui-test\input.txt > text-ui-test\ACTUAL.txt

REM test recovery of corrupted file
echo "file corrupted!" > data/tasks.tsv
echo n > text-ui-test\n
java -classpath bin\production duke/Duke < text-ui-test\n >> text-ui-test\ACTUAL.txt
del text-ui-test\n

REM test deletion of corrupted file
echo "file corrupted!" > data/tasks.tsv
echo y > text-ui-test\ybye
echo bye >> text-ui-test\ybye
java -classpath bin\production duke/Duke < text-ui-test\ybye >> text-ui-test\ACTUAL.txt
del text-ui-test\ybye

REM compare the output to the expected output
cd text-ui-test
FC ACTUAL.txt EXPECTED.txt
