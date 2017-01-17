/**
 * Generic response wrapper.
 */
export class GenericRestWrapper<T> {
  data: T[];
  page: number;
  totalPage: number;
}
