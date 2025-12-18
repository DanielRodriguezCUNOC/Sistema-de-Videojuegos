export interface CrearUsuarioGamerDTO {
  correoUsuario: string;
  nickname: string;
  password: string;
  fechaNacimiento: Date;
  numeroTelefonico: string;
  pais: string;
  avatar: File | null;
}
