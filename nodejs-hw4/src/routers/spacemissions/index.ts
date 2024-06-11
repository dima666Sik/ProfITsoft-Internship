import express from 'express';
import {
  getSpaceMission,
  saveSpaceMission,
  counts,
} from 'src/controllers/spacemissions';

const router = express.Router();

router.get('', getSpaceMission);
router.post('', saveSpaceMission);
router.post('/_counts', counts);

export default router;
