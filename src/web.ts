import { WebPlugin } from '@capacitor/core';

import type { FileShareResult, FileSharerPlugin } from './definitions';

export class FileSharerWeb extends WebPlugin implements FileSharerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async share(): Promise<FileShareResult> {
    throw this.unavailable('FileSharer API not available in this browser.');
  }

  async shareMultiple(): Promise<FileShareResult> {
    throw this.unavailable('FileSharer API not available in this browser.');
  }

}
