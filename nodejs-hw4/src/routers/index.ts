import express from 'express';
import ping from 'src/controllers/ping';
import spaceMissions from './spacemissions';

const router = express.Router();

router.get('/ping', ping);

router.use('/space-missions', spaceMissions);

export default router;
