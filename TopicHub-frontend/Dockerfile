FROM node:23-alpine3.20
WORKDIR /app
COPY . .
RUN npm install
RUN npm run build
RUN npm install -g serve
CMD ["serve", "-s", "dist"]
EXPOSE 3000