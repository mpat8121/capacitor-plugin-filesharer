import { WebPlugin } from '@capacitor/core';

import type { FileSharerPlugin } from './definitions';

export class FileSharerWeb extends WebPlugin implements FileSharerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async share() {
    throw this.unavailable('FileSharer API not available in this browser.');
  }

  async shareMultiple() {
    throw this.unavailable('FileSharer API not available in this browser.');
  }

  async shareMultiple() {
    throw this.unavailable('FielSharer API not available in this browser.');
  }
}
