export class Loan {
    private id: number;
    private date: Date;


    getId(): number {
        return this.id;
    }

    setId(value: number) {
        this.id = value;
    }

    getDate(): Date {
        return this.date;
    }

    setDate(value: Date) {
        this.date = value;
    }
}
