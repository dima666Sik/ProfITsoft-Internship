import axios from 'axios';
import SpaceMissions, {ISpaceMission} from 'src/model/spaceMissions';
import {SpaceMissionSaveDto} from 'src/dto/spacemissions/spaceMissionSaveDto';
import {SpaceMissionPaginationDto} from 'src/dto/spacemissions/spaceMissionPaginationDto';
import {SpaceMissionInfoDto} from 'src/dto/spacemissions/spaceMissionInfoDto';
import {CountsSpaceMissionsDto} from 'src/dto/spacemissions/countsSpaceMissionsDto';
import {CountsSpaceMissionsResultDto} from 'src/dto/spacemissions/countsSpaceMissionsResultDto';

export const createSpaceMission = async (
    spaceMissionDto: SpaceMissionSaveDto
): Promise<string> => {
    await validateSpaceMission(spaceMissionDto);
    const spaceMission = await new SpaceMissions(spaceMissionDto).save();
    return spaceMission._id;
};

export const getSpaceMissions = async (
    spaceMissionPaginationDto: SpaceMissionPaginationDto
): Promise<SpaceMissionInfoDto[]> => {
    const {planetId, size, from} = spaceMissionPaginationDto;
    const spaceMissions = await SpaceMissions.find({
        planetId: planetId,
    })
        .sort({createdAt: -1}) // Sort by descending creation time
        .skip(from)
        .limit(size);

    return spaceMissions.map(spaceMission => toInfoDto(spaceMission));
};

export const countsSpaceMissions = async (
    countsSpaceMissionsDto: CountsSpaceMissionsDto
): Promise<CountsSpaceMissionsResultDto> => {
    const {planetIdArr} = countsSpaceMissionsDto;
    const counts = await SpaceMissions.aggregate([
        {$match: {planetId: {$in: planetIdArr}}},
        {$group: {_id: '$planetId', count: {$sum: 1}}},
    ]);

    const result = counts.reduce((acc, {_id, count}) => {
        acc[_id] = count;
        return acc;
    }, {} as { [key: string]: number });

    return new CountsSpaceMissionsResultDto(result);
};

const toInfoDto = (spaceMission: ISpaceMission): SpaceMissionInfoDto => {
    return {
        _id: spaceMission._id,
        name: spaceMission.name,
        dateStartMission: spaceMission.dateStartMission,
        dateEndMission: spaceMission.dateEndMission,
        planetId: spaceMission.planetId,
    };
};

export const validateSpaceMission = async (spaceMissionDto: SpaceMissionSaveDto) => {
    const id = spaceMissionDto.planetId;
    if (id) {
        try {
            const response = await axios.get(`http://localhost:8080/api/planet/${id}`);
            if (response.status !== 200) {
                throw new Error(`Planet with id ${id} doesn't exist`);
            }
        } catch (error) {
            throw new Error(`Planet with id ${id} doesn't exist`);
        }
    }
    if (
        spaceMissionDto.dateStartMission &&
        spaceMissionDto.dateEndMission
    ) {
        const startDate = new Date(spaceMissionDto.dateStartMission);
        const endDate = new Date(spaceMissionDto.dateEndMission);

        if (startDate.getTime() >= endDate.getTime()) {
            throw new Error('dateStartMission should be before dateEndMission');
        }
    }

};
