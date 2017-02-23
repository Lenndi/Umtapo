import {PipeTransform, Pipe} from '@angular/core';
@Pipe({name: 'condition'})
export class ConditionEnum implements PipeTransform {
  transform(items: any[], status: any): any {
    if (!items || !items.length) {
      return [];
    }
    return items.filter(item => item.status == status);
  }
}
