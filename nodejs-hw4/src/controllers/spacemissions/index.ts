import log4js from 'log4js';
import httpStatus from 'http-status';
import {Request, Response} from 'express';
import {
    createSpaceMission as createSpaceMissionApi,
    getSpaceMissions as getSpaceMissionsApi,
    countsSpaceMissions as countsSpaceMissionsApi,
} from 'src/services/spacemissions';
import {SpaceMissionSaveDto} from 'src/dto/spacemissions/spaceMissionSaveDto';
import {InternalError} from 'src/system/internalError';
import {SpaceMissionPaginationDto} from "../../dto/spacemissions/spaceMissionPaginationDto";
import {CountsSpaceMissionsDto} from "../../dto/spacemissions/countsSpaceMissionsDto";

export const getSpaceMission = async (req: Request, res: Response) => {
    try {
        const spaceMissionPagination = new SpaceMissionPaginationDto(req.body);
        const result = await getSpaceMissionsApi(spaceMissionPagination);
        if (!result) {
            res.status(httpStatus.NOT_FOUND).send();
        } else {
            res.send({
                result,
            });
        }
    } catch (err) {
        const {message, status} = new InternalError(err);
        log4js.getLogger().error(`Error in retrieving spaceMissions with data:${req.body}.`, err);
        res.status(status).send({message});
    }
};

export const saveSpaceMission = async (req: Request, res: Response) => {
    try {
        const spaceMission = new SpaceMissionSaveDto(req.body);
        const id = await createSpaceMissionApi({
            ...spaceMission,
        });
        res.status(httpStatus.CREATED).send({
            id,
        });
    } catch (err) {
        const {message, status} = new InternalError(err);
        log4js.getLogger().error('Error in creating spaceMission.', err);
        res.status(status).send({message});
    }
};

export const counts = async (req: Request, res: Response) => {
    try {
        const countSpaceMission = new CountsSpaceMissionsDto(req.body);
        const result = await countsSpaceMissionsApi(countSpaceMission);
        res.send({
            result,
        });
    } catch (err) {
        const {message, status} = new InternalError(err);
        log4js.getLogger().error(`Error in retrieving counts SpaceMission`, err);
        res.status(status).send({message});
    }
};
