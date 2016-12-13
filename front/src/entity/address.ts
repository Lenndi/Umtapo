export class Address {
    private id: number;
    private address1: string;
    private address2: string;
    private zip: string;
    private city: string;
    private phone: string;
    private email: string;


    getId(): number {
        return this.id;
    }

    setId(value: number) {
        this.id = value;
    }

    getAddress1(): string {
        return this.address1;
    }

    setAddress1(value: string) {
        this.address1 = value;
    }

    getAddress2(): string {
        return this.address2;
    }

    setAddress2(value: string) {
        this.address2 = value;
    }

    getZip(): string {
        return this.zip;
    }

    setZip(value: string) {
        this.zip = value;
    }

    getCity(): string {
        return this.city;
    }

    setCity(value: string) {
        this.city = value;
    }

    getPhone(): string {
        return this.phone;
    }

    setPhone(value: string) {
        this.phone = value;
    }

    getEmail() {
        return this.email;
    }

    setEmail(value) {
        this.email = value;
    }
}
