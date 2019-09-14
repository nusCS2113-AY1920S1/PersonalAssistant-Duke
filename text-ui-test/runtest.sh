#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "/home/tessa/Documents/CS2113/duke/bin" ]
then
    mkdir /home/tessa/Documents/CS2113/duke/bin
fi

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
    rm ACTUAL.TXT
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp /home/tessa/Documents/CS2113/duke/src -Xlint:none -d /home/tessa/Documents/CS2113/duke/bin /home/tessa/Documents/CS2113/duke/src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath /home/tessa/Documents/CS2113/duke/bin Duke < input.txt > ACTUAL.TXT

# compare the output to the expected output
#diff ACTUAL.TXT EXPECTED.TXT
diff <(tail -n +10 ACTUAL.TXT | head -n -4) <(tail -n +10 EXPECTED.TXT | head -n -4)
if [ $? -eq 0 ]
then
    echo "Test result: PASSED"
    exit 0
else
    echo "Test result: FAILED"
    exit 1
fi