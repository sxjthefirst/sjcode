#sxjthefirst / userps.sh
# Get memory used by each user. Tested on RHEL 5.x, 6.x and Mint Nadia

ps -eo rss,user | grep -v USER | awk '{ c=split($2,arr," "); cmd=arr[c]; cmdcnt[cmd]++; cmdrss[cmd] += $1 } END { for (i in cmdrss) print cmdrss[i]/(1024*1024), i, cmdcnt[i] }' | sort -nr | awk '{printf "%60-s %4i Processes %6.2f GB \n", $2, $3, $1}'
