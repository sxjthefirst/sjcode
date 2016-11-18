#! /bin/bash

checkroot() {
    # Check to see if we're running this script as root or sudo
    if [ `id -u` -ne 0 ]; then
        echo "This script must be run as sudo or root" 1>&2
        exit 1
    fi
}


checkroot

mgmt_ip=`hostname -i | cut -d" " -f1`

#Check if the entries  for 8090 and 8181 are already in there
https_entry=false
http_entry=false
ipt_out=`iptables -t nat -L`

echo "$ipt_out" | grep "https.*.$mgmt_ip:8181" 
if [ $? ==  0 ]; then
	https_entry=true
fi

echo "$ipt_out" | grep "http.*.$mgmt_ip:8090" 
if [ $? == 0 ]; then
	http_entry=true
fi

if [[ $https_entry == true  && $http_entry == true ]]; then
 echo "IP Table entries are present"
 exit 0
fi


#If any one of the entries is missing, add it now
echo "Adding missing IP Table entries"
if [[ $https_entry == false ]]; then
	iptables -t nat -A PREROUTING -i bond0 -p tcp --dport 443 -j DNAT --to  $mgmt_ip:8181
fi
if [[ $http_entry == false ]]; then
	iptables -t nat -A PREROUTING -i bond0 -p tcp --dport 80  -j DNAT --to  $mgmt_ip:8090
fi
#save it
service iptables save

echo "Added and saved new IP table"
iptables -t nat -L
