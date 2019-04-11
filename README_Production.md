Odoo reporting application
----
This guide show how to build application in production mode on Debian Ubuntu

### Build production env
1. Install Java
```
$ apt update
$ apt upgrade -y
$ sudo apt install -y openjdk-8-jdk
```

Install git, maven
```
$ sudo apt install -y maven git
```

2. Install Tomcat  
https://linuxize.com/post/how-to-install-tomcat-9-on-ubuntu-18-04/  

```
$ APP_USER="nev"

$ sudo useradd -r -m -U -d /opt/tomcat -s /bin/false tomcat
$ TOMCAT_VERSION="9.0.17"
$ wget http://www-eu.apache.org/dist/tomcat/tomcat-9/v$TOMCAT_VERSION/bin/apache-tomcat-$TOMCAT_VERSION.tar.gz -P /tmp
$ sudo tar xf /tmp/apache-tomcat-$TOMCAT_VERSION.tar.gz -C /opt/tomcat
$ sudo ln -s /opt/tomcat/apache-tomcat-$TOMCAT_VERSION /opt/tomcat/latest
$ sudo chown -RH tomcat: /opt/tomcat/latest
$ sudo chmod o+x /opt/tomcat/latest/bin/
$ sudo chmod g+wx /opt/tomcat/latest/bin/
$ sudo usermod -a -G tomcat $(whoami)
$ sudo chown -R $APP_USER:$APP_USER /opt/tomcat/latest
```

3. Setup Tomcat as a system service
```
$ sudo nano /etc/systemd/system/tomcat.service
```

then add below content to /etc/systemd/system/tomcat.service

-----------------
```
[Unit]
Description=Tomcat 9 servlet container
After=network.target

[Service]
Type=forking

User=stp
Group=stp

Environment="JAVA_HOME=/usr/lib/jvm/java-8-openjdk-amd64"
Environment="JAVA_OPTS=-Djava.security.egd=file:///dev/urandom -Djava.awt.headless=true"

Environment="CATALINA_BASE=/opt/tomcat/latest"
Environment="CATALINA_HOME=/opt/tomcat/latest"
Environment="CATALINA_PID=/opt/tomcat/latest/temp/tomcat.pid"
Environment="CATALINA_OPTS=-Xms512M -Xmx1024M -server -XX:+UseParallelGC"

ExecStart=/opt/tomcat/latest/bin/startup.sh
ExecStop=/opt/tomcat/latest/bin/shutdown.sh

[Install]
WantedBy=multi-user.target
```
then start Tomcat
(Note: if deploy with Azure free subscription (B1S) then Tomcat maybe crash though memory is not enough??!  
so please start Tomcat manually: $ /opt/tomcat/latest/bin/startup.sh)

-----------------
```
$ sudo systemctl daemon-reload
$ sudo systemctl start tomcat
$ sudo systemctl enable tomcat

$ curl localhost:8080 -I
```

4. Build source code in production mode and deploy to tomcat

4.0. Prepare dirs
```
$ APP_USER="nev"
$ sudo mkdir /var/$APP_USER && cd /var/$APP_USER
$ sudo mkdir -p src/ logs/ files/upload files/download
$ sudo chown -R $APP_USER:$APP_USER /var/$APP_USER
$ sudo chmod -R g+rwx src/ logs/ files/
$ sudo chmod -R g+rwx src/ logs/ files/
```

4.1. Create ssh key and register with github (if need)
```
$ ssh-keygen -t rsa -C "buivankhoi@gmail.com"
$ cat ~/.ssh/id_rsa.pub
```
copy public key and go to `https://github.com/settings/keys`, then paste there

4.2. Checkout/update source code

Check out new:
```
$ cd /var/nev/src && git clone git@github.com:nev-khoibv/demo_odoo_report.git
$ sudo chmod +x stp-cron.sh
```
or update repository
```
$ cd /var/nev/src && git pull
```

4.3. Prepare settings before build
- File `src/main/resources/application.properties`:
    + Set spring.profiles.active = production
    + Check DB connection is valid? (spring.datasource.url in production profile)
    + Check locations of external files (application.path.*). They are must be correspond with step 4.0 Prepare directories
    + Check email settings (spring.mail.* and application.mail.*)


4.4. Build and copy to Tomcat path
```
$ mvn clean package -Dmaven.test.skip=true -am -Pproduction
$ cp salary-web/target/salary-web.war /opt/tomcat/latest/webapps/salary.war
```
Check server is running
```
$ curl localhost:8080/salary/ -I
```
or using browser to open url
```
http://35.187.244.67:8080/salary
``` 

4.5. Monitoring
```
# Application log
$ tail -n 500 -f /var/stp/logs/stp_salary.log

# Tomcat log
$ tail -n 500 -f /opt/tomcat/latest/logs/catalina.out

# Cronjob log
$ sudo tail -n 500 -f /var/log/syslog

# Build log
$ tail -n 500 -f /var/stp/logs/build.log

# Check build info (timestamp/SVN revision ...) from remote
http://35.187.244.67:8080/salary/actuator/info
```

4.6 Start/stop/restart Tomcat
- Start
```
$ sudo systemctl start tomcat
or
$ /opt/tomcat/latest/bin/startup.sh
```
- Stop:
```
$ sudo systemctl start tomcat
or
$ /opt/tomcat/latest/bin/shutdown.sh
```
- Restart
```
$ sudo systemctl restart tomcat
```
- Force to build and deploy
```
$ cd /var/stp/src
$ sudo chmod +x stp-cron.sh
$ ./stp-cron.sh --build
```
