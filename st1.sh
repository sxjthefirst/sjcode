#!/bin/bash
#http://stackoverflow.com/questions/17790527/bash-printing-directory-files

for i in "/dev"/*
do
 basename  $i
done
