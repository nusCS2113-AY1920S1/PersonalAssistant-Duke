#!/usr/bin/env bash

# create/erase all.javas
> all.javas
# find all Java files
for i in `find .. -name "*.java" -type f`;
do
  echo "$i" >> all.javas
done

# create bin and data directories if they don't exist (-p option)
mkdir -p ../bin
mkdir -p ../bin
mkdir -p ../data

# delete output from previous run
if [ -e "./ACTUAL.TXT" ]
then
  rm ACTUAL.TXT
fi

if [ -e "../data/tasks.tsv" ]
then
  rm ../data/tasks.tsv
fi


# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../bin/production -Xlint:none -d ../bin/production @all.javas
then
  echo "********** BUILD FAILURE **********"
  exit 1
fi

cd ..
# run the program, feed commands from input.txt file and redirect the output to the ACTUAL.TXT
java -classpath bin/production duke/Duke < text-ui-test/input.txt > text-ui-test/ACTUAL.TXT

# test recovery of corrupted file
echo "file corrupted!" > data/tasks.tsv
echo n > text-ui-test/n
java -classpath bin/production duke/Duke < text-ui-test/n >> text-ui-test/ACTUAL.txt
rm text-ui-test/n

# test deletion of corrupted file
echo "file corrupted!" > data/tasks.tsv
echo y > text-ui-test/ybye
echo bye >> text-ui-test/ybye
java -classpath bin/production duke/Duke < text-ui-test/ybye >> text-ui-test/ACTUAL.txt
rm text-ui-test/ybye

cd text-ui-test
# compare the output to the expected output
diff ACTUAL.TXT EXPECTED.TXT
