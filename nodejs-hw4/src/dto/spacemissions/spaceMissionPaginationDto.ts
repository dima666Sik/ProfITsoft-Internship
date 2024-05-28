export class SpaceMissionPaginationDto {
    planetId: string;
    size: number;
    from: number;

    constructor(data: Partial<SpaceMissionPaginationDto>) {
        this.planetId = data.planetId ?? '';
        this.size = data.size ?? 10;
        this.from = data.from ?? 0;
    }
}
