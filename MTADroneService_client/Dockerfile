FROM nginx:alpine

RUN rm -f /usr/share/nginx/html/*
COPY dist/MTAfrontend /usr/share/nginx/html
COPY nginx.conf /etc/nginx/nginx.conf

EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
