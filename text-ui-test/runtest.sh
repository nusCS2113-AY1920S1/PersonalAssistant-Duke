#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# Goes back up one directory 
cd .. 

# compile the code into the bin folder, terminates if error occurred
if ! ./gradlew build
then
    echo "** BUILD FAILURE **"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -jar ./build/libs/duke-0.1.3.jar < ./text-CLIView-test/input.txt > ./text-CLIView-test/ACTUAL.TXT

# compare the output to the expected output
diff -b ./text-CLIView-test/ACTUAL.TXT ./text-CLIView-test/EXPECTED.txt
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
