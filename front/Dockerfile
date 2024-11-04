FROM node:18-alpine

WORKDIR /front

EXPOSE 8084

COPY package*.json ./

RUN npm install

COPY . .

CMD ["npm", "start"]