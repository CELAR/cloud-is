#!/bin/bash
# Grabs and kill a process from the pidlist that has the word CelarIS-Service

pid=`ps aux | grep CelarIS-Service | awk '{print $2}'`
kill -9 $pid > /dev/null 2>&1 &