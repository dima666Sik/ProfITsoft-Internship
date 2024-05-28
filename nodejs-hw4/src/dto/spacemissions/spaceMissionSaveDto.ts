export class SpaceMissionSaveDto {
    name?: string;
    dateStartMission?: Date;
    dateEndMission?: Date;
    planetId?: string;

    constructor(data: Partial<SpaceMissionSaveDto>) {
        this.name = data.name;
        this.dateStartMission = data.dateStartMission;
        this.dateEndMission = data.dateEndMission;
        this.planetId = data.planetId;
    }
}
