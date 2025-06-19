FROM node:18-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install
COPY . .
RUN npm run build

FROM nginx:stable-alpine AS production
COPY --from=build /app/build /usr/share/nginx/html
COPY nginx-compose.conf /etc/nginx/templates/nginx.compose.conf.template
COPY nginx.conf /etc/nginx/templates/nginx.k8s.conf.template
CMD ["sh", "-c", "cp /etc/nginx/templates/nginx.${NGINX_MODE}.conf.template /etc/nginx/conf.d/default.conf && exec nginx -g 'daemon off;'"]
EXPOSE 80
