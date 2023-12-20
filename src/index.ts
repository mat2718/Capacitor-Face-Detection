import { registerPlugin } from '@capacitor/core';

import type { FaceDetectionPlugin } from './definitions';

const FaceDetection = registerPlugin<FaceDetectionPlugin>(
  'FaceDetectionPlugin',
  {
    web: () => import('./web').then(m => new m.FaceDetectionWeb()),
  },
);

export * from './definitions';
export { FaceDetection };
