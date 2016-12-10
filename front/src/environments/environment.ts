// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `angular-cli.json`.
import * as Winston from 'winston';

export const environment = {
  production: false,
  api_url: 'http://localhost:8080/'
};

export const logger: Winston.LoggerInstance = new (Winston.Logger)({
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
