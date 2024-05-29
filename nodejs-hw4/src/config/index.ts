const config = {
  consul: {
    server: {
      prod: {
        host: 'consul',
        port: '8501',
      },
      dev: {
        host: 'localhost',
        port: '8501',
      },
    },
    service: {
      name: 'nodejs-hw4',
    },
  },
  log4js: {
    appenders: {
      console: {
        type: 'console',
      },
      ms: {
        type: 'dateFile',
        pattern: '-yyyy-MM-dd.log',
        alwaysIncludePattern: true,
        filename: 'log/ms',
        maxLogSize: 1000000,
        compress: true,
      },
    },
    categories: {
      default: {
        appenders: ['ms', 'console'],
        level: 'debug',
      },
    },
  },
};

export default config;
