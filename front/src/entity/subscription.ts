export class Subscription {

    private id: number;
    private start: Date;
    private end: Date;
    private contribution: number;


    getId(): number {
        return this.id;
    }

    setId(value: number) {
        this.id = value;
    }

    getStart(): Date {
        return this.start;
    }

    setStart(value: Date) {
        this.start = value;
    }

    getEnd(): Date {
        return this.end;
    }

    setEnd(value: Date) {
        this.end = value;
    }

    getContribution(): number {
        return this.contribution;
    }

    setContribution(value: number) {
        this.contribution = value;
    }
}
