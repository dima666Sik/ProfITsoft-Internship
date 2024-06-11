export interface CountsSpaceMissionsDto {
  planetIdArr: string[];
}

export class CountsSpaceMissionsDto implements CountsSpaceMissionsDto {
  constructor(data: Partial<CountsSpaceMissionsDto>) {
    this.planetIdArr = data.planetIdArr || [];
  }
}