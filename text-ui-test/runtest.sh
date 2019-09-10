#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./actual.txt" ]
then
    rm actual.txt
fi

# find all files
if [ -e "./sources.txt" ]
then
    rm sources.txt
fi

# check if data directory exist
if [ -d "../data" ]
then
    rm -rf ../data
fi

find ../src/main/ -name "*.java" > sources.txt
# compile the code into the bin folder, terminates if error occurred
if ! javac @sources.txt -d ../bin
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

if [ -d "sources.txt" ]
then
    rm -rf sources.txt
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -cp ../bin duke.Main < input.txt > actual.txt
# compare the output to the expected output
if [ -d "./data" ]
then
    rm -rf ./data
fi
diff actual.txt expected.txt
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi
