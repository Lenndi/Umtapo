import {CustomMap} from './custom-map';

export const conditionEnum = new CustomMap();
conditionEnum
  .add('NEW', 'Neuf')
  .add('GOOD', 'Bon')
  .add('AVERAGE', 'Moyen')
  .add('BAD', 'Mauvais')
  .add('LOST', 'Perdu')
  .add('SOLD', 'Vendu');
