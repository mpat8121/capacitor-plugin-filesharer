export interface FileSharerPlugin {
  share(opts: FileShareOptions): Promise<void>;
  shareMultiple(opts: {}): Promise<void>
}

export interface FileShareOptions {
  filename: string;
  base64Data: string;
  contentType: string;
}
