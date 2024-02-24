#################################
# ADD Frontend Dockerfile
#################################

#curl node-js
FROM node:latest as node

#create root folder
RUN mkdir -p /frontend
# access the root folder
WORKDIR /frontend

#install dependencies
COPY add-frontend/package.json /frontend/
RUN npm install

#build application : as prod
COPY add-frontend/ /frontend/
RUN npm run build --prod

#curl nginx
FROM nginx:alpine
COPY --from=node /frontend/dist/add-frontend /usr/share/nginx/html

#EOF