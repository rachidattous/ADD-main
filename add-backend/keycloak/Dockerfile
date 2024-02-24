FROM jboss/keycloak:14.0.0

COPY ./realm-export.json /tmp/realm-export.json
# RUN mkdir /opt/jboss/tools/
COPY ./entrypoint.sh  /opt/jboss/tools/entrypoint.sh
USER root
RUN chmod +x  /opt/jboss/tools/entrypoint.sh
# ENV KEYCLOAK_IMPORT=/tmp/realm-export.json
# ENV KEYCLOAK_PORT=8180
# CMD -Djboss.http.port=8180 
USER jboss
