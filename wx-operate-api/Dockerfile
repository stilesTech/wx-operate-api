FROM openjdk:8-jdk-alpine
MAINTAINER stiles
VOLUME /tmp
ARG JAR_FILE
ADD target/${JAR_FILE} ${JAR_FILE}
RUN sh -c 'touch /${JAR_FILE}'
ENV JAVA_OPTS="-Xms562m -Xmx3072m"
ENV PROFILE="pro"
ENV APP_NAME=${JAR_FILE}

ENTRYPOINT [ "sh", "-c", "echo 127.0.0.1  $HOSTNAME >>/etc/hosts && java $JAVA_OPTS -Denv=$PROFILE -Duser.timezone=GMT+08 -Djava.security.egd=file:/dev/./urandom -jar /$APP_NAME --spring.profiles.active=$PROFILE " ]
