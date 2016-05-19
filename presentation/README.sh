#!/bin/bash

minijavac generate Main.java -l Math
javah -jni Math
gcc -shared -fpic -o libMath.so -I${JAVA_HOME}/include -I${JAVA_HOME}/include/linux Math.c
java -Djava.library.path=. Main
