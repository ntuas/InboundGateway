FROM openjdk:8-jre-slim
WORKDIR /app
ENTRYPOINT ["/app/run-inbound_gateway.sh"]
ENV JAVA_OPTS="-Xmx2g"
COPY logback.xml /app/logback.xml
COPY *.jar /app/inbound_gateway.jar
COPY *.sh /app/run-inbound_gateway.sh
RUN chmod +x /app/run-inbound_gateway.sh

EXPOSE 8888

ARG VERSION
LABEL nt.uas.group="nt.uas.inboundgateway" nt.uas.name="inboundgateway" nt.uas.version="${VERSION}"