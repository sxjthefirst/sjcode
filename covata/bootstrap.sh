#!/usr/bin/env bash
#Install puppet first
sudo apt-get install -y puppet
#Download java and nginx
puppet module install puppetlabs-java
puppet module install puppetlabs-nginx
#puppet module install maestrodev-jetty

#The init manifest and run it (installs java , ngnix ... )
cp /vagrant/init.pp /etc/puppet/manifests/
puppet apply /etc/puppet/manifests/init.pp --verbose

#The jar file to run
mkdir -p /opt/jetty/webapps
cp /vagrant/spring-boot-sample-jetty-1.0.0.RC5.jar /opt/jetty/webapps
java -jar /opt/jetty/webapps/spring-boot-sample-jetty-1.0.0.RC5.jar &
