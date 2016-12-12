export class Subscription {

    private _id: number;
    private _start: string;
    private _end: string;
    private _contribution: number;


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get start(): string {
        return this._start;
    }

    set start(value: string) {
        this._start = value;
    }

    get end(): string {
        return this._end;
    }

    set end(value: string) {
        this._end = value;
    }

    get contribution(): number {
        return this._contribution;
    }

    set contribution(value: number) {
        this._contribution = value;
    }
}
