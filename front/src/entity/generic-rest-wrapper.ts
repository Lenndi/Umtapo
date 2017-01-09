/**
 * Generic response wrapper.
 */
export class GenericRestWrapper<T> {
  data: T[];
  page: number;
  totalPage: number;

  getData(): T[] {
    return this.data;
  }

  setData(value: T[]) {
    this.data = value;
  }

  getPage(): number {
    return this.page;
  }

  setPage(value: number) {
    this.page = value;
  }

  getTotalPage(): number {
    return this.totalPage;
  }

  setTotalPage(value: number) {
    this.totalPage = value;
  }
}
