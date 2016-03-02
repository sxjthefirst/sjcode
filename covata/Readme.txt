
I've created a vagrant box based on a Ubuntu box to work on this question. The Vagrant file is uploaded to the same folder https://covata.io/#/folders/683187273294237696

To start the box download all the files into a folder and run "vagrant up"
Hit the browser on the host box at http://localhost:8080 - the application runs in 80 within th box but I've decided to not map it to privileged port 80 on the host box. This will require running vagrant with sudo and isn't good practice.

------

1. Used Puppet inside the box to install and configure the application. This gives us better control to test and add modules within the box and later on package it.
2. Used shell script bootstrap.sh,  to provision the vagrant box this calls the puppet commands and starts the jar file
3. Puppet starts nginx and configures it to run on port 80
4. The machine is secured by limiting user ids in it and by firewall rules that only open ports as needed

------

Limitations:
1. Application lifecyle - this can be done by using various tools Jenkins, Bamboo etc to move between different environments. I haven't implemented it in this demo.
