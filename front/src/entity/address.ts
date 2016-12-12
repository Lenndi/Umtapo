export class Address {
    private _id: number;
    private _address1: string;
    private _address2: string;
    private _zip: string;
    private _city: string;
    private _phone: string;
    private _email: string;


    get id(): number {
        return this._id;
    }

    set id(value: number) {
        this._id = value;
    }

    get address1(): string {
        return this._address1;
    }

    set address1(value: string) {
        this._address1 = value;
    }

    get address2(): string {
        return this._address2;
    }

    set address2(value: string) {
        this._address2 = value;
    }

    get zip(): string {
        return this._zip;
    }

    set zip(value: string) {
        this._zip = value;
    }

    get city(): string {
        return this._city;
    }

    set city(value: string) {
        this._city = value;
    }

    get phone(): string {
        return this._phone;
    }

    set phone(value: string) {
        this._phone = value;
    }

    get email() {
        return this._email;
    }

    set email(value) {
        this._email = value;
    }

    get string() {
        return this._string;
    }

    set string(value) {
        this._string = value;
    }
}
