export interface FileSharerPlugin {
  share(opts: FileShareOptions): Promise<void>;

  shareMultiple(opts: FileShareMultiOptions): Promise<void>;
}

export interface FileShareOptions {
  filename: string;
  base64Data: string;
  contentType: string;
  header?: string;
}

export interface FileShareMultiOptions {
  header: string;
  files: FileShareOptions[]
}
