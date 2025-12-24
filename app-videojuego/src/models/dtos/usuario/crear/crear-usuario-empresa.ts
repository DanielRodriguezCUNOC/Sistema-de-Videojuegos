export interface CrearUsuarioEmpresaDTO {
  correoUsuario: string;
  nombreCompleto: string;
  password: string;
  fechaNacimiento: Date;
  numeroTelefonico: string;
  pais: string;
  avatar: File | null;
}
