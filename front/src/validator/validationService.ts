/**
 * Created by axel on 11/12/16.
 */
export class ValidationService {
    static getValidatorErrorMessage(validatorName: string, validatorValue?: any) {
        let config = {
            'required': 'Required',
            'invalidCreditCard': 'Is invalid credit card number',
            'invalidEmailAddress': 'Invalid email address',
            'invalidDate': 'Invalid Date format',
            'invalidPassword': 'Invalid password. Password must be at least 6 characters long, and contain a number.',
            'minlength': `Minimum length ${validatorValue.requiredLength}`
        };

        return config[validatorName];
    }

    static emailValidator(control) {
        // RFC 2822 compliant regex
        let regex = new RegExp('[a-z0-9!#$%&\'*+/=?^_\`{|}~-]+(?:\.[a-z0-9!#$%&\'*+/=?^_\`{|}~-]+)*@(?:[a-z0-9]'
            + '(?:[a-z0-9-]*[a-z0-9])?\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?');
        if (control.value.match(regex)) {
            return null;
        } else {
            return { 'invalidEmailAddress': true };
        }
    }

    static dateValidator(control) {
        if (control.value.match(/(\d{4})-(\d{2})-(\d{2})/)) {
            return null;
        } else {
            return { 'invalidDate': true };
        }
    }
}

