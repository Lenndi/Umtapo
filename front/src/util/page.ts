export class Page<T> {
  content: T[];
  last: boolean;
  totalElements: number;
  totalPages: number;
  sort: string;
  first: boolean;
  numberOfElements: number;
  size: number;
  number: number;
}
