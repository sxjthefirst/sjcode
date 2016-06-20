#!/bin/bash
# set verbose level to info
__LOGLEVEL=6

#Logging stuff
declare -A LOG_LEVELS
# https://en.wikipedia.org/wiki/Syslog#Severity_level
LOG_LEVELS=([0]="emerg" [1]="alert" [2]="critical" [3]="error" [4]="warning" [5]="notice" [6]="info" [7]="debug")
function loglevel () {
  local LEVEL=${1}
  shift
  if [ ${__LOGLEVEL} -ge ${LEVEL} ]; then
    echo "[${LOG_LEVELS[$LEVEL]}]" "$@"
  fi
}

#Get user creds
user=`test -z $USER && echo jagannsr || echo $USER`
outfile="agentdata.csv"
sumfile="agentdatasummary.csv"
echo "LAN password for $user"
read -s password

#Download list of applications first
url="http://apm.prd.emaas.cba/controller/rest" #production controller
echo "INFO: Getting list of applications from $url ..."
apps=`curl --user ${user}@customer1:${password} ${url}/applications/ 2>/dev/null | grep "<name>"`
if [ -z "${apps}" ]; then
	loglevel 3  "Error getting list of applications or no applications created yet"
	exit 1
fi
loglevel 7 $apps

#For each applicataion get all nodes under it
loglevel 6 "Getting nodes for applications..."
echo "Application Name,Machine Name,Status,Machine Agent Version,App Agent Version" >  $outfile
echo "Application Name,Count" > $sumfile 

#This is required because application names might have spaces
OLDIFS=$IFS
IFS=$'\n'

STATUS=([0]="Inactive" [1]="Active")
#Loop thru each app
for app in `echo "$apps"`
do
 app_name=`echo $app | cut -d">" -f2 | cut -d"<" -f1`
 loglevel 6 "Fetching nodes for $app_name"
 #get the nodes
 nodes=`curl  --user ${user}@customer1:${password} ${url}/applications/${app_name}/nodes 2>/dev/null | grep "<id>"`
 app_count=0
 #For each node get tier, status, using the metric browser URL 
 for node in `echo "$nodes"`
 do
	node_id=`echo $node | cut -d">" -f2 | cut -d"<" -f1`
	node_details=`curl  --user ${user}@customer1:${password} ${url}/applications/${app_name}/nodes/${node_id} 2>/dev/null`
	machine_name=`echo "$node_details" | grep "<machineName>" | cut -d">" -f2 | cut -d"<" -f1`
	tier_name=`echo "$node_details" | grep "<tierName>" | cut -d">" -f2 | cut -d"<" -f1`
	node_name=`echo "$node_details" | grep "<name>" | cut -d">" -f2 | cut -d"<" -f1`
	machine_version=`echo "$node_details" | grep "<machineAgentVersion>" | cut -d">" -f2 | cut -d"<" -f1`
	appagent_version=`echo "$node_details" | grep "<appAgentVersion>" | cut -d">" -f2 | cut -d"<" -f1`
	if [ -z "$machine_version" ]; then
		machine_version="NA"
	fi
	if [ -z "$appagent_version" ]; then
		appagent_version="NA"
	fi
	loglevel 7 "Getting details for $app_name --> $node_name"
	loglevel 7 "Getting details for $app_name --> $node_name"
	#Form the REST url in the format expected by metric browser - urlencode spaces and special characters
	metric_path=`echo "metric-path=Application%20Infrastructure%20Performance|${tier_name}%7CIndividual%20Nodes%7C${node_name}%7CAgent%7CApp%7CAvailability&time-range-type=BEFORE_NOW&duration-in-mins=15" | sed 's/ /%20/g'`
	metric_url="${url}/applications/${app_name}/metric-data?$metric_path"
	loglevel 7 $metric_url
	agent_details=`curl  -G --user ${user}@customer1:${password} ${metric_url} 2>/dev/null`
	loglevel 7 "agent xml $agent_details"
	agent_status=`echo "$agent_details" | grep "<value>" | cut -d">" -f2 | cut -d"<" -f1`
	start_time=`echo "$agent_details" | grep "<startTimeInMillis>" | cut -d">" -f2 | cut -d"<" -f1 | awk '{print strftime("%c", ( $1  / 1000 ))}'`
	loglevel 7 "$app_name,$node_name,$agent_status,$start_time,$machine_version,$appagent_version,$metric_url"
	echo $app_name,$node_name,${STATUS[$agent_status]},$machine_version,$appagent_version >>  $outfile
 done
 #Count the unique node per app
 app_count=`grep "$app_name\," $outfile | sort -u | wc -l`
 echo $app_name,$app_count >> $sumfile 
done

#Finish up
IFS=$OLDIFS
echo "INFO: Output stored in $outfile. Summary is in $sumfile"
