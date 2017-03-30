export const ORDER = {
  DESC: 'DESC',
  ASC: 'ASC'
};

export class Pageable {
  page: number;
  size: number;
  sort: string;
  order: string;

  public getQueryString(): string {
    return `size=${this.size}&page=${this.page}&sort=${this.sort}&order=${this.order}`;
  }
}
