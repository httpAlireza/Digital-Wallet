FROM openjdk:21-jdk

RUN mkdir /opt/app
WORKDIR /opt/app

COPY target/Digital-Wallet-*.jar app.jar
COPY scripts/start.sh start.sh

EXPOSE 8080

USER root
RUN chmod +x start.sh
CMD ["./start.sh"]
