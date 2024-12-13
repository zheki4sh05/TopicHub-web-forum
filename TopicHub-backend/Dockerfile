
FROM openjdk:17
WORKDIR /app
COPY target/TopicHub-backend-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/
FROM tomcat:11
COPY --from=0 /usr/local/tomcat/webapps/ /usr/local/tomcat/webapps/
EXPOSE 8080
CMD ["catalina.sh", "run"]