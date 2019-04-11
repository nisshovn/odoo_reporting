Odoo reporting application
----
This guide show how to build application in developer mode on Debian Ubuntu

### Build production env
1. Install Java, Maven, Git  
JDK: 1.8  
Maven: 3.5+  

2. Get source code
```
$ git clone git@github.com:nisshovn/odoo_reporting.git odoo_reporting
```

3. Build and run
```
$ cd odoo_reporting
$ mvn spring-boot:run
```

4. Check

Server is running at [here](http://localhost:8080)

