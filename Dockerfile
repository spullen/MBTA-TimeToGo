FROM java:8
EXPOSE 8080

ADD build/libs/MBTATime2Go.jar MBTATime2Go.jar

CMD java -jar \
 -Dspring.profiles.active=$ENVIRONMENT_NAME \
 MBTATime2Go.jar