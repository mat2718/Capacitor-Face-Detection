/* eslint-disable @typescript-eslint/no-unused-vars */
import { CapacitorException, ExceptionCode, WebPlugin } from '@capacitor/core';

import type {
  FaceDetectionPlugin,
  FaceDetectionResults,
  IsSupportedResult,
  IsTorchAvailableResult,
  IsTorchEnabledResult,
  PermissionStatus,
  StartActiveScanOptions,
  readFaceFromImageOptions,
} from './definitions';

export class FaceDetectionWeb extends WebPlugin implements FaceDetectionPlugin {
  async startActiveScan(_options: StartActiveScanOptions): Promise<void> {
    throw this.createUnavailableException();
  }

  async stopScan(): Promise<void> {
    throw this.createUnavailableException();
  }

  async readFaceFromImage(
    _options: readFaceFromImageOptions,
  ): Promise<FaceDetectionResults> {
    throw this.createUnavailableException();
  }

  async isSupported(): Promise<IsSupportedResult> {
    throw this.createUnavailableException();
  }

  async enableTorch(): Promise<void> {
    throw this.createUnavailableException();
  }

  async disableTorch(): Promise<void> {
    throw this.createUnavailableException();
  }

  async toggleTorch(): Promise<void> {
    throw this.createUnavailableException();
  }

  async isTorchEnabled(): Promise<IsTorchEnabledResult> {
    throw this.createUnavailableException();
  }

  async isTorchAvailable(): Promise<IsTorchAvailableResult> {
    throw this.createUnavailableException();
  }

  async openSettings(): Promise<void> {
    throw this.createUnavailableException();
  }

  async checkPermissions(): Promise<PermissionStatus> {
    throw this.createUnavailableException();
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.createUnavailableException();
  }

  private createUnavailableException(): CapacitorException {
    return new CapacitorException(
      'This Face Detection plugin method is not available on this platform.',
      ExceptionCode.Unavailable,
    );
  }
}
