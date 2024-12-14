FROM node:23-alpine3.20
WORKDIR /app
COPY . .
ARG VITE_APP_BACKEND_ADDRESS
ENV VITE_APP_BACKEND_ADDRESS $VITE_APP_BACKEND_ADDRESS
RUN npm install
RUN npm run build
RUN npm install -g serve
CMD ["serve", "-s", "dist"]
EXPOSE 3000