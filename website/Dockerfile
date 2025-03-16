FROM node:22.9-alpine
LABEL authors="champeic"

ARG BACKEND_URL
ENV BACKEND_URL=$BACKEND_URL

RUN mkdir -p /app
WORKDIR /app

COPY website/package*.json .
RUN npm i
COPY website/ .

RUN npm run build

EXPOSE 5173

ENTRYPOINT ["npx", "serve", "-l", "5173", "-s", "dist"]