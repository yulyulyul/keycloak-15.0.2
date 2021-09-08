FROM amazonlinux:latest

USER root

COPY ./keycloak-15.0.2 /keycloak-15.0.2

RUN yum -y update \ 
    && yum install -y java-11-amazon-corretto \ 
    && yum install -y iputils

EXPOSE 8080

ENTRYPOINT ["/keycloak-15.0.2/bin/standalone.sh" , "-b", "0.0.0.0"]