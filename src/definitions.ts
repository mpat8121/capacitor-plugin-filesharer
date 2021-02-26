export interface FileSharerPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  share(opts: {}): Promise<any>;
}
