export interface FileSharerPlugin {
  /**
   * Open share activity card with an attached base64Data
   * @param {FileShareOptions} opts 
   * @since 0.0.1
   */
  share(opts: FileShareOptions): Promise<void>;

  /**
   * 
   */
  shareMultiple(opts: any): Promise<void>;
}

export interface FileShareOptions {
  filename: string;
  base64Data: string;
  contentType: FileShareContentType | string;
}

export enum FileShareContentType {
  TEXT = 'text/*',
  TEXT_PLAIN = 'text/plain',
  TEXT_RTF = 'text/rtf',
  TEXT_HTML = 'text/html',
  TEXT_JSON = 'text/json',
  IMAGE = 'image/*',
  IMAGE_JPG = 'image/jpg',
  IMAGE_PNG = 'image/png',
  IMAGE_GIF = 'image/gif',
  PDF = 'application/pdf'
}