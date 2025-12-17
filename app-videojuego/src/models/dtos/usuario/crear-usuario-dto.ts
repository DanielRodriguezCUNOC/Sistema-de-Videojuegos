export interface CrearUsuarioDTO {
  correo_usuario: string;
  nickname: string;
  password: string;
  fecha_nacimiento: Date;
  numero_telefonico: string;
  pais: string;
  avatar: File | null;
}
