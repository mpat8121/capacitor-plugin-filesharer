import { WebPlugin } from '@capacitor/core';

import type { FileSharerPlugin } from './definitions';

export class FileSharerWeb extends WebPlugin implements FileSharerPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
