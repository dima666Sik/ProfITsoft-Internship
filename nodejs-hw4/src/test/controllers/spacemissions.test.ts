import express, { Request, Response } from 'express';
import bodyParser from 'body-parser';
import sinon from 'sinon';
import chai from 'chai';
import chaiHttp from 'chai-http';
import * as spaceMissionsService from 'src/services/spacemissions';
import { getSpaceMission, saveSpaceMission, counts } from 'src/controllers/spacemissions';
import log4js from 'log4js';

const { expect } = chai;
chai.use(chaiHttp);
chai.should();

const sandbox = sinon.createSandbox();
const app = express();
app.use(bodyParser.json({ limit: '1mb' }));

describe('Space Missions Controller', () => {

  afterEach(() => {
    sandbox.restore();
  });

  describe('GET /space-missions', () => {
    it('should retrieve space missions', async () => {
      const req = { body: {} } as Request;
      const res = {
        status: sinon.stub().returnsThis(),
        send: sinon.stub(),
      } as unknown as Response;

      const serviceStub = sandbox.stub(spaceMissionsService, 'getSpaceMissions').resolves([]);

      await getSpaceMission(req, res);

      expect(serviceStub.calledOnce).to.be.true;
      expect(res.status(200)).to.be.false;
      expect(res.send({ result: [] })).to.be.true;
    });

    it('should handle errors when retrieving space missions', async () => {
      const req = { body: {} } as Request;
      const res = {
        status: sinon.stub().returnsThis(),
        send: sinon.stub(),
      } as unknown as Response;

      const error = new Error('Test error');
      sandbox.stub(spaceMissionsService, 'getSpaceMissions').throws(error);
      sandbox.stub(log4js.getLogger(), 'error');

      await getSpaceMission(req, res);

      expect(res.status(500)).to.be.true;
      expect(res.send({ message: error.message })).to.be.true;
    });
  });

  describe('POST /space-missions', () => {
    it('should create a space mission', async () => {
      const req = { body: { name: 'Test Mission' } } as Request;
      const res = {
        status: sinon.stub().returnsThis(),
        send: sinon.stub(),
      } as unknown as Response;

      const id = '12345';
      const serviceStub = sandbox.stub(spaceMissionsService, 'createSpaceMission').resolves(id);

      await saveSpaceMission(req, res);

      expect(serviceStub.calledOnce).to.be.true;
      expect(res.status(201)).to.be.true;
      expect(res.send({ id })).to.be.true;
    });

    it('should handle errors when creating a space mission', async () => {
      const req = { body: { name: 'Test Mission' } } as Request;
      const res = {
        status: sinon.stub().returnsThis(),
        send: sinon.stub(),
      } as unknown as Response;

      const error = new Error('Test error');
      sandbox.stub(spaceMissionsService, 'createSpaceMission').throws(error);
      sandbox.stub(log4js.getLogger(), 'error');

      await saveSpaceMission(req, res);

      expect(res.status(500)).to.be.true;
      expect(res.send({ message: error.message })).to.be.true;
    });
  });

  describe('POST /counts', () => {
    it('should count space missions', async () => {
      const req = { body: { planetIdArr: ['planet1'] } } as Request;
      const res = {
        status: sinon.stub().returnsThis(),
        send: sinon.stub(),
      } as unknown as Response;

      const countsResult = { planet1: 5 };
      const serviceStub = sandbox.stub(spaceMissionsService, 'countsSpaceMissions').resolves(countsResult);

      await counts(req, res);

      expect(serviceStub.calledOnce).to.be.true;
      expect(res.send({ result: countsResult })).to.be.true;
    });

    it('should handle errors when counting space missions', async () => {
      const req = { body: { planetIdArr: ['planet1'] } } as Request;
      const res = {
        status: sinon.stub().returnsThis(),
        send: sinon.stub(),
      } as unknown as Response;

      const error = new Error('Test error');
      sandbox.stub(spaceMissionsService, 'countsSpaceMissions').throws(error);
      sandbox.stub(log4js.getLogger(), 'error');

      await counts(req, res);

      expect(res.status(500)).to.be.true;
      expect(res.send({ message: error.message })).to.be.true;
    });
  });
});
