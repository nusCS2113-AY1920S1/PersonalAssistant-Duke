#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete the data file if it exists (for testing purposes)
if [ -e "tasks.dat" ]
then
  rm tasks.dat
fi

# Ensure every .in file has a corresponding .out file
MISSING_OUT_FILES=$(comm -23 \
  <(ls | grep "in$" | sed -e 's/in$/out/g' | sort) \
  <(ls | sort))
if [ $MISSING_OUT_FILES ]
then
  echo "********** TEST FAILURE **********"
  echo "The following files are missing:"
  echo $MISSING_OUT_FILES
  exit 1
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src -Xlint:none -d ../bin ../src/main/java/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from in file and diff with out file
# Exit immediately if any of the tests fail
for file in $(ls | grep "in$" | sort)
do
  TEST_NAME=$(sed -e 's/.in$//g' <<< $file)
  diff "$TEST_NAME.out" <(java -classpath ../bin Duke < $file)
  if [ $? -eq 0 ]
  then
    echo $TEST_NAME "PASSED"
  else
    echo $TEST_NAME "FAILED"
    exit 1
  fi
done

echo "ALL" $(ls | grep "in$" | wc -l) "TESTS PASSED"
exit 0
