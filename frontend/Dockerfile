# syntax=docker/dockerfile:1.7@sha256:a57df69d0ea827fb7266491f2813635de6f17269be881f696fbfdf2d83dda33e

FROM node:22-alpine@sha256:ab07539e0988b63558ff621f5fbe1077054c39d9809112974fb79993949d41cd AS build

WORKDIR /app

COPY package*.json ./
RUN --mount=type=cache,target=/root/.npm npm ci

COPY index.html vite.config.js eslint.config.js ./
COPY public/ public/
COPY src/ src/
ARG VITE_API_BASE_URL
ENV VITE_API_BASE_URL=${VITE_API_BASE_URL}
RUN npm run build

FROM nginx:1.27-alpine@sha256:65645c7bb6a0661892a8b03b89d0743208a18dd2f3f17a54ef4b76fb8e2f2a10

RUN rm -rf /usr/share/nginx/html/* \
    && chown -R nginx:nginx /run /var/cache/nginx /var/run /var/log/nginx /etc/nginx/conf.d /usr/share/nginx/html

COPY --chown=nginx:nginx nginx.conf.template /etc/nginx/templates/default.conf.template
COPY --from=build --chown=nginx:nginx /app/dist /usr/share/nginx/html

USER nginx
ARG FRONTEND_CONTAINER_PORT
EXPOSE ${FRONTEND_CONTAINER_PORT}
