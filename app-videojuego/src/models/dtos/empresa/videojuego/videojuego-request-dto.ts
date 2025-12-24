export interface VideojuegoRequestDto {
  idUsuarioEmpresa: string;
  titulo: string;
  descripcion: string;
  fechaLanzamiento: Date;
  precio: number;
  recurosMinimos: string;
  imagen: File;
  clasificacion: string;
  categorias: string[];
}
