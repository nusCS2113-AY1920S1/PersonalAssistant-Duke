#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
#if [ -e "./ACTUAL.TXT" ]
#then
#    rm ACTUAL1.TXT
#fi

# compile the code into the bin folder, terminates if error occurred
if ! find ../src/main/java/**/ -name "*.java" -print | xargs javac -cp ../src/main/java -Xlint:none -d ../bin
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi


#                 TEST commande list after read of a file
# run the program, feed commands from input1.txt file and redirect the output to the ACTUAL1.TXT
java -classpath ../bin leduc.Duke test1/test1.txt < test1/input1.txt > test1/ACTUAL1.TXT

# compare the output to the expected output
diff test1/ACTUAL1.TXT test1/EXPECTED1.TXT
if [ $? -eq 0 ]
then
    echo "TEST setWelcomeCommand // Test result: PASSED"
    echo ""
else
    echo "TEST setWelcomeCommand // Test result: FAILED"
    exit 1
fi

#                 TEST command done
java -classpath ../bin leduc.Duke test2/test2.txt < test2/input2.txt > test2/ACTUAL2.TXT

# compare the output to the expected output
diff test2/ACTUAL2.TXT test2/EXPECTED2.TXT
if [ $? -eq 0 ]
then
    echo "TEST done // Test result: PASSED"
    echo ""
else
    echo "TEST done // Test result: FAILED"
    exit 1
fi

#                 TEST exception
java -classpath ../bin leduc.Duke test3/test3.txt < test3/input3.txt > test3/ACTUAL3.TXT

# compare the output to the expected output
diff test3/ACTUAL3.TXT test3/EXPECTED3.TXT
if [ $? -eq 0 ]
then
    echo "TEST Exception // Test result: PASSED"
    echo ""
else
    echo "TEST Exception // Test result: FAILED"
    exit 1
fi

#                 TEST todo/deadline/event + delete
java -classpath ../bin leduc.Duke test4/test4.txt < test4/input4.txt > test4/ACTUAL4.TXT

# compare the output to the expected output
diff test4/ACTUAL4.TXT test4/EXPECTED4.TXT
if [ $? -eq 0 ]
then
    echo "TEST todo/deadline/event + delete  // Test result: PASSED"
    echo ""
else
    echo "TEST todo/deadline/event + delete  // Test result: FAILED"

fi

#                 TEST find
java -classpath ../bin leduc.Duke test5/test5.txt < test5/input5.txt > test5/ACTUAL5.TXT

# compare the output to the expected output
diff test5/ACTUAL5.TXT test5/EXPECTED5.TXT
if [ $? -eq 0 ]
then
    echo "TEST find  // Test result: PASSED"
    echo ""
    exit 0
else
    echo "TEST find  // Test result: FAILED"
    exit 1
fi