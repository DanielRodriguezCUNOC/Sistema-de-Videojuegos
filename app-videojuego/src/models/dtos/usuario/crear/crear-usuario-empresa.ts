export interface CrearUsuarioEmpresaDTO {
  idUsuarioCreador: number;
  correoUsuario: string;
  nombreCompleto: string;
  password: string;
  fechaNacimiento: Date;
  numeroTelefonico: string;
  pais: string;
  avatar: File | null;
}
