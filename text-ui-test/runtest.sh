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

if [ -e "./duke.dat" ]
then
    rm duke.dat
fi

# compile the code into the bin folder, terminates if error occurred
cd ..
dir=$(cd $(dirname ${BASH_SOURCE[0]}); pwd )
cd src/main/java
if ! javac logic.Duke.java -Xlint:none -d ${dir}/bin
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi
cd ${dir}/text-ui-test

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath ../bin logic.Duke < input.txt > ACTUAL.TXT

# compare the output to the expected output
diff ACTUAL.TXT EXPECTED.TXT
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi