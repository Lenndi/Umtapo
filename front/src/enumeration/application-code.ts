import {CustomMap} from './custom-map';

export const ApplicationCodeEnum = new CustomMap();
ApplicationCodeEnum
  .add('DOCUMENT_ALREADY_BORROWED', '1000')
  .add('DOCUMENT_ALREADY_RENDERED', '1001')
  .add('DOCUMENT_CANNOT_TO_BE_BORROWED', '1002')
  .add('LOGIN_AND_PASSWORD_ARE_EQUALS', '2001');
