FROM openjdk:10
WORKDIR /home/ibmusic/
COPY target/scala-2.12/ibmusic.jar ./bot.jar
CMD [ "java", "-jar", "bot.jar" ]