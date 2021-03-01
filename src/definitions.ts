/**
 * @hidden
 */
export interface FileShareOptions {
  /** 
   * Filename 
   * */
  filename: string;
  /** 
   * base64Data string
   *  */
  base64Data: string;
  /** 
   * Content type using FileShareContentType of string for advanced types
   *  */
  contentType: string;
}
/**
 * @hidden
 */
export interface FileShareSingleOptions extends FileShareOptions {
  /** 
   * Title of the share window popup
   *  */
  header: string;
}
/**
 * @hidden
 */
export interface FileShareMultiOptions {
  /** 
   * Title of the share window popup
   *  */
  header: string;
  /** 
   * Array of files for uploading
   */
  files: FileShareOptions[]
}
/**
 * @hidden
 */
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

/**
 * @name FileSharePlugin
 * @description
 * A Capacitor 3 plugin that allows sharing of base64 data string via native
 * share action cards on ios and android devices
 * 
 * @interfaces
 * FileShareMultiOptions
 * FileShareContentType
 */
export interface FileSharerPlugin {
  /**
   * Open share activity card with an attached base64Data
   * @param {FileShareSingleOptions} opts 
   * @since 0.0.1
   */
  share(opts: FileShareSingleOptions): Promise<any>;
  /**
   * Open share activity with multiple base64Data strings
   * @param {FileShareMultiOptions} opts 
   * @since 0.0.1
   */
  shareMultiple(opts: FileShareMultiOptions): Promise<any>;
}