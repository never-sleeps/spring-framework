FROM maven:3.6.3-jdk-11

ENV PROJECT_DIR=/opt/project

RUN mkdir -p $PROJECT_DIR
WORKDIR $PROJECT_DIR

ADD ./pom.xml $PROJECT_DIR
RUN mvn dependency:resolve

ADD ./src/ $PROJECT_DIR/src
RUN mvn install

FROM openjdk:11

ENV PROJECT_DIR=/opt/project
# create directories
RUN mkdir -p $PROJECT_DIR
RUN touch /var/log/access.log
RUN touch /var/log/error.log

WORKDIR $PROJECT_DIR
COPY --from=0 $PROJECT_DIR/target/homework-17-1.0.jar $PROJECT_DIR/

# forward request and error logs to docker log collector
RUN ln -sf /dev/stdout /var/log/access.log
RUN ln -sf /dev/stderr /var/log/error.log

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "/opt/project/homework-17-1.0.jar"]