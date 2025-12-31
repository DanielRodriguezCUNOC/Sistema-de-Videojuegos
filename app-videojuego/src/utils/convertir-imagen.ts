export class ConvertirImagen {
  /*
   * Crea una data URL detectando automáticamente si la imagen es PNG o JPEG
   */
  public createImageDataUrl(base64Data: string): string {
    const imageType = this.detectImageType(base64Data);
    return `data:image/${imageType};base64,${base64Data}`;
  }

  /*
   * Detecta el tipo de imagen (png o jpeg) basado en el MIME
   */
  private detectImageType(base64Data: string): string {
    if (base64Data.startsWith('/9j/') || base64Data.startsWith('/9k/')) {
      return 'jpeg';
    } else if (base64Data.startsWith('iVBORw0KGgo')) {
      return 'png';
    }
    return 'png';
  }
}
