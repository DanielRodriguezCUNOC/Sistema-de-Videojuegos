export interface CrearUsuarioEmpresaDTO {
  correoUsuario: string;
  nombreCompleto: string;
  idEmpresa: number;
  password: string;
  fechaNacimiento: Date;
  numeroTelefonico: string;
  pais: string;
  avatar: File | null;
}
