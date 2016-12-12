import {Library} from './library';
import {Address} from './address';
import {Subscription} from './subscription';
import {Loan} from './loan';
export class Borrower {
    private _id: number;
    private _name: string;
    private _birthday: string;
    private _quota: number;
    private _emailOptin: boolean;
    private _address: Address;
    private _subscription: Subscription;
    private _loan: Loan;
    private _library: Library;


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get name(): string {
        return this._name;
    }

    set name(value: string) {
        this._name = value;
    }

    get birthday(): string {
        return this._birthday;
    }

    set birthday(value: string) {
        this._birthday = value;
    }

    get quota(): number {
        return this._quota;
    }

    set quota(value: number) {
        this._quota = value;
    }

    get emailOptin(): boolean {
        return this._emailOptin;
    }

    set emailOptin(value: boolean) {
        this._emailOptin = value;
    }

    get address(): Address {
        return this._address;
    }

    set address(value: Address) {
        this._address = value;
    }

    get subscription(): Subscription {
        return this._subscription;
    }

    set subscription(value: Subscription) {
        this._subscription = value;
    }

    get loan(): Loan {
        return this._loan;
    }

    set loan(value: Loan) {
        this._loan = value;
    }

    get library(): Library {
        return this._library;
    }

    set library(value: Library) {
        this._library = value;
    }
}