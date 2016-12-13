import {Library} from './library';
import {Address} from './address';
import {Subscription} from './subscription';
import {Loan} from './loan';
export class Borrower {
    private id: number;
    private name: string;
    private birthday: Date;
    private quota: number;
    private emailOptin: boolean;
    private address: Address;
    private subscription: Subscription;
    private loan: Loan;
    private library: Library;


    getId(): number {
        return this.id;
    }

    setId(value: number) {
        this.id = value;
    }

    getName(): string {
        return this.name;
    }

    setName(value: string) {
        this.name = value;
    }

    getBirthday(): Date {
        return this.birthday;
    }

    setBirthday(value: Date) {
        this.birthday = value;
    }

    getQuota(): number {
        return this.quota;
    }

    setQuota(value: number) {
        this.quota = value;
    }

    getEmailOptin(): boolean {
        return this.emailOptin;
    }

    setEmailOptin(value: boolean) {
        this.emailOptin = value;
    }

    getAddress(): Address {
        return this.address;
    }

    setAddress(value: Address) {
        this.address = value;
    }

    getSubscription(): Subscription {
        return this.subscription;
    }

    setSubscription(value: Subscription) {
        this.subscription = value;
    }

    getLoan(): Loan {
        return this.loan;
    }

    setLoan(value: Loan) {
        this.loan = value;
    }

    getLibrary(): Library {
        return this.library;
    }

    setLibrary(value: Library) {
        this.library = value;
    }
}