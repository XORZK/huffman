#!/bin/bash

javac -d . $(find . -type f -name '*.java')
java $1
rm *.class
