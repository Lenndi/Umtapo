export const ORDER = {
  DESC: 'DESC',
  ASC: 'ASC'
};

export class Pageable {
  page: number;
  size: number;
  sort: string;
  order: string;

  public constructor(sort: string) {
    this.page = 0;
    this.size = 10;
    this.sort = sort;
    this.order = ORDER.ASC;
  }

  public getQueryString(): string {
    return `size=${this.size}&page=${this.page}&sort=${this.sort}&order=${this.order}`;
  }
}
