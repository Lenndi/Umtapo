import * as Winston from 'winston';

export const environment = {
  production: true,
  api_url: ''
};

export const logger = new (Winston.Logger)({
  transports: [
    new (Winston.transports.Console)({
      level: 'info',
      silent: false,
      json: false,
      colorize: true,
      timestamp: true,
      showLevel: true
    })
  ]
});
