import { expect } from 'chai';
import sinon from 'sinon';
import axios from 'axios';
import SpaceMissions from 'src/model/spaceMissions';
import {
  createSpaceMission,
  getSpaceMissions,
  countsSpaceMissions,
  validateSpaceMission,
} from 'src/services/spacemissions';
import { SpaceMissionSaveDto } from 'src/dto/spacemissions/spaceMissionSaveDto';
import { SpaceMissionPaginationDto } from 'src/dto/spacemissions/spaceMissionPaginationDto';
import { CountsSpaceMissionsDto } from 'src/dto/spacemissions/countsSpaceMissionsDto';
import { CountsSpaceMissionsResultDto } from 'src/dto/spacemissions/countsSpaceMissionsResultDto';
import MockAdapter from 'axios-mock-adapter';
describe('Space Missions Service', () => {
  let sandbox: sinon.SinonSandbox;

  beforeEach(() => {
    sandbox = sinon.createSandbox();
  });

  afterEach(() => {
    sandbox.restore();
  });

  describe('createSpaceMission', () => {

    it('should validate the space mission correctly', async () => {
      const startDate = new Date('2024-05-28T00:00:00.000Z');
      const endDate = new Date('2024-06-28T00:00:00.000Z');
      const spaceMissionDto = new SpaceMissionSaveDto({
        name: 'Mission 1',
        planetId: 'planet1',
        dateStartMission: startDate,
        dateEndMission: endDate,
      });

      sandbox.stub(axios, 'get').resolves({ status: 200 });
      sandbox.stub(SpaceMissions.prototype, 'save').resolves({ _id: '12345' });

      const result = await createSpaceMission(spaceMissionDto);

      expect(result).to.equal('12345');
    });
  });

  describe('getSpaceMissions', () => {
    it('should retrieve space missions with pagination', async () => {
      const spaceMissionPaginationDto = new SpaceMissionPaginationDto({
        planetId: 'planet1',
        size: 10,
        from: 0,
      });

      const mockSpaceMissions = [
        { _id: '1', name: 'Mission 1', dateStartMission: '2024-05-28T00:00:00.000Z', dateEndMission: '2024-06-28T00:00:00.000Z', planetId: 'planet1' },
      ];
      const findStub = sandbox.stub(SpaceMissions, 'find').returns({
        sort: () => ({
          skip: () => ({
            limit: () => Promise.resolve(mockSpaceMissions),
          }),
        }),
      } as any);

      const result = await getSpaceMissions(spaceMissionPaginationDto);

      expect(findStub.calledOnce).to.be.true;
      expect(result).to.deep.equal(mockSpaceMissions.map(spaceMission => ({
        _id: spaceMission._id,
        name: spaceMission.name,
        dateStartMission: spaceMission.dateStartMission,
        dateEndMission: spaceMission.dateEndMission,
        planetId: spaceMission.planetId,
      })));
    });
  });

  describe('countsSpaceMissions', () => {
    it('should count space missions correctly', async () => {
      const countsSpaceMissionsDto = new CountsSpaceMissionsDto({
        planetIdArr: ['planet1'],
      });

      const mockCounts = [
        { _id: 'planet1', count: 5 },
      ];
      const aggregateStub = sandbox.stub(SpaceMissions, 'aggregate').resolves(mockCounts);

      const result = await countsSpaceMissions(countsSpaceMissionsDto);

      expect(aggregateStub.calledOnce).to.be.true;
      expect(result).to.deep.equal(new CountsSpaceMissionsResultDto({ planet1: 5 }));
    });
  });

  describe('validateSpaceMission', () => {
    const axiosMock = new MockAdapter(axios);

    it('should validate planet ID existence', async () => {
      const startDate = new Date('2024-05-28T00:00:00.000Z');
      const endDate = new Date('2024-06-28T00:00:00.000Z');
      const spaceMissionDto = new SpaceMissionSaveDto({
        name: 'Mission 1',
        planetId: '1',
        dateStartMission: startDate,
        dateEndMission: endDate,
      });

      // Mocking the Axios get request
      axiosMock.onGet('http://localhost:8080/api/planet/1').reply(200);

      await validateSpaceMission(spaceMissionDto);

      axiosMock.restore();
    });
  });
});
