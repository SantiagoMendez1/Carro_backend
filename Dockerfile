FROM maven:3.9.2-amazoncorretto-17-debian
LABEL authors="daniel"
RUN apt-get update && apt-get -y upgrade
RUN apt-get install -y inotify-tools dos2unix
ENV HOME=/app
RUN mkdir -p $HOME
WORKDIR $HOME
