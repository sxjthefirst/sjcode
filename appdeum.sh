#!/bin/sh 
#
# /etc/init.d/appdynamics-eum
#
# This file describes the EUM service. Copy it or place it in /etc/init.d to ensure the EUM 
# is started as a service. 
#
#
# chkconfig: 2345 95 05
# description: EUM
# processname: eum.sh
# config: /etc/sysconfig/appdynamics-eum
# pidfile: /var/run/appdynamics-eum
#
### BEGIN INIT INFO
# Provides:          appdynamics-eum
# Required-Start:
# Required-Stop:
# Default-Start:     2 3 4 5
# Default-Stop:      0 1 6
# Short-Description: AppDynamics EUM
# Description:       Enable AppDynamics EUM service 
### END INIT INFO

prog="appdynamics-eum"
lockfile="/var/lock/subsys/$prog"

# Defaults. Do not edit these. They will be overwritten in updates.
# Override in /etc/sysconfig/appdynamics-eum
EUM_HOME=/opt/appdynamics/eum
JAVA_HOME=$EUM_HOME/jre
EUM_USER=ACOE_APM
PIDFILE="$EUM_HOME/eum-processor/pid.txt"

# source script config, overwrite above variables
[ -f /etc/sysconfig/appdynamics-eum ] && . /etc/sysconfig/appdynamics-eum

# status_of_proc() is not a standard LSB function. This implementation is taken
# from /lib/lsb/init-functions on Ubuntu. It is not defined on SuSE, for
# example. This implementation may be overidden by the sourced
# lsb/lsb/init-functions below.
#
# The license for status_of_proc():
#
#     Copyright (c) 2002-08 Chris Lawrence
#     All rights reserved.
#
#     Redistribution and use in source and binary forms, with or without
#     modification, are permitted provided that the following conditions
#     are met:
#     1. Redistributions of source code must retain the above copyright
#        notice, this list of conditions and the following disclaimer.
#     2. Redistributions in binary form must reproduce the above copyright
#        notice, this list of conditions and the following disclaimer in the
#        documentation and/or other materials provided with the distribution.
#     3. Neither the name of the author nor the names of other contributors
#        may be used to endorse or promote products derived from this software
#        without specific prior written permission.
#
#     THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
#     IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
#     WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
#     ARE DISCLAIMED.  IN NO EVENT SHALL THE REGENTS OR CONTRIBUTORS BE
#     LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
#     CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
#     SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR
#     BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
#     WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE
#     OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE,
#     EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
#
# Return LSB status
status_of_proc () {
    pidfile daemon name status OPTIND

    pidfile=
    OPTIND=1
    while getopts p: opt ; do
        case "$opt" in
            p)  pidfile="$OPTARG";;
        esac
    done
    shift $(($OPTIND - 1))

    if [ -n "$pidfile" ]; then
        pidfile="-p $pidfile"
    fi
    daemon="$1"
    name="$2"

    status="0"
    pidofproc $pidfile $daemon >/dev/null || status="$?"
    if [ "$status" = 0 ]; then
        log_success_msg "$name is running"
        return 0
    elif [ "$status" = 4 ]; then
        log_failure_msg "could not access PID file for $name"
        return $status
    else
        log_failure_msg "$name is not running"
        return $status
    fi
}

stop_cmd="cd $EUM_HOME/eum-processor/ ; bin/eum.sh stop"
start_cmd="cd $EUM_HOME/eum-processor/ ; bin/eum.sh start"

# source function library (distribution-dependent)
if [ -f /etc/rc.d/init.d/functions ]; then
    # redhat flavor
    . /etc/rc.d/init.d/functions
    status_cmd="status -p $PIDFILE $prog"
elif [ -f /lib/lsb/init-functions ]; then
    # debian or suse flavor
    . /lib/lsb/init-functions
    status_cmd="status_of_proc -p $PIDFILE java $prog"
else
    echo "Unable to find function library" 1>&2
    exit 1
fi

RETVAL=0

checkroot() {
    # Check to see if we're running this script as root or sudo
    if [ `id -u` -ne 0 ]; then
        echo "This script must be run as sudo or root" 1>&2
        exit 1
    fi
}

start() {
    checkroot
    if [ -f $lockfile ] ; then
        echo "$prog has already been started"
        exit 1
    fi
    # make sure needed dirs are there
    sudo -u $EUM_USER sh -c "mkdir -p $PIDFILE"
    mkdir -p `dirname $lockfile`

    echo -n "Starting $prog:"
    cd $EUM_HOME/eum-processor/ 
    sudo -u $EUM_USER sh -c "$start_cmd"  
    RETVAL=$?
    [ "$RETVAL" = 0 ] && touch $lockfile
    echo
}

stop() {
    checkroot
    echo -n "Stopping $prog:"
    #$stop_cmd
    #cd $EUM_HOME/eum-processor/ 
    sudo -u $EUM_USER sh -c "$stop_cmd"  
    RETVAL=$?
    [ "$RETVAL" = 0 ] && rm -f $lockfile
    echo
}

case "$1" in
    start)
        start
        ;;
    stop)
        stop
        ;;
    restart)
        if [ -f $lockfile ] ; then
            stop
            # avoid race
            sleep 5
            start
        fi
        ;;
    status)
        $status_cmd
        RETVAL=$?
        ;;
    *)    (10)
        echo "Usage: $0 {start|stop|restart|status}"
        RETVAL=1
esac
exit $RETVAL
