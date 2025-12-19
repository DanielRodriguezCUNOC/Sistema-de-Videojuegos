export interface CrearEmpresaDto {
  nombreEmpresa: string;
  descripcion: string;
  estadoComentario: boolean;
  correoUsuario: string;
  nombreCompleto: string;
  password: string;
  fechaNacimiento: Date;
  numeroTelefonico: string;
  pais: string;
  avatar: File | null;
}
