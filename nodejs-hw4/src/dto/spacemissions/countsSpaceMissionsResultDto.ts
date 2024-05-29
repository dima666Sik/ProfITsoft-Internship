export class CountsSpaceMissionsResultDto {
    [key: string]: number;

    constructor(data: { [key: string]: number }) {
      Object.keys(data).forEach(key => {
        this[key] = data[key];
      });
    }
}

