#!/bin/bash
set -e
javac -d ../webapp/WEB-INF/classes/ servletcontroller/*.java servletcontroller/clientservice/*.java dao/*.java pojo/*.java -classpath /usr/share/java/servlet-api-3.1.jar

#mv DemoServ.class /home/vikas/Project/src/main/webapp/WEB-INF/classes/
#mv SqServlet.class /home/vikas/Project/src/main/webapp/WEB-INF/classes/

rm -r /opt/tomcat/apache-tomcat-8.5.53/webapps/webapp
cp -r /home/vikas/Project/src/main/webapp /opt/tomcat/apache-tomcat-8.5.53/webapps