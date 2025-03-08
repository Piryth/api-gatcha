FROM alpine/openssl:latest AS openssl-keygen
LABEL authors="champeic"

RUN mkdir -p /openssl/certs/public && mkdir -p /openssl/certs/private

RUN openssl genrsa -out keypair.pem 2048
RUN openssl rsa -in keypair.pem -pubout -out /openssl/certs/public/public.pem
RUN openssl pkcs8 -in keypair.pem -topk8 -nocrypt -inform PEM -outform PEM -out /openssl/certs/private/private.pem

ENTRYPOINT ["/bin/sh"]