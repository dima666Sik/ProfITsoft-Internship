FROM node:20.11.0 as production-dependencies
WORKDIR /app
COPY package.json package.json
RUN npm install --production

FROM node:20.11.0 as build-dependencies
WORKDIR /app
COPY package.json package.json
RUN npm install --production

FROM node:20.11.0 as builder
WORKDIR /app
COPY --from=build-dependencies /app/node_modules node_modules
COPY . .
RUN npm run build

FROM node:20.11.0
WORKDIR /app
COPY --from=production-dependencies /app .
COPY --from=builder /app/dist /app/dist
CMD [ "node", "dist/index.js" ]
