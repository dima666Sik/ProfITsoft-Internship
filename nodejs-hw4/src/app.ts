import express from 'express';
import routers from './routers';
import config from './config';
import log4js, { Configuration } from 'log4js';
import mongoose, { ConnectOptions } from 'mongoose';

// import cors from 'cors';

export default async () => {
    const app = express();

    log4js.configure(config.log4js as Configuration);

    // to disable caching of requests returning 304 instead of 200
    app.disable('etag');

    // Enable CORS
    // app.use(cors({
    //     origin: '*', // Allows all origins. Replace with specific domains in production.
    //     methods: 'GET,HEAD,PUT,PATCH,POST,DELETE',
    //     preflightContinue: false,
    //     optionsSuccessStatus: 204
    // }));

    app.use(express.json({ limit: '1mb' }));

    app.use((req, _, next) => {
        const dateReviver = (_: string, value: unknown) => {
            if (value && typeof value === 'string') {
                const dateRegex = /^\d{2}-\d{2}-\d{4}$/;
                if (dateRegex.test(value)) {
                    return new Date(value);
                }
            }
            return value;
        };

        req.body = JSON.parse(JSON.stringify(req.body), dateReviver);
        next();
    });

    app.use('/', routers);

    const port = 8888;
    const address = "localhost";
    app.listen(port, address, () => {
        log4js.getLogger().info(`Example app listening on port ${address}:${port}`);
    });

    const mongoAddress = "mongodb://127.0.0.1:27018/spacedb"
    await mongoose.connect(mongoAddress, {
        useNewUrlParser: true,
        useUnifiedTopology: true,
        socketTimeoutMS: 30000,
    } as ConnectOptions);

    return app;
};
