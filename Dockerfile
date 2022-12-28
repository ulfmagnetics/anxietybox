FROM java11:latest
ENV LOG_FILE=/app/logs/clojure.log
ENV POSTGRES_USER=anxietybox
ENV POSTGRES_PASSWORD=please_override_me
ENV DOMAIN_NAME=your.anxiety.box
ENV SMTP_USERNAME=please_override_me
ENV SMTP_PASSWORD=please_override_me
ENV FROM_EMAIL="Your Anxiety <anxietybox@example.com>"

RUN apt-get -y update && apt-get -y full-upgrade

# TODO generate an uberjar and run it directly

# install leiningen
RUN cd /usr/local/bin && \
    wget https://raw.githubusercontent.com/technomancy/leiningen/stable/bin/lein && \
    chmod a+x ./lein && \
    ./lein

COPY anxietybox/ /app
WORKDIR /app
CMD ["lein", "ring", "server"]
